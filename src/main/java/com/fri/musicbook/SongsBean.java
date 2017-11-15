package com.fri.musicbook;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import com.fri.musicbook.Song;

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

    public Song getSongByAlbumId(Integer albumId){
        Query query=em.createNamedQuery("songs.getSongByAlbumId",Song.class).setParameter("albumId",albumId);
        Object result= query.getSingleResult();
        if(result==null) return null;
        return (Song) result;
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
