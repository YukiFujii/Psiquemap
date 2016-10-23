package com.example.psiquemap.psiquemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;
import com.example.psiquemap.psiquemap.entidades.Questionario;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaNull;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaTime;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaUnica;

import java.util.ArrayList;

public class InicioQuestionario extends AppCompatActivity
{
    protected Questionario questionario;
    private Button btnComecar;
    private TextView txtAviso1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_questionario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnComecar = (Button)findViewById(R.id.btnComecar);
        txtAviso1 = (TextView)findViewById(R.id.txtAviso1);

        questionario = new Questionario();
        questionario.buscarQuestionario();

        txtAviso1.setText("- O questionário possui "+questionario.perguntasRestantes()+" pergunta(s).");

    }

    public void chamarTelaPergunta(View view)
    {

        String tipoDaPergunta;

        tipoDaPergunta = questionario.getTipoProximaPergunta();

        switch (tipoDaPergunta)
        {
            case "boolean":
                Intent it = new Intent(this, RespostaUnica.class);
                it.putExtra("QUESTIONARIO",questionario);
                startActivityForResult(it,0);
                finish();
                break;

            case "null":
                it = new Intent(this, RespostaNull.class);
                it.putExtra("QUESTIONARIO", questionario);
                startActivityForResult(it, 0);
                finish();
                break;

            case "time":
                it = new Intent(this, RespostaTime.class);
                it.putExtra("QUESTIONARIO", questionario);
                startActivityForResult(it, 0);
                finish();
                break;

            default:
                finish();
        }

    }

    public int perguntasRestantes(ArrayList<PerguntaDoQuestionario> questionario)
    {
        int ret = 0;

        for (int i=0;i<questionario.size();i++)
        {
            if(questionario.get(i).getFoiRespondida()==false)
                ret++;
        }

        return ret;
    }


}
