package com.example.psiquemap.psiquemap.alarme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.psiquemap.psiquemap.DetalheMedicamento;
import com.example.psiquemap.psiquemap.MetodosEmComum;
import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.Alarme;
import com.example.psiquemap.psiquemap.entidades.Controle;
import com.example.psiquemap.psiquemap.entidades.Medicamento;
import com.example.psiquemap.psiquemap.sql.Alarmes;
import com.example.psiquemap.psiquemap.sql.DataBase;
import com.example.psiquemap.psiquemap.sql.Medicamentos;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by yuki on 16/10/16.
 */

public class BroadcastReceiverAlarme extends BroadcastReceiver {

    private Context context;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Alarmes alarmes;
    private Medicamento medicamento;
    private Medicamentos medicamentos;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        Alarme alarme;

        if(this.conexaoBD())
        {
            medicamentos = new Medicamentos(this.conn);
            alarmes = new Alarmes(this.conn);

            alarme = alarmes.pegarProximoAlarme();
            medicamento = medicamentos.getMedicamento(alarme.getIdPaciente(),alarme.getIdMedicacao());

            Intent it = new Intent(context,DetalheMedicamento.class);
            it.putExtra("MEDICAMENTO",this.medicamento);

            gerarNotificacao(context,it, "Psiquimap", "Lembrete: ", "Hora de tomar "+this.medicamento.getNomeMedicacao()+".");

            Log.i("DELETANDO EM ORDEM",this.medicamento.getIdPaciente()+" / " + this.medicamento.getIdMedicacao());
            alarmes.deletarEmOrdem(this.medicamento.getIdPaciente(),this.medicamento.getIdMedicacao());
            this.onDestroy();

            if(alarmes.temProximoAlarme())
            {
                chamarAlarme();
                Log.i("PROXIMO ALARME", "OK");
            }
        }
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(context);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    public void chamarAlarme()
    {
        Alarme a = alarmes.pegarProximoAlarme();

        Log.i("ALARME PEGO",a.toString());

        medicamento = medicamentos.getMedicamento(a.getIdPaciente(),a.getIdMedicacao());

        Intent intent = new Intent("DISPARAR_ALARME");
        intent.putExtra("MEDICAMENTO", medicamento);
        PendingIntent p = PendingIntent.getBroadcast(this.context, 0, intent, 0);

        Log.i("Valor de ProxHorario",this.medicamento.getProximoHorario());
        Calendar c = MetodosEmComum.ajusteData(MetodosEmComum.stringToCalendar(medicamento.getProximoHorario()));

        Calendar calendar = Calendar.getInstance();
        Log.i("GTM ALARME",""+c.getTimeInMillis());
        Log.i("GTM Agora",""+calendar.getTimeInMillis());

        AlarmManager alarme = (AlarmManager)this.context.getSystemService(ALARM_SERVICE);
        alarme.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),p);
        Log.i("ALARME PROGRAMADO",medicamento.getProximoHorario());
    }

    public void onDestroy()
    {
        Intent intent = new Intent("DISPARAR_ALARME");
        PendingIntent p = PendingIntent.getBroadcast(this.context, 0, intent, 0);

        AlarmManager alarme = (AlarmManager) this.context.getSystemService(ALARM_SERVICE);
        alarme.cancel(p);
        p.cancel();
    }

    private boolean conexaoBD()
    {
        try {

            dataBase = new DataBase(this.context);
            conn = dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.mipmap.ic_notificacoes);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_notificacoes));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.vibrate = new long[]{100, 200, 400, 800};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.mipmap.ic_notificacoes, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }
        catch(Exception e){}
    }

}