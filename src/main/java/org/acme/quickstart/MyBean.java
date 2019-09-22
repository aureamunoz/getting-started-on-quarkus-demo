package org.acme.quickstart;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyBean {

    @ConfigProperty(name="greeting")
    String greeting;

    public String greeting(String name){
        return  greeting +" "+name;
    }
}
