package com.artbyte.resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.security.PermitAll;
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
import javax.ws.rs.core.MediaType;

import com.artbyte.model.AuthRequest;
import com.artbyte.model.User;
import com.artbyte.service.AccountService;
import com.artbyte.utils.AuthoritiesConstants;
import com.artbyte.utils.JwtTokenUtils;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.quarkus.elytron.security.common.BcryptUtil;

@RequestScoped
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    private static final Logger LOGGER = Logger.getLogger(AccountResource.class);

    @Inject
    AccountService accountService;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    public AccountResource() {
    }

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return accountService.findAllUsers();
    }

    @GET
    @Path("/{id}")
    public User getUserById(@PathParam("id") Long id) {
        return accountService.findUserById(id);
    }

    @POST
    @Path("/register")
    public User register(User user) throws Exception {
        accountService.addUser(user);
        return user;
    }

    @POST
    @Path("/authenticate")
    public User login(AuthRequest authRequest) throws Exception {

        User authentifiedUser = null;
        User user = accountService.findUserByUsername(authRequest.getUsername());

        LOGGER.info("Registered Password :" + BcryptUtil.bcryptHash(user.getPassword()));
        LOGGER.info("Entered password :" + BcryptUtil.bcryptHash(user.getPassword()));

        if (user != null && JwtTokenUtils.verifyPassword(authRequest.getPassword(), user.getPassword())) {
            user.setToken(JwtTokenUtils.generateToken(user.getUsername(),
                    new HashSet<String>(Arrays.asList(AuthoritiesConstants.USER)), 3600L, issuer));
            authentifiedUser = user;
        }
        return authentifiedUser;
    }

    @DELETE
    @Path("/{id}")
    public User deleteUser(@PathParam("id") Long id) {
        return accountService.deleteUserById(id);
    }

    @PUT
    public User updateUser(User user) {
        return accountService.userUpdate(user);
    }
}
