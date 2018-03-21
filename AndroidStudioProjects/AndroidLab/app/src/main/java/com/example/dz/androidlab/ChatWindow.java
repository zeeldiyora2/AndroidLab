package com.example.dz.androidlab;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME = "ChatWindow";
    ArrayList<String> alist = new ArrayList<>();

    ChatDatabaseHelper cdh;//2
    SQLiteDatabase db;  //1
    Cursor cursor;
    ContentValues cValues;
    ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView list = (ListView) findViewById(R.id.chatView);
        final EditText edit = (EditText) findViewById(R.id.txtChat);
        Button sendbtn = (Button) findViewById(R.id.sendBtn);

        cdh = new ChatDatabaseHelper(getApplicationContext()); //3
        db = cdh.getWritableDatabase(); //4
        cValues = new ContentValues();
//        cValues.put(cdh.KEY_MESSAGE,"Hello");
//        db.insert(cdh.TABLE_MESSAGE, null, cValues);
        messageAdapter = new ChatAdapter(this);


        cursor = db.query(false, cdh.TABLE_MESSAGE, new String[]{cdh.KEY_ID, cdh.KEY_MESSAGE}, null, null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            alist.add(cursor.getString(cursor.getColumnIndex(cdh.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "MESSAGE: " + cursor.getString(cursor.getColumnIndex(cdh.KEY_MESSAGE)));
            Log.i("Cursor’s column count: ",cursor.getColumnCount()+"");
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getColumnName(cursor.getColumnIndex(cdh.KEY_MESSAGE)));
            cursor.moveToNext();
        }


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alist.add(edit.getText().toString());

                cValues.put(cdh.KEY_MESSAGE, edit.getText().toString());
                db.insert(cdh.TABLE_MESSAGE, null, cValues);
                edit.setText("");
            }
        });
        list.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return alist.size();
        }

        public String getItem(int position){
            return alist.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }

        public long getId(int position){
            return position;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        db.close();
    }
}