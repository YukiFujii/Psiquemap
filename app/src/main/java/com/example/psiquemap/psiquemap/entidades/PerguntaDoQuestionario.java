package com.example.psiquemap.psiquemap.entidades;

import java.io.Serializable;

/**
 * Created by YUKI FUJII on 11/09/2016.
 */
public class PerguntaDoQuestionario implements Serializable
{
    private String questionarioId;
    private String modulo;
    private String questao;
    private String pergunta;
    private String tipoPergunta;
    private boolean foiRespondida;

    public PerguntaDoQuestionario(String questionarioId,String modulo,String questao,String pergunta,String tipoPergunta)
    {
        this.setQuestionarioId(questionarioId);
        this.setModulo(modulo);
        this.setQuestao(questao);
        this.setPergunta(pergunta);
        this.setTipoPergunta(tipoPergunta);
        this.setFoiRespondida(false);
    }

    public PerguntaDoQuestionario(String perguntaId,String pergunta,String tipoPergunta)
    {
        this.setQuestionarioId(perguntaId);
        this.setPergunta(pergunta);
        this.setTipoPergunta(tipoPergunta);
        this.setFoiRespondida(false);
    }

    public String getQuestionarioId() {
        return questionarioId;
    }

    public void setQuestionarioId(String questionarioId) {
        this.questionarioId = questionarioId;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getQuestao() {
        return this.questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getTipoPergunta() {
        return tipoPergunta;
    }

    public void setTipoPergunta(String tipoPergunta) {
        this.tipoPergunta = tipoPergunta;
    }

    public boolean getFoiRespondida() {
        return foiRespondida;
    }

    public void setFoiRespondida(boolean foiRespondida) {
        this.foiRespondida = foiRespondida;
    }
}
