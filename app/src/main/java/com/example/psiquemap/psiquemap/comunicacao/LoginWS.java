package com.example.psiquemap.psiquemap.comunicacao;

import android.content.Context;
import android.util.Log;

import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.entidades.Dados;
import com.example.psiquemap.psiquemap.sql.Pacientes;
import com.google.gson.Gson;

/**
 * Created by yuki on 27/11/16.
 */

public class LoginWS extends Thread
{
    private String dataJson;
    private Context context;

    public void run(){

        String resp = HttpClient.connect("http://192.168.0.15:8080/psiquemap-ws/login", this.getDataJson());

        Gson gson = new Gson();
        Dados dados = gson.fromJson(resp, Dados.class);

        Log.i("Script", "ANSWER: "+dados.toString());

        if(dados.getFlagPaciente()==1)
        {
            Pacientes.insert(dados.getPaciente(), MetodosEmComum.conexaoBD(this.getContext()));
        }

    }

    public LoginWS(Context context,String dataJson)
    {
        this.setContext(context);
        this.setDataJson(dataJson);

    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
