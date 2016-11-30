package com.example.psiquemap.psiquemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.psiquemap.psiquemap.entidades.Feedback;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Feedbacks;

public class FeedbackScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spnFeedback;
    private EditText editObservacao;
    private Button btnSalvarFeedback;

    private String classificacaoFeedback="";
    private String[] nomeSentimentos={"--------","Confiante","De coração partido","Em paz","Muito feliz","Tranquilo","Triste"};
    private int emojis[] = {R.drawable.branco,R.drawable.confiante,R.drawable.de_coracao_partido,R.drawable.em_paz,R.drawable.muito_feliz, R.drawable.tranquilo, R.drawable.triste};
    //private int spnPosicao = 0;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Feedbacks feedbacks;
    private Controles controles;

    public static Context getApplicationContext;
    public static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editObservacao = (EditText)findViewById(R.id.editObservacao);
        btnSalvarFeedback = (Button)findViewById(R.id.btnSalvarFeedback);
        spnFeedback = (Spinner)findViewById(R.id.spnFeedback);
        getApplicationContext = getApplicationContext();
        thisContext = this;

        spnFeedback.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),emojis,nomeSentimentos);
        spnFeedback.setAdapter(customAdapter);

        if(this.conexaoBD())
        {
            this.feedbacks = new Feedbacks(this.conn);
            this.controles = new Controles(this.conn);
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

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

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id)
    {
        this.classificacaoFeedback = nomeSentimentos[position];
        //this.spnPosicao = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean verificacao()
    {
        if(this.classificacaoFeedback.equals("")||this.classificacaoFeedback.equals("--------"))
            return false;
        else
            return true;
    }

    public void salvarFeedback(View view)
    {
        if(this.verificacao())
        {
            Feedback feedback = new Feedback(controles.getIdPaciente(),this.classificacaoFeedback,this.editObservacao.getText().toString());
            feedbacks.insert(feedback);
            Controles.setFlagFeedback(MetodosEmComum.getIdPaciente(this),1);

            finish();
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Por favor, selecione um dos sentimentos da lista!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

}
