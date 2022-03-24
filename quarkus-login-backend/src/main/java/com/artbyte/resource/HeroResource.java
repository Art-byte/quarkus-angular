package com.artbyte.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.artbyte.model.Hero;
import com.artbyte.service.HeroeService;
import com.artbyte.utils.AuthoritiesConstants;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;


@RequestScoped
@Path("/api/heroes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HeroResource {

    private static final Logger LOGGER = Logger.getLogger(HeroResource.class);

    @Inject
    JsonWebToken jwt;

    @Inject
    HeroeService heroeService;

	@GET
	@RolesAllowed({AuthoritiesConstants.USER})
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hero> getHeroes(@Context SecurityContext ctx) {
		LOGGER.info(">> Is Secure: "+ctx.isSecure());
		LOGGER.info(">> JWT Name: "+jwt.getName());
		LOGGER.info(">> Context Name: "+ctx.getUserPrincipal().getName());
		
		return heroeService.findAllHeroes();
	}

	@GET
	@Path("/name={term}")
	public List<Hero> searchHeroes(@PathParam(value = "term") String term) {
		return heroeService.findHeroByMatchingName(term);
	}

	@GET
	@Path("/{id}")
	public Hero getHero(@PathParam(value = "id") Long id) {
		return heroeService.findHeroById(id);
	}

	@POST
	public Hero addHero(Hero hero) {
		return heroeService.addHero(hero);
	}

	@DELETE
	@Path("/{id}")
	public Hero deleteHero(@PathParam("id") Long id) {
		return heroeService.deleteHeroById(id);
	}

	@PUT
	public Hero updateHero(Hero hero) {
		return heroeService.updateHero(hero);
	}
}
