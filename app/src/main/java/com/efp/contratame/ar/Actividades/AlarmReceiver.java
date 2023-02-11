package com.efp.contratame.ar.Actividades;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.MenuPpalFragment;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.modelo.Usuario;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Alarm", "receiver");
        //presiona la notificacion
        Intent destino = new Intent(context.getApplicationContext(), MainActivity.class);
        Bundle extras = intent.getExtras();

        destino.putExtra("idUsuario", extras.getString("idUsuario"));
        destino.putExtra("mail", extras.getString("mail"));
        destino.putExtra("nombre", extras.getString("nombre"));
        destino.putExtra("foto", extras.getString("foto"));
        destino.putExtra("sesion", extras.getString("sesion"));

        destino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        //presiona el boton eliminar
        Intent eliminar = new Intent(context.getApplicationContext(), MainActivity.class);
        eliminar.putExtra("idUsuario", extras.getString("idUsuario"));
        eliminar.putExtra("mail", extras.getString("mail"));
        eliminar.putExtra("nombre", extras.getString("nombre"));
        eliminar.putExtra("foto", extras.getString("foto"));
        eliminar.putExtra("sesion", extras.getString("sesion"));

        //eliminar.putExtra("fragment", "perfil");
        eliminar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent eliminarPIntent = PendingIntent.getActivity(context, 0, eliminar, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        //presiona el boton mantener
        Intent mantener = new Intent (context.getApplicationContext(), MainActivity.class);
        mantener.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mantener.putExtra("idUsuario",extras.getString("idUsuario"));
        mantener.putExtra("mail",extras.getString("mail"));
        mantener.putExtra("nombre",extras.getString("nombre"));
        mantener.putExtra("foto",extras.getString("foto"));
        mantener.putExtra("sesion",extras.getString("sesion"));
        PendingIntent mantenerPIntent = PendingIntent.getActivity(context,0,mantener,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelid")
                .setSmallIcon(R.drawable.icono_sin_fondo)
                .setContentTitle("Acerca de su requerimiento")
                .setContentText("¿Desea mantenerlo o eliminarlo?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.ic_delete,"Eliminar",eliminarPIntent)
                .addAction(android.R.drawable.checkbox_on_background,"Mantener",mantenerPIntent)
                .setAutoCancel(true);

        Log.i("Alarm", "notification");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());

    }
}
