package com.example.psiquemap.psiquemap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;

public class SplashScreen extends Activity {
    // Timer da splash screen
    private static int SPLASH_TIME_OUT = 3000;

    private boolean flagLogin = false;
    private Controles controles;
    private Context context = this;

    private DataBase dataBase;
    private SQLiteDatabase conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {

                if(conexaoBD())
                {
                    controles = new Controles(conn);

                    if(controles.hasControle())
                    {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
                else
                {
                    android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(context);
                    dlg.setMessage("Erro ao conectar com banco!");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private boolean conexaoBD()
    {
        try {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }
}


