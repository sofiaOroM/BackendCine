package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Cartera;
import com.mycompany.cinebackend.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            TypedQuery<Usuario> q = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.correo = :c", Usuario.class);
            q.setParameter("c", u.getCorreo());

            if (!q.getResultList().isEmpty()) {
                throw new RuntimeException("El usuario ya existe");
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            u.setPassword(encoder.encode(u.getPassword()));

            em.getTransaction().begin();

            Cartera cartera = new Cartera();
            cartera.setSaldo(0);
            cartera.setUsuario(u);
            u.setCartera(cartera);

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
                    "SELECT u FROM Usuario u LEFT JOIN FETCH u.cartera WHERE u.correo = :c",
                    Usuario.class
            );
            q.setParameter("c", correo);

            Usuario usuario = q.getSingleResult();

            //Validar la contraseña usando BCrypt
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (!encoder.matches(password, usuario.getPassword())) {
                return null; // contraseña incorrecta
            }

            usuario.setPassword(null);
            return usuario;

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Usuario actualizarUsuario(Usuario u) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Usuario actualizado = em.merge(u);
            tx.commit();
            return actualizado;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
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
