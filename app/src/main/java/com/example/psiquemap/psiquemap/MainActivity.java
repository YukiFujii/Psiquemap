package com.example.psiquemap.psiquemap;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.psiquemap.psiquemap.comunicacao.EnviarDados;
import com.example.psiquemap.psiquemap.entidades.Dados;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Feedbacks;
import com.example.psiquemap.psiquemap.sql.PerguntasDoQuestionarioMINI;
import com.google.gson.Gson;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    private ImageButton btnMinhaConta;
    private ImageButton btnDiario;
    private ImageButton btnQuestionario;
    private ImageButton btnSintomas;
    private ImageButton btnFeedback;
    private ImageButton btnNotificacoes;

    private TextView txtMinhaConta;
    private TextView txtDiario;
    private TextView txtQuestionario;
    private TextView txtSintomas;
    private TextView txtFeedback;
    private TextView txtNotificacoes;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private PerguntasDoQuestionarioMINI perguntasDoQuestionarioMINI;
    private Feedbacks feedbacks;
    private Controles controles;

    public static Context getApplicationContext;
    public static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMinhaConta = (ImageButton) findViewById(R.id.btnMinhaConta);
        btnDiario = (ImageButton) findViewById(R.id.btnDiario);
        btnQuestionario = (ImageButton) findViewById(R.id.btnQuestionario);
        btnSintomas = (ImageButton) findViewById(R.id.btnSintomas);
        btnFeedback = (ImageButton) findViewById(R.id.btnFeedback);
        btnNotificacoes = (ImageButton) findViewById(R.id.btnNotificacoes);

        txtMinhaConta = (TextView) findViewById(R.id.txtMinhaConta);
        txtDiario = (TextView) findViewById(R.id.txtDiario);
        txtQuestionario = (TextView) findViewById(R.id.txtQuestionario);
        txtSintomas = (TextView) findViewById(R.id.txtSintomas);
        txtFeedback = (TextView) findViewById(R.id.txtFeedback);
        txtNotificacoes = (TextView) findViewById(R.id.txtNotificacoes);
        getApplicationContext = getApplicationContext();
        thisContext = this;

        if(this.conexaoBD())
        {
            controles = new Controles(this.conn);
            perguntasDoQuestionarioMINI = new PerguntasDoQuestionarioMINI(this.conn);
            Log.i("Perguntas restantes",""+perguntasDoQuestionarioMINI.getPerguntasRestantes());
            if(perguntasDoQuestionarioMINI.getPerguntasRestantes()==0)
            {
                txtQuestionario.setEnabled(false);
                txtQuestionario.setTextColor(Color.GRAY);
                btnQuestionario.setEnabled(false);
            }
            else
            {
                txtQuestionario.setEnabled(true);
                txtQuestionario.setTextColor(Color.BLACK);
                btnQuestionario.setEnabled(true);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,-1);

            feedbacks = new Feedbacks(this.conn);

            if(feedbacks.hasFeedback(this.controles.getIdPaciente(),MetodosEmComum.getDataAtual(calendar)))
            {
                txtFeedback.setEnabled(false);
                txtFeedback.setTextColor(Color.GRAY);
                btnFeedback.setEnabled(false);
            }
            else
            {
                txtFeedback.setEnabled(true);
                txtFeedback.setTextColor(Color.BLACK);
                btnFeedback.setEnabled(true);
            }
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mni_logout:
                logout();
                break;

            case R.id.mni_enviar:
                enviarInformacoes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout()
    {
        controles.delete(controles.getIdPaciente());
        Intent it = new Intent(this, LoginActivity.class);
        startActivityForResult(it, 0);
        finish();
    }

    private void enviarInformacoes()
    {
        Dados dados = MetodosEmComum.getDados(this);

        new EnviarDados().execute(dados);
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

    public void chamarMinhaConta (View view)
    {
        Intent it = new Intent(this, MinhaConta.class);
        startActivityForResult(it, 0);
    }

    public void chamarSintomas (View view)
    {
        Intent it = new Intent(this, SintomasScreen.class);
        startActivityForResult(it, 0);
    }

    public void chamarQuestinario (View view)
    {
        Intent it = new Intent(this, InicioQuestionario.class);
        startActivityForResult(it, 0);
    }

    public void chamarDiario (View view)
    {
        Intent it = new Intent(this, InicioDiario.class);
        startActivityForResult(it, 0);
    }

    public void chamarMedicacao (View view)
    {
        Intent it = new Intent(this, MedicamentosScreen.class);
        startActivityForResult(it, 0);
    }

    public void chamarFeedback (View view)
    {
        Intent it = new Intent(this, FeedbackScreen.class);
        startActivityForResult(it, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(this.conexaoBD())
        {

            perguntasDoQuestionarioMINI = new PerguntasDoQuestionarioMINI(this.conn);
            Log.i("Perguntas restantes",""+perguntasDoQuestionarioMINI.getPerguntasRestantes());
            if(perguntasDoQuestionarioMINI.getPerguntasRestantes()==0)
            {
                txtQuestionario.setEnabled(false);
                txtQuestionario.setTextColor(Color.GRAY);
                btnQuestionario.setEnabled(false);
            }
            else
            {
                txtQuestionario.setEnabled(true);
                txtQuestionario.setTextColor(Color.BLACK);
                btnQuestionario.setEnabled(true);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,-1);

            feedbacks = new Feedbacks(this.conn);

            if(feedbacks.hasFeedback(this.controles.getIdPaciente(),MetodosEmComum.getDataAtual(calendar)))
            {
                txtFeedback.setEnabled(false);
                txtFeedback.setTextColor(Color.GRAY);
                btnFeedback.setEnabled(false);
            }
            else
            {
                txtFeedback.setEnabled(true);
                txtFeedback.setTextColor(Color.BLACK);
                btnFeedback.setEnabled(true);
            }
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

}
