package com.example.psiquemap.psiquemap;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.PerguntasDoQuestionarioMINI;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaNull;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaTime;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaUnica;

public class InicioQuestionario extends AppCompatActivity
{
    private Button btnComecar;
    private TextView txtAviso1;
    private PerguntaDoQuestionario pergunta;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private PerguntasDoQuestionarioMINI perguntasDoQuestionarioMINI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_questionario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnComecar = (Button)findViewById(R.id.btnComecar);
        txtAviso1 = (TextView)findViewById(R.id.txtAviso1);

        if(this.conexaoBD())
        {
            perguntasDoQuestionarioMINI = new PerguntasDoQuestionarioMINI(conn);

            txtAviso1.setText("- O questionário possui " + perguntasDoQuestionarioMINI.getPerguntasRestantes() + " pergunta(s) não respondida(s).");

            if (perguntasDoQuestionarioMINI.getPerguntasRestantes() != 0)
                btnComecar.setEnabled(true);
            else
                btnComecar.setEnabled(false);
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

    public void chamarTelaPergunta(View view)
    {

        this.pergunta = perguntasDoQuestionarioMINI.getPerguntaQuestionarioMINI();

        switch (this.pergunta.getTipoPergunta())
        {
            case "boolean":
                Intent it = new Intent(this, RespostaUnica.class);
                it.putExtra("QUESTIONARIO",this.pergunta);
                startActivityForResult(it,0);
                finish();
                break;

            case "null":
                it = new Intent(this, RespostaNull.class);
                it.putExtra("QUESTIONARIO", this.pergunta);
                startActivityForResult(it, 0);
                finish();
                break;

            case "time":
                it = new Intent(this, RespostaTime.class);
                it.putExtra("QUESTIONARIO", this.pergunta);
                startActivityForResult(it, 0);
                finish();
                break;

            default:
                finish();
        }

    }

}
