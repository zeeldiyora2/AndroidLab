package com.example.dz.androidlab;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import static com.example.dz.androidlab.ChatDatabaseHelper.KEY_ID;

public class MessageDetails extends Activity {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);


        Bundle bundle = new Bundle();
        MessageFragment messageFragment = new MessageFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,messageFragment);

        messageFragment.setArguments(bundle);
        fragmentTransaction.commit();


    }
}
