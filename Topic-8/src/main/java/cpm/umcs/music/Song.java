package cpm.umcs.music;

import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
public class Song {
    public String artist;
    public String title;
    public int duration;

    public Song(String artist, String title, int duration) {
        this.artist = artist;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return duration == song.duration && Objects.equals(artist, song.artist) && Objects.equals(title, song.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, title, duration);
    }
    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
    public static class Persistance {
        public static Optional<Song> read(int index) throws SQLException{
            String query = "SELECT artist, title, length FROM song WHERE id = ?";
            PreparedStatement statement = cpm.umcs.database.DatabaseConnection.getConnection("connection").prepareStatement(query);
            statement.setInt(1, index);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return Optional.of(new Song(resultSet.getString("artist"), resultSet.getString("title"), resultSet.getInt("length")));
            }
            return Optional.empty();
        }
    }
}
