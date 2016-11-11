package com.example.psiquemap.psiquemap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.psiquemap.psiquemap.entidades.Alarme;
import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Medicamento;
import com.example.psiquemap.psiquemap.sql.Alarmes;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Medicamentos;

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
    private Medicamento primeiroMedicamentoDaFilaDeAlarmes;

    private Calendar ultimoHorario=Calendar.getInstance();
    private TimePickerDialog.OnTimeSetListener t;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Medicamentos medicamentos;
    private Alarmes alarmes;

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

        if(this.conexaoBD())
        {
            medicamentos = new Medicamentos(this.conn);
            alarmes = new Alarmes(conn);

            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("MEDICAMENTO")))
            {
                this.medicamento = (Medicamento) bundle.getSerializable("MEDICAMENTO");

                Controle.setIdPaciente(this.medicamento.getIdPaciente());
                Log.i("Controle idPaciente",Controle.getIdPaciente());

                preencheDados();

                if (medicamento.getUltimoHorario().equals(""))
                    checkBoxAviso.setEnabled(false);
                else
                    checkBoxAviso.setEnabled(true);

                if (medicamento.getAlarmeAtivo() == 1)
                    checkBoxAviso.setChecked(true);
                else
                    checkBoxAviso.setChecked(false);

                checkBoxAviso.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkBoxAviso.isChecked())
                        {
                            Alarme alarme = new Alarme(medicamento.getIdPaciente(),medicamento.getIdMedicacao(),medicamento.getProximoHorario());
                            alarmes.insert(alarme);

                            if(alarmes.temProximoAlarme())
                            {
                                alarme = alarmes.pegarProximoAlarme();

                                primeiroMedicamentoDaFilaDeAlarmes = medicamentos.getMedicamento(alarme.getIdPaciente(), alarme.getIdMedicacao());

                                AlertDialog.Builder dlg = new AlertDialog.Builder(DetalheMedicamento.this);
                                dlg.setMessage("Alarme ativado! Você receberá uma notificação às " + primeiroMedicamentoDaFilaDeAlarmes.getProximoHorario());
                                dlg.setNeutralButton("OK", null);
                                dlg.show();

                                chamarAlarme();
                            }
                            else
                            {
                                AlertDialog.Builder dlg = new AlertDialog.Builder(DetalheMedicamento.this);
                                dlg.setMessage("Erro ao inserir alarme!");
                                dlg.setNeutralButton("OK", null);
                                dlg.show();
                            }

                        } else {

                            AlertDialog.Builder dlg = new AlertDialog.Builder(DetalheMedicamento.this);
                            dlg.setMessage("Alarme foi desativado!");
                            dlg.setNeutralButton("OK", null);
                            dlg.show();

                            alarmes.delete(medicamento.getIdPaciente(),medicamento.getIdMedicacao());

                            if(alarmes.temProximoAlarme())
                            {
                                Alarme proxAlarme = alarmes.pegarProximoAlarme();
                                primeiroMedicamentoDaFilaDeAlarmes = medicamentos.getMedicamento(proxAlarme.getIdPaciente(),proxAlarme.getIdMedicacao());
                                chamarAlarme();
                            }

                            else
                                onDestroy();
                        }
                    }
                });
            }
            else
                finish();
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

    public void chamarAlarme()
    {
        Intent intent = new Intent("DISPARAR_ALARME");
        intent.putExtra("MEDICAMENTO", this.primeiroMedicamentoDaFilaDeAlarmes);
        PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

        Log.i("Valor de ProxHorario",this.primeiroMedicamentoDaFilaDeAlarmes.getProximoHorario());
        Calendar c = MetodosEmComum.stringToCalendar(this.primeiroMedicamentoDaFilaDeAlarmes.getProximoHorario());

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarme.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),p);
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

        if(this.medicamento.getMedicacaoContinua()==1)
            txtDurante.setText("Medicação contínua");
        else
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
        editUltimoHorario.setText(MetodosEmComum.horaToString(calendar));
        medicamento.setUltimoHorario(MetodosEmComum.horaToString(calendar));
        medicamentos.update(this.medicamento);
    }

    public void btnConfirmar(View view)
    {
        updateProximoHorario();

        if (medicamento.getMedicacaoContinua()==0)
        {
            medicamento.decrementarQtdRestantesDoMedicamento();

            if (medicamento.getQtdRestantesDoMedicamento()==0)
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Todas as doses foram tomadas! O medicamento será excluído da lista.");
                dlg.setNeutralButton("OK", null);
                dlg.show();

                medicamentos.delete(medicamento.getIdPaciente(), medicamento.getIdMedicacao());
            }
        }

        btnConfirmar.setEnabled(false);
        checkBoxAviso.setEnabled(true);

    }

    private void updateProximoHorario()
    {
        Calendar proximoHorario = MetodosEmComum.stringToCalendar(medicamento.getUltimoHorario());
        proximoHorario.add(Calendar.HOUR_OF_DAY,medicamento.getIntervalo());

        medicamento.setProximoHorario(MetodosEmComum.horaToString(proximoHorario));
        medicamentos.update(medicamento);

        txtProximoHorario.setText(medicamento.getProximoHorario()+" horas.");
    }

    public void btnSalvarDetalheMedicamento(View view)
    {
        Intent it = new Intent(this, MainActivity.class);
        startActivityForResult(it,0);
    }

}
