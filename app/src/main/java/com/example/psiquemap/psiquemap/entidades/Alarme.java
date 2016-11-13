package com.example.psiquemap.psiquemap.entidades;

import com.example.psiquemap.psiquemap.MetodosEmComum;

import java.util.Calendar;

/**
 * Created by yuki on 09/11/16.
 */

public class Alarme
{
    private String idPaciente;
    private String idMedicacao;
    private String horaDoAlarme;
    private int ordem;

    public Alarme(){}

    public Alarme(String idPaciente, String idMedicacao, String horaDoAlarme)
    {
        this.setIdPaciente(idPaciente);
        this.setIdMedicacao(idMedicacao);
        this.setHoraDoAlarme(horaDoAlarme);
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdMedicacao() {
        return idMedicacao;
    }

    public void setIdMedicacao(String idMedicacao) {
        this.idMedicacao = idMedicacao;
    }

    public String getHoraDoAlarme() {
        return horaDoAlarme;
    }

    public void setHoraDoAlarme(String proxHorario) {
        this.horaDoAlarme = proxHorario;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String toString()
    {
        String ret = "";
        ret = ret+"idPaciente: "+this.getIdPaciente()+"/n";
        ret = ret+"idMedicacao: "+this.getIdMedicacao()+"/n";
        ret = ret+"horaDoAlarme: "+this.getHoraDoAlarme()+"/n";
        ret = ret+"ordem: "+this.getOrdem()+"/n";

        return ret;
    }

}
