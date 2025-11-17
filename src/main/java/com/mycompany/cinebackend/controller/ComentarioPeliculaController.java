/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.ComentarioPelicula;
import com.mycompany.cinebackend.service.ComentarioPeliculaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author sofia
 */
@Path("/comentarios/pelicula")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComentarioPeliculaController {

    @Inject
    private ComentarioPeliculaService comentarioService;

    @POST
    @Path("/crear")
    public Response crearComentario(ComentarioPelicula comentario) {
        comentarioService.crearComentario(comentario);
        return Response.ok().build();
    }

    @GET
    @Path("/listar/{idPelicula}")
    public List<ComentarioPelicula> obtenerComentarios(@PathParam("idPelicula") int idPelicula) {
        return comentarioService.listarComentariosPorPelicula(idPelicula);
    }

    @GET
    @Path("/promedio/{idPelicula}")
    public double obtenerPromedio(@PathParam("idPelicula") int idPelicula) {
        return comentarioService.obtenerPromedioPorPelicula(idPelicula);
    }
}