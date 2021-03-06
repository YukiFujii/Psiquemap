package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.psiquemap.psiquemap.entidades.Feedback;

import java.util.ArrayList;

/**
 * Created by yuki on 08/11/16.
 */

public class Feedbacks
{
    private SQLiteDatabase conn;

    public Feedbacks(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Feedback feedback)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",feedback.getIdPaciente());
        values.put("DATA",feedback.getData());
        values.put("SENTIMENTO",feedback.getSentimento());
        values.put("OBSERVACAO",feedback.getObservacao());
        return values;
    }

    public void insert(Feedback feedback)
    {
        if(!this.hasFeedback(feedback))
            conn.insertOrThrow("FEEDBACK", null, preencheContentValues(feedback));
    }

    public boolean hasFeedback(Feedback feedback)
    {
        Cursor cursor = conn.query("FEEDBACK",null,"_id_PACIENTE = ? AND DATA = ?",new String[]{feedback.getIdPaciente(),feedback.getData()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public boolean hasFeedback(String idPaciente,String data)
    {
        Cursor cursor = conn.query("FEEDBACK",null,"_id_PACIENTE = ? AND DATA = ?",new String[]{idPaciente,data},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void update(Feedback feedback)
    {
        conn.update("FEEDBACK",preencheContentValues(feedback),"_id_PACIENTE = ? AND DATA = ?",new String[]{feedback.getIdPaciente(),feedback.getData()});
    }

    public void delete(String idPaciente,String data)
    {
        conn.delete("FEEDBACK","_id_PACIENTE = ? AND DATA = ?",new String[]{idPaciente,data});
    }

    public ArrayList<Feedback> getFeedbacks(String idPaciente)
    {
        ArrayList<Feedback> ret = null;

        Cursor cursor = conn.query("FEEDBACK",null,"_id_PACIENTE = ?",new String[]{idPaciente},null,null,null);

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();

            ret = new ArrayList<>();

            do
            {
                Feedback resp = new Feedback();
                resp.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                resp.setData(cursor.getString(cursor.getColumnIndex("DATA")));
                resp.setSentimento(cursor.getString(cursor.getColumnIndex("SENTIMENTO")));
                resp.setObservacao(cursor.getString(cursor.getColumnIndex("OBSERVACAO")));
                ret.add(resp);

            }while (cursor.moveToNext());

        }

        return ret;
    }
}
