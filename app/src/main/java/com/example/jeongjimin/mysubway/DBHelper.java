package com.example.jeongjimin.mysubway;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*SQL데이터 베이스 구축*/

class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MySubway.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE StationHistory(_id INTEGER PRIMARY KEY AUTOINCREMENT, startstation TXT, destistation TXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS StationHistory");
        onCreate(db);
    }

}