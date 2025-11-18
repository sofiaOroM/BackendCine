
package com.mycompany.cinebackend.service;

import jakarta.inject.Singleton;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.mycompany.cinebackend.model.Transaccion;
import java.util.*;
import java.math.BigDecimal;
import java.io.InputStream;

@Singleton
public class ReporteService {

    private final TransaccionService transaccionService;

    public ReporteService() {
        this.transaccionService = new TransaccionService();
    }

    public byte[] generarReporteGanancias(Date desde, Date hasta) throws JRException {
        List<Object[]> rows = transaccionService.resumenGananciasPorCine(desde, hasta);
rows.forEach(row -> System.out.println(Arrays.toString(row)));

        List<Map<String, Object>> data = new ArrayList<>();
        BigDecimal totalIngresos = BigDecimal.ZERO;
        BigDecimal totalCostos = BigDecimal.ZERO; // si aplicable

        for (Object[] r : rows) {
            Integer cineId = (r[0] != null) ? ((Number) r[0]).intValue() : null;
            String cineNombre = (String) r[1];
            BigDecimal ingresosAnuncios = r[2] != null ? new BigDecimal(r[2].toString()) : BigDecimal.ZERO;
            BigDecimal ingresosBloqueos = r[3] != null ? new BigDecimal(r[3].toString()) : BigDecimal.ZERO;
            BigDecimal recargas = r[4] != null ? new BigDecimal(r[4].toString()) : BigDecimal.ZERO;

            BigDecimal ingresosTotales = ingresosAnuncios.add(ingresosBloqueos).add(recargas);
            BigDecimal costos = BigDecimal.ZERO;

            Map<String, Object> m = new HashMap<>();
            m.put("cineId", cineId);
            m.put("cineNombre", cineNombre);
            m.put("ingresosAnuncios", ingresosAnuncios);
            m.put("ingresosBloqueos", ingresosBloqueos);
            m.put("recargas", recargas);
            m.put("ingresosTotales", ingresosTotales);
            m.put("costos", costos);
            m.put("ganancia", ingresosTotales.subtract(costos));

            totalIngresos = totalIngresos.add(ingresosTotales);
            totalCostos = totalCostos.add(costos);

            data.add(m);
        }

        InputStream jrxml = getClass().getResourceAsStream("/reportes/ganancias.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_TITLE", "Reporte de Ganancias");
        params.put("DESDE", desde);
        params.put("HASTA", hasta);
        params.put("TOTAL_INGRESOS", totalIngresos);
        params.put("TOTAL_COSTOS", totalCostos);
        params.put("TOTAL_GANANCIA", totalIngresos.subtract(totalCostos));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.Reporte;
import jakarta.persistence.*;
import java.io.InputStream;
import java.util.*;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.sf.jasperreports.engine.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author sofia
 */

/*public class ReporteService {
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

    public byte[] generarReporteComentariosPelicula(int peliculaId) throws Exception {
        EntityManager em = emf.createEntityManager();

        try {
            InputStream reporte = getClass().getClassLoader().getResourceAsStream(
                    "reportes/comentarios_pelicula.jasper"
            );

            if (reporte == null) {
                throw new RuntimeException("No se encontró el archivo del reporte");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("pelicula_id", peliculaId);

            JasperPrint print = JasperFillManager.fillReport(
                    reporte,
                    params,
                    em.unwrap(java.sql.Connection.class)
            );

            return JasperExportManager.exportReportToPdf(print);

        } finally {
            em.close();
        }
    }
}*/