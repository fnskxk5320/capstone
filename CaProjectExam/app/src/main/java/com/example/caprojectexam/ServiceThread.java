package com.example.caprojectexam;

import android.os.Handler;
import android.os.Message;
public class ServiceThread extends Thread {
    Handler handler;
    int Alarms;
    int setAlarmTime;
    boolean isRun = true;

    public ServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }
    @Override
    public void run() {
        try {
            System.out.println("서비스 쓰레드 시작");
            while (isRun) {
                Alarms = TimeCalc.CountTime();
                setAlarmTime = Integer.parseInt(UserData.alarm.replaceAll("[^0-9]", ""));
                System.out.println(Alarms);
                if (Alarms > 0) {
                    String notice = "";
                    if (setAlarmTime == 30) {
                        if (Alarms * setAlarmTime / 60 > 0) {
                            notice = Alarms * setAlarmTime / 60 + "시간 ";
                        }
                        notice = notice + Alarms * setAlarmTime % 60 + "분 앉아있었습니다";

                    } else {
                        notice = Alarms * setAlarmTime + "시간 앉아있었습니다";
                    }
                    Message message = handler.obtainMessage();
                    message.obj = notice;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(300000); //5분
                    } catch (Exception e) {
                        System.out.println("서비스 쓰레드 종료");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("서비스 쓰레드 종료");
        }
    }
}
