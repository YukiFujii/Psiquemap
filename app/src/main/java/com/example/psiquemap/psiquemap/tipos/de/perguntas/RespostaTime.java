package com.example.psiquemap.psiquemap.tipos.de.perguntas;

import java.text.DateFormat;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.Diario;
import com.example.psiquemap.psiquemap.entidades.Questionario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RespostaTime extends AppCompatActivity {

    private TextView txtTituloRespUnica;
    private TextView txtMarcadorRespoUnica;
    private TextView txtPerguntaUnica;
    private Questionario questionario;
    private Diario questionarioDiario;
    private String resposta="";
    private String tipoQuestionario;
    private EditText editTime;
    private Calendar dateAndTime=Calendar.getInstance();
    private TimePickerDialog.OnTimeSetListener t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        txtTituloRespUnica = (TextView)findViewById(R.id.txtTituloRespUnica);
        txtMarcadorRespoUnica = (TextView)findViewById(R.id.txtMarcadorRespUnica);
        txtPerguntaUnica = (TextView)findViewById(R.id.txtPerguntaRespUnica);
        editTime = (EditText)findViewById(R.id.editTime);


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

    public void chamarTelaPerguntaRespUnica(View view)
    {
        resposta = this.editTime.getText().toString();

        if(resposta!="")
        {
                String tipoDaPergunta;

                if (this.getTipoQuestionario().equals("Questionário")) {
                    if(this.questionario.perguntasRestantes()-1>0) {
                        Intent it;

                        questionario.getListaDePerguntas().get(questionario.indexDaProximaPegunta()).setFoiRespondida(true);

                        tipoDaPergunta = questionario.getTipoProximaPergunta();

                        switch (tipoDaPergunta) {
                            case "boolean":
                                it = new Intent(this, RespostaUnica.class);
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
                        Intent it;

                        questionarioDiario.getListaDePerguntas().get(questionarioDiario.indexDaProximaPegunta()).setFoiRespondida(true);

                        tipoDaPergunta = questionarioDiario.getTipoProximaPergunta();

                        switch (tipoDaPergunta) {
                            case "boolean":
                                it = new Intent(this, RespostaUnica.class);
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

    }

}
