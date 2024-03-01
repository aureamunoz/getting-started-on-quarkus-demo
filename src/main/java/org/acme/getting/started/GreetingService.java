package org.acme.getting.started;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    @ConfigProperty(name = "greeting")
    String greeting;

    public String greeting(String name) {
        return greeting + " " + name;
    }
}
