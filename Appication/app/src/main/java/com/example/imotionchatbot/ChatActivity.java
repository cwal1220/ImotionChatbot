package com.example.imotionchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<DataItem> dataList;
    private Button btn_send1;
    private EditText editText1;
    private RecyclerView recyvlerv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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
                editText1.setText("");
                dataList.add(new DataItem("조까세요","사용자1",Code.ViewType.LEFT_CONTENT));
                recyvlerv.setAdapter(new MyAdapter(dataList));
                recyvlerv.scrollToPosition(dataList.size()-1);
                break;
        }
    }
}