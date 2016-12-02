package com.example.psiquemap.psiquemap.comunicacao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.psiquemap.psiquemap.LoginActivity;
import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Dados;
import com.example.psiquemap.psiquemap.entidades.Login;
import com.example.psiquemap.psiquemap.entidades.Medicamento;
import com.example.psiquemap.psiquemap.entidades.Paciente;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.Medicamentos;
import com.example.psiquemap.psiquemap.sql.Pacientes;
import com.example.psiquemap.psiquemap.sql.Sintomas;
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

public class EnviarLogin extends AsyncTask<Login,Void,String> {

    protected void onPreExecute() {
    }

    protected String doInBackground(Login... arg0) {

        try {

            URL url = new URL(MetodosEmComum.urlLogin); // here is your URL path

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

            Log.i("loginJson", loginJson);
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

            Log.i("Recebido", sb.toString());

            String statusJson;

            Controles controles = new Controles(MetodosEmComum.conexaoBD(LoginActivity.thisContext));

            try
            {
                Dados dados = gson.fromJson(sb.toString(), Dados.class);
                Log.i("Controle",dados.getControle().toString());

                controles.insert(dados.getControle());

                Log.i("FLAG Medicamento",dados.getControle().getFlagMedicamento()+"");
                Log.i("FLAG PACIENTE",dados.getControle().getFlagPaciente()+"");
                Log.i("FLAG TodosSintomas",dados.getControle().getFlagTodosSintomas()+"");

                if(dados.getControle().getFlagTodosSintomas()==1)
                {
                    Sintomas sintomas = new Sintomas(MetodosEmComum.conexaoBD(LoginActivity.thisContext));
                    for (int i = 0; i < dados.getTodosSintomas().size(); i++) {
                        sintomas.insert(dados.getTodosSintomas().get(i));
                        Log.i("Sintoma", "inserido");
                    }
                    controles.setFlagTodosSintomas(dados.getControle().getIdPaciente(),0);
                }

                if(dados.getControle().getFlagMedicamento()==1)
                {
                    Medicamentos medicamentos = new Medicamentos(MetodosEmComum.conexaoBD(LoginActivity.thisContext));
                    for (int i=0;i<dados.getMedicamentos().size();i++)
                    {
                        Medicamento medicamento = dados.getMedicamentos().get(i);
                        medicamento.setIdPaciente(dados.getControle().getIdPaciente());
                        medicamento.setUltimoHorario("");
                        medicamento.setProximoHorario("");
                        medicamento.setQtdRestantesDoMedicamento(medicamento.calcularQtdRestantesDoMedicamento());
                        medicamentos.insert(medicamento);
                        Log.i("Medicamentos", "inserido");
                    }
                    controles.setFlagMedicamento(dados.getControle().getIdPaciente(),0);
                }

                if(dados.getControle().getFlagPaciente()==1)
                {
                    Pacientes pacientes = new Pacientes( MetodosEmComum.conexaoBD(LoginActivity.thisContext));
                    pacientes.insert(dados.getPaciente());
                    Log.i("Paciente", "inserido");
                    controles.setFlagPaciente(dados.getControle().getIdPaciente(),0);
                }

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
            Toast.makeText(LoginActivity.getApplicationContext, "Login efetuado com sucesso!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.getApplicationContext, "Não foi possível conectar-se ao servidor! Por favor, tente mais tarde.",
                    Toast.LENGTH_LONG).show();
        }
    }
}