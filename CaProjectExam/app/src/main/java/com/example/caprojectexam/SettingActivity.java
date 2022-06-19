package com.example.caprojectexam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    Button returnlogin;
    Button changeAlarmTime;
    Button deleteAllData;
    Button signout;
    TextView textView;
    private String[] selectTime = {"30분","1시간","2시간"};
    private AlertDialog timeSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        returnlogin = (Button)findViewById(R.id.returnlogin2);
        returnlogin.setOnClickListener(this);
        changeAlarmTime = (Button)findViewById(R.id.changeAlarmTime);
        changeAlarmTime.setOnClickListener(this);
        deleteAllData = (Button)findViewById(R.id.deleteAllData);
        deleteAllData.setOnClickListener(this);
        signout = (Button)findViewById(R.id.signout);
        signout.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.showAlarm);
        textView.setText("알림 설정 시간 : " + UserData.alarm);
        timeSelectDialog = new AlertDialog.Builder(SettingActivity.this )
                .setItems(selectTime, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UserData.alarm = selectTime[i];
                        textView.setText("알림 설정 시간 : " + UserData.alarm);

                    }
                })
                .setTitle("알람 설정")
                .setNegativeButton("취소",null)
                .setPositiveButton("확인",null)
                .create();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnlogin2:
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.changeAlarmTime:
                timeSelectDialog.show();
                break;
            case R.id.deleteAllData:
                deleteAlldata();
                break;
            case R.id.signout:
                signOut();
        }
    }
    private void deleteAlldata(){
        Log.w("deleteAllData","데이터 초기화");
        try {
            SettingActivity.CustomTask task = new SettingActivity.CustomTask();
            String result = task.execute("deleteAllData").get();
            Log.w("받은값",result);
            UserData.id = "";
            UserData.user = "";
            UserData.serial ="";
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            Toast myToast = Toast.makeText(this.getApplicationContext(),"데이터를 초기화합니다", Toast.LENGTH_SHORT);
            myToast.show();
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
    private void signOut(){
        Log.w("signOut","회원탈퇴");
        try {
            SettingActivity.CustomTask task = new SettingActivity.CustomTask();
            String result = task.execute("SignOut").get();
            Log.w("받은값",result);
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            Toast myToast = Toast.makeText(this.getApplicationContext(),"탈퇴되었습니다", Toast.LENGTH_SHORT);
            myToast.show();
            UserData.id = "";
            UserData.user = "";
            UserData.serial ="";
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                if(strings[0].equals("deleteAllData")) {
                    String str;
                    URL url = new URL("http://10.0.2.2:8080/CapProject/deleteAllConnectData.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id=" + UserData.id; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();

                    // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = conn.getHeaderField("result");
                        Log.i("통신 결과", conn.getHeaderField("result"));
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                } else if(strings[0].equals("SignOut")) {
                    String str;
                    URL url = new URL("http://10.0.2.2:8080/CapProject/signOut.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id=" + UserData.id; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();

                    // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = conn.getHeaderField("result");
                        Log.i("통신 결과", conn.getHeaderField("result"));
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 서버에서 보낸 값을 리턴합니다.
            return receiveMsg;
        }
    }

}

