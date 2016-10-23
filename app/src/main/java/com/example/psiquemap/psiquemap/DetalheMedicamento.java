package com.example.psiquemap.psiquemap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.psiquemap.psiquemap.entidades.Medicamento;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetalheMedicamento extends AppCompatActivity {

    private TextView txtNomeMedicamento;
    private TextView txtIntervalo;
    private TextView txtDosagem;
    private TextView txtDurante;
    private EditText editUltimoHorario;
    private TextView txtProximoHorario;
    private CheckBox checkBoxAviso;
    private Button btnConfirmar;
    private Button btnSalvarDetalheMedicamento;
    private Medicamento medicamento;

    private Calendar ultimoHorario=Calendar.getInstance();
    private TimePickerDialog.OnTimeSetListener t;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_medicamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNomeMedicamento = (TextView)findViewById(R.id.txtNomeMedicamento);
        txtIntervalo = (TextView)findViewById(R.id.txtIntervalo);
        txtDosagem = (TextView)findViewById(R.id.txtDosagem);
        txtDurante = (TextView)findViewById(R.id.txtDurante);
        txtProximoHorario = (TextView)findViewById(R.id.txtProximoHorario);
        editUltimoHorario = (EditText)findViewById(R.id.editUltimoHorario);
        checkBoxAviso = (CheckBox)findViewById(R.id.checkBoxAviso);
        btnSalvarDetalheMedicamento = (Button)findViewById(R.id.btnSalvarDetalheMedicamento);
        btnConfirmar = (Button)findViewById(R.id.btnConfirmar);

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("MEDICAMENTO")))
        {
            medicamento = (Medicamento) bundle.getSerializable("MEDICAMENTO");
            preencheDados();

            if(medicamento.getUltimoHorario()==null)
                checkBoxAviso.setEnabled(false);
            else
                checkBoxAviso.setEnabled(true);

            if(medicamento.isAlarmeAtivo())
                checkBoxAviso.setChecked(true);
            else
                checkBoxAviso.setChecked(false);

            checkBoxAviso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkBoxAviso.isChecked())
                    {


                        medicamento.setAlarmeAtivo(true);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(DetalheMedicamento.this);
                        dlg.setMessage("Alarme ativado! Você receberá uma notificação às "+dataToString(medicamento.getProximoHorario()));
                        dlg.setNeutralButton("OK",null);
                        dlg.show();

                        chamarAlarme();

                    }
                    else
                    {
                        medicamento.setAlarmeAtivo(false);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(DetalheMedicamento.this);
                        dlg.setMessage("Alarme foi desativado!");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();

                        onDestroy();
                    }
                }
            });
        }
        else
            finish();

    }

    public void chamarAlarme()
    {
            Log.i("Script", "Novo alarme");

            Intent intent = new Intent("DISPARAR_ALARME");
            intent.putExtra("MEDICAMENTO", medicamento);
            PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarme.set(AlarmManager.RTC_WAKEUP,medicamento.getProximoHorario().getTimeInMillis(),p);
    }

    public void onDestroy()
    {
        Intent intent = new Intent("DISPARAR_ALARME");
        PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarme.cancel(p);
        p.cancel();
    }

    private void preencheDados()
    {
        txtNomeMedicamento.setText("Medicamento: "+medicamento.getNomeMedicacao());
        txtIntervalo.setText("Tomar a cada: "+medicamento.getIntervalo()+" hora(s)");
        txtDosagem.setText("Dosagem: "+medicamento.getDosagem()+"mg");
        txtDurante.setText("Durante: "+medicamento.getDurante()+" dia(s)");
    }

    public void exibeHoraUltimoHorario(View view)
    {
        new TimePickerDialog(DetalheMedicamento.this,t = new TimePickerDialog.OnTimeSetListener()
        {
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                ultimoHorario.set(Calendar.HOUR_OF_DAY,hourOfDay);
                ultimoHorario.set(Calendar.MINUTE, minute);
                updateUltimoHorario(ultimoHorario);
            }
        },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true).show();

        btnConfirmar.setEnabled(true);
    }

    private void updateUltimoHorario(Calendar calendar)
    {
        editUltimoHorario.setText(dataToString(calendar));
        medicamento.setUltimoHorario(calendar);
    }

    public void btnConfirmar(View view)
    {
        updateProximoHorario();
        medicamento.decrementarQtdRestantesDoMedicamento();

        if (medicamento.getFlagDeletarMedicamento())
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Todas as doses foram tomadas! O medicamento será excluído da lista.");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        btnConfirmar.setEnabled(false);
        checkBoxAviso.setEnabled(true);

        if(checkBoxAviso.isChecked())
            chamarAlarme();
    }

    public void btnSalvarDetalheMedicamento(View view)
    {
        Intent it = new Intent(this, MainActivity.class);
        startActivityForResult(it,0);
    }

    private void updateProximoHorario()
    {
        Calendar proximoHorario = medicamento.getUltimoHorario();
        proximoHorario.add(Calendar.HOUR_OF_DAY,medicamento.getIntervalo());

        medicamento.setProximoHorario(proximoHorario);

        txtProximoHorario.setText(dataToString(medicamento.getProximoHorario())+" horas.");
        Log.i("proximo horario ",dataToString(medicamento.getProximoHorario()));
    }

    public String dataToString(Calendar calendar)
    {
        Date hora = calendar.getTime();

        DateFormat data = new SimpleDateFormat("HH:mm");
        String str = data.format(hora);

        return str;
    }

}
