package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;

/**
 * Created by yuki on 31/10/16.
 */

public class PerguntasDoQuestionarioMINI
{
    private SQLiteDatabase conn;

    public PerguntasDoQuestionarioMINI (SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(PerguntaDoQuestionario pergunta)
    {
        ContentValues values = new ContentValues();

        values.put("_id",pergunta.getPerguntaId());
        values.put("MODULO",pergunta.getModulo());
        values.put("QUESTAO",pergunta.getQuestao());
        values.put("PERGUNTA",pergunta.getPergunta());
        values.put("TIPO_PERGUNTA",pergunta.getTipoPergunta());
        values.put("FOI_RESPONDIDA",pergunta.getFoiRespondida());

        Log.i("PREENCHER","OK");

        return values;
    }

    public void insertPerguntaDoQuestionarioMINI(PerguntaDoQuestionario pergunta)
    {
        if(this.hasPergunta(pergunta))
            this.update(pergunta);
        else
            conn.insertOrThrow("PERGUNTAS_DO_QUESTIONARIO_MINI",null,this.preencheContentValues(pergunta));
    }

    private boolean hasPergunta(PerguntaDoQuestionario pergunta)
    {
        Cursor cursor = conn.query("PERGUNTAS_DO_QUESTIONARIO_MINI",null,"_id = ? AND QUESTAO = ?",new String[]{pergunta.getPerguntaId(),pergunta.getQuestao()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void update(PerguntaDoQuestionario pergunta)
    {
        conn.update("PERGUNTAS_DO_QUESTIONARIO_MINI",preencheContentValues(pergunta),"_id = ? AND QUESTAO = ?",new String[]{pergunta.getPerguntaId(),pergunta.getQuestao()});
    }

    public void delete(String id, String questao)
    {
        conn.delete("PERGUNTAS_DO_QUESTIONARIO_MINI","_id = ? AND QUESTAO = ?",new String[]{id,questao});
    }

    public PerguntaDoQuestionario getPerguntaQuestionarioMINI ()
    {
        PerguntaDoQuestionario pergunta = null;

        Cursor cursor = conn.query("PERGUNTAS_DO_QUESTIONARIO_MINI",null,"FOI_RESPONDIDA = ?",new String[]{"0"},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            pergunta = new PerguntaDoQuestionario();
            pergunta.setPerguntaId(cursor.getString(cursor.getColumnIndex("_id")));
            pergunta.setModulo(cursor.getString(cursor.getColumnIndex("MODULO")));
            pergunta.setQuestao(cursor.getString(cursor.getColumnIndex("QUESTAO")));
            pergunta.setPergunta(cursor.getString(cursor.getColumnIndex("PERGUNTA")));
            pergunta.setTipoPergunta(cursor.getString(cursor.getColumnIndex("TIPO_PERGUNTA")));
            pergunta.setFoiRespondida(cursor.getInt(cursor.getColumnIndex("FOI_RESPONDIDA")));
        }

        return pergunta;
    }

    public int getPerguntasRestantes ()
    {
        Cursor cursor = conn.query("PERGUNTAS_DO_QUESTIONARIO_MINI",null,"FOI_RESPONDIDA = ?",new String[]{"0"},null,null,null);

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
        Cursor cursor = conn.query("PERGUNTAS_DO_QUESTIONARIO_MINI",null,null,null,null,null,null);

        int ret = cursor.getCount();

        return ret;
    }
}
