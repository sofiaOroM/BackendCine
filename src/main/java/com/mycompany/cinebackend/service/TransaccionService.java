/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Transaccion;
import jakarta.inject.Singleton;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
/**
 *
 * @author sofia
 */
@Singleton
public class TransaccionService {

    private final EntityManagerFactory emf;

    public TransaccionService() {
        this.emf = Persistence.createEntityManagerFactory("cinePU");
    }

    public Transaccion crearTransaccion(Transaccion t) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(t);
            tx.commit();
            return t;
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<Object[]> resumenGananciasPorCine(Date desde, Date hasta) {
        EntityManager em = emf.createEntityManager();
        try {
            String sql
                    = "SELECT c.id, c.nombre, "
                    + " COALESCE(SUM(CASE WHEN tr.tipo = 'compra_anuncio' THEN tr.monto ELSE 0 END),0) as ingresos_anuncios, "
                    + " COALESCE(SUM(CASE WHEN tr.tipo = 'bloqueo_anuncio' THEN tr.monto ELSE 0 END),0) as ingresos_bloqueos, "
                    + " COALESCE(SUM(CASE WHEN tr.tipo = 'recarga' THEN tr.monto ELSE 0 END),0) as recargas "
                    + "FROM transaccion tr "
                    + "LEFT JOIN cines c ON tr.cine_id = c.id "
                    + ((desde != null && hasta != null) ? " WHERE tr.fecha BETWEEN :desde AND :hasta " : "")
                    + "GROUP BY c.id, c.nombre";

            Query q = em.createNativeQuery(sql);
            if (desde != null && hasta != null) {
                q.setParameter("desde", desde);
                q.setParameter("hasta", hasta);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
