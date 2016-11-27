package com.example.psiquemap.psiquemap.comunicacao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
	
	public static String connect(String u, String param)
	{
		try
		{
			URL url = new URL(u);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			String parameters = param;
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",Integer.toString(parameters.getBytes().length));
			connection.setRequestProperty("Content-Language","en-US");
			
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			
			writer.writeBytes(parameters);
			writer.close();
			
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			StringBuilder resp = new StringBuilder();
			
			String line;
			
			while ((line = reader.readLine()) != null)
			{
				resp.append(line);
				resp.append("\r");
			}
			
			reader.close();
			connection.disconnect();
			
			return(resp.toString());
			
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}

}
