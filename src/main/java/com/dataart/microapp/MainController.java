package com.dataart.microapp;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;

@Path("/service")
@RequestScoped
public class MainController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello @" + LocalDateTime.now();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String customizedHello(@PathParam("name") String name) {
        if (name == null) {
            return "not hello >_>";
        }
        return "hello " + name + " @ " + LocalDateTime.now();
    }

}