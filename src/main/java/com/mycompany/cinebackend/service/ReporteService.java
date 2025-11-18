package com.mycompany.cinebackend.service;

import java.io.InputStream;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.*;

public class ReporteService {
    
    // Método general para generar PDF desde JRXML en resources
    private byte[] generarPdf(String jrxmlFile, List<Map<String, Object>> data) throws JRException {

        // Se carga el JRXML desde el classpath
        InputStream reportStream = getClass().getResourceAsStream("/reportes/" + jrxmlFile);
        if (reportStream == null) {
            throw new JRException("No se encontró el archivo de reporte: " + jrxmlFile);
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        System.out.println(data);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    // Reporte Comentarios
    public byte[] reporteComentarios(FiltroReporte filtro) throws JRException {
        List<Map<String, Object>> data = generarDatosComentarios(filtro);
        return generarPdf("comentarios_sala.jrxml", data);
    }

    // Reporte Películas
    public byte[] reportePeliculas(FiltroReporte filtro) throws JRException {
        List<Map<String, Object>> data = generarDatosPeliculas(filtro);
        return generarPdf("peliculas_sala.jrxml", data);
    }

    // Reporte Top Salas
    public byte[] reporteTopSalas(FiltroReporte filtro) throws JRException {
        List<Map<String, Object>> data = generarDatosTopSalas(filtro);
        return generarPdf("top_salas.jrxml", data);
    }

    // Reporte Boletos
    public byte[] reporteBoletos(FiltroReporte filtro) throws JRException {
        List<Map<String, Object>> data = generarDatosBoletos(filtro);
        return generarPdf("boletos_vendidos.jrxml", data);
    }
    
    // Datos simulados para probar 
    private List<Map<String, Object>> generarDatosComentarios(FiltroReporte filtro) {
        List<Map<String, Object>> lista = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("usuario", "Cliente Demo");
        row1.put("sala", "Sala 1");
        row1.put("comentario", "Muy buena experiencia");
        row1.put("calificacion", 4.5);
        row1.put("fecha", "2025-11-18");
        lista.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("usuario", "Usuario Prueba");
        row2.put("sala", "Sala 2");
        row2.put("comentario", "Regular");
        row2.put("calificacion", 3.0);
        row2.put("fecha", "2025-11-17");
        lista.add(row2);

        return lista;
    }

    private List<Map<String, Object>> generarDatosPeliculas(FiltroReporte filtro) {
        List<Map<String, Object>> lista = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("sala", "Sala 1");
        row1.put("pelicula", "Avengers");
        row1.put("fecha", "2025-11-18");
        lista.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("sala", "Sala 2");
        row2.put("pelicula", "Matrix");
        row2.put("fecha", "2025-11-18");
        lista.add(row2);

        return lista;
    }

    private List<Map<String, Object>> generarDatosTopSalas(FiltroReporte filtro) {
        List<Map<String, Object>> lista = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("sala", "Sala 1");
        row1.put("promedioCalificacion", 4.7);
        row1.put("calificaciones", Arrays.asList(5, 4, 5, 5, 4));
        lista.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("sala", "Sala 2");
        row2.put("promedioCalificacion", 4.3);
        row2.put("calificaciones", Arrays.asList(4, 4, 5, 4, 4));
        lista.add(row2);

        return lista;
    }

    private List<Map<String, Object>> generarDatosBoletos(FiltroReporte filtro) {
        List<Map<String, Object>> lista = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("sala", "Sala 1");
        row1.put("usuario", "Cliente Demo");
        row1.put("boleto", "B001");
        row1.put("precio", 50.0);
        lista.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("sala", "Sala 1");
        row2.put("usuario", "Usuario Prueba");
        row2.put("boleto", "B002");
        row2.put("precio", 50.0);
        lista.add(row2);

        return lista;
    }
}
