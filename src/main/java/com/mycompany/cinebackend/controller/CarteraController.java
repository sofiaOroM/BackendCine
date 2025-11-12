package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Cartera;
import com.mycompany.cinebackend.service.CarteraService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/cartera")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarteraController {

    private final CarteraService service = new CarteraService();

    @GET
    @Path("/{usuarioId}")
    public Response obtener(@PathParam("usuarioId") int usuarioId) {
        Cartera cartera = service.obtenerPorUsuario(usuarioId);
        if (cartera != null) {
            return Response.ok(cartera).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Cartera no encontrada para el usuario con ID " + usuarioId)
                .build();
    }

    @PUT
    @Path("/{id}/recargar")
    public Response recargar(@PathParam("id") int id, Map<String, Double> body) {
        double cantidad = body.getOrDefault("cantidad", 0.0);

        if (cantidad <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La cantidad debe ser mayor a 0").build();
        }

        try {
            Cartera actualizada = service.recargar(id, cantidad);
            return Response.ok(actualizada).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al recargar saldo: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/debitar")
    public void debitar(@PathParam("id") int id, @QueryParam("cantidad") double cantidad) {
        service.debitar(id, cantidad);
    }

}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Cartera;
import com.mycompany.cinebackend.service.CarteraService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
/**
 *
 * @author sofia
 */
 /*@Path("/cartera")
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
}*/
