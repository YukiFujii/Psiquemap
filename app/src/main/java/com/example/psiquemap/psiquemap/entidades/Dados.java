package com.example.psiquemap.psiquemap.entidades;

import java.util.ArrayList;

public class Dados
{
	private Controle controle;

    protected Paciente paciente;
    private ArrayList<RespostaQuestionarioDiario> respostasQuestDiario;
    private ArrayList<Acontecimento> acontecimentos;
    private ArrayList<RespostaQuestionarioMINI> respostaQuestMini;
    private ArrayList<SintomaSentido> sintomas;
    private ArrayList<Feedback> feedbacks;

	public Dados() {

	}

    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

    public ArrayList<RespostaQuestionarioDiario> getRespostasQuestDiario() {
        return respostasQuestDiario;
    }

    public void setRespostasQuestDiario(ArrayList<RespostaQuestionarioDiario> respostasQuestDiario) {
        this.respostasQuestDiario = respostasQuestDiario;
    }

    public ArrayList<Acontecimento> getAcontecimentos() {
        return acontecimentos;
    }

    public void setAcontecimentos(ArrayList<Acontecimento> acontecimentos) {
        this.acontecimentos = acontecimentos;
    }

    public ArrayList<RespostaQuestionarioMINI> getRespostaQuestMini() {
        return respostaQuestMini;
    }

    public void setRespostaQuestMini(ArrayList<RespostaQuestionarioMINI> respostaQuestMini) {
        this.respostaQuestMini = respostaQuestMini;
    }

    public ArrayList<SintomaSentido> getSintomas() {
        return sintomas;
    }

    public void setSintomas(ArrayList<SintomaSentido> sintomas) {
        this.sintomas = sintomas;
    }

    public ArrayList<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
