package com.example.psiquemap.psiquemap.entidades;

import java.io.Serializable;

/**
 * Created by YUKI FUJII on 11/09/2016.
 */
public class PerguntaDoQuestionario implements Serializable
{
    private String perguntaId;
    private String modulo;
    private String questao;
    private String pergunta;
    private String tipoPergunta;
    private int foiRespondida;

    public  PerguntaDoQuestionario(){}

    public PerguntaDoQuestionario(String perguntaId,String modulo,String questao,String pergunta,String tipoPergunta)
    {
        this.setPerguntaId(perguntaId);
        this.setModulo(modulo);
        this.setQuestao(questao);
        this.setPergunta(pergunta);
        this.setTipoPergunta(tipoPergunta);
        this.setFoiRespondida(0);
    }

    public PerguntaDoQuestionario(String perguntaId,String pergunta,String tipoPergunta)
    {
        this.setPerguntaId(perguntaId);
        this.setPergunta(pergunta);
        this.setTipoPergunta(tipoPergunta);
        this.setFoiRespondida(0);
    }

    public String getPerguntaId() {
        return perguntaId;
    }

    public void setPerguntaId(String perguntaId) {
        this.perguntaId = perguntaId;
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

    public int getFoiRespondida() {
        return foiRespondida;
    }

    public void setFoiRespondida(int foiRespondida) {
        this.foiRespondida = foiRespondida;
    }

    public String toString()
    {
        String ret = getPerguntaId()+" - "+getQuestao()+" - "+getTipoPergunta();
        return ret;
    }
}
