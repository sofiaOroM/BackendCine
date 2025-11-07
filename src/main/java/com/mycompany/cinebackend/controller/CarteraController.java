/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Cartera;
import com.mycompany.cinebackend.service.CarteraService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
/**
 *
 * @author sofia
 */
@Path("/cartera")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarteraController {

    private final CarteraService service = new CarteraService();

    @GET
    @Path("/{usuarioId}")
    public Cartera obtener(@PathParam("usuarioId") int usuarioId) {
        return service.obtenerPorUsuario(usuarioId);
    }

    @PUT
    @Path("/{usuarioId}/recargar")
    public void recargar(@PathParam("usuarioId") int usuarioId, @QueryParam("cantidad") double cantidad) {
        service.recargar(usuarioId, cantidad);
    }
}