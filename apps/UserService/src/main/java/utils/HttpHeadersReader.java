package utils;


import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

public class HttpHeadersReader {

    public static User getUser(EntityManager em, HttpHeaders httpHeaders) {

        List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");

        String authHeaderValue = authHeaderValues.get(0);
        String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")), StandardCharsets.UTF_8);
        StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
        String username = stringTokenizer.nextToken();

        return em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();

    }
}
