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

    @Inject
    MyBean bean;


    @ConfigProperty(name="city")
    Optional<String> city;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return bean.greeting("Ramon");
    }

    @GET
    @Path("/city")
    @Produces(MediaType.TEXT_PLAIN)
    public String city() {
        return city.orElse("Bilbao");
    }


}