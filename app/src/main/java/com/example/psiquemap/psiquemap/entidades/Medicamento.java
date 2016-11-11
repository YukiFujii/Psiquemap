package com.example.psiquemap.psiquemap.entidades;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.psiquemap.psiquemap.sql.Pacientes;

import java.io.Serializable;

/**
 * Created by YUKI FUJII on 19/09/2016.
 */
public class Medicamento implements Serializable
{
        private String idPaciente;
        private String idMedicacao;
        private String nomeMedicacao;
        private int intervalo;
        private int dose;
        private int durante;
        private String observacoes;
        private String ultimoHorario;
        private String proximoHorario;
        private int medicacaoContinua;
        private int qtdRestantesDoMedicamento;

        public Medicamento(){}

        public Medicamento (String idMedicacao,String nomeMedicacao,int intervalo,int dose,int durante,String observacoes,int medicacaoContinua)
        {
            setIdPaciente(Controle.getIdPaciente());
            setIdMedicacao(idMedicacao);
            setNomeMedicacao(nomeMedicacao);
            setIntervalo(intervalo);
            setDosagem(dose);
            setDurante(durante);
            setObservacoes(observacoes);
            setUltimoHorario("");
            setProximoHorario("");
            setMedicacaoContinua(medicacaoContinua);

            if(this.getMedicacaoContinua()==0)
                setQtdRestantesDoMedicamento(calcularQtdRestantesDoMedicamento());
        }


        public int calcularQtdRestantesDoMedicamento()
        {
            int ret = (this.durante*24)/this.intervalo;
            return ret;
        }

        public void decrementarQtdRestantesDoMedicamento()
        {
            this.setQtdRestantesDoMedicamento(this.getQtdRestantesDoMedicamento()-1);
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

    public int getDosagem() {
        return dose;
    }

    public void setDosagem(int dose) {
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

    public String getUltimoHorario() {
        return ultimoHorario;
    }

    public void setUltimoHorario(String ultimoHorario) {
        this.ultimoHorario = ultimoHorario;
    }

    public String getProximoHorario() {
        return proximoHorario;
    }

    public void setProximoHorario(String proximoHorario) {
        this.proximoHorario = proximoHorario;
    }

    public int getQtdRestantesDoMedicamento() {
        return qtdRestantesDoMedicamento;
    }

    public void setQtdRestantesDoMedicamento(int qtdRestantesDoMedicamento) {
        this.qtdRestantesDoMedicamento = qtdRestantesDoMedicamento;
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

    public int getMedicacaoContinua() {
        return medicacaoContinua;
    }

    public void setMedicacaoContinua(int medicacaoContinua) {
        this.medicacaoContinua = medicacaoContinua;
    }


    public String toString()
    {
        String ret = this.getNomeMedicacao();

        return ret;
    }
}
