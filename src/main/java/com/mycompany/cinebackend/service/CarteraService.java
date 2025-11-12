
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Cartera;
import jakarta.persistence.*;

public class CarteraService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");

    public Cartera obtenerPorUsuario(int usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Cartera> q = em.createQuery(
                "SELECT c FROM Cartera c WHERE c.usuario.id = :usuarioId", Cartera.class);
            q.setParameter("usuarioId", usuarioId);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Cartera recargar(int id, double cantidad) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Cartera c = em.find(Cartera.class, id);
        if (c == null) {
            em.getTransaction().rollback();
            em.close();
            throw new RuntimeException("Cartera no encontrada con ID " + id);
        }

        c.setSaldo(c.getSaldo() + cantidad);
        em.merge(c);
        em.getTransaction().commit();
        em.close();
        return c;
    }
    
        public Cartera debitar(int id, double cantidad) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Cartera c = em.find(Cartera.class, id);
        if (c == null) {
            em.getTransaction().rollback();
            em.close();
            throw new RuntimeException("Cartera no encontrada con ID " + id);
        }

        c.setSaldo(c.getSaldo() - cantidad);
        em.merge(c);
        em.getTransaction().commit();
        em.close();

        return c;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Cartera;
import jakarta.persistence.*;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author sofia
 */
/*public class CarteraService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");

    public Cartera obtenerPorUsuario(int usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cartera c WHERE c.usuario.id = :id", Cartera.class)
                    .setParameter("id", usuarioId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void recargar(int usuarioId, double cantidad) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Cartera c = obtenerPorUsuario(usuarioId);
        if (c == null) {
            c = new Cartera();
            c.setSaldo(cantidad);
        } else {
            c.setSaldo(c.getSaldo() + cantidad);
        }
        em.merge(c);
        em.getTransaction().commit();
        em.close();
    }
}
*/