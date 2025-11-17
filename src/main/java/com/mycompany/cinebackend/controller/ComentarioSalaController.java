/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.ComentarioSala;
import com.mycompany.cinebackend.service.ComentarioSalaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;


/**
 *
 * @author sofia
 */
@Path("/comentarios/sala")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ComentarioSalaController {

    @Inject
    private ComentarioSalaService service;

    @POST
    @Path("/crear")
    public void crear(ComentarioSala comentario) {
        service.crear(comentario);
    }

    @GET
    @Path("/{idSala}")
    public List<ComentarioSala> listarPorSala(@PathParam("idSala") Long idSala) {
        return service.listarPorSala(idSala);
    }

    @GET
    @Path("/{idSala}/promedio")
    public double promedio(@PathParam("idSala") Long idSala) {
        return service.promedioCalificacion(idSala);
    }
}
