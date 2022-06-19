package com.example.caprojectexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button join_btn;                 // 회원가입 버튼
    Button login_btn;                // 로그인 버튼

    EditText id_edit;                // id 에디트
    EditText pw_edit;                // pw 에디트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        join_btn = (Button)findViewById(R.id.join_btn);    // 회원가입 버튼을 찾고
        login_btn = (Button)findViewById(R.id.login_btn);  // 로그인 버튼을 찾고

        join_btn.setOnClickListener(this);                 // 리스너를 달아줌.
        login_btn.setOnClickListener(this);                // 리스너를 달아줌.

        id_edit = (EditText)findViewById(R.id.id_edit);    // id 에디트를 찾음.
        pw_edit = (EditText)findViewById(R.id.pw_edit);    // pw 에디트를 찾음.
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_btn:     // 회원가입 버튼
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_btn:    // 로그인 버튼
                login();

        }
    }
    void login() {
        Log.w("login","로그인 하는중");
        try {
            String id = id_edit.getText().toString();
            String pw = pw_edit.getText().toString();
            Log.w("앱에서 보낸값",id+", "+pw);

            CustomTask task = new CustomTask();
            String result = task.execute(id,pw).get();
            Log.w("받은값",result);

            Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
            if(result.contains("success")){
                String[] array = result.split("/");
                UserData.userId = array[1];
                UserData.user = array[2];
                startActivity(intent2);
                finish();
            }else if (result.contains("failure")){
                Toast myToast = Toast.makeText(this.getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT);
                myToast.show();
            }

        } catch (Exception e) {

        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://59.4.232.234:50505/CapProject/login.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);
                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userId="+strings[0]+"&password="+strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();

                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    receiveMsg = conn.getHeaderField("result")+"/";
                    receiveMsg += conn.getHeaderField("userId")+"/";
                    receiveMsg += conn.getHeaderField("userName");
                } else {    // 통신이 실패한 이유를 찍기위한 로그
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
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