package com.example.psiquemap.psiquemap.tipos.de.perguntas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.Diario;
import com.example.psiquemap.psiquemap.entidades.Questionario;

public class RespostaNull extends AppCompatActivity {

    private TextView txtTituloRespNull;
    private TextView txtMarcadorRespNull;
    private TextView txtPerguntaRespNull;

    private Questionario questionario;
    private Diario questionarioDiario;
    private String tipoQuestionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta_null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTituloRespNull = (TextView)findViewById(R.id.txtTituloRespNull);
        txtMarcadorRespNull = (TextView)findViewById(R.id.txtMarcadorRespNull);
        txtPerguntaRespNull = (TextView)findViewById(R.id.txtPerguntaRespNull);

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("QUESTIONARIO")))
        {
            questionario = (Questionario) bundle.getSerializable("QUESTIONARIO");
            this.setTipoQuestionario("Questionário");
        }
        else if((bundle != null) && (bundle.containsKey("DIARIO")))
        {
            questionarioDiario = (Diario) bundle.getSerializable("DIARIO");
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
            txtMarcadorRespNull.setText(questionarioDiario.indexDaProximaPegunta() + 1 + "/" + questionarioDiario.getListaDePerguntas().size());
            txtPerguntaRespNull.setText(questionarioDiario.getListaDePerguntas().get(questionarioDiario.indexDaProximaPegunta()).getPergunta());
        }
    }

    public void chamarTelaPerguntaRespNull(View view)
    {
            String tipoDaPergunta;

            if (this.getTipoQuestionario().equals("Questionário")) {

                if(this.questionario.perguntasRestantes()-1>0) {
                    questionario.getListaDePerguntas().get(questionario.indexDaProximaPegunta()).setFoiRespondida(true);

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
                if(this.questionarioDiario.perguntasRestantes()-1>0) {
                    questionarioDiario.getListaDePerguntas().get(questionarioDiario.indexDaProximaPegunta()).setFoiRespondida(true);

                    tipoDaPergunta = questionarioDiario.getTipoProximaPergunta();

                    switch (tipoDaPergunta) {
                        case "boolean":
                            Intent it = new Intent(this, RespostaUnica.class);
                            it.putExtra("DIARIO", questionarioDiario);
                            startActivityForResult(it, 0);
                            finish();
                            break;

                        case "null":
                            it = new Intent(this, RespostaNull.class);
                            it.putExtra("DIARIO", questionarioDiario);
                            startActivityForResult(it, 0);
                            finish();
                            break;

                        case "time":
                            it = new Intent(this, RespostaTime.class);
                            it.putExtra("DIARIO", questionarioDiario);
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
    }

    public String getTipoQuestionario() {
        return tipoQuestionario;
    }

    public void setTipoQuestionario(String tipoQuestionario)
    {
        this.tipoQuestionario = tipoQuestionario;
    }
}
