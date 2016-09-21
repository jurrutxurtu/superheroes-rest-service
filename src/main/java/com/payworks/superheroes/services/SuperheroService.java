package com.payworks.superheroes.services;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.payworks.superheroes.dao.SuperheroesDAO;
import com.payworks.superheroes.dto.Superhero;

@Path("superheroes")
public class SuperheroService {

	public SuperheroService() {
	}

	@RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSuperhero(Superhero superhero)
    {
    	if (superhero == null || superhero.getName() == null)
    	{
    		throw new WebApplicationException(Response.Status.BAD_REQUEST);
    	}
        SuperheroesDAO.addSuperhero(superhero);
        System.out.println("Created superhero: " + superhero.getId());
        return Response.created(URI.create("/superheroes/" + superhero.getId())).build();

    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Superhero> getAll() {
		List<Superhero> superheroes = SuperheroesDAO.superheroes();
		if (superheroes == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return superheroes;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Superhero getSuperhero(@PathParam("id") int id) {
		Superhero superhero = SuperheroesDAO.getSuperhero(id);
		if (superhero == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return superhero;
	}

}
