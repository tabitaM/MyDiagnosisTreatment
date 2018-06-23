package com.tabita.mydiagnosistreatment.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.tabita.mydiagnosistreatment.R;

public class MyNotification {

    private static NotificationManagerCompat notificationManager;
    private static NotificationCompat.Builder notificationBuilder;

    public MyNotification(Context context) {
        notificationManager = NotificationManagerCompat.from(context);
        notificationBuilder = new NotificationCompat.Builder(context, "NOTIFICATION_CHANNEL")
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("It's time to take your pills")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        createNotificationChannel(context);
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel for API26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("NOTIFICATION_CHANNEL", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setContentText(String text) {
        notificationBuilder.setContentText(text);
    }

    public static void sendNotification() {
        notificationManager.notify(1234, notificationBuilder.build());
    }
}
