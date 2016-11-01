package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.entidades.Acontecimento;
import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by yuki on 01/11/16.
 */

public class Acontecimentos
{
    private SQLiteDatabase conn;

    public Acontecimentos (SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Acontecimento acontecimento)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",acontecimento.getIdPaciente());
        values.put("DATA",acontecimento.getData());
        values.put("HORA",acontecimento.getHora());
        values.put("SENTIMENTO",acontecimento.getSentimento());
        values.put("TITULO",acontecimento.getTitulo());
        values.put("DESCRICAO",acontecimento.getDescricao());

        return values;
    }

    public void insert(Acontecimento acontecimento)
    {
            conn.insertOrThrow("ACONTECIMENTOS", null, preencheContentValues(acontecimento));
    }

    public ArrayAdapter<Acontecimento> getAcontecimentos(Context context)
    {
        ArrayAdapter<Acontecimento> acontecimentos = new ArrayAdapter<Acontecimento>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("ACONTECIMENTOS",null,"_id_PACIENTE = ? AND DATA = ?",new String[]{Pacientes.getIdPaciente(),MetodosEmComum.getDataAtual()},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                Acontecimento acontecimento = new Acontecimento();
                acontecimento.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                acontecimento.setData(cursor.getString(cursor.getColumnIndex("DATA")));
                acontecimento.setHora(cursor.getString(cursor.getColumnIndex("HORA")));
                acontecimento.setSentimento(cursor.getString(cursor.getColumnIndex("SENTIMENTO")));
                acontecimento.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                acontecimento.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));

                acontecimentos.add(acontecimento);

            }while (cursor.moveToNext());
        }

        return acontecimentos;

    }

}
