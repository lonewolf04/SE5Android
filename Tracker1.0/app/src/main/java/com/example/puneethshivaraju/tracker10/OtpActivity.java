package com.example.puneethshivaraju.tracker10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Puneeth Shivaraju on 09-03-2016.
 */
public class OtpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        findViewById(R.id.btn_next).setOnClickListener(handleClick);



    }

    private View.OnClickListener handleClick = new View.OnClickListener(){
        public void onClick(View arg0)
        {
            //Button btn = (Button)arg0;

            Intent intent=new Intent(OtpActivity.this,MapsActivity.class);
            startActivity(intent);


        }
    };

}
