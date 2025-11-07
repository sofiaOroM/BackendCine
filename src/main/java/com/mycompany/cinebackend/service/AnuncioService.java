/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Anuncio;
import jakarta.persistence.*;
import java.util.List;
/**
 *
 * @author sofia
 */
public class AnuncioService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");

    public void crear(Anuncio a) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();
    }

    public List<Anuncio> listar() {
        EntityManager em = emf.createEntityManager();
        List<Anuncio> lista = em.createQuery("SELECT a FROM Anuncio a", Anuncio.class).getResultList();
        em.close();
        return lista;
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Anuncio a = em.find(Anuncio.class, id);
        if (a != null) em.remove(a);
        em.getTransaction().commit();
        em.close();
    }

    public void cambiarEstado(int id, boolean activo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Anuncio a = em.find(Anuncio.class, id);
        if (a != null) a.setActivo(activo);
        em.getTransaction().commit();
        em.close();
    }
}