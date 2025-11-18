/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Cine;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author sofia
 */
@Singleton
public class CineService {

    private final EntityManagerFactory emf;

    public CineService() {
        this.emf = Persistence.createEntityManagerFactory("cinePU");
    }

    public List<Cine> listar() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT c FROM Cine c", Cine.class).getResultList();
    }

    public Cine buscar(int id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Cine.class, id);
    }

    public Cine crear(Cine cine) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(cine);
            tx.commit();
            return cine;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Cine actualizar(Cine cine) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Cine actualizado = em.merge(cine);
            tx.commit();
            return actualizado;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Cine c = em.find(Cine.class, id);
            if (c == null) return false;

            tx.begin();
            em.remove(c);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
