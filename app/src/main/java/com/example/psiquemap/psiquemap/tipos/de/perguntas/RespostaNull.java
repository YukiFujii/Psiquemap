package com.example.psiquemap.psiquemap.tipos.de.perguntas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.psiquemap.psiquemap.InicioDiario;
import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;
import com.example.psiquemap.psiquemap.entidades.Questionario;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.PerguntasDoDiario;

public class RespostaNull extends AppCompatActivity {

    private TextView txtTituloRespNull;
    private TextView txtMarcadorRespNull;
    private TextView txtPerguntaRespNull;

    private Questionario questionario;
    private String tipoQuestionario;
    private PerguntaDoQuestionario pergunta;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private PerguntasDoDiario perguntasDoDiario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta_null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTituloRespNull = (TextView)findViewById(R.id.txtTituloRespNull);
        txtMarcadorRespNull = (TextView)findViewById(R.id.txtMarcadorRespNull);
        txtPerguntaRespNull = (TextView)findViewById(R.id.txtPerguntaRespNull);

        if(this.conexaoBD())
        {
            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("QUESTIONARIO")))
            {
                questionario = (Questionario) bundle.getSerializable("QUESTIONARIO");
                this.setTipoQuestionario("Questionário");
            }
            else if((bundle != null) && (bundle.containsKey("DIARIO")))
            {
                pergunta = (PerguntaDoQuestionario) bundle.getSerializable("DIARIO");
                this.setTipoQuestionario("Diário");
            }
            else
                finish();

            txtTituloRespNull.setText(this.getTipoQuestionario());


            if(getTipoQuestionario().equals("Questionário"))
            {
                txtMarcadorRespNull.setText(questionario.indexDaProximaPegunta() + 1 + "/" + questionario.getListaDePerguntas().size());
                txtPerguntaRespNull.setText(questionario.getListaDePerguntas().get(questionario.indexDaProximaPegunta()).getPergunta());
            }
            else
            {
                this.perguntasDoDiario = new PerguntasDoDiario(conn);
                txtMarcadorRespNull.setText(this.perguntasDoDiario.getIndexPerguntasAtual() + "/" + this.perguntasDoDiario.getTotalPerguntas());
                txtPerguntaRespNull.setText(this.pergunta.getPergunta());
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

    public void chamarTelaPerguntaRespNull(View view)
    {
            String tipoDaPergunta;

            if (this.getTipoQuestionario().equals("Questionário")) {

                if(this.questionario.perguntasRestantes()-1>0) {
                    questionario.getListaDePerguntas().get(questionario.indexDaProximaPegunta()).setFoiRespondida(1);

                    tipoDaPergunta = questionario.getTipoProximaPergunta();

                    switch (tipoDaPergunta) {
                        case "boolean":
                            Intent it = new Intent(this, RespostaUnica.class);
                            it.putExtra("QUESTIONARIO", questionario);
                            startActivityForResult(it, 0);
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
                else
                    finish();
            }
            else
            {
                this.pergunta.setFoiRespondida(1);
                this.perguntasDoDiario.update(this.pergunta);

                this.pergunta = this.perguntasDoDiario.getPerguntaDiario();

                if(this.pergunta==null)
                {
                    Intent it = new Intent(this, InicioDiario.class);
                    startActivityForResult(it, 0);
                    finish();
                }
                else
                {

                    switch (this.pergunta.getTipoPergunta()) {
                        case "boolean":
                            Intent it = new Intent(this, RespostaUnica.class);
                            it.putExtra("DIARIO", this.pergunta);
                            startActivityForResult(it, 0);
                            finish();
                            break;

                        case "null":
                            it = new Intent(this, RespostaNull.class);
                            it.putExtra("DIARIO", this.pergunta);
                            startActivityForResult(it, 0);
                            finish();
                            break;

                        case "time":
                            it = new Intent(this, RespostaTime.class);
                            it.putExtra("DIARIO", this.pergunta);
                            startActivityForResult(it, 0);
                            finish();
                            break;

                        default:
                            finish();
                    }
                }

            }
    }

    public String getTipoQuestionario() {
        return tipoQuestionario;
    }

    public void setTipoQuestionario(String tipoQuestionario)
    {
        this.tipoQuestionario = tipoQuestionario;
    }
}
