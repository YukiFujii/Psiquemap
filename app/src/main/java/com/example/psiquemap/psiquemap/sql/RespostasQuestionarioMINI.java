package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.psiquemap.psiquemap.entidades.RespostaQuestionarioMINI;

import java.util.ArrayList;

/**
 * Created by yuki on 11/11/16.
 */

public class RespostasQuestionarioMINI
{
    private SQLiteDatabase conn;

    public RespostasQuestionarioMINI (SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(RespostaQuestionarioMINI resposta)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",resposta.getIdPaciente());
        values.put("DATA",resposta.getData());
        values.put("_id_MODULO",resposta.getIdModulo());
        values.put("QUESTAO",resposta.getQuestao());
        values.put("RESPOSTA",resposta.getResposta());

        return values;
    }

    public void insert(RespostaQuestionarioMINI resposta)
    {
        if(!this.hasResposta(resposta))
            conn.insertOrThrow("RESPOSTAS_DO_QUESTIONARIO_MINI",null,this.preencheContentValues(resposta));

        Log.i("Inserindo resposta",resposta.toString());
    }

    private boolean hasResposta(RespostaQuestionarioMINI resposta)
    {
        Cursor cursor = conn.query("RESPOSTAS_DO_QUESTIONARIO_MINI",null,"_id_PACIENTE = ? AND DATA = ? AND _id_MODULO = ? AND QUESTAO = ?",
                new String[]{resposta.getIdPaciente(),resposta.getData(),resposta.getIdModulo(),resposta.getQuestao()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public boolean hasResposta(String idPaciente, String data, String idModulo, String questao)
    {
        Cursor cursor = conn.query("RESPOSTAS_DO_QUESTIONARIO_MINI",null,"_id_PACIENTE = ? AND DATA = ? AND _id_MODULO = ? AND QUESTAO = ?",
                new String[]{idPaciente,data,idModulo,questao},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void update(RespostaQuestionarioMINI resposta)
    {
        conn.update("RESPOSTAS_DO_QUESTIONARIO_MINI",preencheContentValues(resposta),"_id_PACIENTE = ? AND DATA = ? AND _id_MODULO = ? AND QUESTAO = ?",
                new String[]{resposta.getIdPaciente(),resposta.getData(),resposta.getIdModulo(),resposta.getQuestao()});
    }

    public void delete(String idPaciente, String data, String idModulo, String questao)
    {
        conn.delete("RESPOSTAS_DO_QUESTIONARIO_MINI","_id_PACIENTE = ? AND DATA = ? AND _id_MODULO = ? AND QUESTAO = ?",
                new String[]{idPaciente,data,idModulo,questao});
    }

    public ArrayList<RespostaQuestionarioMINI> getRespostasQuestMini(String idPaciente)
    {
        ArrayList<RespostaQuestionarioMINI> ret = null;

        Cursor cursor = conn.query("RESPOSTAS_DO_QUESTIONARIO_MINI",null,"_id_PACIENTE = ?",new String[]{idPaciente},null,null,null);

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();

            ret = new ArrayList<>();

            do
            {
                RespostaQuestionarioMINI resp = new RespostaQuestionarioMINI();
                resp.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                resp.setData(cursor.getString(cursor.getColumnIndex("DATA")));
                resp.setIdModulo(cursor.getString(cursor.getColumnIndex("_id_MODULO")));
                resp.setQuestao(cursor.getString(cursor.getColumnIndex("QUESTAO")));
                resp.setResposta(cursor.getString(cursor.getColumnIndex("RESPOSTA")));
                ret.add(resp);

            }while (cursor.moveToNext());

        }

        return ret;

    }

}

