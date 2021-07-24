package endpoints;


import entities.User;
import utils.HttpHeadersReader;

import javax.ejb.Stateless;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Path("users")
@Stateless
public class UsersEndpoint {
    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;

    @GET
    @Path("login")
    public Response getUser(@Context HttpHeaders httpHeaders){
        return Response.ok().entity(HttpHeadersReader.getUser(em, httpHeaders)).build();
    }


    private boolean usernameAvailable(String username) {
        return em.createNamedQuery("User.findByUsername", User.class).setParameter("username",username).getResultList().isEmpty();
    }

    private boolean emailAvailable(String email) {
        return em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getResultList().isEmpty();
    }


    @POST
    @Path("register")
    public Response registerUser(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName,
                           @FormParam("email") String email, @FormParam("username") String username, @FormParam("password") String password,
                           @FormParam("homeAddress") String homeAddress,@FormParam("homeLatitude") double homeLatitude,@FormParam("homeLongitude") double homeLongitude) {

        if (!this.usernameAvailable(username))
            return Response.status(Response.Status.CONFLICT).entity("This username is already in use.").build();
        if (!this.emailAvailable(email))
            return Response.status(Response.Status.CONFLICT).entity("This e-mail is already in use.").build();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setHomeAddress(homeAddress);
        user.setHomeLatitude(homeLatitude);
        user.setHomeLongitude(homeLongitude);

        em.persist(user);
        em.flush();

        return Response.ok().entity(user).build();
    }


}
