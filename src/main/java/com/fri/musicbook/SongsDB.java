package com.fri.musicbook;

import java.util.ArrayList;
import java.util.List;

public class SongsDB {
    private static List<Song> songs = new ArrayList<>();

    public static List<Song> getSongs(){
        return songs;
    }

    public static boolean isIdAlreadyIn(Integer id){
        if(getSongById(id)==null) return false;
        else return true;
    }

    public static Song getSongById(Integer id){
        for (Song song : songs){
            if(song.getId()==id) return song;
        }
        return null;
    }

    public static boolean addSong(Song song){
        if(SongsDB.isIdAlreadyIn(song.getId())==false) {
            songs.add(song);
            return true;
        }
        return false;
    }

    public static void removeSong(Integer id){
        for (Song song : songs) {
            if (song.getId() == id) {
                songs.remove(song);
                break;
            }
        }
    }


}
