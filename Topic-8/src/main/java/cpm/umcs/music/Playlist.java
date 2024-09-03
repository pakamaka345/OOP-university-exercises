package cpm.umcs.music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int second){
        if (second < 0){
            throw new IndexOutOfBoundsException(second + " - seconds are negative");
        }
        int sum = 0;
        for (Song song : this){
            sum += song.getDuration();
            if (sum >= second){
                return song;
            }
        }
        throw new IndexOutOfBoundsException("Seconds grater than playlist duration " + second);
    }
}
