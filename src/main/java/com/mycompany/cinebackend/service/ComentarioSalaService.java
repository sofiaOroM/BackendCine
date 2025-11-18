/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.ComentarioSala;
import jakarta.inject.Singleton;
import jakarta.persistence.*;
import java.util.List;
/**
 *
 * @author sofia
 */
@Singleton
public class ComentarioSalaService {

    private final EntityManagerFactory emf;

    public ComentarioSalaService() {
        this.emf = Persistence.createEntityManagerFactory("cinePU");
    }

    public void crearComentario(ComentarioSala comentario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            var sala = em.find(com.mycompany.cinebackend.model.Sala.class,
                    comentario.getSala().getId());

            if (sala == null) {
                throw new RuntimeException("La sala no existe");
            }
            comentario.setSala(sala);

            var usuario = em.find(com.mycompany.cinebackend.model.Usuario.class,
                    comentario.getUsuario().getId());

            if (usuario == null) {
                throw new RuntimeException("El usuario no existe");
            }

            comentario.setUsuario(usuario);

            em.persist(comentario);
            tx.commit();

        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;

        } finally {
            em.close();
        }
    }

    public List<ComentarioSala> listarComentariosPorSala(int idSala) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM ComentarioSala c WHERE c.sala.id = :id ORDER BY c.fechaComentario DESC",
                    ComentarioSala.class
            ).setParameter("id", idSala).getResultList();

        } finally {
            em.close();
        }
    }

    public double obtenerPromedioPorSala(int idSala) {
        EntityManager em = emf.createEntityManager();

        try {
            Double avg = em.createQuery(
                    "SELECT AVG(c.calificacion) FROM ComentarioSala c WHERE c.sala.id = :id",
                    Double.class
            ).setParameter("id", idSala).getSingleResult();

            return (avg != null ? avg : 0);

        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf.isOpen()) emf.close();
    }
}
