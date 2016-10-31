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

        Log.i("PERGUNTAS_DO_DIARIO","criado com sucesso!");

        return sqlBuilder.toString();
    }

}
