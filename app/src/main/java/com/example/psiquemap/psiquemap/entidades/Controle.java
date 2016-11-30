package com.example.psiquemap.psiquemap.entidades;

/**
 * Created by yuki on 10/11/16.
 */

public final class Controle
{
    private String idPaciente;
    private int flagPaciente;
    private int flagQuestDiario;
    private int flagAcontecimento;
    private int flagQuestMini;
    private int flagSintomas;
    private int flagFeedback;

    public Controle(Paciente idPaciente)
    {
        setIdPaciente(idPaciente.getId());
        setFlagPaciente(0);
        setFlagQuestDiario(0);
        setFlagAcontecimento(0);
        setFlagQuestMini(0);
        setFlagSintomas(0);
        setFlagFeedback(0);
    }

    public Controle(){}

    public String getIdPaciente() {
        return this.idPaciente;
    }

    public void setIdPaciente(String idPaciente)
    {
        this.idPaciente = idPaciente;
    }

    public int getFlagPaciente() {
        return flagPaciente;
    }

    public void setFlagPaciente(int flagPaciente) {
        this.flagPaciente = flagPaciente;
    }

    public int getFlagQuestDiario() {
        return flagQuestDiario;
    }

    public void setFlagQuestDiario(int flagQuestDiario) {
        this.flagQuestDiario = flagQuestDiario;
    }

    public int getFlagAcontecimento() {
        return flagAcontecimento;
    }

    public void setFlagAcontecimento(int flagAcontecimento) {
        this.flagAcontecimento = flagAcontecimento;
    }

    public int getFlagQuestMini() {
        return flagQuestMini;
    }

    public void setFlagQuestMini(int flagQuestMini) {
        this.flagQuestMini = flagQuestMini;
    }

    public int getFlagSintomas() {
        return flagSintomas;
    }

    public void setFlagSintomas(int flagSintomas) {
        this.flagSintomas = flagSintomas;
    }

    public int getFlagFeedback() {
        return flagFeedback;
    }

    public void setFlagFeedback(int flagFeedback) {
        this.flagFeedback = flagFeedback;
    }

    public String toString()
    {
        String ret = "";

        ret = ret+"idPaciente:"+this.getIdPaciente();
        ret = ret + " | ";
        ret = ret+"flagPaciente:"+this.getFlagPaciente();
        ret = ret + " | ";
        ret = ret+"flagQuestDiario:"+this.getFlagQuestDiario();
        ret = ret + " | ";
        ret = ret+"flagAcontecimento:"+this.getFlagAcontecimento();
        ret = ret + " | ";
        ret = ret+"flagQuestMini:"+this.getFlagQuestMini();
        ret = ret + " | ";
        ret = ret+"flagSintomas:"+this.getFlagSintomas();
        ret = ret + " | ";
        ret = ret+"flagFeedback:"+this.getFlagFeedback();

        return ret;
    }

}
