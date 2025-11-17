/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.repository;

import com.mycompany.cinebackend.model.ComentarioSala;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author sofia
 */
@Stateless
public class ComentarioSalaRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(ComentarioSala c) {
        em.persist(c);
    }

    public List<ComentarioSala> findBySala(Long idSala) {
        return em.createQuery("SELECT c FROM ComentarioSala c WHERE c.sala.id = :id", ComentarioSala.class)
                 .setParameter("id", idSala)
                 .getResultList();
    }

    public double promedioCalificacion(Long idSala) {
        Double promedio = em.createQuery("SELECT AVG(c.calificacion) FROM ComentarioSala c WHERE c.sala.id = :id", Double.class)
                            .setParameter("id", idSala)
                            .getSingleResult();
        return promedio != null ? promedio : 0;
    }
}
