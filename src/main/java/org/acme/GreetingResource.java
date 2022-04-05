package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello")
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @ConfigProperty(name="city")
    Optional<String> city;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{name}")
    public String hello(@PathParam("name") String name) {
        return greetingService.greeting(name);
    }



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/city")
    public String city(){
        return city.orElse("Barcelona");
    }
}