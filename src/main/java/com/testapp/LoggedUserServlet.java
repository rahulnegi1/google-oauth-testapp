package com.testapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class User {

    String email;
    String picture;
    String sub;
    String email_verified;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(String email_verified) {
        this.email_verified = email_verified;
    }
}

public class LoggedUserServlet extends HttpServlet {

    public static final String API_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Override
    public void service(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        BufferedReader reader = null;
        URLConnection urlConn = null;
        OutputStreamWriter writer = null;
        PrintWriter out = null;

        try {
            System.out.println("***************** In LoggedUserServlet servlet's service() method.");
            HttpSession session = request.getSession(false);
            String access_token = (String) session.getAttribute("access_token");
            URL url = new URL(API_URL);
            urlConn = url.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setRequestProperty("Token-Type", "Bearer");
            urlConn.setRequestProperty("User-Agent", "rahulnegi1_testX");
            writer = new OutputStreamWriter(urlConn.getOutputStream());
            writer.flush();
            String line;
            StringBuilder responseObj = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseObj.append(line);
            }

            System.out.println("***************** api call response :" + responseObj.toString());
            ObjectMapper mapper = new ObjectMapper();
            User user = (User) mapper.readValue(responseObj.toString(), User.class);
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println("<!DOCTYPE html><html>");
            out.println("<head><title>Sample Google API response</title>");
            out.println("<meta charset=\"UTF-8\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Response recieved from Google</h1>");
            out.println("<br/><br/>");
            out.println("User : " + user.getEmail());
            out.println("<br/>");
            out.println("Email Verified : " + user.getEmail_verified());
            out.println("<br/>");
            out.println("Sub : " + user.getSub());
            out.println("<br/>");
            out.println("Picture link : " + user.getPicture());
            out.println("<br/><br/>");
            out.println("Picture : <img src=\"" + user.getPicture() + "\"></img>");
            out.println("<br/><br/>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
            writer.close();
            urlConn = null;
            reader.close();
        }
    }
}
