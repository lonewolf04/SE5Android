package com.example.puneethshivaraju.tracker10;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DriverActivity extends ActionBarActivity {
    private static final String TAG = "StudentActivity";

    @Bind(R.id.input_dname) EditText _dname;
    @Bind(R.id.input_dmobile) EditText _dmobile;
    @Bind(R.id.input_did) EditText _did;
    @Bind(R.id.btn_dreg) Button _dreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        ButterKnife.bind(this);

        //insert code for sending reg data to server
        //Dont send data till OPT confirms

        _dreg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Enable below for OTP
                //Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                //startActivity(intent);

                login();

            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _dreg.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(DriverActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String name = _dname.getText().toString();
        String mobile = _dmobile.getText().toString();
        String usn = _did.getText().toString();

        // TODO: Implement code to send data to server.(same as student)

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onLoginSuccess() {
        _dreg.setEnabled(true);
       // MainActivity.hasReg=true;
        setResult(RESULT_OK, null);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _dreg.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String name = _dname.getText().toString();
        String mobile = _dmobile.getText().toString();
        String usn = _did.getText().toString();


        if (name.isEmpty()) {
            _dname.setError("Enter name");
            valid = false;
        } else {
            _dname.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 10 || mobile.length() > 10) {
            _dmobile.setError("Enter a valid number");
            valid = false;
        } else {
            _dmobile.setError(null);
        }


        if (usn.isEmpty()) {
            _did.setError("Enter ID");
            valid = false;
        } else {
            _did.setError(null);
        }

        return valid;
    }
}
