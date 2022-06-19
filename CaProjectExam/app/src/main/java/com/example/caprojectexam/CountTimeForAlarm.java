package com.example.caprojectexam;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.Toast;
import android.os.Handler;

import androidx.core.app.NotificationCompat;

import java.util.logging.LogRecord;


public class CountTimeForAlarm extends Service {
    NotificationManager notificationManager;
    Notification notification;
    ServiceThread thread;
    int NOTIFICATION_ID = 234;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("서비스 시작");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
            System.out.println("채널 생성");
        }
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        thread.stopForever();
        System.out.println("서비스 종료");
    }
    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(CountTimeForAlarm.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(CountTimeForAlarm.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            notification = new NotificationCompat.Builder(getApplicationContext(),"my_channel_01")
                    .setContentTitle("일어나십시오")
                    .setContentText((String)msg.obj)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setTicker("알림!!!")
                    .setContentIntent(pendingIntent)
                    .build();

            //소리추가
            notification.defaults = Notification.DEFAULT_SOUND;

            //알림 소리를 한번만 내도록
            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            //확인하면 자동으로 알림이 제거 되도록
            notification.flags = Notification.FLAG_AUTO_CANCEL;


            notificationManager.notify(NOTIFICATION_ID , notification);

        }
    }
}
