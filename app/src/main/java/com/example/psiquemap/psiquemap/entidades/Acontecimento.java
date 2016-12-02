package com.example.psiquemap.psiquemap.entidades;

import android.database.sqlite.SQLiteDatabase;

import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Pacientes;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yuki on 01/11/16.
 */

public class Acontecimento implements Serializable
{
    private String idPaciente;
    private String data;
    private String hora;
    private String sentimento;
    private String titulo;
    private String descricao;

    public Acontecimento(String idPaciente,String sentimento,String titulo,String descricao)
    {
        this.setIdPaciente(idPaciente);
        this.setData(MetodosEmComum.getDataAtual());
        this.setHora(MetodosEmComum.getHoraAtual());
        this.setSentimento(sentimento);
        this.setTitulo(titulo);
        this.setDescricao(descricao);
    }

    public Acontecimento(){}

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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSentimento() {
        return sentimento;
    }

    public void setSentimento(String sentimento) {
        this.sentimento = sentimento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String toString()
    {
        String ret = this.getSentimento()+": "+this.getTitulo();

        return ret;
    }
}
