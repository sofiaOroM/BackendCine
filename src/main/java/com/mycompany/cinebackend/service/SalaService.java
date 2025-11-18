/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Sala;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author sofia
 */
public class SalaService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");

    public List<Sala> listar() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Sala s", Sala.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Sala> obtenerPorCine(int cineId) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery(
                "SELECT s FROM Sala s WHERE s.cine.id = :cineId", Sala.class
        ).setParameter("cineId", cineId)
                .getResultList();
    }

    public Sala obtener(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Sala.class, id);
        } finally {
            em.close();
        }
    }

    public Sala crear(Sala sala) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sala);
            em.getTransaction().commit();
            return sala;
        } finally {
            em.close();
        }
    }

    public Sala actualizar(int id, Sala nueva) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Sala existente = em.find(Sala.class, id);
            if (existente == null) {
                throw new RuntimeException("Sala no encontrada con ID: " + id);
            }
            existente.setNombre(nueva.getNombre());
            existente.setCapacidad(nueva.getCapacidad());
            em.merge(existente);
            em.getTransaction().commit();
            return existente;
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Sala sala = em.find(Sala.class, id);
            if (sala != null) {
                em.remove(sala);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
