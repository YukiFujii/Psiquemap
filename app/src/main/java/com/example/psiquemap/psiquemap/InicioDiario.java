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

import com.example.psiquemap.psiquemap.entidades.Diario;
import com.example.psiquemap.psiquemap.entidades.Questionario;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaUnica;

public class InicioDiario extends AppCompatActivity {

    private TextView txtAviso1Diario;
    private Button btnQuestionarioDoDiario;
    private Button btnAdicionarEvento;
    private Diario questionarioDiario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_diario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtAviso1Diario = (TextView)findViewById(R.id.txtAviso1Diario);
        btnAdicionarEvento = (Button)findViewById(R.id.btnAdicionarEvento);
        btnQuestionarioDoDiario = (Button)findViewById(R.id.btnQuestionarioDoDiario);

        questionarioDiario = new Diario();
        questionarioDiario.buscarQuestionario();

        txtAviso1Diario.setText("- O questionário do diário possui "+questionarioDiario.perguntasRestantes()+" pergunta(s).");
    }

    public void chamarTelaAdicionarEvento(View view)
    {
        Intent it = new Intent(this,EventoDoDiario.class);
        startActivityForResult(it,0);
        finish();
    }

    public void chamarTelaPerguntaDiario(View view)
    {
        String tipoDaPergunta;

        tipoDaPergunta = questionarioDiario.getTipoProximaPergunta();

        switch (tipoDaPergunta)
        {
            case "boolean":
                Intent it = new Intent(this, RespostaUnica.class);
                it.putExtra("DIARIO",questionarioDiario);
                startActivityForResult(it,0);
                finish();
                break;

            case "listBox":
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Chamou a tela listBox -> " + this.questionarioDiario.getListaDePerguntas().get(questionarioDiario.indexDaProximaPegunta()).getPergunta());
                dlg.setNeutralButton("OK", null);
                dlg.show();
                break;

            case "time":
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(this);
                dlg2.setMessage("Chamou a tela time -> " + this.questionarioDiario.getListaDePerguntas().get(questionarioDiario.indexDaProximaPegunta()).getPergunta());
                dlg2.setNeutralButton("OK", null);
                dlg2.show();
                break;

            default:
                finish();
        }

    }

}
