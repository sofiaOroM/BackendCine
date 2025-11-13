/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Funcion;
import com.mycompany.cinebackend.service.FuncionService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author sofia
 */
@Path("/funciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionController {

    private final FuncionService service = new FuncionService();

    @GET
    @Path("/listar")
    public List<Funcion> listar() {
        return service.listar();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") int id) {
        Funcion funcion = service.obtener(id);
        if (funcion == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Función no encontrada con ID: " + id)
                    .build();
        }
        return Response.ok(funcion).build();
    }

    @POST
    @Path("/registrar")
    public Response crear(Funcion funcion) {
        try {
            Funcion creada = service.crear(funcion);
            return Response.status(Response.Status.CREATED).entity(creada).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al crear función: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Funcion funcion) {
        try {
            Funcion actualizada = service.actualizar(id, funcion);
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
                    .entity("Error al eliminar función: " + e.getMessage()).build();
        }
    }
}
