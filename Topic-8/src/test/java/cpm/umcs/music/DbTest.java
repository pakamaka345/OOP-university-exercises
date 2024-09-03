package cpm.umcs.music;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DbTest {
    @BeforeAll
    public static void openDB(){
        cpm.umcs.database.DatabaseConnection.connect("songs.db", "connection");
    }
    @Test
    public void songRead(){
        try {
            Optional<Song> song = Song.Persistance.read(1);
            assertTrue(song.isPresent());
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }

    }
    @Test
    public void correctIndexRead(){
        Optional<Song> song = Optional.of(new Song("The Rolling Stones", "(I Can't Get No) Satisfaction", 224));
        try{
            Optional<Song> songOptional = Song.Persistance.read(2);
            assertEquals(song, songOptional);
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }
    @ParameterizedTest
    @MethodSource("provideIndexAndExpectedSong")
    public void testReadParameterized(int index, Song expectedSong) throws SQLException{
        Optional<Song> song = Song.Persistance.read(index);
        assertEquals(expectedSong, song.orElse(null));
    }
    static Stream<Arguments> provideIndexAndExpectedSong(){
        return Stream.of(
                arguments(3, new Song("Led Zeppelin", "Stairway to Heaven", 482)),
                arguments(47, new Song("The Doors", "Riders on the Storm", 434)),
                arguments(-1, null),
                arguments(100, null)
        );
    }
    @ParameterizedTest
    @CsvFileSource(resources = "songs.csv", numLinesToSkip = 1)
    public void testReadFromCSV(int index, String artist, String title, int duration) throws SQLException{
        Song expectedSong = new Song(artist, title, duration);
        Optional<Song> song = Song.Persistance.read(index);
        assertEquals(expectedSong, song.orElse(null));
    }



    @AfterAll
    public static void closeDB(){
        cpm.umcs.database.DatabaseConnection.disconnect("connection");
    }
}
