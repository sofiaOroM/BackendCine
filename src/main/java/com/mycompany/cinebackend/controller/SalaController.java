/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Cine;
import com.mycompany.cinebackend.model.Sala;
import com.mycompany.cinebackend.service.SalaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author sofia
 */
@Path("/salas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalaController {

    private final SalaService service = new SalaService();

    @GET
    public List<Sala> listar() {
        return service.listar();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") int id) {
        Sala sala = service.obtener(id);
        if (sala == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sala no encontrada con ID: " + id)
                    .build();
        }
        return Response.ok(sala).build();
    }

    @POST
    public Response crear(Sala sala) {
        try {
            Sala creada = service.crear(sala);
            return Response.status(Response.Status.CREATED).entity(creada).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al crear sala: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Sala sala) {
        try {
            Sala actualizada = service.actualizar(id, sala);
            return Response.ok(actualizada).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        try {
            service.eliminar(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar sala: " + e.getMessage()).build();
        }
    }
}
