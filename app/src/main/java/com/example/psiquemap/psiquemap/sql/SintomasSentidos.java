package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.entidades.Sintoma;
import com.example.psiquemap.psiquemap.entidades.SintomaSentido;

import java.util.ArrayList;

/**
 * Created by yuki on 08/11/16.
 */

public class SintomasSentidos {

    private SQLiteDatabase conn;

    public SintomasSentidos(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(SintomaSentido sintomaSentido)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",sintomaSentido.getIdPaciente());
        values.put("_id_CATEGORIA",sintomaSentido.getIdCategoria());
        values.put("_id_SINTOMA",sintomaSentido.getIdSintoma());
        values.put("NOME",sintomaSentido.getNome());
        values.put("DATA",sintomaSentido.getData());

        return values;
    }

    public void insert(SintomaSentido sintomaSentido)
    {
        if(this.hasSintoma(sintomaSentido))
            this.update(sintomaSentido);
        else
            conn.insertOrThrow("SINTOMAS_SENTIDOS", null, preencheContentValues(sintomaSentido));
    }

    public boolean hasSintoma(Sintoma sintomaSentido)
    {
        Cursor cursor = conn.query("SINTOMAS_SENTIDOS",null,"_id_CATEGORIA = ? AND _id_SINTOMA = ?",new String[]{sintomaSentido.getIdCategoria(),sintomaSentido.getIdSintoma()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public boolean hasSintoma(SintomaSentido sintomaSentido)
    {
        Cursor cursor = conn.query("SINTOMAS_SENTIDOS",null,"_id_CATEGORIA = ? AND _id_SINTOMA = ?",new String[]{sintomaSentido.getIdCategoria(),sintomaSentido.getIdSintoma()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }


    public void update(SintomaSentido sintomaSentido)
    {
        conn.update("SINTOMAS_SENTIDOS",preencheContentValues(sintomaSentido),"_id_CATEGORIA = ? AND _id_SINTOMA = ?",new String[]{sintomaSentido.getIdCategoria(),sintomaSentido.getIdSintoma()});
    }

    public void delete(String idCat,String idSint)
    {
        conn.delete("SINTOMAS_SENTIDOS","_id_CATEGORIA = ? AND _id_SINTOMA = ?",new String[]{idCat,idSint});
    }


    public ArrayAdapter<String> getSintomasSentidos(Context context)
    {
        ArrayAdapter<String> sintomasSentidos = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("SINTOMAS_SENTIDOS",null,"_id_PACIENTE = ? AND DATA = ?",new String[]{MetodosEmComum.getIdPaciente(context),MetodosEmComum.getDataAtual()},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                String sintoma = cursor.getString(cursor.getColumnIndex("NOME"));

                sintomasSentidos.add(sintoma);

            } while (cursor.moveToNext());
        }

        return sintomasSentidos;
    }

    public ArrayList<SintomaSentido> getSintomasSentidos(String idPaciente)
    {
        ArrayList<SintomaSentido> ret = null;

        Cursor cursor = conn.query("SINTOMAS_SENTIDOS",null,"_id_PACIENTE = ?",new String[]{idPaciente},null,null,null);

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();

            ret = new ArrayList<>();

            do
            {
                SintomaSentido resp = new SintomaSentido();
                resp.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                resp.setIdCategoria(cursor.getString(cursor.getColumnIndex("_id_CATEGORIA")));
                resp.setIdSintoma(cursor.getString(cursor.getColumnIndex("_id_SINTOMA")));
                resp.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                resp.setData(cursor.getString(cursor.getColumnIndex("DATA")));

                Log.i("Sintoma sentido",resp.getNome());
                ret.add(resp);

            }while (cursor.moveToNext());

        }

        return ret;
    }
}
