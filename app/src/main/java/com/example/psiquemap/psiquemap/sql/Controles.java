package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.psiquemap.psiquemap.AcontecimentoDoDiario;
import com.example.psiquemap.psiquemap.FeedbackScreen;
import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.MinhaConta;
import com.example.psiquemap.psiquemap.SintomasScreen;
import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Paciente;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaUnica;

/**
 * Created by yuki on 13/11/16.
 */

public class Controles {

    private SQLiteDatabase conn;

    public Controles(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Controle controle)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",controle.getIdPaciente());
        values.put("FLAG_PACIENTE",controle.getFlagPaciente());
        values.put("FLAG_QUEST_DIARIO",controle.getFlagQuestDiario());
        values.put("FLAG_ACONTECIMENTO",controle.getFlagAcontecimento());
        values.put("FLAG_QUEST_MINI",controle.getFlagQuestMini());
        values.put("FLAG_SINTOMAS",controle.getFlagSintomas());
        values.put("FLAG_FEEDBACK",controle.getFlagFeedback());

        return values;
    }

    public void insert(Controle controle)
    {
        if(hasControle(controle))
            update(controle);
        else
            conn.insertOrThrow("CONTROLE", null, preencheContentValues(controle));
    }

    private boolean hasControle(Controle controle)
    {
        Cursor cursor = conn.query("CONTROLE",null,"_id_PACIENTE = ?",new String[]{controle.getIdPaciente()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public boolean hasControle()
    {
        Cursor cursor = conn.query("CONTROLE",null,null,null,null,null,null);
        Log.i("Count hasControle",cursor.getCount()+"");

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void update(Controle controle)
    {
        conn.update("CONTROLE",preencheContentValues(controle),"_id_PACIENTE = ?",new String[]{controle.getIdPaciente()});
    }

    public void delete(String id)
    {
        conn.delete("CONTROLE","_id_PACIENTE = ?",new String[]{id});
    }

    public String getIdPaciente ()
    {
        Cursor cursor = conn.query("CONTROLE",null,null,null,null,null,null);

        cursor.moveToFirst();

        String ret = cursor.getString(cursor.getColumnIndex("_id_PACIENTE"));

        return ret;
    }

    public Controle getControle (String idPaciente)
    {
        Controle controle = null;

        Cursor cursor = conn.query("CONTROLE",null,"_id_PACIENTE = ?",new String[]{idPaciente},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            controle = new Controle();
            controle.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
            controle.setFlagPaciente(cursor.getInt(cursor.getColumnIndex("FLAG_PACIENTE")));
            controle.setFlagQuestDiario(cursor.getInt(cursor.getColumnIndex("FLAG_QUEST_DIARIO")));
            controle.setFlagAcontecimento(cursor.getInt(cursor.getColumnIndex("FLAG_ACONTECIMENTO")));
            controle.setFlagQuestMini(cursor.getInt(cursor.getColumnIndex("FLAG_QUEST_MINI")));
            controle.setFlagSintomas(cursor.getInt(cursor.getColumnIndex("FLAG_SINTOMAS")));
            controle.setFlagFeedback(cursor.getInt(cursor.getColumnIndex("FLAG_FEEDBACK")));
            controle.setFlagPergDiario(cursor.getInt(cursor.getColumnIndex("FLAG_PERG_DIARIO")));
            controle.setFlagPergMini(cursor.getInt(cursor.getColumnIndex("FLAG_PERG_MINI")));
            controle.setFlagTodosSintomas(cursor.getInt(cursor.getColumnIndex("FLAG_TODOS_SINTOMAS")));
            controle.setFlagMedicamento(cursor.getInt(cursor.getColumnIndex("FLAG_MEDICAMENTO")));
        }

        return controle;
    }

    public void setFlagPaciente(String idPaciente,int valor)
    {
        conn = MetodosEmComum.conexaoBD(MinhaConta.thisContext);

        Controle controle = getControle(idPaciente);
        controle.setFlagPaciente(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagQuestMini(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagQuestMini(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagQuestDiario(String idPaciente, int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagQuestDiario(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagAcontecimento(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagAcontecimento(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagSintomas(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagSintomas(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagFeedback(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagFeedback(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagPergDiario(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagPergDiario(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagPergMini(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagPergMini(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagTodosSintomas(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagTodosSintomas(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

    public void setFlagMedicamento(String idPaciente,int valor)
    {
        Controle controle = getControle(idPaciente);
        controle.setFlagMedicamento(valor);
        Log.i("Controle",controle.toString());
        insert(controle);
    }

}
