package com.example.psiquemap.psiquemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.psiquemap.psiquemap.sql.DataBase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuki on 01/11/16.
 */

public final class MetodosEmComum
{
    public static String getDataAtual()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat data = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String str = data.format(calendar.getTime());

        return str;
    }

    public static String getDataAtual(Calendar calendar)
    {
        DateFormat data = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String str = data.format(calendar.getTime());

        return str;
    }

    public static String getHoraAtual()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat f24h = new SimpleDateFormat("HH:mm");
        String str = f24h.format(calendar.getTime());

        return str;
    }

    public static String horaToString(Calendar calendar)
    {
        Date hora = calendar.getTime();

        DateFormat data = new SimpleDateFormat("HH:mm");
        String str = data.format(hora);

        return str;
    }

    public static Calendar stringToCalendar(String horas)
    {
        Date hora=null;
        DateFormat data = new SimpleDateFormat("HH:mm");
        try {
             hora = data.parse(horas);
            Log.i("Conversão to date","sucess");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,hora.getMinutes());
        calendar.set(Calendar.HOUR_OF_DAY,hora.getHours());
        calendar.set(Calendar.SECOND,0);

        Log.i("Calender com hora",MetodosEmComum.horaToString(calendar));

        return calendar;
    }

    public static Calendar ajusteData(Calendar calendar)
    {
        Calendar cal = Calendar.getInstance();

        if(calendar.getTimeInMillis()<cal.getTimeInMillis())
            calendar.add(Calendar.DATE,1);

        return calendar;
    }

    public static SQLiteDatabase conexaoBD(Context context)
    {
        SQLiteDatabase conn = null;

        try {

            DataBase dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();

        }
        catch (Exception ex)
        {
            ex.getStackTrace();
        }

        return conn;

    }



}
