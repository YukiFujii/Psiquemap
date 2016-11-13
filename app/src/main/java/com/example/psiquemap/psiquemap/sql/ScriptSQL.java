package com.example.psiquemap.psiquemap.sql;

import android.util.Log;

/**
 * Created by yuki on 23/10/16.
 */

public class ScriptSQL {

    public static String getPaciente()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PACIENTE ( ");
        sqlBuilder.append("_id                             VARCHAR (15)");
        sqlBuilder.append("PRIMARY KEY                               , ");
        sqlBuilder.append("NOME_COMPLETO                VARCHAR (30) , ");
        sqlBuilder.append("RUA                          VARCHAR (30) , ");
        sqlBuilder.append("NUMERO                            INTEGER , ");
        sqlBuilder.append("CEP                           VARCHAR (9) , ");
        sqlBuilder.append("DATA_NASC                    VARCHAR (10) , ");
        sqlBuilder.append("EMAIL                        VARCHAR (30) , ");
        sqlBuilder.append("RG                           VARCHAR (13) , ");
        sqlBuilder.append("CPF                          VARCHAR (14) , ");
        sqlBuilder.append("TELEFONE                     VARCHAR (15) , ");
        sqlBuilder.append("SENHA                        VARCHAR (20) , ");
        sqlBuilder.append("CNS                          VARCHAR (30)   ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getPerguntasDoDiario()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PERGUNTAS_DO_DIARIO ( ");
        sqlBuilder.append("_id                             VARCHAR (15)");
        sqlBuilder.append("PRIMARY KEY                               , ");
        sqlBuilder.append("PERGUNTA                    VARCHAR (500) , ");
        sqlBuilder.append("TIPO_PERGUNTA                VARCHAR (30) , ");
        sqlBuilder.append("FOI_RESPONDIDA                    INTEGER   ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getRespostasDoQuestionarioDiario()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS RESPOSTAS_DO_QUESTIONARIO_DIARIO    ( ");
        sqlBuilder.append("_id_PACIENTE                                      VARCHAR (15) , ");
        sqlBuilder.append("DATA                                              VARCHAR (10) , ");
        sqlBuilder.append("_id_PERGUNTA                                       VARCHAR (3) , ");
        sqlBuilder.append("RESPOSTA                                          VARCHAR (10) , ");
        sqlBuilder.append("PRIMARY KEY                     (_id_PACIENTE,DATA,_id_PERGUNTA) ");
        sqlBuilder.append("                                                               );");

        return sqlBuilder.toString();
    }

    public static String getPerguntasDoQuestionarioMINI()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PERGUNTAS_DO_QUESTIONARIO_MINI ( ");
        sqlBuilder.append("_id                            VARCHAR (1), ");
        sqlBuilder.append("MODULO                       VARCHAR (50) , ");
        sqlBuilder.append("QUESTAO                       VARCHAR (3) , ");
        sqlBuilder.append("PERGUNTA                    VARCHAR (500) , ");
        sqlBuilder.append("TIPO_PERGUNTA                VARCHAR (30) , ");
        sqlBuilder.append("FOI_RESPONDIDA                    INTEGER , ");
        sqlBuilder.append("PRIMARY KEY                    (_id,QUESTAO)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getRespostasDoQuestionarioMINI()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS RESPOSTAS_DO_QUESTIONARIO_MINI ( ");
        sqlBuilder.append("_id_PACIENTE                                 VARCHAR (15) , ");
        sqlBuilder.append("DATA                                         VARCHAR (10) , ");
        sqlBuilder.append("_id_MODULO                                    VARCHAR (1) , ");
        sqlBuilder.append("QUESTAO                                       VARCHAR (3) , ");
        sqlBuilder.append("RESPOSTA                                     VARCHAR (10) , ");
        sqlBuilder.append("PRIMARY KEY          (_id_PACIENTE,DATA,_id_MODULO,QUESTAO) ");
        sqlBuilder.append("                                                          );");

        return sqlBuilder.toString();
    }

