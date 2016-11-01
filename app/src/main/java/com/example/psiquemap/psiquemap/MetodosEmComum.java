package com.example.psiquemap.psiquemap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public static String getHoraAtual()
    {
        Calendar calendar = Calendar.getInstance();
        DateFormat f24h = new SimpleDateFormat("HH:mm");
        String str = f24h.format(calendar.getTime());

        return str;
    }


}
