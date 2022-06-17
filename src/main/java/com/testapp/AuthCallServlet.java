package com.testapp;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthCallServlet extends HttpServlet {

    public static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth?";
    public static final String REDIRECT_URL = "http://localhost:8080/testapp/token";
    public static final String CLIENT_ID = "YOUR_CLIENT_ID";
    public static final String SCOPE = "openid email";

    @Override
    public void service(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        System.out.println("***************** In AuthCallServlet servlet's service() method.");
        UUID uuid = UUID.randomUUID();
        StringBuilder queryParams = new StringBuilder(AUTH_URL);
        queryParams.append("response_type=").append("code");
        queryParams.append("&client_id=").append(CLIENT_ID);
        queryParams.append("&scope=").append(SCOPE);
        queryParams.append("&redirect_uri=").append(REDIRECT_URL);
        queryParams.append("&state=").append(uuid.toString());
        response.sendRedirect(queryParams.toString());
    }
}
