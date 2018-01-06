package com.fri.musicbook;




import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fri.musicbook.*;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.metrics.annotation.Metered;


@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON+ ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
@Path("/songs")
@Metered(name = "SongResources")
@Log
public class SongsResource {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private SongsBean SongsBean;

    @GET
    public Response getSongsFiltered() {

        List<Song> customers;

        customers = SongsBean.getSongsFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(customers).build();
    }
    /*
    @GET
    public Response getAllSongs(){
        List<Song> songs =SongsBean.getSongs();
        return Response.ok(songs).build();
    }*/

    @GET
    @Path("/by_song_id")
    public Response getSongBySongId(@QueryParam("id") Integer id) {
        Song song = SongsBean.getSongById(id);

        // pridobivanje podatkov iz drugih MS
        //List<Album> albums=getAlbums(song.getAlbumIds());
        //song.setAlbumIds(albums);


        if (song == null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok(SongsBean.getSongById(id)).build();
    }

    @GET
    @Path("/by_album_id")
    public Response getSongByAlbumId(@QueryParam("id") Integer id) {
        List<Song> songs = SongsBean.getSongByAlbumId(id);

        // pridobivanje podatkov iz drugih MS
        //List<Album> albums=getAlbums(song.getAlbumIds());
        //song.setAlbumIds(albums);


        if (songs == null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok(songs).build();
    }


    @POST
    public Response addNewSong(Song song) {
        if (song.getSongName() != null && song.getSongName().isEmpty() != true) {
            if (song.getSongId() == 0) {
                song.setSongId(SongsBean.getSongs().size() + 1);
            }else{
                if(SongsBean.getSongById(song.getSongId())!=null){
                    return Response.status(Response.Status.CONFLICT).build();
                }
            }
            if (SongsBean.createSong(song) != null) {
                return Response.status(Response.Status.CREATED).entity(song).build();
            }
        }

        return Response.status(Response.Status.CONFLICT).build();
    }

    @DELETE
    @Path("/songId/{songId}")
    public Response deleteSong(@PathParam("songId") int id) {
        if (SongsBean.deleteSong(id)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.CONFLICT).build();

    }
}