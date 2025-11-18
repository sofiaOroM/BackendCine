/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Anuncio;
import com.mycompany.cinebackend.model.Cartera;
import com.mycompany.cinebackend.model.Transaccion;
import com.mycompany.cinebackend.model.Transaccion.TipoTransaccion;
import com.mycompany.cinebackend.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sofia
 */
public class AnuncioService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");
    private final TransaccionService transaccionService = new TransaccionService();

    public void crear(Anuncio anuncio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Usuario usuario = em.find(Usuario.class, anuncio.getOwnerId());
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado con id=" + anuncio.getOwnerId());
            }
            anuncio.setUsuario(usuario);

            Cartera cartera = em.find(Cartera.class, usuario.getId());
            if (cartera == null) {
                throw new RuntimeException("Cartera no encontrada para usuario id=" + usuario.getId());
            }

            if (cartera.getSaldo() < anuncio.getCosto()) {
                throw new RuntimeException("Saldo insuficiente para crear el anuncio");
            }

            cartera.setSaldo(cartera.getSaldo() - anuncio.getCosto());
            em.merge(cartera);

            if (anuncio.getPeriodo() != null && !anuncio.getPeriodo().isEmpty()) {
                anuncio.setDias(Integer.parseInt(anuncio.getPeriodo().replaceAll("[^0-9]", "")));
            }
            if (anuncio.getFechaInicio() == null) {
                anuncio.setFechaInicio(OffsetDateTime.now());
            }
            if (anuncio.getFechaFin() == null && anuncio.getDias() > 0) {
                anuncio.setFechaFin(anuncio.getFechaInicio().plusDays(anuncio.getDias()));
            }

            em.persist(anuncio);

            Transaccion t = new Transaccion();
            t.setDescripcion("Compra de anuncio: " + anuncio.getTitulo());
            t.setFecha(new Date());
            t.setMonto(BigDecimal.valueOf(anuncio.getCosto()));
            t.setTipo(TipoTransaccion.compra_anuncio);
            t.setUsuarioId(usuario.getId());
            t.setCine(usuario.getCine());
            transaccionService.crearTransaccion(t);

            em.getTransaction().commit();

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<Anuncio> listar() {
        EntityManager em = emf.createEntityManager();
        List<Anuncio> lista = em.createQuery("SELECT a FROM Anuncio a", Anuncio.class).getResultList();
        em.close();
        return lista;
    }

    public List<Anuncio> listarAnunciosPorUsuario(int usuarioId) {
        EntityManager em = emf.createEntityManager();
        List<Anuncio> lista = em.createQuery(
                "SELECT a FROM Anuncio a WHERE a.usuario.id = :uid", Anuncio.class)
                .setParameter("uid", usuarioId)
                .getResultList();
        em.close();
        return lista;
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Anuncio a = em.find(Anuncio.class, id);
        if (a != null) {
            em.remove(a);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void cambiarEstado(int id, boolean activo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Anuncio a = em.find(Anuncio.class, id);
        if (a != null) {
            a.setActivo(activo);
            em.merge(a);
        }
        em.getTransaction().commit();
        em.close();
    }
}
