/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.ComentarioSala;
import com.mycompany.cinebackend.service.ComentarioSalaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

/**
 *
 * @author sofia
 */
@Path("/comentarios/sala")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComentarioSalaController {

    private final ComentarioSalaService comentarioService = new ComentarioSalaService();

    @POST
    @Path("/crear")
    public Response crearComentario(ComentarioSala comentario) {
        try {
            comentarioService.crearComentario(comentario);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{idSala}")
    public Response obtenerComentarios(@PathParam("idSala") int idSala) {
        return Response.ok(
                comentarioService.listarComentariosPorSala(idSala)
        ).build();
    }

    @GET
    @Path("/{idSala}/promedio")
    public Response obtenerPromedio(@PathParam("idSala") int idSala) {
        double promedio = comentarioService.obtenerPromedioPorSala(idSala);
        return Response.ok(promedio).build();
    }
}
