package com.fri.musicbook;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import com.fri.musicbook.Song;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

@ApplicationScoped
public class SongsBean {
    @PersistenceContext(unitName = "songs-jpa")
    private EntityManager em;

    public List<Song> getSongs(){
        Query query=em.createNamedQuery("songs.getAll",Song.class);
        return query.getResultList();
    }

    public Song getSongById(Integer id){
        Song song=em.find(Song.class,id);
        return song;
    }

    public List<Song> getSongByAlbumId(Integer albumId){
        Query query=em.createNamedQuery("songs.getSongByAlbumId",Song.class).setParameter("albumId",albumId);
        return query.getResultList();

    }

    public Song createSong(Song song){
        try {
            beginTx();
            em.persist(song);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }

        return song;
    }

    public Song putSong(Integer SongId,Song song){
        Song s=em.find(Song.class,SongId);
        if(s== null) return null;

        try {
            beginTx();
            song.setSongId(s.getSongId());
            song = em.merge(song);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }

        return song;
    }

    public boolean deleteSong(Integer songId){
        Song song=em.find(Song.class,songId);
        if(song!=null) {
            try {
                beginTx();
                em.remove(song);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
                return false;
            }
        }else return false;

        return true;
    }

    public List<Song> getSongsFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        List<Song> songs = JPAUtils.queryEntities(em, Song.class, queryParameters);

        return songs;
    }



    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
