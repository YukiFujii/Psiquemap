package com.example.psiquemap.psiquemap.tipos.de.perguntas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.psiquemap.psiquemap.InicioDiario;
import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;
import com.example.psiquemap.psiquemap.entidades.Questionario;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.PerguntasDoDiario;

public class RespostaUnica extends AppCompatActivity {

    private TextView txtTituloRespUnica;
    private TextView txtMarcadorRespoUnica;
    private TextView txtPerguntaUnica;
    private RadioGroup rgrRespUnica;
    private RadioButton rbtSimRespUnica;
    private RadioButton rbtNaoRespUnica;
    private Button btnProximoRespUnica;
    private Questionario questionario;
    private int resposta=-1;
    private String tipoQuestionario;
    private PerguntaDoQuestionario pergunta;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private PerguntasDoDiario perguntasDoDiario;

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

            txtTituloRespUnica.setText(this.getTipoQuestionario());

            if(getTipoQuestionario().equals("Questionário"))
            {
                txtMarcadorRespoUnica.setText(questionario.indexDaProximaPegunta() + 1 + "/" + questionario.getListaDePerguntas().size());
                txtPerguntaUnica.setText(questionario.getListaDePerguntas().get(questionario.indexDaProximaPegunta()).getPergunta());
            }
            else
            {
                this.perguntasDoDiario = new PerguntasDoDiario(conn);
                txtMarcadorRespoUnica.setText(this.perguntasDoDiario.getIndexPerguntasAtual() + "/" + this.perguntasDoDiario.getTotalPerguntas());
                txtPerguntaUnica.setText(this.pergunta.getPergunta());
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

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbtSimRespUnica:
                if (checked)
                resposta=1;
                break;
            case R.id.rbtNaoRespUnica:
                if (checked)
                resposta=0;
                break;
        }
    }

    public void chamarTelaPerguntaRespUnica(View view)
    {

        if(resposta!=-1)
        {
                String tipoDaPergunta;

                if (this.getTipoQuestionario().equals("Questionário"))
                {
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
