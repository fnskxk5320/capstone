package com.example.caprojectexam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener{
    Button returnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        returnlogin = (Button) findViewById(R.id.returnlogin);
        ListView listView = (ListView) findViewById(R.id.statisticsList);
        TextView textView = (TextView) findViewById(R.id.totalAvg);
        returnlogin.setOnClickListener(this);
        ArrayList<String> getDataList = new ArrayList<>();
        if(getData() != null){
            getDataList = getData();
        }
        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getDataList);
        listView.setAdapter(adpater);
        if(getData() != null){
            textView.setText(TimeCalc.getAvg(getData()));
        }else{
            textView.setText("자료 없음");
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                new AlertDialog.Builder(StatisticsActivity.this)
                        .setTitle("데이터 삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인",null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteData(position);
                                Toast.makeText(StatisticsActivity.this, "삭제했습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(StatisticsActivity.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnlogin:
                Intent intent = new Intent(StatisticsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

        }
    }

    private ArrayList<String> getData() {
        Log.w("getData","데이터 읽는중");
        try {
            StatisticsActivity.CustomTask task = new StatisticsActivity.CustomTask();
            String result = task.execute("getdata").get();
            System.out.println(result);
            String[] strings = result.split("/");
            System.out.println(strings.length);
            ArrayList<String[]> results = new ArrayList<>();
            for(int i = 0;i < strings.length; i++){
                results.add(strings[i].split(","));
            }
            ArrayList<String[]> sitData = new ArrayList<>();
            ArrayList<String[]> standData = new ArrayList<>();
            Iterator<String[]> it = results.iterator();
            while(it.hasNext()){
                String[] temp = it.next();
                if(temp[1].equals("0")){
                    sitData.add(temp);
                } else {
                    standData.add(temp);
                }
            }
            if(sitData.size()>standData.size()){
                sitData.remove(sitData.size() - 1);
            }
            ArrayList<String> resultList = new ArrayList<>();
            for(int i = 0; i < sitData.size();i++){
                resultList.add(TimeCalc.TimeGapRootToCmp(sitData.get(i)[2],standData.get(i)[2]));
            }
            return resultList;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    private void deleteData(int position){
        Log.w("deleteData","데이터 삭제중");
        try {
            StatisticsActivity.CustomTask task = new StatisticsActivity.CustomTask();
            String result = task.execute("deletedata",position+"").get();
            System.out.println(result);

        } catch (Exception e) {
            e.getMessage();
        }
    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                if(strings[0].equals("getdata")) {
                    String str;
                    URL url = new URL("http://10.0.2.2:8080/CapProject/getDataList.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id=" + UserData.id + "&user=" + UserData.user; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();

                    // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        receiveMsg = conn.getHeaderField("result");
                        Log.i("통신 결과", conn.getHeaderField("result"));
                    } else {    // 통신이 실패한 이유를 찍기위한 로그
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                } else if(strings[0].equals("deletedata")) {
                    String str;
                    URL url = new URL("http://10.0.2.2:8080/CapProject/deleteConnectData.do");  // 어떤 서버에 요청할지(localhost 안됨.)
                    // ex) http://123.456.789.10:8080/hello/android
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                    conn.setDoOutput(true);
                    // 서버에 보낼 값 포함해 요청함.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id=" + UserData.id + "&position=" + strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                    osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                    osw.flush();

                    // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                    if (conn.getResponseCode() == conn.HTTP_OK) {
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
