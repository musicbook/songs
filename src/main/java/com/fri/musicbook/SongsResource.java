package com.fri.musicbook;




import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fri.musicbook.*;


@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON+ ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
@Path("/songs")
public class SongsResource {

    @Inject
    private SongsBean SongsBean;

    @GET
    public Response getAllSongs(){
        List<Song> songs =SongsBean.getSongs();
        return Response.ok(songs).build();
    }

    @GET
    @Path("/by_song_id")
    public Response getSongBySongId(@QueryParam("id") Integer id){
        Song song=SongsBean.getSongById(id);

        // pridobivanje podatkov iz drugih MS
        //List<Album> albums=getAlbums(song.getAlbumIds());
        //song.setAlbumIds(albums);




        if(song==null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok(SongsBean.getSongById(id)).build();
    }

    @GET
    @Path("/by_album_id")
    public Response getSongByAlbumId(@QueryParam("id") Integer id){
        Song song=SongsBean.getSongByAlbumId(id);

        // pridobivanje podatkov iz drugih MS
        //List<Album> albums=getAlbums(song.getAlbumIds());
        //song.setAlbumIds(albums);




        if(song==null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok(SongsBean.getSongById(id)).build();
    }


    @POST
    public Response addNewSong(Song song){
        if  (
                   song.getSongId() != 0
                && song.getSongName()!= null && song.getSongName().isEmpty() != true         // ime mora obstajati
                && SongsBean.createSong(song)!=null                                             // more bit zadna pri preverjanju ker doda komad v db
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
