package com.example.psiquemap.psiquemap;

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

public class Feedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spnFeedback;
    private EditText editObservacao;
    private Button btnSalvarFeedback;

    private String classificacaoFeedback="";
    private String[] nomeSentimentos={"Confiante","De coração partido","Em paz","Muito feliz","Tranquilo","Triste"};
    private int emojis[] = {R.drawable.confiante,R.drawable.de_coracao_partido,R.drawable.em_paz,R.drawable.muito_feliz, R.drawable.tranquilo, R.drawable.triste};
    private int spnPosicao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editObservacao = (EditText)findViewById(R.id.editObservacao);
        btnSalvarFeedback = (Button)findViewById(R.id.btnSalvarFeedback);
        spnFeedback = (Spinner)findViewById(R.id.spnFeedback);

        spnFeedback.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),emojis,nomeSentimentos);
        spnFeedback.setAdapter(customAdapter);

    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id)
    {
        Log.i("Sentimento",nomeSentimentos[position]);

        this.spnPosicao = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void salvarFeedback(View view)
    {
        finish();
    }

}
