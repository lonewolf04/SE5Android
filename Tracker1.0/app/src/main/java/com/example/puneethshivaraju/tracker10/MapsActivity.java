package com.example.puneethshivaraju.tracker10;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//package com.example.shobhana.maps;

//TODO: Check and resolve space between location issue, Show the route of the corresponding user based on phone number
//TODO for all activities involving server, check for internet connection
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude, longitude,latitude1,longitude1;
    String route="indiranagar,koramangala,banashankri";
    int count=0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //Used to retrieve the users phone
        //Add the permission <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        /*TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        System.out.println("phone number: "+mPhoneNumber);*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        String[] routearr = route.split(",");
        for(String place:routearr)
            new GetLocation().execute(place);
        mMap = googleMap;

        if(latitude!=0.0 && longitude !=0.0)
            setup();
        // Add a marker in Sydney and move the camera


    }

    public void setup() {

        if(count==0) {
            LatLng loc = new LatLng(latitude, longitude);
            System.out.println(latitude + " " + longitude);
            mMap.addMarker(new MarkerOptions().position(loc).title("Marker in bangalore"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
            latitude1=latitude;
            longitude1=longitude;
        }
        count=count+1;

        if(latitude!=0.0 && longitude!=0.0 && count>1) {

            draw();

        }
    }

    public void draw() {
        Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(latitude1, longitude1)
                , new LatLng(latitude,longitude))
                .width(10).color(Color.BLACK).geodesic(true));

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("Marker"));
        latitude1=latitude;
        longitude1=longitude;

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                //Uri.parse("android-app://com.example.shobhana.maps/http/host/path")
                Uri.parse("android-app://com.example.puneethshivaraju.tracker10/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.puneethshivaraju.tracker10/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class GetLocation extends AsyncTask<String, String, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String JsonResponse = null;
            String response = " ";
            String JsonData = params[0];
            System.out.println(params[0]);
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {
                URL url = new URL("http://maps.google.com/maps/api/geocode/json?address="+params[0]+"&sensor=false");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                connection.setDoOutput(true);
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    System.out.println(responseCode);
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                } else {
                    response = "";
                }


                response = response.replaceAll("[{}]", " ");
                response = response.replaceAll("\\s+", " ");

                System.out.println(response);
                String[] part1 = response.split("location");
                part1[1] = part1[1].replaceAll(":", " ");
                part1[1] = part1[1].replaceAll("\"", " ");
                part1[1] = part1[1].replaceAll(",", " ");
                response = response.replaceAll("\\s+", " ");
                String[] l1 = part1[1].split("lng");
                String[] lat1 = l1[0].split("lat");
                String lat = lat1[1];
                // String[] l2=part1[1].split("lng");
                String lng = l1[1];
                lat = lat.replaceAll("\\s", "");
                lng = lng.replaceAll("\\s", "");

                latitude = Double.parseDouble(lat);
                longitude = Double.parseDouble(lng);


                System.out.println("latitude= " + latitude + "longitude=  " + longitude);

                //return value;

                /*for(String e:part1)
                {
                    response = response.replaceAll("\\s+", " ");
                    String[] part2=e.split(":");
                    System.out.println(e);
                }*/

            } catch (IOException e) {
                e.printStackTrace();
            }

            // System.out.println(e);


            String value = latitude + ":" + longitude;
            return new String[]{value};
        }


        @Override
        protected void onPostExecute(String... result) {

            setup();


        }
    }

}
