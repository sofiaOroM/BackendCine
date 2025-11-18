/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Cine;
import com.mycompany.cinebackend.service.CineService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author sofia
 */
@Path("/cines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CineController {

    private CineService cineService = new CineService();

    @GET
    public Response listar() {
        return Response.ok(cineService.listar()).build();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") int id) {
        Cine c = cineService.buscar(id);
        if (c == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(c).build();
    }

    @POST
    public Response crear(Cine cine) {
        try {
            Cine creado = cineService.crear(cine);
            return Response.status(Response.Status.CREATED).entity(creado).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Cine data) {
        Cine existente = cineService.buscar(id);

        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existente.setNombre(data.getNombre());
        existente.setDireccion(data.getDireccion());
        existente.setCartera(data.getCartera());

        Cine actualizado = cineService.actualizar(existente);

        return Response.ok(actualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        if (cineService.eliminar(id)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}