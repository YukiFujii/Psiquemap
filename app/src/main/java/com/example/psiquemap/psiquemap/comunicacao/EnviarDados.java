package com.example.psiquemap.psiquemap.comunicacao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.psiquemap.psiquemap.LoginActivity;
import com.example.psiquemap.psiquemap.MainActivity;
import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Dados;
import com.example.psiquemap.psiquemap.entidades.Paciente;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.Pacientes;
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

public class EnviarDados extends AsyncTask<Dados,Void,String> {

    protected void onPreExecute() {
    }

    protected String doInBackground(Dados... arg0) {

        try {

            URL url = new URL(MetodosEmComum.urlEnviar); // here is your URL path

            Dados dados = arg0[0];

            Gson gson = new Gson();
            String dadosJson = gson.toJson(dados);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(dadosJson);
            Log.i("Json enviado",dadosJson);

            writer.flush();
            writer.close();
            os.close();

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

            Log.i("Resposta", "recebida");

            String statusJson;

            try
            {

                dados = gson.fromJson(sb.toString(), Dados.class);

                /*Paciente paciente = dados.getPaciente();
                Pacientes pacientes = new Pacientes(MetodosEmComum.conexaoBD(LoginActivity.thisContext));
                pacientes.insert(paciente);

                Controle controle = new Controle(paciente);
                Controles controles= new Controles(MetodosEmComum.conexaoBD(LoginActivity.thisContext));
                controles.insert(controle);*/

                Log.i("Controle",dados.getControle().toString());

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

        Log.i("Result",result);

        if(result.equals("true"))
        {
            Toast.makeText(MainActivity.getApplicationContext, "Dados enviados com sucesso!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.getApplicationContext, "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.",
                    Toast.LENGTH_LONG).show();
        }
    }
}