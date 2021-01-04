package com.example.polyville2.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.polyville2.R;

public class PublicaitonNotification {

    private final String CHANNEL_NAME = "PUBLICATIONS";
    private final String CHANNEL_DESC = "Publication des magasins aux alentours";
    private final String CHANNEL_ID = "PUBLICATIONS";

    private int notificationId = 100;


    private Context context;

    public PublicaitonNotification(Context c){
        context = c;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private NotificationCompat.Builder createNotification(String title, String content){


        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.store)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

    }

    public void sendNotificaiton(String title, String content){

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId++, createNotification(title,content).build());
    }

}
