package authoriztion_utils;


import com.oracle.webservices.internal.api.message.ContentType;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthorizationGitHub {

    private String client_id = "1fdea54379b6f87b119b";
    private String redirect_uri = "http://localhost:8080/home?type=github";
    private String scope = "read:user";
    private String client_secret = "82ab861ebef353f13b0902753ba88f758ef60f90";

    public void execute(HttpServletResponse response) throws IOException {
        String url = String.format("https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s",
                client_id, redirect_uri, scope);
        System.out.println(url);
        response.sendRedirect(url);
    }

    private String getToken(String code) throws IOException {
        String url = String.format("https://github.com/login/oauth/access_token?"+"client_id=%s&client_secret=%s&" +
                "grant_type=authorization_code" + "&" +
                "code=" + code,client_id,client_secret);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString().substring(13,53);
    }

    public String getName(String code) throws IOException {
        String url = String.format("https://api.github.com/user?access_token=%s",
                getToken(code));
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        JSONTokener tokener = new JSONTokener(in);
        JSONObject userInfoResponse = new JSONObject(tokener);
        String login = userInfoResponse.getString("login");
        in.close();
        return login;
    }
}
