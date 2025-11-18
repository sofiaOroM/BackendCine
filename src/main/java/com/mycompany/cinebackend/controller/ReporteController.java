package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.service.ReporteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import net.sf.jasperreports.engine.JRException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/reportes")
@Produces(MediaType.APPLICATION_OCTET_STREAM)
public class ReporteController {

    private final ReporteService reporteService = new ReporteService();

    @GET
    @Path("/ganancias/pdf")
    public Response generarGananciasPdf(@QueryParam("desde") String desdeStr, @QueryParam("hasta") String hastaStr) {
        try {
            Date desde = null;
            Date hasta = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (desdeStr != null && !desdeStr.isEmpty()) desde = sdf.parse(desdeStr);
            if (hastaStr != null && !hastaStr.isEmpty()) hasta = sdf.parse(hastaStr);

            byte[] pdf = reporteService.generarReporteGanancias(desde, hasta);

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=report_ganancias.pdf")
                    .type("application/pdf")
                    .build();

        } catch (JRException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error generando reporte JR: " + e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: " + e.getMessage()).build();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Reporte;
import com.mycompany.cinebackend.service.ReporteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import static org.glassfish.jersey.internal.inject.Bindings.service;

/**
 *
 * @author sofia
 */
/*@Path("/reportes")
@Produces(MediaType.APPLICATION_JSON)
public class ReporteController {

    @Inject
    private ReporteService reporteService;

    @GET
    @Path("/comentarios/pelicula/{id}")
    @Produces("application/pdf")
    public Response generarReporte(@PathParam("id") int id) {
        try {
            byte[] pdf = reporteService.generarReporteComentariosPelicula(id);

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=comentarios_pelicula.pdf")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    /*@GET
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
}*/
