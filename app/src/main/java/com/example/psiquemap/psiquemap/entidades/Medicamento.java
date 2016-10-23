package com.example.psiquemap.psiquemap.entidades;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by YUKI FUJII on 19/09/2016.
 */
public class Medicamento implements Serializable
{
        private String nomeMedicacao;
        private int intervalo;
        private float dose;
        private int durante;
        private String observacoes;
        private Calendar ultimoHorario;
        private Calendar proximoHorario;
        private int qtdRestantesDoMedicamento;
        private boolean flagDeletarMedicamento;
        private boolean alarmeAtivo;
        //private int idSintoma;

        public Medicamento (String nomeMedicacao,int intervalo,float dose,int durante,String observacoes)
        {
            setNomeMedicacao(nomeMedicacao);
            setIntervalo(intervalo);
            setDosagem(dose);
            setDurante(durante);
            setObservacoes(observacoes);
            this.calcularQtdRestantesDoMedicamento();
            setFlagDeletarMedicamento(false);
            setAlarmeAtivo(false);
        }


        public void calcularQtdRestantesDoMedicamento()
        {
            setQtdRestantesDoMedicamento((this.durante*24)/this.intervalo);
        }

        public void decrementarQtdRestantesDoMedicamento()
        {
            this.setQtdRestantesDoMedicamento(this.getQtdRestantesDoMedicamento()-1);

            if(this.getQtdRestantesDoMedicamento()==0)
                this.setFlagDeletarMedicamento(true);

        }

        public static ArrayAdapter<Medicamento> buscarMedicacao(Context context)
        {

            ArrayAdapter<Medicamento> ret = new ArrayAdapter<Medicamento>(context,android.R.layout.simple_list_item_1);

            Medicamento m1 = new Medicamento("Aurorix",2,100,2,"Sem observações.");

            Medicamento m2 = new Medicamento("STABLON",8,25,5,"Sem observações.");

            Medicamento m3 = new Medicamento("Tofranil",1,25,7,"Sem observações.");

            ret.add(m1);
            ret.add(m2);
            ret.add(m3);

            return ret;
        }



    public String toString()
    {
        String ret = "";

        ret = ret + this.getNomeMedicacao();

        return ret;

    }

    public String getNomeMedicacao() {
        return nomeMedicacao;
    }

    public void setNomeMedicacao(String nomeMedicacao) {
        this.nomeMedicacao = nomeMedicacao;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public float getDosagem() {
        return dose;
    }

    public void setDosagem(float dose) {
        this.dose = dose;
    }

    public int getDurante() {
        return durante;
    }

    public void setDurante(int durante) {
        this.durante = durante;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Calendar getUltimoHorario() {
        return ultimoHorario;
    }

    public void setUltimoHorario(Calendar ultimoHorario) {
        this.ultimoHorario = ultimoHorario;
    }

    public Calendar getProximoHorario() {
        return proximoHorario;
    }

    public void setProximoHorario(Calendar proximoHorario) {
        this.proximoHorario = proximoHorario;
    }

    public int getQtdRestantesDoMedicamento() {
        return qtdRestantesDoMedicamento;
    }

    public void setQtdRestantesDoMedicamento(int qtdRestantesDoMedicamento) {
        this.qtdRestantesDoMedicamento = qtdRestantesDoMedicamento;
    }

    public boolean getFlagDeletarMedicamento() {
        return flagDeletarMedicamento;
    }

    public void setFlagDeletarMedicamento(boolean flagDeletarMedicamento) {
        this.flagDeletarMedicamento = flagDeletarMedicamento;
    }

    public boolean isAlarmeAtivo() {
        return alarmeAtivo;
    }

    public void setAlarmeAtivo(boolean alarmeAtivo) {
        this.alarmeAtivo = alarmeAtivo;
    }
}
