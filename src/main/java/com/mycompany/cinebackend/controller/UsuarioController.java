/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Usuario;
import com.mycompany.cinebackend.service.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
/**
 *
 * @author sofia
 */

 /*@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final UsuarioService service = new UsuarioService();

    @POST
    public void crear(Usuario u) {
        service.crear(u); 
    }

    @POST
    @Path("/login")
    public Usuario login(Usuario u) {
        return service.login(u.getCorreo(), u.getPassword()); 
    }

    @GET
    public List<Usuario> listar() {
        return service.listar(); 
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") int id) {
        service.eliminar(id); 
    }
}*/
package com.mycompany.cinebackend.controller;

import com.mycompany.cinebackend.model.Usuario;
import com.mycompany.cinebackend.service.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final UsuarioService service = new UsuarioService();

    @POST
    @Path("/registrar")
    public Response crear(Usuario u) {
        try {
            service.crear(u);
            return Response.status(Response.Status.CREATED).entity(u).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(Usuario u) {
        Usuario user = service.login(u.getCorreo(), u.getPassword());
        if (user == null) {
            // Usuario no encontrado o contraseña incorrecta
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensaje\":\"Correo o contraseña incorrectos\"}")
                    .build();
        }

        user.setPassword(null);
        return Response.ok(user).build();
    }

    /*@PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Usuario updated) {
        Usuario u = service.actualizar(id, updated);
        if (u != null) {
            return Response.ok(u).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
    }*/
    @PUT
    @Path("/{id}")
    public Response actualizarUsuario(@PathParam("id") int id, Usuario datos) {
        try {
            Usuario u = service.buscarPorId(id);
            if (u == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            u.setNombre(datos.getNombre());
            u.setCorreo(datos.getCorreo());
            u.setRol(datos.getRol());

            if ("anunciante".equalsIgnoreCase(datos.getRol())) {
                u.setCine(datos.getCine());
            } else {
                u.setCine(null);
            }

            Usuario actualizado = service.actualizarUsuario(u);
            return Response.ok(actualizado).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/listar")
    public List<Usuario> listar() {
        return service.listar();
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") int id) {
        service.eliminar(id);
    }

    @OPTIONS
    @Path("/{path: .*}")
    public Response options() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

}
