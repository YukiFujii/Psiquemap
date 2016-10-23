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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.Diario;
import com.example.psiquemap.psiquemap.entidades.Questionario;

public class RespostaUnica extends AppCompatActivity {

    private TextView txtTituloRespUnica;
    private TextView txtMarcadorRespoUnica;
    private TextView txtPerguntaUnica;
    private RadioGroup rgrRespUnica;
    private RadioButton rbtSimRespUnica;
    private RadioButton rbtNaoRespUnica;
    private Button btnProximoRespUnica;
    private Questionario questionario;
    private Diario questionarioDiario;
    private String resposta="";
    private String tipoQuestionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta_unica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        txtTituloRespUnica = (TextView)findViewById(R.id.txtTituloRespUnica);
        txtMarcadorRespoUnica = (TextView)findViewById(R.id.txtMarcadorRespUnica);
        txtPerguntaUnica = (TextView)findViewById(R.id.txtPerguntaRespUnica);
        rgrRespUnica = (RadioGroup)findViewById(R.id.rgrRespUnica);

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

        txtTituloRespUnica.setText(this.getTipoQuestionario());

        if(getTipoQuestionario().equals("Questionário"))
        {
            txtMarcadorRespoUnica.setText(questionario.indexDaProximaPegunta() + 1 + "/" + questionario.getListaDePerguntas().size());
            txtPerguntaUnica.setText(questionario.getListaDePerguntas().get(questionario.indexDaProximaPegunta()).getPergunta());
        }
        else
        {
            txtMarcadorRespoUnica.setText(questionarioDiario.indexDaProximaPegunta() + 1 + "/" + questionarioDiario.getListaDePerguntas().size());
            txtPerguntaUnica.setText(questionarioDiario.getListaDePerguntas().get(questionarioDiario.indexDaProximaPegunta()).getPergunta());
        }

    }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbtSimRespUnica:
                if (checked)
                resposta="sim";
                break;
            case R.id.rbtNaoRespUnica:
                if (checked)
                resposta="nao";
                break;
        }
    }

    public void chamarTelaPerguntaRespUnica(View view)
    {

        if(resposta!="")
        {
                String tipoDaPergunta;

                if (this.getTipoQuestionario().equals("Questionário"))
                {
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
                    if(this.questionarioDiario.perguntasRestantes()-1>0)
                    {
                        questionarioDiario.getListaDePerguntas().get(questionarioDiario.indexDaProximaPegunta()).setFoiRespondida(true);

                        tipoDaPergunta = questionarioDiario.getTipoProximaPergunta();

                        Log.i("Tipo proxima pergunta",tipoDaPergunta);

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
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Assinale uma das opções para prosseguir!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    public String getTipoQuestionario() {
        return tipoQuestionario;
    }

    public void setTipoQuestionario(String tipoQuestionario) {
        this.tipoQuestionario = tipoQuestionario;
    }
}
