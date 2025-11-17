/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.ComentarioPelicula;
import jakarta.inject.Singleton;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author sofia
 */
@Singleton
public class ComentarioPeliculaService {

    private final EntityManagerFactory emf;

    public ComentarioPeliculaService() {
        this.emf = Persistence.createEntityManagerFactory("cinePU");
    }

    public void crearComentario(ComentarioPelicula comentario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            var pelicula = em.find(com.mycompany.cinebackend.model.Pelicula.class,
                    comentario.getPelicula().getId());
            comentario.setPelicula(pelicula);

            var usuario = em.find(com.mycompany.cinebackend.model.Usuario.class,
                    comentario.getUsuario().getId());
            comentario.setUsuario(usuario);

            em.persist(comentario);

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<ComentarioPelicula> listarComentariosPorPelicula(int idPelicula) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM ComentarioPelicula c WHERE c.pelicula.id = :id",
                    ComentarioPelicula.class
            ).setParameter("id", idPelicula).getResultList();
        } finally {
            em.close();
        }
    }

    public double obtenerPromedioPorPelicula(int idPelicula) {
        EntityManager em = emf.createEntityManager();
        Double avg = em.createQuery(
                "SELECT AVG(c.calificacion) FROM ComentarioPelicula c WHERE c.pelicula.id = :id",
                Double.class
        ).setParameter("id", idPelicula)
                .getSingleResult();

        return avg != null ? avg : 0;
    }

    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
