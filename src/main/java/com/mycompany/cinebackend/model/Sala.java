/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author sofia
 */
@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private int capacidad;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    @JsonbTransient
    private List<Funcion> funciones;
    
    @ManyToOne
    @JoinColumn(name = "cine_id")
    private Cine cine;

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<Funcion> funciones) {
        this.funciones = funciones;
    }
    
    public Cine getCine() {
        return cine; 
    }
    public void setCine(Cine cine) {
        this.cine = cine; 
    }
    
}
