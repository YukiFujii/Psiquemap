package com.example.psiquemap.psiquemap.sql;

/**
 * Created by yuki on 23/10/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        //db.execSQL(ScriptSQL.getRepositorioDePerguntas());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }


}