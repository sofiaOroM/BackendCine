/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Anuncio;
import com.mycompany.cinebackend.model.Usuario;
import com.mycompany.cinebackend.service.AnuncioService;
import com.mycompany.cinebackend.service.UsuarioService;
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
    private final UsuarioService usuarioService = new UsuarioService();

    @POST
    @Path("/crear")
    public void crear(Anuncio anuncio) {
        Usuario usuario = usuarioService.buscarPorId(anuncio.getOwnerId());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        anuncio.setUsuario(usuario);
        service.crear(anuncio);
    }

    @GET
    @Path("/listar")
    public List<Anuncio> listar() {
        return service.listar();
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public List<Anuncio> obtenerAnunciosPorUsuario(@PathParam("usuarioId") int usuarioId) {
        return service.listarAnunciosPorUsuario(usuarioId);
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
