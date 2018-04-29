package authoriztion_utils;

import org.json.JSONObject;
import org.json.JSONTokener;
import response_entity.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class AuthorizationVk {

    private String client_id="6451652";
    private String redirect_uri="http://localhost:8080/home?type=vk";
    private String scope="users";
    private String version="5.74";
    private String client_secret="AzUR2DlF8KmXi0GCPVFo";

    public void execute(HttpServletResponse response) throws IOException {
        String url= String.format("https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=%s&scope=%s&response_type=code&v=%s",
                client_id, redirect_uri, scope, version);
        System.out.println(url);
        response.sendRedirect(url);
    }

    public String getName(String code) throws IOException {
        String url= String.format("https://api.vk.com/method/users.get?user_ids=%s&fields=bdate&v=5.74",
                getId(code));
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        InputStreamReader in=new InputStreamReader(con.getInputStream(),"UTF-8");

        JSONTokener tokener = new JSONTokener(in);
        JSONObject userInfoResponse = new JSONObject(tokener).getJSONArray("response").getJSONObject(0);
        String firstName = userInfoResponse.getString("first_name");
        String lastName = userInfoResponse.getString("last_name");
        in.close();
        return firstName+" "+lastName;
    }

    private int getId(String code) throws IOException {
        String url= String.format("https://oauth.vk.com/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
                client_id, client_secret, redirect_uri, code);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        JSONTokener tokener = new JSONTokener(in);
        JSONObject userResponse = new JSONObject(tokener);
        int userId = userResponse.getInt("user_id");
        in.close();
        return userId;
    }
}
