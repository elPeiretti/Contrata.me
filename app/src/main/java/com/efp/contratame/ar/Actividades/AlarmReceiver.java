package com.efp.contratame.ar.Actividades;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Log.i("Alarm", "receiver");
        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelid")
                .setSmallIcon(R.drawable.icono_sin_fondo)
                .setContentTitle("Notificacion")
                .setContentText("Texto")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);
        Log.i("Alarm", "notification");

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());

    }
}
