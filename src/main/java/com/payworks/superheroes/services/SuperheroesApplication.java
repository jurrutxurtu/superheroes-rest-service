package com.payworks.superheroes.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class SuperheroesApplication extends Application {
   private Set<Object> singletons = new HashSet<Object>();

   public SuperheroesApplication() {
      singletons.add(new SuperheroService());
   }

   @Override
   public Set<Object> getSingletons() {
      return singletons;
   }
}