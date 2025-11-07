/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Anuncio;
import com.mycompany.cinebackend.service.AnuncioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 * @author sofia
 */
@Path("/anuncios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnuncioController {

    private final AnuncioService service = new AnuncioService();

    @POST
    public void crear(Anuncio a) {
        service.crear(a);
    }

    @GET
    public List<Anuncio> listar() {
        return service.listar();
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") int id) {
        service.eliminar(id);
    }

    @PUT
    @Path("/{id}/estado")
    public void cambiarEstado(@PathParam("id") int id, @QueryParam("activo") boolean activo) {
        service.cambiarEstado(id, activo);
    }
}
