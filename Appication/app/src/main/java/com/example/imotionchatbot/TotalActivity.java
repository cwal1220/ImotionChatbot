package com.example.imotionchatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class TotalActivity extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(TotalActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        TextView emotionAvgView = (TextView) findViewById(R.id.emotionAvgView);

        ArrayList<EmotionDAO> emotionDAOS = dbHelper.getEmotionThisMonth();
        double averageThisMonth = dbHelper.getEmotionAverageThisMonth();
        emotionAvgView.setText(String.format("%.2f", averageThisMonth) + "점");

        List<Entry> entries = new ArrayList<>();

        for(int i=0; i<31; i++)
        {
            entries.add(new Entry(i+1, 0));
        }

        for(EmotionDAO emotionDAO:emotionDAOS)
        {
            entries.set(Integer.parseInt(emotionDAO.emotiondate), new Entry(Integer.parseInt(emotionDAO.emotiondate) + 1, emotionDAO.emotionval));
        }


        LineDataSet lineDataSet = new LineDataSet(entries, "날짜");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);

        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.invalidate();

    }
}