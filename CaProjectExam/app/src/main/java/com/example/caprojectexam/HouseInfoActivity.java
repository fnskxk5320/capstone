package com.example.caprojectexam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HouseInfoActivity extends AppCompatActivity implements View.OnClickListener{
    Button tologinbtn;
    Button changeSettedTemperature;
    Button upOrder;
    Button downOrder;
    Button changeHouseName;
    String houseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        houseName = getIntent().getStringExtra("houseName");
        setContentView(R.layout.activity_house_info);
        tologinbtn = (Button) findViewById(R.id.tologinbtn);
        changeSettedTemperature = (Button) findViewById(R.id.changeSettedTemperature);
        changeHouseName = (Button) findViewById(R.id.changeHouseName);
        upOrder = (Button) findViewById(R.id.upOrder);
        downOrder = (Button) findViewById(R.id.downOrder);
        ArrayList<String> houseInfo = getHouseInfo(houseName);
        TextView textViewHouseName = (TextView) findViewById(R.id.houseName);
        textViewHouseName.setText(houseName);
        TextView textViewTemperature = (TextView) findViewById(R.id.temperature);
        textViewTemperature.setText(textViewTemperature.getText()+houseInfo.get(2));
        TextView textViewOrder = (TextView) findViewById(R.id.order);
        if(Integer.parseInt(houseInfo.get(6)) == -1){
            textViewOrder.setText(textViewOrder.getText()+"초기정보");
        }else if(Integer.parseInt(houseInfo.get(6)) == 1){
            textViewOrder.setText(textViewOrder.getText()+"올라감");
        }else if(Integer.parseInt(houseInfo.get(6)) == 0){
            textViewOrder.setText(textViewOrder.getText()+"내려감");
        }
        TextView textViewSettedTemperature = (TextView) findViewById(R.id.settedTemperature);
        textViewSettedTemperature.setText(textViewSettedTemperature.getText()+houseInfo.get(7));
        TextView textViewNatrium = (TextView) findViewById(R.id.natrium);
        textViewNatrium.setText(textViewNatrium.getText()+houseInfo.get(3));
        TextView textViewPhosphorus = (TextView) findViewById(R.id.phosphorus);
        textViewPhosphorus.setText(textViewPhosphorus.getText()+houseInfo.get(4));
        TextView textViewKalium = (TextView) findViewById(R.id.kalium);
        textViewKalium.setText(textViewKalium.getText()+houseInfo.get(5));
        tologinbtn.setOnClickListener(this);
        changeSettedTemperature.setOnClickListener(this);
        changeHouseName.setOnClickListener(this);
        upOrder.setOnClickListener(this);
        downOrder.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        HouseInfoActivity.CustomTask task = new HouseInfoActivity.CustomTask();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText settedTemperature = new EditText(this);
        final EditText changeHouseName = new EditText(this);
        switch (v.getId()) {
            case R.id.upOrder:
                try {
                    String result = task.execute(UserData.userId,houseName,"upOrder").get();
                    Log.w("받은값",result);
                    finish();//인텐트 종료
                    Intent intent = getIntent(); //인텐트
                    startActivity(intent); //액티비티 열기
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.downOrder:
                try {
                    String result = task.execute(UserData.userId,houseName,"downOrder").get();
                    Log.w("받은값",result);
                    finish();//인텐트 종료
                    Intent intent = getIntent(); //인텐트
                    startActivity(intent); //액티비티 열기
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tologinbtn:
                Intent intent = new Intent(HouseInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.changeSettedTemperature:
                alert = new AlertDialog.Builder(this);
                alert.setTitle(houseName+"하우스의 설정온도 변경");
                alert.setMessage("변경하실 온도를 입력하세요");
                alert.setView(settedTemperature);
                alert.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            if(settedTemperature.getText().toString().equals("")){
                            }
                            else{
                                String result = task.execute(UserData.userId,houseName,settedTemperature.getText().toString()).get();
                            }
                            finish();//인텐트 종료
                            Intent intent = getIntent(); //인텐트
                            startActivity(intent); //액티비티 열기
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alert.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
                break;
            case R.id.changeHouseName:
                alert = new AlertDialog.Builder(this);
                alert.setTitle(houseName+"하우스 이름 변경");
                alert.setMessage("변경하실 이름을 입력하세요");
                alert.setView(changeHouseName);
                alert.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            if(changeHouseName.getText().toString().equals("")){
                            }
                            else{
                                String result = task.execute(UserData.userId,houseName,"UpdateName",changeHouseName.getText().toString()).get();
                            }
                            Intent intent = new Intent(HouseInfoActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alert.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
                break;
        }
    }
    private ArrayList<String> getHouseInfo(String houseName){
        Log.w("getHouseNames","데이터 읽는중");
        try {
            HouseInfoActivity.CustomTask task = new HouseInfoActivity.CustomTask();
            String result = task.execute(UserData.userId,houseName,"houseInfo").get();
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
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                if(strings[2].equals("houseInfo")){
                    String str;
                    Log.i("하우스", "불러오는 중");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/getHouse.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "userId=" + strings[0] + "&houseName=" + strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = conn.getHeaderField("result") + "/";
                        receiveMsg += conn.getHeaderField("houseName") + "/";
                        receiveMsg += conn.getHeaderField("temperature") + "/";
                        receiveMsg += conn.getHeaderField("natrium") + "/";
                        receiveMsg += conn.getHeaderField("phosphorus") + "/";
                        receiveMsg += conn.getHeaderField("kalium") + "/";
                        receiveMsg += conn.getHeaderField("order") + "/";
                        receiveMsg += conn.getHeaderField("settedTemperature");
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }
                else if(strings[2].equals("upOrder")){
                    String str;
                    Log.i("개폐기", "올림");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/updateOrder.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "userId=" + strings[0] + "&houseName=" + strings[1]+"&order=1"; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = "order=1";
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }
                else if(strings[2].equals("downOrder")){
                    String str;
                    Log.i("개폐기", "내림");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/updateOrder.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "userId=" + strings[0] + "&houseName=" + strings[1]+"&order=0"; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = "order=0";
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }else if(strings[2].equals("UpdateName")){
                    String str;
                    Log.i("하우스 이름", "변경");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/updateHouseName.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "userId=" + strings[0] + "&houseName=" + strings[1]+"&updateHouseName=" + strings[3]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = "success";
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }
                else {
                    String str;
                    Log.i("설정온도", "변경중");
                    URL url = new URL("http://59.4.232.234:50505/CapProject/updateSettedTemperature.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "userId=" + strings[0] + "&houseName=" + strings[1]+"&settedTemperature="+strings[2]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = "success";
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                }
                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

            // 서버에서 보낸 값을 리턴합니다.
            return receiveMsg;
        }
    }
}