    public static String getAcontecimentos()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS ACONTECIMENTOS ( ");
        sqlBuilder.append("_id_PACIENTE                 VARCHAR (15) , ");
        sqlBuilder.append("DATA                         VARCHAR (10) , ");
        sqlBuilder.append("HORA                          VARCHAR (5) , ");
        sqlBuilder.append("SENTIMENTO                    VARCHAR (3) , ");
        sqlBuilder.append("TITULO                      VARCHAR (100) , ");
        sqlBuilder.append("DESCRICAO                     VARCHAR (1000)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getSintomas()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS SINTOMAS       ( ");
        sqlBuilder.append("_id_CATEGORIA                VARCHAR (15) , ");
        sqlBuilder.append("_id_SINTOMA                  VARCHAR (15) , ");
        sqlBuilder.append("NOME                         VARCHAR (20) , ");
        sqlBuilder.append("PRIMARY KEY      (_id_CATEGORIA,_id_SINTOMA)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getSintomasSentidos()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS SINTOMAS_SENTIDOS(            ");
        sqlBuilder.append("_id_PACIENTE                              VARCHAR (15) , ");
        sqlBuilder.append("_id_CATEGORIA                             VARCHAR (15) , ");
        sqlBuilder.append("_id_SINTOMA                               VARCHAR (15) , ");
        sqlBuilder.append("NOME                                      VARCHAR (20) , ");
        sqlBuilder.append("DATA                                      VARCHAR (10) , ");
        sqlBuilder.append("PRIMARY KEY (_id_PACIENTE,_id_CATEGORIA,_id_SINTOMA,DATA)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getFeedback()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS FEEDBACK(                     ");
        sqlBuilder.append("_id_PACIENTE                              VARCHAR (15) , ");
        sqlBuilder.append("DATA                                      VARCHAR (10) , ");
        sqlBuilder.append("SENTIMENTO                                VARCHAR (25) , ");
        sqlBuilder.append("OBSERVACAO                               VARCHAR (500) , ");
        sqlBuilder.append("PRIMARY KEY                           (_id_PACIENTE,DATA)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getMedicamentos()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS MEDICAMENTOS(                  ");
        sqlBuilder.append("_id_PACIENTE                              VARCHAR (15) , ");
        sqlBuilder.append("_id_MEDICACAO                             VARCHAR (15) , ");
        sqlBuilder.append("NOME_MEDICACAO                            VARCHAR (25) , ");
        sqlBuilder.append("INTERVALO                                      INTEGER , ");
        sqlBuilder.append("DOSE                                           INTEGER , ");
        sqlBuilder.append("DURANTE                                        INTEGER , ");
        sqlBuilder.append("OBSERVACOES                              VARCHAR (250) , ");
        sqlBuilder.append("ULTIMO_HORARIO                             VARCHAR (5) , ");
        sqlBuilder.append("PROXIMO_HORARIO                            VARCHAR (5) , ");
        sqlBuilder.append("MEDICACAO_CONTINUA                             INTEGER , ");
        sqlBuilder.append("QTD_REST_MEDICAMENTO                           INTEGER , ");
        sqlBuilder.append("PRIMARY KEY                  (_id_PACIENTE,_id_MEDICACAO)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getProxAlarme()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PROXIMO_ALARME(               ");
        sqlBuilder.append("_id_PACIENTE                              VARCHAR (15) , ");
        sqlBuilder.append("_id_MEDICACAO                             VARCHAR (15) , ");
        sqlBuilder.append("HORA_DO_ALARME                             VARCHAR (5) , ");
        sqlBuilder.append("ORDEM                                           INTEGER  ");
        sqlBuilder.append(", PRIMARY KEY                (_id_PACIENTE,_id_MEDICACAO)");
        sqlBuilder.append(");");

        return sqlBuilder.toString();

    }

    public static String getControle()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CONTROLE(                 ");
        sqlBuilder.append("_id_PACIENTE                           VARCHAR (15)  ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();

    }

}
