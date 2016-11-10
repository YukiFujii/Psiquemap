package com.example.psiquemap.psiquemap.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.entidades.Alarme;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuki on 09/11/16.
 */

public class Alarmes
{
    private SQLiteDatabase conn;
    private static String idMedicacao = "";
    private static String idPaciente = "";
    private static Calendar proxHorario = null;

    public Alarmes(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Alarme alarme)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",alarme.getIdPaciente());
        values.put("_id_MEDICACAO",alarme.getIdMedicacao());
        String tempoRestante = MetodosEmComum.horaToString(this.tempoRestante(MetodosEmComum.stringToCalendar(alarme.getTempoRestante())));
        values.put("TEMPO_RESTANTE",tempoRestante);
        values.put("E_O_PROXIMO",alarme.geteProximo());

        return values;
    }

    public void insert(Alarme alarme)
    {
        if(hasAlarme(alarme))
        {
            Log.i("Entrou no","JA TEM");
            update(alarme);

            Cursor cursor = conn.query("PROXIMO_ALARME",null,null,null,null,null,null);
            Log.i("ALARMES APOS ATUALIZAR",""+cursor.getCount());
        }

        else
        {
            conn.insertOrThrow("PROXIMO_ALARME", null, preencheContentValues(alarme));
            ordenarAlarmes();
            Cursor cursor = conn.query("PROXIMO_ALARME",null,null,null,null,null,null);
            Log.i("ALARMES APOS INSERIR",""+cursor.getCount());
        }
    }

    private boolean hasAlarme(Alarme alarme)
    {
        Cursor cursor = conn.query("PROXIMO_ALARME",null,"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{alarme.getIdPaciente(),alarme.getIdMedicacao()},null,null,null);

        if (cursor.getCount()==0)
            return false;
        else
            return true;
    }

    public void update(Alarme alarme)
    {
        conn.update("PROXIMO_ALARME",preencheContentValues(alarme),"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{alarme.getIdPaciente(),alarme.getIdMedicacao()});
    }

    public void delete(String idPac,String idMed)
    {
        if(idPac.equals(idPaciente)&&idMed.equals(idMedicacao))
        {
            idMedicacao = "";
            idPaciente = "";
            proxHorario = null;
        }

        conn.delete("PROXIMO_ALARME","_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{idPac,idMed});
        ordenarAlarmes();

        Cursor cursor = conn.query("PROXIMO_ALARME",null,null,null,null,null,null);
        Log.i("ALARMES APOS DELETAR",""+cursor.getCount());
    }

    public void ordenarAlarmes()
    {
        Cursor cursor = conn.query("PROXIMO_ALARME",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("QTD",""+cursor.getCount());

        if(cursor.getCount()>0)
        {


            do {
                Alarme alarme = new Alarme();
                alarme.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                alarme.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
                alarme.setTempoRestante(cursor.getString(cursor.getColumnIndex("TEMPO_RESTANTE")));
                alarme.seteProximo(cursor.getInt(cursor.getColumnIndex("E_O_PROXIMO")));

                Log.i("ALARME ATRIBUTOS",alarme.toString());

                if (idMedicacao.equals(""))
                {
                    Log.i("idMedicacao","");
                    idMedicacao = alarme.getIdMedicacao();
                    idPaciente = alarme.getIdPaciente();
                    proxHorario = MetodosEmComum.stringToCalendar(alarme.getTempoRestante());
                    this.inverterValorEhProximo(alarme.getIdPaciente(),alarme.getIdMedicacao());
                    Log.i("TEMPO SUBTRAIDO", MetodosEmComum.horaToString(proxHorario));
                } else
                {
                    Log.i("idMedicacao",alarme.getIdMedicacao());
                    int resul = proxHorario.compareTo(tempoRestante(MetodosEmComum.stringToCalendar(alarme.getTempoRestante())));

                    Log.i("Resul Compare",""+proxHorario.compareTo(tempoRestante(MetodosEmComum.stringToCalendar(alarme.getTempoRestante()))));

                    if (resul > 0)
                    {
                        this.inverterValorEhProximo(idPaciente,idMedicacao);
                        idMedicacao = alarme.getIdMedicacao();
                        idPaciente = alarme.getIdPaciente();
                        proxHorario = MetodosEmComum.stringToCalendar(alarme.getTempoRestante());
                        this.inverterValorEhProximo(alarme.getIdPaciente(),alarme.getIdMedicacao());
                        Log.i("TEMPO SUBTRAIDO", MetodosEmComum.horaToString(proxHorario));
                    }
                }

            } while (cursor.moveToNext());

        }

    }

    private void inverterValorEhProximo(String idPaciente,String idMedicacao)
    {
        Alarme alarme = null;

        Cursor cursor = conn.query("PROXIMO_ALARME",null,"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{idPaciente,idMedicacao},null,null,null);

        cursor.moveToFirst();

        Log.i("INV. VAL. QTD",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            alarme = new Alarme();
            alarme.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
            alarme.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
            alarme.setTempoRestante(cursor.getString(cursor.getColumnIndex("TEMPO_RESTANTE")));
            alarme.seteProximo(cursor.getInt(cursor.getColumnIndex("E_O_PROXIMO")));

            if (alarme.geteProximo() == 0)
            {
                Log.i("geteProximo igual", "0");
                alarme.seteProximo(1);
                Log.i("Set to", "" + alarme.geteProximo());
            }
            else
            {
                Log.i("geteProximo igual", "1");
                alarme.seteProximo(0);
                Log.i("Set to", "" + alarme.geteProximo());
            }
        }

        this.insert(alarme);
    }

    public Alarme pegarProximoAlarme()
    {
        Alarme alarme = null;

        Cursor cursor = conn.query("PROXIMO_ALARME",null,"E_O_PROXIMO = ?",new String[]{"1"},null,null,null);

        Log.i("pegarProxAlarme QTD",""+cursor.getCount());

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            alarme = new Alarme();
            alarme.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
            alarme.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
            alarme.setTempoRestante(cursor.getString(cursor.getColumnIndex("TEMPO_RESTANTE")));
            alarme.seteProximo(cursor.getInt(cursor.getColumnIndex("E_O_PROXIMO")));
        }

        return alarme;
    }

    private Calendar tempoRestante(Calendar cal)
    {
        Calendar calendar = Calendar.getInstance();

        cal.add(Calendar.MINUTE,-(calendar.get(Calendar.MINUTE)));
        cal.add(Calendar.HOUR_OF_DAY,-(calendar.get(Calendar.HOUR_OF_DAY)));

        return cal;
    }
}
