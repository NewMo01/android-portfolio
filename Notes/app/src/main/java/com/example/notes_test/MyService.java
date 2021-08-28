package com.example.notes_test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.notes_test.fragments.GoalProgress;

import java.util.Calendar;

public class MyService extends Service {

    public static final String CHANNEL_ID = "my_notification";
    public static final int NOTIFICATION_ID = 1;
    public static final String BROADCAST_DATA = "time";
    public static final String MY_BROADCAST = "com.example.notes_test.REMAINING_TIME";
    CountDownTimer countDownTimer ;
    Intent intent = new Intent(MY_BROADCAST);



    @Override
    public void onCreate() {
        super.onCreate();

        countDownTimer = new CountDownTimer(GoalProgress.chosenTime-Calendar.getInstance().getTimeInMillis() ,  1000) {
            @Override
            public void onTick(long l) {
                intent.putExtra(BROADCAST_DATA , l);
                sendBroadcast(intent);

            }

            @Override
            public void onFinish() {
                Log.e("timer finished" , "on finish timer now");
                showNotification();
                stopSelf();
            }
        };
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyService();
        else
            startForeground(2, new Notification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        countDownTimer.start();
        createNotificationChannel();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            countDownTimer.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }
        stopForeground(true);
        Log.e("Destroy service" , "service has destroyed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


   @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyService(){
       Intent intent = new Intent(this , GoalProgress.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
       PendingIntent pendingIntent = PendingIntent.getActivity(this,0 ,intent,  0);
        Notification notification = new Notification.Builder(this,CHANNEL_ID)
                .setContentTitle("Focus!")
                .setContentText("Time is running , good luck")
                .setSmallIcon(R.drawable.ic_run)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(3,notification);
    }

    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name , importance );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void showNotification(){
        // start an Activity by notification
        Intent intent = new Intent(this , MainScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 ,intent,  0);
        // build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Congratulations")
                .setContentText("you finished your target , well done!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        // show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
