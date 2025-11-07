package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Usuario;
import jakarta.persistence.*;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author sofia
 */
public class UsuarioService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");

    public void crear(Usuario u) {
        EntityManager em = emf.createEntityManager();
        try {
            // Validar si el correo ya existe
            TypedQuery<Usuario> q = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.correo = :c", Usuario.class);
            q.setParameter("c", u.getCorreo());
            if (!q.getResultList().isEmpty()) {
                throw new RuntimeException("El usuario ya existe");
            }

            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Usuario login(String correo, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Usuario> q = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.correo = :c AND u.password = :p", Usuario.class);
            q.setParameter("c", correo);
            q.setParameter("p", password);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Usuario actualizar(int id, Usuario updated) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Usuario u = em.find(Usuario.class, id);
        if (u != null) {
            u.setNombre(updated.getNombre() != null ? updated.getNombre() : u.getNombre());
            u.setCorreo(updated.getCorreo() != null ? updated.getCorreo() : u.getCorreo());
            u.setPassword(updated.getPassword() != null ? updated.getPassword() : u.getPassword());
            u.setRol(updated.getRol() != null ? updated.getRol() : u.getRol());
        }
        em.getTransaction().commit();
        em.close();
        return u;
    }

    public List<Usuario> listar() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> lista = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        em.close();
        return lista;
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Usuario u = em.find(Usuario.class, id);
        if (u != null) {
            em.remove(u);
        }
        em.getTransaction().commit();
        em.close();
    }
}
