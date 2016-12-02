package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;
import com.example.psiquemap.psiquemap.entidades.Sintoma;

/**
 * Created by yuki on 01/11/16.
 */

public class Sintomas
{
    private static SQLiteDatabase conn;

    public Sintomas(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private static ContentValues preencheContentValues(Sintoma sintoma)
    {
        ContentValues values = new ContentValues();

        values.put("_id_CATEGORIA",sintoma.getIdCategoria());
        values.put("_id_SINTOMA",sintoma.getIdSintoma());
        values.put("NOME",sintoma.getNome());

        return values;
    }

    public static void insert(Sintoma sintoma,SQLiteDatabase c)
    {
        conn = c;

        if(hasSintoma(sintoma))
            update(sintoma);
        else
            conn.insertOrThrow("SINTOMAS", null, preencheContentValues(sintoma));
    }

    public static boolean hasSintoma(Sintoma sintoma)
    {
        Cursor cursor = conn.query("SINTOMAS",null,"_id_CATEGORIA = ? AND _id_SINTOMA = ?",new String[]{sintoma.getIdCategoria(),sintoma.getIdSintoma()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public static void update(Sintoma sintoma,SQLiteDatabase c)
    {
        conn =c;
        conn.update("SINTOMAS",preencheContentValues(sintoma),"_id_SINTOMA = ?",new String[]{sintoma.getIdSintoma()});
    }

    private static void update(Sintoma sintoma)
    {
        conn.update("SINTOMAS",preencheContentValues(sintoma),"_id_SINTOMA = ?",new String[]{sintoma.getIdSintoma()});
    }

    public void delete(String id)
    {
        conn.delete("SINTOMAS","_id_SINTOMA = ?",new String[]{id});
    }

    public ArrayAdapter<String> getTodosSintomas(Context context)
    {
        ArrayAdapter<String> todosSintomas = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("SINTOMAS",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                String sintoma = cursor.getString(cursor.getColumnIndex("NOME"));

                todosSintomas.add(sintoma);

            } while (cursor.moveToNext());
        }

        return todosSintomas;
    }

    public Sintoma getSintomaPorNome(String nome)
    {
        Sintoma sintoma = new Sintoma();

        Cursor cursor = conn.query("SINTOMAS",null,"NOME = ?",new String[]{nome},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            sintoma.setIdCategoria(cursor.getString(cursor.getColumnIndex("_id_CATEGORIA")));
            sintoma.setIdSintoma(cursor.getString(cursor.getColumnIndex("_id_SINTOMA")));
            sintoma.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        }

        return sintoma;
    }

}
