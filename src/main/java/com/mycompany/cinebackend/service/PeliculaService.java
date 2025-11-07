/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Pelicula;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author sofia
 */
public class PeliculaService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");

    public List<Pelicula> listar() {
        EntityManager em = emf.createEntityManager();
        List<Pelicula> lista = em.createQuery("SELECT p FROM Pelicula p", Pelicula.class).getResultList();
        em.close();
        return lista;
    }

    public Pelicula obtenerPorId(int id) {
        EntityManager em = emf.createEntityManager();
        Pelicula p = em.find(Pelicula.class, id);
        em.close();
        return p;
    }

    public void crear(Pelicula p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    public Pelicula actualizar(Pelicula p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Pelicula actualizado = em.merge(p);
        em.getTransaction().commit();
        em.close();
        return actualizado;
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Pelicula p = em.find(Pelicula.class, id);
        if (p != null) {
            em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }
}
