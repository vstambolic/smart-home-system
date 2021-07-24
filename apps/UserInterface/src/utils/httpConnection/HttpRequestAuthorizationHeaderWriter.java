package utils.httpConnection;

import entities.User;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpRequestAuthorizationHeaderWriter {
    
    
    
    public static void setBasicAuthHeader(HttpURLConnection connection, User user) {
        String authHeader =  "Basic " +  Base64.getEncoder().encodeToString((user.getUsername()+":"+user.getPassword()).getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", authHeader);
    }
    

}
