package com.example.shobhana.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main22Activity extends AppCompatActivity {

    EditText name1;
    EditText phone1;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // View.OnClickListener handleClick=null;
        findViewById(R.id.submit).setOnClickListener(handleClick);
        name1=(EditText)findViewById(R.id.name);
        phone1=(EditText)findViewById(R.id.phone);
        id=(EditText)findViewById(R.id.ID);
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
            //foo();
            Intent intent=new Intent(Main22Activity.this,Main2Activity.class);
            startActivity(intent);


        }
    };

}
