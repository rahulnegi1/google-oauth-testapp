package com.testapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URLConnection;

class AccessToken {

    String access_token;
    String token_type;
    String scope;
    String expires_in;
    String id_token;

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}

public class AccessTokenCall extends HttpServlet {

    public static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    public static final String REDIRECT_URL = "http://localhost:8080/testapp/token";
    public static final String CLIENT_ID = "YOUR_CLIENT_ID";
    public static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";

    @Override
    public void service(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        BufferedReader reader = null;
        HttpURLConnection urlConn = null;
        DataOutputStream wr = null;

        try {
            System.out.println("***************** In AccessTokenCall servlet's service() method.");
            String code = request.getParameter("code");
            System.out.println("***************** code : " + code);
            StringBuilder responseObj = new StringBuilder();
            if (code != null) {
                StringBuilder queryParams = new StringBuilder("code=").append(code);
                queryParams.append("&client_id=").append(CLIENT_ID);
                queryParams.append("&client_secret=").append(CLIENT_SECRET);
                queryParams.append("&redirect_uri=").append(REDIRECT_URL);
                queryParams.append("&grant_type=").append("authorization_code");
                URL url = new URL(TOKEN_URL);
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setDoOutput(true);
                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConn.setRequestMethod("POST");
                byte[] postData = queryParams.toString().getBytes();
                wr = new DataOutputStream(urlConn.getOutputStream());
                wr.write(postData);
                wr.flush();

                String line;
                reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseObj.append(line);
                }
                System.out.println("***************** access token call response :" + responseObj.toString());

                ObjectMapper mapper = new ObjectMapper();
                AccessToken token = (AccessToken) mapper.readValue(responseObj.toString(), AccessToken.class);
                HttpSession session = request.getSession();
                session.setAttribute("access_token", token.getAccess_token());
                request.getRequestDispatcher("/logged").include(request, response);
            }
        } finally {
            wr.close();
            urlConn = null;
            reader.close();
        }
    }
}
