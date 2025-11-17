/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.service;

import com.mycompany.cinebackend.model.ComentarioSala;
import com.mycompany.cinebackend.repository.ComentarioSalaRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author sofia
 */
@Stateless
public class ComentarioSalaService {

    @Inject
    private ComentarioSalaRepository repo;

    public void crear(ComentarioSala c) {
        repo.create(c);
    }

    public List<ComentarioSala> listarPorSala(Long idSala) {
        return repo.findBySala(idSala);
    }

    public double promedioCalificacion(Long idSala) {
        return repo.promedioCalificacion(idSala);
    }
}
