package com.example.jeongjimin.mysubway;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/*경로찾기의 시작이 될 액티비티, 이 액티비티는 edit text에서 시작, 도착역 정보를 받고 그 데이터를 compute로 넘겨주는 역할을 함.
* 데이터를 compute로 넘김녀서 동시에 데이터베이스에 정보를 저장, 정보를 삭제하거나 저장된 정보를 바로 edit text에 올리는 역할도 수행*/
public class PathFindActivity extends AppCompatActivity {

    /*db생성과 사용에 필요한 것들*/
    DBHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    SubwayCursorAdapter cursorAdapter;

    private Button SearchButton;
    private EditText StartStationEdit;
    private String StartStationTxt;
    private EditText DestiStationEdit;
    private String DestiStationTxt;

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

    /*출발, 도착역 정보를 intent에 담아서 compute액티비티를 수행, 그와 동시에 데이터베이스에 두 정보를 저장함,
    * 저장된 정보는 custom adapter(MysubwayCursorAdapter)을 통해서 listview로 보여줌.
    *         cursorAdapter.changeCursor(cursor);
    *         구문이 일종의 adapter의 notifydatachanged 같은 역할을 수행함.*/
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

    /*짧게 클릭하면 데이터 베이스 안의 내용이 edit text에 올라감*/
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

    /*롱클릭하면 대화상자로 이 내용을 삭제할 것인지 올라옴, 예 누르면 삭제, 아니면 종료*/
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


