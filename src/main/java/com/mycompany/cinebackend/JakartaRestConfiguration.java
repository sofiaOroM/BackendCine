package com.mycompany.cinebackend;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures Jakarta RESTful Web Services for the application.
 *
 * @author Juneau
 *
 * @ApplicationPath("resources") public class JakartaRestConfiguration extends
 * Application {
 *
 * }
 */
@ApplicationPath("/api")
public class JakartaRestConfiguration extends ResourceConfig {

    public JakartaRestConfiguration() {
        packages("com.mycompany.cinebackend.controller", "com.mycompany.cinebackend.service").register(MultiPartFeature.class);
        register(MultiPartFeature.class);
        register(new AppBinder());
    }
}


//        register(com.mycompany.cinebackend.config.CorsFilter.class);

        // Registrar soporte para envío de archivos (multipart/form-data)
 //  