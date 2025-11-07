/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Reporte;
import com.mycompany.cinebackend.service.ReporteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
/**
 *
 * @author sofia
 */    
@Path("/reportes")
@Produces(MediaType.APPLICATION_JSON)
public class ReporteController {

    private final ReporteService service = new ReporteService();

    @GET
    @Path("/ganancias")
    public Map<String, Double> reporteGanancias() {
        return service.obtenerGananciasTotales();
    }

    @GET
    @Path("/anuncios")
    public Map<String, Long> reporteAnunciosActivosInactivos() {
        return service.obtenerConteoAnuncios();
    }

    @GET
    @Path("/ganancias-anunciantes")
    public Map<String, Double> reporteGananciasPorAnunciante() {
        return service.obtenerGananciasPorAnunciante();
    }
}