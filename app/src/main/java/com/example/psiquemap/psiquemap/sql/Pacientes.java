package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.psiquemap.psiquemap.entidades.Paciente;

/**
 * Created by yuki on 23/10/16.
 */

public class Pacientes
{
    private SQLiteDatabase conn;

    public Pacientes(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Paciente paciente)
    {
        ContentValues values = new ContentValues();

        values.put("_id",paciente.getId());
        values.put("NOME_COMPLETO",paciente.getNomeCompleto());
        values.put("RUA",paciente.getRua());
        values.put("NUMERO",paciente.getNumero());
        values.put("CEP",paciente.getCep());
        values.put("DATA_NASC",paciente.getDataNasc());
        values.put("EMAIL",paciente.getEmail());
        values.put("RG",paciente.getRg());
        values.put("CPF",paciente.getCpf());
        values.put("TELEFONE",paciente.getTelefone());
        values.put("SENHA",paciente.getSenha());
        values.put("CNS",paciente.getCns());

        return values;
    }

    public void insert(Paciente paciente)
    {
        Cursor cursor = conn.query("PACIENTE",null,null,null,null,null,null);

        int qtdPacientes = cursor.getCount();

        if(qtdPacientes==0)
            conn.insertOrThrow("PACIENTE", null, preencheContentValues(paciente));
        else
            conn.update("PACIENTE",preencheContentValues(paciente),null,null);
    }

    public void update(Paciente paciente)
    {
        conn.update("PACIENTE",preencheContentValues(paciente),"_id = ?",new String[]{paciente.getId()+""});
    }

    public void delete(int id)
    {
        conn.delete("PACIENTE","_id = ?",new String[]{""+id});
    }

    public Paciente getPaciente (Context context)
    {
        Paciente paciente = new Paciente();

        Cursor cursor = conn.query("PACIENTE",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            paciente.setId(cursor.getString(0));
            paciente.setNomeCompleto(cursor.getString(1));
            paciente.setRua(cursor.getString(2));
            paciente.setNumero(cursor.getInt(3));
            paciente.setCep(cursor.getString(4));
            paciente.setDataNasc(cursor.getString(5));
            paciente.setEmail(cursor.getString(6));
            paciente.setRg(cursor.getString(7));
            paciente.setCpf(cursor.getString(8));
            paciente.setTelefone(cursor.getString(9));
            paciente.setSenha(cursor.getString(10));
            paciente.setCns(cursor.getString(11));
        }

        return paciente;
    }

}
