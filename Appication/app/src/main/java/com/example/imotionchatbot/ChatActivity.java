package com.example.imotionchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<DataItem> dataList;
    private Button btn_send1;
    private EditText editText1;
    private RecyclerView recyvlerv;
    private Post ChattingPost;
    private ClovaPost clovaPost;
    private String reply;

    private JSONObject clovaPostParam = new JSONObject();

    private DBHelper dbHelper = new DBHelper(ChatActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChattingPost = new Post("138.2.126.137", "/request/chatting");
        clovaPost = new ClovaPost();
        initData();

        recyvlerv = findViewById(R.id.recyvlerv);
        btn_send1 = findViewById(R.id.btn_send1);
        btn_send1.setOnClickListener(this);
        editText1 = findViewById(R.id.editText1);

        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyvlerv.setLayoutManager(manager);
        recyvlerv.setAdapter(new MyAdapter(dataList));

    }

    private void initData(){
        dataList = new ArrayList<>();
        dataList.add(new DataItem("Ary님 입장!",null,Code.ViewType.CENTER_CONTENT));
        dataList.add(new DataItem("사용자님 입장!",null,Code.ViewType.CENTER_CONTENT));
        dataList.add(new DataItem("안녕하세요! Ary 입니다.","Ary",Code.ViewType.LEFT_CONTENT));
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        switch(b.getId()) {
            case R.id.btn_send1:
                dataList.add(new DataItem(editText1.getText().toString(),"사용자",Code.ViewType.RIGHT_CONTENT));

                new Thread(() -> {
                    try {
                        String userMessage = editText1.getText().toString();
                        JSONObject jo = new JSONObject();
                        jo.put("chat", userMessage);

                        JSONObject jsonObject = ChattingPost.sendPost(jo.toString());

                        if (jsonObject != null) {
                            reply = jsonObject.getString("data");
                        }
                        else {
                            reply = "잘 이해하지 못했어요 ㅠ_ㅠ";
                        }

                        // TODO: 네이버 API를 이용한 감정 파악코드 추가하기
                        clovaPostParam.put("content", userMessage);
                        int clovaRet = clovaPost.sendPost(clovaPostParam.toString());
                        Log.i("TELECHIPS", "clovaRet : " + clovaRet);

                        // 현재 날짜를 구해 날짜 기준을 이용해 DB에 삽입
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c1 = Calendar.getInstance();
                        String strToday = sdf.format(c1.getTime());
                        Log.i("TELECHIPS", strToday);
                        dbHelper.insertEmotionTable(strToday, clovaRet);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataList.add(new DataItem(reply,"Ary",Code.ViewType.LEFT_CONTENT));
                                recyvlerv.setAdapter(new MyAdapter(dataList));
                                recyvlerv.scrollToPosition(dataList.size()-1);
                                editText1.setText("");
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }).start();

                break;
        }
    }
}
