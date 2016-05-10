package com.full.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("serial")
public class ProjectOAuthServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		String line1 = req.getParameter("j");
		
		String code = req.getParameter("code");
		String urlParameters = "code="
		                    + code
		                    + "&client_id=43915650295-18hjs0smnqpb829fjmaqr5mc3to370be.apps.googleusercontent.com"
		                    + "&client_secret=TjDdCPryxgYKjojG0Lh8JsSO"
		                    + "&redirect_uri=http://1-dot-projectoauthdemo.appspot.com/projectoauth"
		                    + "&grant_type=authorization_code";
		
		
		URL url = new URL("https://accounts.google.com/o/oauth2/token");
		URLConnection urlConn = url.openConnection();
		urlConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(
		urlConn.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		
		String line, outputString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                urlConn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            outputString += line;
        }
        System.out.println(outputString);
  
		
		JsonObject json = (JsonObject)new JsonParser().parse(line1);
		String access_token = json.get("access_token").getAsString();
		
		url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+ access_token);
		urlConn = url.openConnection();
		outputString = "";
		reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		while ((line = reader.readLine()) != null) {
		        outputString += line;
		}
		
		
		GooglePojo data = new Gson().fromJson(outputString, GooglePojo.class);
        System.out.println(data);
        writer.close();
        reader.close();
	}
}
