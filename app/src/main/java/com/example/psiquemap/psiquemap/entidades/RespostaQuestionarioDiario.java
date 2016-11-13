package com.example.psiquemap.psiquemap.entidades;

/**
 * Created by yuki on 11/11/16.
 */

public class RespostaQuestionarioDiario
{
    private String idPaciente;
    private String data;
    private String idPergunta;
    private String resposta;

    public  RespostaQuestionarioDiario(){}

    public RespostaQuestionarioDiario(String idPaciente,String data,String idPergunta,String resposta)
    {
        setIdPaciente(idPaciente);
        setData(data);
        setIdPergunta(idPergunta);
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

    public String getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(String idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String toString()
    {
        String ret = getIdPaciente()+" - "+getData()+" - "+getIdPergunta()+" - "+getResposta();

        return ret;
    }
}

