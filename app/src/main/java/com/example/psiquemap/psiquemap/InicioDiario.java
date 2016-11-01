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
import com.example.psiquemap.psiquemap.sql.PerguntasDoDiario;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaNull;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaTime;
import com.example.psiquemap.psiquemap.tipos.de.perguntas.RespostaUnica;

public class InicioDiario extends AppCompatActivity {

    private TextView txtAviso1Diario;
    private Button btnQuestionarioDoDiario;
    private Button btnAdicionarEvento;
    private Button btnVisualizarEvento;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private PerguntasDoDiario perguntasDoDiario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_diario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtAviso1Diario = (TextView)findViewById(R.id.txtAviso1Diario);
        btnAdicionarEvento = (Button)findViewById(R.id.btnAdicionarEvento);
        btnQuestionarioDoDiario = (Button)findViewById(R.id.btnQuestionarioDoDiario);
        btnVisualizarEvento = (Button)findViewById(R.id.btnVisualizarEvento);

        if(this.conexaoBD())
        {
            perguntasDoDiario = new PerguntasDoDiario(conn);

            txtAviso1Diario.setText("- Seu diário possui " + perguntasDoDiario.getPerguntasRestantes() + " pergunta(s) não respondida(s).");

            if(perguntasDoDiario.getPerguntasRestantes()!=0)
                btnQuestionarioDoDiario.setEnabled(true);
            else
                btnQuestionarioDoDiario.setEnabled(false);
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

    public void chamarTelaAdicionarAcontecimento(View view)
    {
        Intent it = new Intent(this,AcontecimentoDoDiario.class);
        startActivityForResult(it,0);
        finish();
    }

    public void chamarTelaVisualizarAcontecimento(View view)
    {
        Intent it = new Intent(this,VisualizarAcontecimentos.class);
        startActivityForResult(it,0);
        finish();
    }

    public void chamarTelaPerguntaDiario(View view)
    {
        PerguntaDoQuestionario pergunta = this.perguntasDoDiario.getPerguntaDiario();

        Intent it;

        switch (pergunta.getTipoPergunta())
        {
            case "boolean":
                it = new Intent(this, RespostaUnica.class);
                it.putExtra("DIARIO",pergunta);
                startActivityForResult(it,0);
                finish();
                break;

            case "null":
                it = new Intent(this, RespostaNull.class);
                it.putExtra("DIARIO",pergunta);
                startActivityForResult(it,0);
                finish();
                break;

            case "time":
                it = new Intent(this, RespostaTime.class);
                it.putExtra("DIARIO",pergunta);
                startActivityForResult(it,0);
                finish();
                break;

            default:
                finish();
        }

    }

}
