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


    public Alarmes(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Alarme alarme)
    {
        ContentValues values = new ContentValues();

        values.put("_id_PACIENTE",alarme.getIdPaciente());
        values.put("_id_MEDICACAO",alarme.getIdMedicacao());
        values.put("HORA_DO_ALARME",alarme.getHoraDoAlarme());
        values.put("ORDEM",alarme.getOrdem());

        return values;
    }

    public void insert(Alarme alarme)
    {
        if(this.hasAlarme(alarme))
            update(alarme);
        else
            conn.insertOrThrow("PROXIMO_ALARME",null,this.preencheContentValues(alarme));

        Log.i("Inserindo alarme",""+alarme);
    }

    public void insertEmOrdem(Alarme alarmeIn)
    {
        Log.i("Chamou","insertEmOrdem");

        if(this.hasAlarme(alarmeIn))
            this.deletarEmOrdem(alarmeIn.getIdPaciente(),alarmeIn.getIdMedicacao());

        Cursor cursor = conn.query("PROXIMO_ALARME",null,null,null,null,null,"ORDEM");

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            boolean flagAchouOrdem = false;

            do
            {
                Alarme alarme = new Alarme();
                alarme.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                alarme.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
                alarme.setHoraDoAlarme(cursor.getString(cursor.getColumnIndex("HORA_DO_ALARME")));
                alarme.setOrdem(cursor.getInt(cursor.getColumnIndex("ORDEM")));

                Log.i("Alarme do BD",alarme.toString());
                Log.i("Alarme entrando",alarmeIn.toString());

                if(!flagAchouOrdem)
                {
                    if (MetodosEmComum.ajusteData(MetodosEmComum.stringToCalendar(alarmeIn.getHoraDoAlarme())).before(MetodosEmComum.ajusteData(MetodosEmComum.stringToCalendar(alarme.getHoraDoAlarme()))))
                    {
                        Log.i("Alarme que ta entrando", "vem antes");

                        alarmeIn.setOrdem(alarme.getOrdem());
                        this.insert(alarmeIn);

                        alarme.setOrdem(alarme.getOrdem()+1);
                        this.update(alarme);
                        flagAchouOrdem = true;
                    }
                    else
                    {
                        Log.i("Alarme que ta entrando", "vem depois");

                        if(cursor.isLast())
                        {
                            Log.i("Cursor", "não tem proximo");
                            alarmeIn.setOrdem(alarme.getOrdem() + 1);
                            this.insert(alarmeIn);
                        }
                    }
                }
                else
                {
                    Log.i("", "Adicionando +1 ao get ordem dos proximos");
                    alarme.setOrdem(alarme.getOrdem()+1);
                    update(alarme);
                }

                Log.i("flagAchouOrdem", ""+flagAchouOrdem);

            } while (cursor.moveToNext());

        }
        else
        {
            alarmeIn.setOrdem(1);
            this.insert(alarmeIn);
            Log.i("Entrada","direta");
            Log.i("Alarme entrando",alarmeIn.toString());
        }

        Cursor cursor2 = conn.query("PROXIMO_ALARME",null,null,null,null,null,"ORDEM");
        cursor2.moveToFirst();

        if(cursor2.getCount()>0)
        {
            do {
                Alarme alarme = new Alarme();
                alarme.setIdPaciente(cursor2.getString(cursor2.getColumnIndex("_id_PACIENTE")));
                alarme.setIdMedicacao(cursor2.getString(cursor2.getColumnIndex("_id_MEDICACAO")));
                alarme.setHoraDoAlarme(cursor2.getString(cursor2.getColumnIndex("HORA_DO_ALARME")));
                alarme.setOrdem(cursor2.getInt(cursor2.getColumnIndex("ORDEM")));

                Log.i("Alarme", alarme.toString());

            } while (cursor2.moveToNext());
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
        Log.i("Chamou","update");
        conn.update("PROXIMO_ALARME",preencheContentValues(alarme),"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{alarme.getIdPaciente(),alarme.getIdMedicacao()});
    }

    public void delete(String idPac,String idMed)
    {
        Log.i("Chamou","delete");
        conn.delete("PROXIMO_ALARME","_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{idPac,idMed});
    }

    public void deletarEmOrdem(String idPac,String idMed)
    {
        Log.i("Chamou","deletarEmOrdem");
        Cursor cursor = conn.query("PROXIMO_ALARME",null,"_id_PACIENTE = ? AND _id_MEDICACAO = ?",new String[]{idPac,idMed},null,null,null);
        cursor.moveToFirst();

        int ordem = cursor.getInt(cursor.getColumnIndex("ORDEM"));

        this.delete(idPac,idMed);

        cursor = conn.query("PROXIMO_ALARME",null,"ORDEM > ?",new String[]{ordem+""},null,null,null);

        Log.i("QTD alarmes > que"+ordem,cursor.getCount()+"");

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                Alarme alarme = new Alarme();
                alarme.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
                alarme.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
                alarme.setHoraDoAlarme(cursor.getString(cursor.getColumnIndex("HORA_DO_ALARME")));
                alarme.setOrdem(cursor.getInt(cursor.getColumnIndex("ORDEM")));
                alarme.setOrdem(alarme.getOrdem() - 1);

                Log.i("ALARME ATUALIZADO",alarme.toString());

                this.update(alarme);

            }while (cursor.moveToNext());
        }

    }

    public Alarme pegarProximoAlarme()
    {
        Log.i("Chamou","pegarProximoAlarme");

        Alarme alarme = null;

        Cursor cursor = conn.query("PROXIMO_ALARME",null,"ORDEM = ?",new String[]{"1"},null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            alarme = new Alarme();
            alarme.setIdPaciente(cursor.getString(cursor.getColumnIndex("_id_PACIENTE")));
            alarme.setIdMedicacao(cursor.getString(cursor.getColumnIndex("_id_MEDICACAO")));
            alarme.setHoraDoAlarme(cursor.getString(cursor.getColumnIndex("HORA_DO_ALARME")));
            alarme.setOrdem(cursor.getInt(cursor.getColumnIndex("ORDEM")));
        }

        Log.i("Alarme Pego",alarme.toString());

        return alarme;
    }

    public boolean temProximoAlarme()
    {
        Log.i("Chamou","temProximoAlarme");

        boolean ret;
        Cursor cursor = conn.query("PROXIMO_ALARME",null,"ORDEM = ?",new String[]{"1"},null,null,null);

        if(cursor.getCount()>0)
            ret = true;
        else
            ret = false;

        Log.i("TEM PROXIMO ALARME",""+ret);

        return ret;
    }

    /*private String tempoRestante(String hora)
    {
        Log.i("Hora que entrou",hora);
        Calendar calendar = Calendar.getInstance();

        Calendar cal = MetodosEmComum.stringToCalendar(hora);
        cal.add(Calendar.MINUTE,-(calendar.get(Calendar.MINUTE)));
        cal.add(Calendar.HOUR_OF_DAY,-(calendar.get(Calendar.HOUR_OF_DAY)));

        String ret = MetodosEmComum.horaToString(cal);
        Log.i("Hora que saiu",ret);
        return ret;
    }*/

}
