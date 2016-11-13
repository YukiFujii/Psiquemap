package com.example.psiquemap.psiquemap.tipos.de.perguntas;

import java.text.DateFormat;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.psiquemap.psiquemap.InicioDiario;
import com.example.psiquemap.psiquemap.InicioQuestionario;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RespostaTime extends AppCompatActivity {

    private TextView txtTituloRespTime;
    private TextView txtMarcadorRespTime;
    private TextView txtPerguntaRespTime;

    private String resposta="";
    private String tipoQuestionario;
    private EditText editTime;
    private Calendar dateAndTime=Calendar.getInstance();
    private TimePickerDialog.OnTimeSetListener t;
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
        setContentView(R.layout.activity_resposta_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        txtTituloRespTime = (TextView)findViewById(R.id.txtTituloRespUnica);
        txtMarcadorRespTime = (TextView)findViewById(R.id.txtMarcadorRespUnica);
        txtPerguntaRespTime = (TextView)findViewById(R.id.txtPerguntaRespUnica);
        editTime = (EditText)findViewById(R.id.editTime);

        if(this.conexaoBD())
        {
            controles = new Controles(this.conn);

            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("QUESTIONARIO")))
            {
                this.pergunta = (PerguntaDoQuestionario) bundle.getSerializable("QUESTIONARIO");
                this.setTipoQuestionario("Questionário");
            }
            else if((bundle != null) && (bundle.containsKey("DIARIO")))
            {
                this.pergunta = (PerguntaDoQuestionario) bundle.getSerializable("DIARIO");
                this.setTipoQuestionario("Diário");
            }
            else
                finish();


            txtTituloRespTime.setText(this.getTipoQuestionario());

            if(getTipoQuestionario().equals("Questionário"))
            {
                this.perguntasDoQuestionarioMINI = new PerguntasDoQuestionarioMINI(conn);
                this.respostasQuestionarioMINI = new RespostasQuestionarioMINI(this.conn);
                txtMarcadorRespTime.setText(this.perguntasDoQuestionarioMINI.getIndexPerguntasAtual() + "/" + this.perguntasDoQuestionarioMINI.getTotalPerguntas());
                txtPerguntaRespTime.setText(this.pergunta.getPergunta());
            }
            else
            {
                this.perguntasDoDiario = new PerguntasDoDiario(conn);
                this.respostasQuestionarioDiario = new RespostasQuestionarioDiario(this.conn);
                txtMarcadorRespTime.setText(this.perguntasDoDiario.getIndexPerguntasAtual() + "/" + this.perguntasDoDiario.getTotalPerguntas());
                txtPerguntaRespTime.setText(this.pergunta.getPergunta());
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

    public void chamarTelaPerguntaRespUnica(View view)
    {
        resposta = this.editTime.getText().toString();

        if(resposta!="")
        {

                if (this.tipoQuestionario.equals("Questionário"))
                {
                    this.perguntasDoQuestionarioMINI.delete(this.pergunta.getPerguntaId(),this.pergunta.getQuestao());

                    this.respostaQuestionarioMINI = new RespostaQuestionarioMINI(controles.getIdPaciente(),MetodosEmComum.getDataAtual(),this.pergunta.getPerguntaId(),this.pergunta.getQuestao(),this.resposta);
                    this.respostasQuestionarioMINI.insert(this.respostaQuestionarioMINI);

                    this.pergunta = this.perguntasDoQuestionarioMINI.getPerguntaQuestionarioMINI();

                    if(this.pergunta==null)
                    {
                        Intent it = new Intent(this, InicioQuestionario.class);
                        startActivityForResult(it, 0);
                        finish();
                    }
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
            dlg.setMessage("Campo em não pode ficar em branco.");
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

    public void exibeHora(View view)
    {
        new TimePickerDialog(RespostaTime.this,t = new TimePickerDialog.OnTimeSetListener()
                {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        dateAndTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        dateAndTime.set(Calendar.MINUTE, minute);
                        updateLabel(dateAndTime);
                    }
                },
                8,
                0,
                true).show();
    }

    private void updateLabel(Calendar calendar)
    {

        Date hora = calendar.getTime();

        DateFormat f24h = new SimpleDateFormat("HH:mm");
        String str = f24h.format(hora);

        editTime.setText(str);

        /*
        Date to String - String to Date

        DateFormat data = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Calendar calendar1 = Calendar.getInstance();
        String dt = data.format(calendar1.getTime());

        Log.i("Data",dt);

        try {

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(data.parse(dt));

            Log.i("Data2",data.format(calendar2.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        */
    }

}
