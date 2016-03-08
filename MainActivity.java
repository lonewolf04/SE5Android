package com.example.shobhana.tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{

    EditText name1;
    EditText phone1;
    EditText usn1;
    EditText area1;
    EditText age;

    String phone;
    String name;
    String age1;
    String PayloadData="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.submit).setOnClickListener(handleClick);
        name1=(EditText)findViewById(R.id.name);
        phone1=(EditText)findViewById(R.id.phone);
        //System.out.println("phone number::: "+phone1);
        usn1=(EditText)findViewById(R.id.usn);
        area1=(EditText)findViewById(R.id.area);
        age=(EditText)findViewById(R.id.age);


    }

    private View.OnClickListener handleClick = new View.OnClickListener(){
        public void onClick(View arg0)
        {
            Button btn = (Button)arg0;
            phone=MainActivity.this.phone1.getText().toString();
            name=MainActivity.this.name1.getText().toString();
            age1=MainActivity.this.age.getText().toString();;
            foo();
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);


        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void foo()
    {
        //Context context = getApplicationContext();
        CharSequence text = "In foo";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

        JSONObject jsonobj = new JSONObject();

        String json = " ";

        try
        {

            jsonobj.put("phonenumber", phone);
            System.out.println("json::: " + jsonobj);

            PayloadData="phonenumber="+phone+"&name="+name+"&age="+age1;
            //jsonobj.put("name", name);
            //jsonobj.put("age", age1);
            System.out.println("string " + PayloadData);
            //jsonobj.put("usn", usn1);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        //json = jsonobj.toString();
        if (jsonobj.length() > 0)
        {
            new TransferData().execute(PayloadData);

        }
    }
        class TransferData extends AsyncTask <String,String,String>
        {

            private static final String TAG = " ";

            @Override
            protected String doInBackground(String... params)
            {
                String JsonResponse = null;
                String JsonData = params[0];

                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try
                {
                    URL url = new URL("http://seteambackend-saimadhav.rhcloud.com/userinfo/");

                    //http://seteambackend-saimadhav.rhcloud.com/userinfo/9008442961
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setDoOutput(true);
                    // is output buffer writter
                    connection.setRequestMethod("POST");
                    //connection.setRequestProperty("Content-Type", "application/json");
                    //connection.setRequestProperty("Accept", "application/json");

                    Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    writer.write(PayloadData);
                    /* json data */
                    writer.close();
                    PayloadData="";
                    //System.out.println("JSONDATA:  "+JsonDATA);
                    //line added
                    int errorCode = connection.getResponseCode(); System.out.println("GetErrorStream " + errorCode);
                    InputStream inputStream = connection.getInputStream();
                    //input stream
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String inputLine;
                    while ((inputLine = reader.readLine()) != null)
                        buffer.append(inputLine + "\n");
                    if (buffer.length() == 0)
                    {

                        return null;
                    }
                    JsonResponse = buffer.toString();
//response data
                    Log.i(TAG, JsonResponse);

                    //System.out.println("Done!!");
                    System.out.println("response: "+JsonResponse);
                    return JsonResponse;






                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (connection != null)
                    {
                        connection.disconnect();
                    }
                    if (reader != null)
                    {
                        try
                        {
                            reader.close();
                        } catch (final IOException e)
                        {
                            Log.e(TAG, "Error closing stream", e);
                        }
                    }
                }
                return null;
            }


            @Override
            protected void onPostExecute(String s)
            {
                //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

            }

        }




}
