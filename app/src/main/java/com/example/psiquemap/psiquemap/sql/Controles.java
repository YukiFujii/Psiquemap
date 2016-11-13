package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.psiquemap.psiquemap.entidades.Controle;

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

        return values;
    }

    public void insert(Controle controle)
    {
        if(this.hasControle(controle))
            this.update(controle);
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
}
