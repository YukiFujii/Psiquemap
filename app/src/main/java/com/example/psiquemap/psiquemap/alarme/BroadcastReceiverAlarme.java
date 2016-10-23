package com.example.psiquemap.psiquemap.alarme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.psiquemap.psiquemap.DetalheMedicamento;
import com.example.psiquemap.psiquemap.MainActivity;
import com.example.psiquemap.psiquemap.Medicacao;
import com.example.psiquemap.psiquemap.R;
import com.example.psiquemap.psiquemap.entidades.Medicamento;

/**
 * Created by yuki on 16/10/16.
 */

public class BroadcastReceiverAlarme extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Script", "-> Alarme");

        Bundle bundle = intent.getExtras();

        Medicamento medicamento = (Medicamento) bundle.get("MEDICAMENTO");

        Log.i("Script",medicamento.getNomeMedicacao());

        Intent it = new Intent(context,DetalheMedicamento.class);
        it.putExtra("MEDICAMENTO",medicamento);

        gerarNotificacao(context,it, "Psiquimap", "Lembrete: ", "Hora de tomar "+medicamento.getNomeMedicacao()+".");


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