package cpm.umcs.music;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlaylistTest {
    @Test
    public void testPlaylist() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }
    @Test
    public void testAddSong() {
        Playlist playlist = new Playlist();
        Song song = new Song("artist", "title", 240);
        playlist.add(song);
        assertEquals(1, playlist.size());
    }
    @Test
    public void correctlyAdded() {
        Song song = new Song("artist", "title", 240);
        Playlist playlist = new Playlist();
        playlist.add(song);
        assertEquals(song, playlist.get(0));
    }
    @Test
    public void correctlyAdded2() {
        Song song = new Song("artist", "title", 240);
        Playlist playlist = new Playlist();
        playlist.add(new Song("artist", "title", 240));
        assertEquals(song, playlist.get(0));
    }
    @Test
    public void atSecondTest() {
        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);
        Playlist playlist = new Playlist();
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);

        assertEquals(song2, playlist.atSecond(350));
        assertNull(playlist.atSecond(800));
        assertEquals(song1, playlist.atSecond(200));
        assertEquals(song3, playlist.atSecond(600));
    }
    @Test
    public void exceptionTest(){
        Playlist playlist = new Playlist();

        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);

        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);

        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {playlist.atSecond(-1);});

        assertEquals("-1 - seconds are negative", exception.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class, () -> {playlist.atSecond(1000);});
        assertEquals("Seconds grater than playlist duration 1000", exception2.getMessage());
    }
}
