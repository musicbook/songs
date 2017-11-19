package com.fri.musicbook;

import javax.persistence.*;
import java.util.List;
@Entity(name = "songs")
@NamedQueries(value =
        {
                @NamedQuery(name = "songs.getAll", query = "SELECT s FROM songs s"),
                @NamedQuery(name = "songs.getSongByAlbumId", query = "SELECT s from songs s where s.albumId=:albumId")
        })
public class Song {
    @Id
    @Column(name = "song_id")
    private int songId;
    @Column(name = "song_name")
    private String songName;

    @Column(name="album_id")
    private Integer albumId;

//    private List<Integer> creatorId; //




    // SETS



        // GETS
    public int getSongId(){
        return songId;
    }

    public String getSongName(){
        return songName;
    }


    public Integer getAlbumId(){
        return albumId;
    }

    /*public List<Integer> getCreatorId(){
        return creatorId;
    }*/

    // SETS
    public void setSongId(int id){
        this.songId=id;
    }

    public void setSongName(String name){
        this.songName=name;
    }

    public void setAlbumId(Integer id){
        this.albumId=id;
    }

/*
    public void setCreatorId(List<Integer> id){
        this.creatorId=id;
    }
*/

}
