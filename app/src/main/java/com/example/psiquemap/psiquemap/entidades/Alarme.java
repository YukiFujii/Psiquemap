package com.example.psiquemap.psiquemap.entidades;

/**
 * Created by yuki on 09/11/16.
 */

public class Alarme
{
    private String idPaciente;
    private String idMedicacao;
    private String tempoRestante;
    private int eProximo;

    public Alarme(){}

    public Alarme(String idPaciente, String idMedicacao, String tempoRestante)
    {
        this.setIdPaciente(idPaciente);
        this.setIdMedicacao(idMedicacao);
        this.setTempoRestante(tempoRestante);
        this.seteProximo(0);
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

    public String getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante(String proxHorario) {
        this.tempoRestante = proxHorario;
    }

    public int geteProximo() {
        return eProximo;
    }

    public void seteProximo(int eProximo) {
        this.eProximo = eProximo;
    }

    public String toString()
    {
        String ret = "";
        ret = ret+"idPaciente: "+this.getIdPaciente()+"/n";
        ret = ret+"idMedicacao: "+this.getIdMedicacao()+"/n";
        ret = ret+"tempoRestante: "+this.getTempoRestante()+"/n";
        ret = ret+"eProximo: "+this.geteProximo()+"/n";

        return ret;
    }
}
