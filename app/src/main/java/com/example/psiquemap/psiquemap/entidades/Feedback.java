package com.example.psiquemap.psiquemap.entidades;

import android.util.Log;

import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.sql.Pacientes;

import java.util.Calendar;

/**
 * Created by yuki on 08/11/16.
 */

public class Feedback
{
    private String idPaciente;
    private String data;
    private String sentimento;
    private String observacao;

    public Feedback(){}

    public Feedback(String idPaciente,String sentimento,String observacao)
    {
        this.setIdPaciente(idPaciente);
        this.setSentimento(sentimento);
        this.setObservacao(observacao);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);

        this.setData(MetodosEmComum.getDataAtual(calendar));

    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSentimento() {
        return sentimento;
    }

    public void setSentimento(String sentimento) {
        this.sentimento = sentimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
