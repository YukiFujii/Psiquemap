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

    private ArrayList<PerguntaDoQuestionario> perguntasQuestDiario;
    private ArrayList<PerguntaDoQuestionario> perguntasQuestMini;
    private ArrayList<Sintoma> todosSintomas;
    private ArrayList<Medicamento> medicamentos;

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

    public ArrayList<PerguntaDoQuestionario> getPerguntasQuestDiario() {
        return perguntasQuestDiario;
    }

    public void setPerguntasQuestDiario(ArrayList<PerguntaDoQuestionario> perguntasQuestDiario) {
        this.perguntasQuestDiario = perguntasQuestDiario;
    }

    public ArrayList<PerguntaDoQuestionario> getPerguntasQuestMini() {
        return perguntasQuestMini;
    }

    public void setPerguntasQuestMini(ArrayList<PerguntaDoQuestionario> perguntasQuestMini) {
        this.perguntasQuestMini = perguntasQuestMini;
    }

    public ArrayList<Sintoma> getTodosSintomas() {
        return todosSintomas;
    }

    public void setTodosSintomas(ArrayList<Sintoma> todosSintomas) {
        this.todosSintomas = todosSintomas;
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
}