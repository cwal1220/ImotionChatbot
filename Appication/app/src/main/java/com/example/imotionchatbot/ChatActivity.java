package com.example.imotionchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<DataItem> dataList;
    private Button btn_send1;
    private EditText editText1;
    private RecyclerView recyvlerv;
    private Post ChattingPost;
    private String reply;

    private final String ClientID = "gv6bxin5su";
    private final String ClientSecret = "xB5jO0EAJrbBFxkwzMvAGl62dBMwdtBFeWVcNf69";
    private final String ClientUrl="https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";

    private JSONObject headers = new JSONObject();

    private DBHelper dbHelper = new DBHelper(ChatActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            headers.put("X-NCP-APIGW-API-KEY-ID", ClientID);
            headers.put("X-NCP-APIGW-API-KEY", ClientSecret);
            headers.put("Content-Type", "application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChattingPost = new Post("210.183.6.81", "/request/chatting");

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
        dataList.add(new DataItem("시용자1님 입장했음",null,Code.ViewType.CENTER_CONTENT));
        dataList.add(new DataItem("사용자2님 입장했음",null,Code.ViewType.CENTER_CONTENT));
        dataList.add(new DataItem("안녕하세요11","사용자1",Code.ViewType.LEFT_CONTENT));
        dataList.add(new DataItem("안녕하세요22","사용자2",Code.ViewType.RIGHT_CONTENT));
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        switch(b.getId()) {
            case R.id.btn_send1:
                dataList.add(new DataItem(editText1.getText().toString(),"사용자2",Code.ViewType.RIGHT_CONTENT));

                new Thread(() -> {
                    try {
                        JSONObject jo = new JSONObject();
                        jo.put("chat", editText1.getText().toString());

                        JSONObject jsonObject = ChattingPost.sendPost(jo.toString());

                        if (jsonObject != null) {
                            reply = jsonObject.getString("data");
                        }
                        else {
                            reply = "잘 모르겠어요 ㅠ_ㅠ";
                        }

                        // TODO: 네이버 API를 이용한 감정 파악코드 추가하기
                        int emotionvalue = 1;

                        // 현재 날짜를 구해 날짜 기준을 이용해 DB에 삽입
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c1 = Calendar.getInstance();
                        String strToday = sdf.format(c1.getTime());
                        Log.i("TELECHIPS", strToday);
                        dbHelper.insertEmotionTable(strToday, emotionvalue);

                        // Debug
                        double avg = dbHelper.getEmotionAverageThisWeek();
                        Log.i("TELECHIPS", "평균 :" + avg);
                        
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataList.add(new DataItem(reply,"사용자1",Code.ViewType.LEFT_CONTENT));
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