package com.example.caprojectexam;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TimeCalc {
    static String TimeGapRootToCmp(String bigTime,String cmpTime){
        try {
            Date rootDate = new Date();
            rootDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bigTime);
            Date cmpDate = new Date();
            cmpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cmpTime);
            Calendar cmpCalDate = Calendar.getInstance();
            Calendar rootCalDate = Calendar.getInstance();
            rootCalDate.setTime(rootDate);
            cmpCalDate.setTime(cmpDate);
            long diffSec = (rootCalDate.getTimeInMillis() - cmpCalDate.getTimeInMillis()) / 1000;
            long leftSec = diffSec % 60;
            long diffMin = diffSec / 60;
            long leftMin = diffMin % 60;
            long diffHour = diffMin / 60;
            long leftHour = diffHour % 24;
            long diffDays = diffHour / (24);
            String getTime = "";
            if (diffDays != 0) {
                getTime += diffDays + "일 ";
            }
            if (leftHour != 0) {
                getTime += leftHour + "시간 ";
            }
            getTime += leftMin + "분";
            return getTime;
        }catch (Exception e){}
        return null;
    }
    static String getAvg(ArrayList<String> stringArrayList){
        List<Integer> days = new ArrayList<>();
        List<Integer> hours = new ArrayList<>();
        List<Integer> minutes = new ArrayList<>();
        Iterator<String> it = stringArrayList.iterator();
        while(it.hasNext()){
            String temp1 = it.next().replaceAll(" ", "");
            String[] temp2 = null;
            if(temp1.contains("일")){
                temp2 = temp1.split("일");
                days.add(Integer.parseInt(temp2[0]));
                temp1 = temp2[1];
            }
            if(temp1.contains("시")){
                temp2 = temp1.split("시");
                hours.add(Integer.parseInt(temp2[0]));
                temp1 = temp2[1];
            }
            temp2 = temp1.split("분");
            minutes.add(Integer.parseInt(temp2[0]));
        }

        int daysum = 0;
        Iterator<Integer> itInt = days.iterator();
        while (itInt.hasNext()){
            daysum += itInt.next();
        }
        System.out.println(daysum);
        int hoursum = 0;
        itInt = hours.iterator();
        while (itInt.hasNext()){
            hoursum += itInt.next();
        }
        System.out.println(hoursum);
        int minutesum = 0;
        itInt = minutes.iterator();
        while (itInt.hasNext()){
            minutesum += itInt.next();
        }
        System.out.println(minutesum);
        int avgday = daysum/stringArrayList.size();
        System.out.println(avgday);
        int avghour = hoursum/stringArrayList.size();
        System.out.println(avghour);
        int avgminute = minutesum/stringArrayList.size();
        System.out.println(avgminute);
        String getTime = "";
        if (avgday != 0) {
            getTime += avgday + "일 ";
        }
        if (avghour != 0) {
            getTime += avghour + "시 ";
        }
        getTime += avgminute + "분";
        return getTime;
    }
    static int CountTime(){
        try {
            int setAlarmTime = Integer.parseInt(UserData.alarm.replaceAll("[^0-9]", ""));
            if(setAlarmTime == 1){
                setAlarmTime = 60;
            } else if(setAlarmTime == 2){
                setAlarmTime = 120;
            }
            Date rootDate = new Date();
            Date cmpDate = new Date();
            cmpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(UserData.lastSit);
            Calendar cmpCalDate = Calendar.getInstance();
            Calendar rootCalDate = Calendar.getInstance();
            rootCalDate.setTime(rootDate);
            cmpCalDate.setTime(cmpDate);
            int diffSec = (int)(rootCalDate.getTimeInMillis() - cmpCalDate.getTimeInMillis()) / 1000;
            int diffMin = diffSec / 60;
            int AlarmCount = diffMin / setAlarmTime;
            return AlarmCount;
        }catch (Exception e){}
        return 0;
    }
}
