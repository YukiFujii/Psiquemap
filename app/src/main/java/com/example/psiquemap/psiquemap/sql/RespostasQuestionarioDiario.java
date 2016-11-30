package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.psiquemap.psiquemap.entidades.RespostaQuestionarioDiario;

import java.util.ArrayList;

/**
 * Created by yuki on 11/11/16.
 */

public class RespostasQuestionarioDiario
{
    private SQLiteDatabase conn;

    public RespostasQuestionarioDiario (SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(RespostaQuestionarioDiario resposta)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",resposta.getIdPaciente());
        values.put("DATA",resposta.getData());
        values.put("_id_PERGUNTA",resposta.getIdPergunta());
        values.put("RESPOSTA",resposta.getResposta());

        return values;
    }

    public void insert(RespostaQuestionarioDiario resposta)
    {
        if(!this.hasResposta(resposta))
            conn.insertOrThrow("RESPOSTAS_DO_QUESTIONARIO_DIARIO",null,this.preencheContentValues(resposta));

        Log.i("Inserindo resposta",resposta.toString());
    }

    private boolean hasResposta(RespostaQuestionarioDiario resposta)
    {
        Cursor cursor = conn.query("RESPOSTAS_DO_QUESTIONARIO_DIARIO",null,"_id_PACIENTE = ? AND DATA = ? AND _id_PERGUNTA = ?",
                new String[]{resposta.getIdPaciente(),resposta.getData(),resposta.getIdPergunta()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public boolean hasResposta(String idPaciente, String data, String questao)
    {
        Cursor cursor = conn.query("RESPOSTAS_DO_QUESTIONARIO_DIARIO",null,"_id_PACIENTE = ? AND DATA = ? AND _id_PERGUNTA = ?",
                new String[]{idPaciente,data,questao},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void update(RespostaQuestionarioDiario resposta)
    {
        conn.update("RESPOSTAS_DO_QUESTIONARIO_DIARIO",preencheContentValues(resposta),"_id_PACIENTE = ? AND DATA = ? AND _id_PERGUNTA = ?",
                new String[]{resposta.getIdPaciente(),resposta.getData(),resposta.getIdPergunta()});
    }

    public void delete(String idPaciente, String data, String questao)
    {
        conn.delete("RESPOSTAS_DO_QUESTIONARIO_DIARIO","_id_PACIENTE = ? AND DATA = ? AND _id_PERGUNTA = ?",
                new String[]{idPaciente,data,questao});
    }

    public ArrayList<RespostaQuestionarioDiario> getRespostasQuestDiario(String idPaciente)
    {
        ArrayList<RespostaQuestionarioDiario> ret = null;

        Cursor cursor = conn.query("RESPOSTAS_DO_QUESTIONARIO_DIARIO",null,"_id_PACIENTE = ?",new String[]{idPaciente},null,null,null);

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();

            ret = new ArrayList<>();

            do
            {
                RespostaQuestionarioDiario resp = new RespostaQuestionarioDiario();
                resp.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                resp.setData(cursor.getString(cursor.getColumnIndex("DATA")));
                resp.setIdPergunta(cursor.getString(cursor.getColumnIndex("_id_PERGUNTA")));
                resp.setResposta(cursor.getString(cursor.getColumnIndex("RESPOSTA")));
                ret.add(resp);

            }while (cursor.moveToNext());

        }

        return ret;

    }

}
