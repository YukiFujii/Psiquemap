package com.example.psiquemap.psiquemap.entidades;

/**
 * Created by yuki on 01/11/16.
 */

public class Sintoma
{
    private String idCategoria;
    private String idSintoma;
    private String nome;

    public Sintoma(){}

    public Sintoma(String idCategoria,String idSintoma,String nome)
    {
        this.setIdCategoria(idCategoria);
        this.setIdSintoma(idSintoma);
        this.setNome(nome);
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString()
    {
        return this.getNome();
    }
}
