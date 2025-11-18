package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.service.ReporteService;
import com.mycompany.cinebackend.service.FiltroReporte;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import net.sf.jasperreports.engine.JRException;

import java.time.LocalDate;

@Path("/reportes")
public class ReporteController {

    private final ReporteService reporteService = new ReporteService();

    private Response generarPdf(FiltroReporte filtro, String tipoReporte) {
        try {
            byte[] pdfBytes;

            switch (tipoReporte) {
                case "comentarios":
                    pdfBytes = reporteService.reporteComentarios(filtro);
                    break;
                case "peliculas":
                    pdfBytes = reporteService.reportePeliculas(filtro);
                    break;
                case "top-salas":
                    pdfBytes = reporteService.reporteTopSalas(filtro);
                    break;
                case "boletos":
                    pdfBytes = reporteService.reporteBoletos(filtro);
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Tipo de reporte inválido")
                            .build();
            }

            StreamingOutput fileStream = output -> {
                output.write(pdfBytes);
                output.flush();
            };

            return Response
                    .ok(fileStream, "application/pdf")
                    .header("Content-Disposition", "attachment; filename=\"" + tipoReporte + ".pdf\"")
                    .build();

        } catch (JRException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generando el reporte: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error inesperado: " + e.getMessage())
                    .build();
        }
    }

    private FiltroReporte crearFiltro(String fechaInicio, String fechaFin, Long salaId) {
        FiltroReporte filtro = new FiltroReporte();

        if (fechaInicio != null && !fechaInicio.isEmpty()) {
            filtro.setFechaInicio(LocalDate.parse(fechaInicio));
        }

        if (fechaFin != null && !fechaFin.isEmpty()) {
            filtro.setFechaFin(LocalDate.parse(fechaFin));
        }

        filtro.setSalaId(salaId); // puede ser null
        return filtro;
    }

    @GET
    @Path("/comentarios")
    @Produces("application/pdf")
    public Response reporteComentarios(
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin,
            @QueryParam("salaId") Long salaId) {

        FiltroReporte filtro = crearFiltro(fechaInicio, fechaFin, salaId);
        return generarPdf(filtro, "comentarios");
    }

    @GET
    @Path("/peliculas")
    @Produces("application/pdf")
    public Response reportePeliculas(
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin,
            @QueryParam("salaId") Long salaId) {

        FiltroReporte filtro = crearFiltro(fechaInicio, fechaFin, salaId);
        return generarPdf(filtro, "peliculas");
    }

    @GET
    @Path("/top-salas")
    @Produces("application/pdf")
    public Response reporteTopSalas(
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin,
            @QueryParam("salaId") Long salaId) {

        FiltroReporte filtro = crearFiltro(fechaInicio, fechaFin, salaId);
        return generarPdf(filtro, "top-salas");
    }

    @GET
    @Path("/boletos")
    @Produces("application/pdf")
    public Response reporteBoletos(
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin,
            @QueryParam("salaId") Long salaId) {

        FiltroReporte filtro = crearFiltro(fechaInicio, fechaFin, salaId);
        return generarPdf(filtro, "boletos");
    }
}