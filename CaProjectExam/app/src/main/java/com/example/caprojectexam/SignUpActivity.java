package com.example.caprojectexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    Button loginpage_btn;             // 로그인 화면 버튼
    Button signup_btn;                // 가입 버튼

    EditText id_confirm;                // id 에디트
    EditText pw_confirm;                // pw 에디트
    EditText un_confirm;                // sn 에디트
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginpage_btn = (Button)findViewById(R.id.loginpage_btn);    // 로그인 화면 버튼 연결
        signup_btn = (Button)findViewById(R.id.signup_btn);         // 가입 버튼 연결

        loginpage_btn.setOnClickListener(this);                 // 리스너 생성
        signup_btn.setOnClickListener(this);                // 리스너 생성

        id_confirm = (EditText)findViewById(R.id.id_confirm);    // id 찾음
        pw_confirm = (EditText)findViewById(R.id.pw_confirm);    // pw 찾음
        un_confirm = (EditText)findViewById(R.id.un_confirm);    // sn 찾음
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginpage_btn:     // 로그인 화면 버튼
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.signup_btn:    // 가입 버튼
                signUp();
                //Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                //startActivity(intent2);
                //finish();
                //break;

        }
    }
    void signUp(){
        Log.w("signUp","회원가입 중");
        try {
            String id = id_confirm.getText().toString();
            String pw = pw_confirm.getText().toString();
            String un = un_confirm.getText().toString();
            Log.w("앱에서 보낸값",id+", "+pw+ ", " + un);

            SignUpActivity.CustomTask task = new SignUpActivity.CustomTask();
            String result = task.execute(id,pw,un).get();
            Log.w("받은값",result);

            Intent intent2 = new Intent(SignUpActivity.this, MainActivity.class);
            if(result.equals("success")){
                Toast myToast = Toast.makeText(this.getApplicationContext(),"회원가입 성공", Toast.LENGTH_SHORT);
                myToast.show();
                startActivity(intent2);
                finish();
            }else if (result.equals("failure")){
                Toast myToast = Toast.makeText(this.getApplicationContext(),"회원가입 실패", Toast.LENGTH_SHORT);
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
                URL url = new URL("59.4.232.234:50505/CapProject/signUp.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userId="+strings[0]+"&password="+strings[1]+"&userName="+strings[2]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();

                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    receiveMsg = conn.getHeaderField("result");
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
