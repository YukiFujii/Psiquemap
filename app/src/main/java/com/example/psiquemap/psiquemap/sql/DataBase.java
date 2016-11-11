package com.example.psiquemap.psiquemap.sql;

/**
 * Created by yuki on 23/10/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

public class DataBase extends SQLiteOpenHelper implements Serializable
{
    public DataBase (Context context)
    {
        super(context,"PSIQUEMAP",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(ScriptSQL.getPaciente());
        db.execSQL(ScriptSQL.getPerguntasDoDiario());
        db.execSQL(ScriptSQL.getRespostasDoQuestionarioDiario());
        db.execSQL(ScriptSQL.getPerguntasDoQuestionarioMINI());
        db.execSQL(ScriptSQL.getRespostasDoQuestionarioMINI());
        db.execSQL(ScriptSQL.getAcontecimentos());
        db.execSQL(ScriptSQL.getSintomas());
        db.execSQL(ScriptSQL.getSintomasSentidos());
        db.execSQL(ScriptSQL.getFeedback());
        db.execSQL(ScriptSQL.getMedicamentos());
        db.execSQL(ScriptSQL.getProxAlarme());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }


}
