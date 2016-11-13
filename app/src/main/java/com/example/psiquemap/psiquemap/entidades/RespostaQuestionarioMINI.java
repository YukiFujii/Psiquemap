package com.example.psiquemap.psiquemap.entidades;

import java.io.Serializable;

/**
 * Created by yuki on 11/11/16.
 */
public class RespostaQuestionarioMINI
{
    private String idPaciente;
    private String data;
    private String idModulo;
    private String questao;
    private String resposta;

    public  RespostaQuestionarioMINI(){}

    public RespostaQuestionarioMINI(String idPaciente,String data,String modulo,String questao,String resposta)
    {
        setIdPaciente(idPaciente);
        setData(data);
        setIdModulo(modulo);
        setQuestao(questao);
        setResposta(resposta);
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String toString()
    {
        String ret = getIdPaciente()+" - "+getData()+" - "+getIdModulo()+" - "+getQuestao()+" - "+getResposta();

        return ret;
    }
}
