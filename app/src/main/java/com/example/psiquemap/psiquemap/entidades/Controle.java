package com.example.psiquemap.psiquemap.entidades;

/**
 * Created by yuki on 10/11/16.
 */

public final class Controle
{
    private String idPaciente;

    public Controle(String idPaciente)
    {
        setIdPaciente(idPaciente);
    }

    public String getIdPaciente() {
        return this.idPaciente;
    }

    public void setIdPaciente(String idPaciente)
    {
        this.idPaciente = idPaciente;
    }

}
