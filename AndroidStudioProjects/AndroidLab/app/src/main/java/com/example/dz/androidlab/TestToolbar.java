package com.example.dz.androidlab;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {

        AlertDialog.Builder builder;
        AlertDialog.Builder builder1;

        switch (mi.getItemId()) {
            case R.id.action_one:
            Log.d("Toolbar", "Twitch selected");
            SharedPreferences sharedPref = getSharedPreferences("newMessage", Context.MODE_PRIVATE);
            String x = sharedPref.getString("newMessage", "");
            Snackbar.make(fab, x, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            break;

            case R.id.action_two:

                Log.d("Toolbar", "Football selected");
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(TestToolbar.this);
                builder2.setMessage("Do you want to go back?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("User Clicked", "OK");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // What to do on Cancel
                                Log.i("User Clicked","Cancel" );
                            }
                        });

                AlertDialog dialog = builder2.create();
                dialog.show();
                break;

            case R.id.action_three:
                Log.d("Toolbar", "BasketBall selected");
                AlertDialog.Builder build = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater1 = TestToolbar.this.getLayoutInflater();
                View d = inflater1.inflate(R.layout.custom_dialog, null);
                final EditText newMessage =  d.findViewById(R.id.cmt) ;
                build.setView(d)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /**
                                 * string entered will be the new message displayed by
                                 * the Snackbar object when selecting menu item 1
                                 */
                                SharedPreferences sharedPref = getSharedPreferences("newMessage", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sharedPref.edit();
                                edit.putString("newMessage", newMessage.getText().toString());
                                edit.apply();
                            }
                        });
                build.setView(d)
                        .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                onBackPressed();
                            }
                        });
                build.show();
                break;

            case R.id.action_four:
                Log.d("Toolbar", "About Selected");
                Toast.makeText(getApplicationContext(), "Version 1.0 by Zeel Diyora", Toast.LENGTH_LONG).show();
                break;

        }

        return true;
    }
}
