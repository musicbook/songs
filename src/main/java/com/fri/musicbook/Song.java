package com.fri.musicbook;






import java.util.List;

public class Song {
    private int id;
    private String name;
    private Integer genreId;
    private List<Integer> albumId;
    private List<Integer> artistId;
    private List<Integer> creatorId;


    private List<Album> albums;
    //private List<Artist> artists;
    //private List<Creator> creators;
    //private Genre genre;

    // GETS
    public List<Album> getAlbums(){
        return albums;
    }

    /*public List<Artist> getArtists(){
        return artists;
    }

    public List<Creator> getCreators(){
        return creators;
    }

    public Genre getGenre(){
        return genre;
    }*/

    // SETS
    public void setAlbums(List<Album> albums){
        this.albums = albums;
    }

    /*public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public void setCreators(List<Creator> creators){
        this.creators=creators;
    }

    public void setGenre(Genre genre){
        this.genre=genre;
    }*/




        // GETS
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getGenreId(){
        return genreId;
    }

    public List<Integer> getAlbumId(){
        return albumId;
    }

    public List<Integer> getArtistId(){
        return artistId;
    }

    public List<Integer> getCreatorId(){
        return creatorId;
    }

    // SETS
    public void setId(int id){
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setAlbumId(List<Integer> id){
        this.albumId=id;
    }

    public void setArtistId(List<Integer> id){
        this.artistId=id;
    }

    public void setGenreId(int id){
        this.genreId=id;
    }

    public void setCreatorId(List<Integer> id){
        this.creatorId=id;
    }


}
