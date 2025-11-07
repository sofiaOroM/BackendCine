/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Reporte;
import jakarta.persistence.*;
import java.util.*;

/**
 *
 * @author sofia
 */

public class ReporteService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinePU");

    //Total de ingresos por anuncios comprados
    public Map<String, Double> obtenerGananciasTotales() {
        EntityManager em = emf.createEntityManager();
        Double total = em.createQuery(
            "SELECT SUM(a.costo) FROM Anuncio a", Double.class)
            .getSingleResult();
        em.close();
        Map<String, Double> result = new HashMap<>();
        result.put("gananciaTotal", total != null ? total : 0.0);
        return result;
    }

    //Cantidad de anuncios activos/inactivos
    public Map<String, Long> obtenerConteoAnuncios() {
        EntityManager em = emf.createEntityManager();
        Long activos = em.createQuery(
            "SELECT COUNT(a) FROM Anuncio a WHERE a.activo = true", Long.class)
            .getSingleResult();
        Long inactivos = em.createQuery(
            "SELECT COUNT(a) FROM Anuncio a WHERE a.activo = false", Long.class)
            .getSingleResult();
        em.close();
        Map<String, Long> result = new HashMap<>();
        result.put("activos", activos);
        result.put("inactivos", inactivos);
        return result;
    }

    //Ganancias totales agrupadas por anunciante
    public Map<String, Double> obtenerGananciasPorAnunciante() {
        EntityManager em = emf.createEntityManager();
        List<Object[]> resultados = em.createQuery(
            "SELECT a.usuario.nombre, SUM(a.costo) FROM Anuncio a GROUP BY a.usuario.nombre",
            Object[].class).getResultList();
        em.close();

        Map<String, Double> mapa = new HashMap<>();
        for (Object[] fila : resultados) {
            mapa.put((String) fila[0], (Double) fila[1]);
        }
        return mapa;
    }
}