package com.example.caprojectexam;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextView user_id;
    Button logout_btn;
    Button getData_btn;
    Button setting_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logout_btn = (Button)findViewById(R.id.logout_btn);
        user_id= (TextView)findViewById(R.id.userId);
        logout_btn.setOnClickListener(this);
        user_id.setText(UserData.userId+"님");
        ListView listView = (ListView) findViewById(R.id.houseList);
        ArrayList<String> getDataList = new ArrayList<>();
        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getHouseNames());
        listView.setAdapter(adpater);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(LoginActivity.this, HouseInfoActivity.class);
                intent.putExtra("houseName",adpater.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.logout_btn:     // 로그아웃 버튼
                logout();
                break;
        }
    }
    private ArrayList<String> getHouseNames(){
        Log.w("getHouseNames","데이터 읽는중");
        try {
            LoginActivity.CustomTask task = new LoginActivity.CustomTask();
            String result = task.execute(UserData.userId).get();
            Log.w("받은값",result);
            Toast myToast = Toast.makeText(this.getApplicationContext(),"데이터 읽음", Toast.LENGTH_SHORT);
            myToast.show();
            String[] temp = result.split("/");
            ArrayList<String> results = new ArrayList<>();
            for(int i = 0; i< temp.length;i++){
                results.add(temp[i]);
            }
            return results;
        } catch (Exception e) {
            return null;
        }
    }


    private void logout() {
        Log.w("login","로그아웃 하는중");
        try {
            LoginActivity.CustomTask task = new LoginActivity.CustomTask();
            String result = task.execute("logout").get();
            Log.w("받은값",result);
            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
            Toast myToast = Toast.makeText(this.getApplicationContext(),"로그아웃 하셨습니다", Toast.LENGTH_SHORT);
            myToast.show();
            startActivity(intent2);
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
                String str;
                if(strings[0].equals("logout")){
                    Log.i("로그아웃","진행 중");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/logout.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    UserData.userId = "";
                    UserData.user = "";
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = conn.getHeaderField("result");
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                } else if(strings[0].equals(UserData.userId)){
                    Log.i("하우스 이름","불러오는 중");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/getHouseNames.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "userId="+UserData.userId; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();

                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = conn.getHeaderField("result");

                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }else if(strings[0].equals("getHouse")) {
                    Log.i("하우스","불러오는 중");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/getHouse.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "userId="+UserData.userId+"&HouseName="+strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = conn.getHeaderField("result")+"/";
                        receiveMsg += conn.getHeaderField("houseName")+"/";
                        receiveMsg += conn.getHeaderField("temperature")+"/";
                        receiveMsg += conn.getHeaderField("natrium")+"/";
                        receiveMsg += conn.getHeaderField("phosphorus")+"/";
                        receiveMsg += conn.getHeaderField("kalium")+"/";
                        receiveMsg += conn.getHeaderField("order")+"/";
                        receiveMsg += conn.getHeaderField("settedTemperature");

                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }
                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.

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
