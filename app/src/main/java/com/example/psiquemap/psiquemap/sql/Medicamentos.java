package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Medicamento;

/**
 * Created by yuki on 09/11/16.
 */

public class Medicamentos
{
    private SQLiteDatabase conn;

    public Medicamentos(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Medicamento medicamento)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",medicamento.getIdPaciente());
        values.put("_id_MEDICACAO",medicamento.getIdMedicacao());
        values.put("NOME_MEDICACAO",medicamento.getNomeMedicacao());
        values.put("INTERVALO",medicamento.getIntervalo());
        values.put("DOSE",medicamento.getDosagem());
        values.put("DURANTE",medicamento.getDurante());
        values.put("OBSERVACOES",medicamento.getObservacoes());
        values.put("ULTIMO_HORARIO",medicamento.getUltimoHorario());
        values.put("PROXIMO_HORARIO",medicamento.getProximoHorario());
        values.put("MEDICACAO_CONTINUA",medicamento.getMedicacaoContinua());
        values.put("QTD_REST_MEDICAMENTO",medicamento.getQtdRestantesDoMedicamento());
        values.put("ALARME_ATIVO",medicamento.getAlarmeAtivo());
        return values;
    }

    public void insert(Medicamento medicamento)
    {
        if(this.hasPergunta(medicamento))
            this.update(medicamento);
        else
            conn.insertOrThrow("MEDICAMENTOS", null, preencheContentValues(medicamento));
    }

    private boolean hasPergunta(Medicamento medicamento)
    {
        Cursor cursor = conn.query("MEDICAMENTOS",null,"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{medicamento.getIdPaciente(),medicamento.getIdMedicacao()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void update(Medicamento medicamento)
    {
        conn.update("MEDICAMENTOS",preencheContentValues(medicamento),"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{medicamento.getIdPaciente(),medicamento.getIdMedicacao()});
    }

    public void delete(String idPaciente,String idMedicacao)
    {
        conn.delete("MEDICAMENTOS","_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{idPaciente,idMedicacao});
    }

    public ArrayAdapter<Medicamento> getTodosMedicamentos(Context context)
    {
        ArrayAdapter<Medicamento> todosMedicamentos = new ArrayAdapter<Medicamento>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("MEDICAMENTOS",null,"_id_PACIENTE = ?",new String[]{Controle.getIdPaciente()},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                Medicamento medicamento = new Medicamento();
                medicamento.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                medicamento.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
                medicamento.setNomeMedicacao(cursor.getString(cursor.getColumnIndex("NOME_MEDICACAO")));
                medicamento.setIntervalo(cursor.getInt(cursor.getColumnIndex("INTERVALO")));
                medicamento.setDosagem(cursor.getInt(cursor.getColumnIndex("DOSE")));
                medicamento.setDurante(cursor.getInt(cursor.getColumnIndex("DURANTE")));
                medicamento.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
                medicamento.setUltimoHorario(cursor.getString(cursor.getColumnIndex("ULTIMO_HORARIO")));
                medicamento.setProximoHorario(cursor.getString(cursor.getColumnIndex("PROXIMO_HORARIO")));
                medicamento.setMedicacaoContinua(cursor.getInt(cursor.getColumnIndex("MEDICACAO_CONTINUA")));
                medicamento.setQtdRestantesDoMedicamento(cursor.getInt(cursor.getColumnIndex("QTD_REST_MEDICAMENTO")));
                medicamento.setAlarmeAtivo(cursor.getInt(cursor.getColumnIndex("ALARME_ATIVO")));

                todosMedicamentos.add(medicamento);

            } while (cursor.moveToNext());
        }

        return todosMedicamentos;
    }

    public Medicamento getMedicamento(String idPaciente,String idMedicacao)
    {
        Medicamento medicamento = null;

        Cursor cursor = conn.query("MEDICAMENTOS",null,"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{idPaciente,idMedicacao},null,null,null);

        Log.i("Medicamento encontrado",""+cursor.getCount());
        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
                medicamento = new Medicamento();
                medicamento.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                medicamento.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
                medicamento.setNomeMedicacao(cursor.getString(cursor.getColumnIndex("NOME_MEDICACAO")));
                medicamento.setIntervalo(cursor.getInt(cursor.getColumnIndex("INTERVALO")));
                medicamento.setDosagem(cursor.getInt(cursor.getColumnIndex("DOSE")));
                medicamento.setDurante(cursor.getInt(cursor.getColumnIndex("DURANTE")));
                medicamento.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
                medicamento.setUltimoHorario(cursor.getString(cursor.getColumnIndex("ULTIMO_HORARIO")));
                medicamento.setProximoHorario(cursor.getString(cursor.getColumnIndex("PROXIMO_HORARIO")));
                medicamento.setMedicacaoContinua(cursor.getInt(cursor.getColumnIndex("MEDICACAO_CONTINUA")));
                medicamento.setQtdRestantesDoMedicamento(cursor.getInt(cursor.getColumnIndex("QTD_REST_MEDICAMENTO")));
                medicamento.setAlarmeAtivo(cursor.getInt(cursor.getColumnIndex("ALARME_ATIVO")));
        }

        Log.i("GET MEDICAMENTO",medicamento.toString());
        return medicamento;
    }

    public boolean deletarMedicamento(Medicamento medicamento)
    {
        if(medicamento.getQtdRestantesDoMedicamento()==0)
        {
            this.delete(medicamento.getIdPaciente(), medicamento.getIdMedicacao());
            return true;
        }

        return false;
    }

}
