package com.example.psiquemap.psiquemap.entidades;

import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.sql.Pacientes;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by yuki on 08/11/16.
 */

public class SintomaSentido
{
    private String idPaciente;
    private String idCategoria;
    private String idSintoma;
    private String nome;
    private String data;

    public SintomaSentido(){}

    public SintomaSentido(String idCategoria,String idSintoma,String nome)
    {
        this.setIdPaciente(Pacientes.getIdPaciente());
        this.setIdCategoria(idCategoria);
        this.setIdSintoma(idSintoma);
        this.setNome(nome);
        this.setData(MetodosEmComum.getDataAtual());
    }

    public String toString()
    {
        return this.getNome();
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdSintoma() {
        return idSintoma;
    }

    public void setIdSintoma(String idSintoma) {
        this.idSintoma = idSintoma;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }
}
