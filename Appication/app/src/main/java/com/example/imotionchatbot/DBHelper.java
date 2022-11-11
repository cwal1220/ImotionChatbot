package com.example.imotionchatbot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "emotion.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS EMOTION(emotiondate DATE UNIQUE, emotionval INT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS EMOTION");
        onCreate(db);
    }

    public void insertEmotionTable(String emotiondate, int emotionval) {
        // 데이터 쓰기
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO EMOTION (emotiondate, emotionval) VALUES ('" + emotiondate + "'," + emotionval + ") ON CONFLICT(emotiondate) DO UPDATE SET emotionval=emotionval+" + emotionval);
    }

    public void deleteEmotionTable(String emotiondate) {
        // 조건에 일치하는 행을 삭제
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM EMOTION WHERE emotiondate='" + emotiondate + "'");
    }

    public void updateEmotionTable(String emotiondate, int emotionval) {
        // 조건에 일치하는 행의 데이터 변경
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE EMOTION SET emotiondate=" + emotionval + "WHERE emotiondate=" + emotiondate);
    }

    public EmotionDAO getEmotionDAOByDate(String emotiondate) {
        // 날짜로 정보 조회하기
        EmotionDAO ret = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM EMOTION WHERE emotiondate=?", new String[] {emotiondate});
        if(mCursor.moveToFirst()) {
            ret = new EmotionDAO(mCursor.getString(0), mCursor.getInt(1));
        }
        mCursor.close();
        return ret;
    }

    public double getEmotionAverageThisMonth() {
        // 이번달의 감정수치 평균
        double ret;
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT avg(emotionval) FROM EMOTION WHERE strftime('%m',emotiondate)=strftime('%m', date('now'))", null);
        mCursor.moveToNext();
        ret = mCursor.getDouble(0);
        mCursor.close();
        return ret;
    }

    public ArrayList<EmotionDAO> getEmotionThisMonth() {
        // 이번달의 감정 수치
        double ret;
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT strftime('%d', emotiondate), emotionval FROM EMOTION WHERE emotiondate between strftime('%Y-%m-01', date('now')) and strftime('%Y-%m-%d', datetime(date('now'), '+1 days'))", null);
        ArrayList<EmotionDAO> list = new ArrayList<>();
        if(mCursor.moveToFirst()) {
            do {
                list.add(new EmotionDAO(mCursor.getString(0), mCursor.getInt(1)));
            } while(mCursor.moveToNext());
        }
        mCursor.close();
        return list;
    }

    public ArrayList<EmotionDAO> selectEmotionTable() {
        // 테이블의 모든 데이터 선택
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM EMOTION", null);
        ArrayList<EmotionDAO> list = new ArrayList<>();
        if(mCursor.moveToFirst()) {
            do {
                list.add(new EmotionDAO(mCursor.getString(0), mCursor.getInt(1)));
            } while(mCursor.moveToNext());
        }
        mCursor.close();
        return list;
    }
}
