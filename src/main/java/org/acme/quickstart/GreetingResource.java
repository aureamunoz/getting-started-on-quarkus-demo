package org.acme.quickstart;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "city")
    Optional<String> city;

    @Inject
    MyBean bean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return bean.greeting("World");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city() {
        return city.orElse("Barcelona");
    }
}