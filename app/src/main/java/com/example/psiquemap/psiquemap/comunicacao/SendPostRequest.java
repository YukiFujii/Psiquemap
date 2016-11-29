package com.example.psiquemap.psiquemap.comunicacao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.psiquemap.psiquemap.LoginActivity;
import com.example.psiquemap.psiquemap.entidades.Dados;
import com.example.psiquemap.psiquemap.entidades.Login;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuki on 28/11/16.
 */

public class SendPostRequest extends AsyncTask<Login,Void,String> {

    protected void onPreExecute() {
    }

    protected String doInBackground(Login... arg0) {

        try {

            URL url = new URL("http://192.168.0.22:8080/psiquemap-ws/login"); // here is your URL path

            //Login login = new Login("y","123");
            Login login = arg0[0];
            Log.i("email", login.getEmail());
            Log.i("senha", login.getSenha());

            Gson gson = new Gson();
            String loginJson = gson.toJson(login);

                /*JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");
                Log.e("params",postDataParams.toString());*/

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(loginJson);

            writer.flush();
            writer.close();
            os.close();

            //int responseCode = conn.getResponseCode();

            //if (responseCode == HttpsURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(new
                    InputStreamReader(
                    conn.getInputStream()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {

                sb.append(line);
                break;
            }

            in.close();

            String statusJson;

            try
            {
                Dados dados;

                dados = gson.fromJson(sb.toString(), Dados.class);

                statusJson = "true";

            } catch (Exception e) {
                statusJson = "false";
            }

            return statusJson;

            //} else {
            //return new String("false : " + responseCode);
            //}
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }

    }

    @Override
    protected void onPostExecute(String result) {


        if(result.equals(true))
        {
            Toast.makeText(LoginActivity.getApplicationContext, "Bem-vindo!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.getApplicationContext, "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.",
                    Toast.LENGTH_LONG).show();
        }
    }
}