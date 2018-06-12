package com.example.jeongjimin.mysubway;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/*custom adapter bind view는 어뎁터에서 갖고있는 데이터를 리스트뷰에 올릴 아이템 형식메 맞춰 textview에 데이터 넣어줌
  newview는 커서를 통해서 새로운 아이템 들어오면 adapter에 추가*/
class SubwayCursorAdapter extends CursorAdapter {

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
