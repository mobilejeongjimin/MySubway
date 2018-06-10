package com.example.jeongjimin.mysubway;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class PathFindActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    SubwayCursorAdapter cursorAdapter;

    private Button SearchButton;
    private EditText StartStationEdit;
    private String StartStationTxt;
    private EditText DestiStationEdit;
    private String DestiStationTxt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find);

        SearchButton = findViewById(R.id.SearchButton);
        StartStationEdit = findViewById(R.id.StartStationText);
        DestiStationEdit = findViewById(R.id.DestiStationText);

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM StationHistory",null);
        cursorAdapter = new SubwayCursorAdapter(this, cursor);
        ListView list = findViewById(R.id.StationLIst);
        list.setAdapter(cursorAdapter);

        list.setOnItemClickListener(onItemClickListener);
        list.setOnItemLongClickListener(onitemLongClickListener);

    }

    public void onClick(View view){

        StartStationTxt = String.valueOf(StartStationEdit.getText());
        DestiStationTxt = String.valueOf(DestiStationEdit.getText());

        db.execSQL("INSERT INTO StationHistory VALUES(null, '"+StartStationTxt+"','"+DestiStationTxt+"')");
        cursor = db.rawQuery("SELECT * FROM StationHistory",null);
        cursorAdapter.changeCursor(cursor);
        StartStationEdit.setText("");
        DestiStationEdit.setText("");

        Intent Pathfindcomputeintent = new Intent(this, PathFindComputeActivity.class);
        Pathfindcomputeintent.putExtra("StartStation",StartStationTxt);
        Pathfindcomputeintent.putExtra("DestiStation",DestiStationTxt);
        startActivity(Pathfindcomputeintent);

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            StartStationTxt = cursor.getString(1);
            Log.d("TAG",StartStationTxt);
            DestiStationTxt = cursor.getString(2);
            Log.d("TAG",DestiStationTxt);

            StartStationEdit.setText(StartStationTxt);
            DestiStationEdit.setText(DestiStationTxt);

        }
    };

    private AdapterView.OnItemLongClickListener onitemLongClickListener = new AdapterView.OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("경로를 삭제 하시겠습니까?")
            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete("StationHistory","StartStation=?", new String[]{cursor.getString(1)});
                    cursor = db.rawQuery("SELECT * FROM StationHistory",null);
                    cursorAdapter.changeCursor(cursor);
                    StartStationEdit.setText("");
                    DestiStationEdit.setText("");
                }
            })
            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create();
            builder.show();

            return false;
        }
    };

}


class SubwayCursorAdapter extends CursorAdapter{

    public SubwayCursorAdapter(Context context, Cursor cursor){
        super(context,cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemstart = view.findViewById(R.id.item_start);
        TextView itemdesti = view.findViewById(R.id.item_desti);

        String itemstartstation = cursor.getString(cursor.getColumnIndex("startstation"));
        String itemdestistation = cursor.getString(cursor.getColumnIndex("destistation"));

        itemstart.setText(itemstartstation);
        itemdesti.setText(itemdestistation);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item,parent,false);
        return v;
    }
}


class DBHelper extends SQLiteOpenHelper{

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