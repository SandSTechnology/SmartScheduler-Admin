package com.smartscheduler_admin.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import com.smartscheduler_admin.R;

/**
 * Helper class for showing and canceling location
 * notifications.
 * <p>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */

public class LocationNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private static final String NOTIFICATION_TAG = "HomeServiceAppLocationTracking";
    private static Notification notification;
    private static Notification.Builder builder;
    @SuppressLint("StaticFieldLeak")
    private static NotificationCompat.Builder builderCompact;
    private static Intent iStopService;
    private static PendingIntent piStopService;
    private static boolean isFirst = true;

    public static void notify(final Context context,
                              final String title, final String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifyO(context, title, text);
        } else {
            notifyPre(context, title, text);
        }
    }

    private static String createLocationChannel(Context ctx) {
        // Create a channel.
        NotificationManager notificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelName = ctx.getString(R.string.channel_id);
        int importance = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_DEFAULT;
        }

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        NotificationChannel notificationChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(
                    ctx.getString(R.string.channel_id), channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }

        return channelName;
    }

    public static void notifyO(Context context, final String title, final String text) {
        if (isFirst) {
            String channelId = createLocationChannel(context);
            iStopService = new Intent(context, LocationTrackerService.class);
            iStopService.putExtra("key", "stop");
            piStopService = PendingIntent.getService(
                    context, 1, iStopService, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new Notification.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setOnlyAlertOnce(true)
                        .setOngoing(true)
                        .setContentIntent(piStopService)
                        .setAutoCancel(false); // Automatically dismiss the notification when it is touched.
            }
            notification = builder.build();
            // THIS LINE IS THE IMPORTANT ONE
            // This notification will not be cleared by swiping or by pressing "Clear all"
            notification.flags = Notification.FLAG_NO_CLEAR; // & Notification.FLAG_ONLY_ALERT_ONCE & Notification.FLAG_ONGOING_EVENT;
            notify(context, notification);
            isFirst = false;
        } else {
            builder.setContentText(text);
            notify(context, notification = builder.build());
        }
    }

    public static void notifyPre(final Context context,
                                 final String title, final String text) {
        if (isFirst) {
            iStopService = new Intent(context, LocationTrackerService.class);
            iStopService.putExtra("key", "stop");
            piStopService = PendingIntent.getService(
                    context, 1, iStopService, PendingIntent.FLAG_UPDATE_CURRENT);

            builderCompact = new NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setOnlyAlertOnce(true)
                    .setOngoing(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(piStopService)
                    .setAutoCancel(false); // Automatically dismiss the notification when it is touched.

            notification = builderCompact.build();
            // THIS LINE IS THE IMPORTANT ONE
            // This notification will not be cleared by swiping or by pressing "Clear all"
            notification.flags = Notification.FLAG_NO_CLEAR;//& Notification.FLAG_ONLY_ALERT_ONCE & Notification.FLAG_ONGOING_EVENT;
            notify(context, notification);
            isFirst = false;
        } else {
            builderCompact.setContentText(text);
            notify(context, notification = builderCompact.build());
        }
    }

    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }

    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_TAG, 0);
    }
}
