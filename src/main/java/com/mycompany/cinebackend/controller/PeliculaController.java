/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Pelicula;
import com.mycompany.cinebackend.service.PeliculaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.*;

/**
 *
 * @author sofia
 */
@Path("/peliculas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PeliculaController {

    private final PeliculaService service = new PeliculaService();

    @GET
    public List<Pelicula> listar() {
        return service.listar();
    }

    @GET
    @Path("/{id}")
    public Pelicula obtener(@PathParam("id") int id) {
        return service.obtenerPorId(id);
    }

    @POST
    public Pelicula crear(Pelicula p) {
        service.crear(p);
        return p;
    }

    @PUT
    @Path("/{id}")
    public Pelicula actualizar(@PathParam("id") int id, Pelicula p) {
        p.setId(id);
        service.actualizar(p);
        return p;
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") int id) {
        service.eliminar(id);
    }
}

