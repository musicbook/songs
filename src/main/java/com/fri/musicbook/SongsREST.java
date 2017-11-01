package com.fri.musicbook;




import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/songs")
public class SongsREST {
    @GET
    public Response getAllSongs(){
        List<Song> songs =SongsDB.getSongs();
        return Response.ok(songs).build();
    }

    @GET
    @Path("/query")
    public Response getSong(@QueryParam("id") Integer id){
        Song song=SongsDB.getSongById(id);

        // pridobivanje podatkov iz drugih MS
        List<Album> albums=getAlbums(song.getAlbumId());
        song.setAlbums(albums);




        if(song==null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok(SongsDB.getSongById(id)).build();
    }


    @POST
    public Response addNewSong(Song song){
        if  (
                   song.getId() != 0
                && song.getName()!= null && song.getName().isEmpty() != true         // ime mora obstajat
                && song.getArtistId()!= null && song.getArtistId().isEmpty()!=true   // artist mora obstajat
                && SongsDB.addSong(song)                                             // more bit zadna pri preverjanju ker doda komad v db
            )
            return Response.status(Response.Status.CREATED).entity(song).build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // http req

    private List<Album> getAlbums(List<Integer> albumIds){
        Optional<String> albumURL= Optional.of("http://192.168.99.100:1000");
        Client httpClient = ClientBuilder.newClient();
        List<Album> albums=new ArrayList<Album>();

            if (albumURL.isPresent()) {
                for (Integer albumId : albumIds) {
                    try {
                        albums.add(httpClient
                                .target(albumURL.get() + "/v1/albums/query?id=" + albumId)
                                .request().get(new GenericType<Album>() {
                                })
                        );
                    } catch (WebApplicationException | ProcessingException e) {
                        System.out.println(e);
                        throw new InternalServerErrorException(e);
                    }
                }
            } else {
                    return null;
            }
            return albums;

    }



}
