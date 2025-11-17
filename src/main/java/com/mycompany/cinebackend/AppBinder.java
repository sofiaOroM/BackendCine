/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinebackend;

import com.mycompany.cinebackend.service.ComentarioPeliculaService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import jakarta.inject.Singleton;

/** *
 * @author sofia
 */
public class AppBinder extends AbstractBinder {
        @Override
        protected void configure
        
            () {
        bind(ComentarioPeliculaService.class)
                    .to(ComentarioPeliculaService.class)
                    .in(Singleton.class);
        }

    }
