package com.example.psiquemap.psiquemap.entidades;

/**
 * Created by yuki on 10/11/16.
 */

public final class Controle
{
    private static String idPaciente;

    public static String getIdPaciente() {
        return idPaciente;
    }

    public static void setIdPaciente(String idPaciente) {
        Controle.idPaciente = idPaciente;
    }
}
