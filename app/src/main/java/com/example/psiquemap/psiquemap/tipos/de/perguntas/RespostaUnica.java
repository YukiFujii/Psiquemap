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
import com.example.psiquemap.psiquemap.InicioQuestionario;
import com.example.psiquemap.psiquemap.MainActivity;
import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.PerguntaDoQuestionario;
import com.example.psiquemap.psiquemap.entidades.RespostaQuestionarioDiario;
import com.example.psiquemap.psiquemap.entidades.RespostaQuestionarioMINI;
import com.example.psiquemap.psiquemap.sql.Controles;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.PerguntasDoDiario;
import com.example.psiquemap.psiquemap.sql.PerguntasDoQuestionarioMINI;
import com.example.psiquemap.psiquemap.sql.RespostasQuestionarioDiario;
import com.example.psiquemap.psiquemap.sql.RespostasQuestionarioMINI;

public class RespostaUnica extends AppCompatActivity {

    private TextView txtTituloRespUnica;
    private TextView txtMarcadorRespoUnica;
    private TextView txtPerguntaUnica;

    private RadioGroup rgrRespUnica;
    private RadioButton rbtSimRespUnica;
    private RadioButton rbtNaoRespUnica;
    private Button btnProximoRespUnica;

    private String resposta="";
    private String tipoQuestionario;
    private PerguntaDoQuestionario pergunta;
    private RespostaQuestionarioMINI respostaQuestionarioMINI;
    private RespostaQuestionarioDiario respostaQuestionarioDiario;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Controles controles;
    private PerguntasDoDiario perguntasDoDiario;
    private RespostasQuestionarioDiario respostasQuestionarioDiario;
    private PerguntasDoQuestionarioMINI perguntasDoQuestionarioMINI;
    private RespostasQuestionarioMINI respostasQuestionarioMINI;

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
            this.controles = new Controles(this.conn);

            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("QUESTIONARIO")))
            {
                pergunta = (PerguntaDoQuestionario) bundle.getSerializable("QUESTIONARIO");
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
                this.perguntasDoQuestionarioMINI = new PerguntasDoQuestionarioMINI(this.conn);
                this.respostasQuestionarioMINI = new RespostasQuestionarioMINI(this.conn);
                txtMarcadorRespoUnica.setText("");
                txtPerguntaUnica.setText(this.pergunta.getPergunta());
            }
            else
            {
                this.perguntasDoDiario = new PerguntasDoDiario(conn);
                this.respostasQuestionarioDiario = new RespostasQuestionarioDiario(this.conn);
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
                resposta="true";
                break;
            case R.id.rbtNaoRespUnica:
                if (checked)
                resposta="false";
                break;
        }
    }

    public void chamarTelaPerguntaRespUnica(View view)
    {

        if(!resposta.equals(""))
        {
                if (this.tipoQuestionario.equals("Questionário"))
                {
                    this.perguntasDoQuestionarioMINI.delete(this.pergunta.getPerguntaId(),this.pergunta.getQuestao());

                    this.respostaQuestionarioMINI = new RespostaQuestionarioMINI(controles.getIdPaciente(),MetodosEmComum.getDataAtual(),this.pergunta.getPerguntaId(),this.pergunta.getQuestao(),this.resposta);
                    this.respostasQuestionarioMINI.insert(this.respostaQuestionarioMINI);

                    this.pergunta = this.perguntasDoQuestionarioMINI.getPerguntaQuestionarioMINI();

                    if(this.pergunta==null)
                        finish();

                    else
                    {

                        switch (this.pergunta.getTipoPergunta()) {
                            case "boolean":
                                Intent it = new Intent(this, RespostaUnica.class);
                                it.putExtra("QUESTIONARIO", this.pergunta);
                                startActivityForResult(it, 0);
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
                else
                {
                    this.pergunta.setFoiRespondida(1);
                    this.perguntasDoDiario.update(this.pergunta);

                    this.respostaQuestionarioDiario = new RespostaQuestionarioDiario(controles.getIdPaciente(),MetodosEmComum.getDataAtual(),this.pergunta.getPerguntaId(),this.resposta);
                    this.respostasQuestionarioDiario.insert(this.respostaQuestionarioDiario);

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
