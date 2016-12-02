package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;

/**
 * Created by yuki on 30/10/16.
 */

public class PerguntasDoDiario
{
    private static SQLiteDatabase conn;

    public PerguntasDoDiario(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private static ContentValues preencheContentValues(PerguntaDoQuestionario pergunta)
    {
        ContentValues values = new ContentValues();

        values.put("_id",pergunta.getPerguntaId());
        values.put("PERGUNTA",pergunta.getPergunta());
        values.put("TIPO_PERGUNTA",pergunta.getTipoPergunta());
        values.put("FOI_RESPONDIDA",pergunta.getFoiRespondida());

        return values;
    }

    public static void insert(PerguntaDoQuestionario pergunta,SQLiteDatabase c)
    {
        conn = c;
        if(hasPergunta(pergunta))
            update(pergunta);
        else
            conn.insertOrThrow("PERGUNTAS_DO_DIARIO", null, preencheContentValues(pergunta));
    }

    private static boolean hasPergunta(PerguntaDoQuestionario pergunta)
    {
        Cursor cursor = conn.query("PERGUNTAS_DO_DIARIO",null,"_id = ?",new String[]{pergunta.getPerguntaId()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void update(PerguntaDoQuestionario pergunta)
    {
        conn.update("PERGUNTAS_DO_DIARIO",preencheContentValues(pergunta),"_id = ?",new String[]{pergunta.getPerguntaId()});
    }

    public void delete(String id)
    {
        conn.delete("PERGUNTAS_DO_DIARIO","_id = ?",new String[]{id});
    }

    public PerguntaDoQuestionario getPerguntaDiario ()
    {
        PerguntaDoQuestionario pergunta = null;

        Cursor cursor = conn.query("PERGUNTAS_DO_DIARIO",null,"FOI_RESPONDIDA = ?",new String[]{"0"},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            pergunta = new PerguntaDoQuestionario();
            pergunta.setPerguntaId(cursor.getString(cursor.getColumnIndex("_id")));
            pergunta.setPergunta(cursor.getString(cursor.getColumnIndex("PERGUNTA")));
            pergunta.setTipoPergunta(cursor.getString(cursor.getColumnIndex("TIPO_PERGUNTA")));
            pergunta.setFoiRespondida(cursor.getInt(cursor.getColumnIndex("FOI_RESPONDIDA")));
        }

        return pergunta;
    }

    public int getPerguntasRestantes ()
    {
        Cursor cursor = conn.query("PERGUNTAS_DO_DIARIO",null,"FOI_RESPONDIDA = ?",new String[]{"0"},null,null,null);

        int ret = cursor.getCount();

        return ret;
    }

    public int getIndexPerguntasAtual ()
    {

        int ret = this.getTotalPerguntas()-this.getPerguntasRestantes()+1;

        return ret;
    }

    public int getTotalPerguntas ()
    {
        Cursor cursor = conn.query("PERGUNTAS_DO_DIARIO",null,null,null,null,null,null);

        int ret = cursor.getCount();

        return ret;
    }

}
