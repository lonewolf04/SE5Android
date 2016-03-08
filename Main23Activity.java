package com.example.shobhana.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Main23Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.student).setOnClickListener(handleClick);
        findViewById(R.id.driver).setOnClickListener(handleClick1);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private View.OnClickListener handleClick = new View.OnClickListener(){
        public void onClick(View arg0)
        {
            Button btn = (Button)arg0;
            Intent intent=new Intent(Main23Activity.this,MainActivity.class);
            startActivity(intent);


        }
    };

    private View.OnClickListener handleClick1 = new View.OnClickListener(){
        public void onClick(View arg0)
        {
            Button btn = (Button)arg0;
            Intent intent=new Intent(Main23Activity.this,Main22Activity.class);
            startActivity(intent);


        }
    };


}
