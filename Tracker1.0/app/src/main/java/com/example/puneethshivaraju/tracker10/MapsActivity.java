package com.example.shobhana.maps;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude, longitude,latitude1,longitude1;
    String route="indiranagar,koramangala,hosekerahalli";
    int count=0;
    String s1=null;
    String s2=null;
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

        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        System.out.println("phone number: "+mPhoneNumber);
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

        mMap = googleMap;

        for(int i=1;i<routearr.length;i++)
            new GetRoute().execute(routearr[i-1],routearr[i]);

        for(String place:routearr) {
            new GetLocation().execute(place);

        }



    }

   public void setup(String place) {


            LatLng loc = new LatLng(latitude1, longitude1);

            mMap.addMarker(new MarkerOptions().position(loc).title(place));

            if (count==0)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));

            count++;





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
                Uri.parse("android-app://com.example.shobhana.maps/http/host/path")
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
                Uri.parse("android-app://com.example.shobhana.maps/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    /**
     * This class gets the driving directions by querying the google maps api
     * The start and end locations are sent and a json response containg the directions
     * with the 'legs' is returned.
     **/
    class GetRoute extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params)
        {
            String JsonResponse = null;
            String response = " ";
            s1 = params[0];
            s2 = params[1];
            System.out.println(params[0]+" "+params[1]);
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+s1+"&destination="+s2+"&sensor=false");
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
                    InputStream in = connection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (in == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(in));

                    String inputLine;
                    while ((inputLine = reader.readLine()) != null)
                        buffer.append(inputLine + "\n");
                    if (buffer.length() == 0)
                    {

                        return null;
                    }
                    Log.i("Json", String.valueOf(buffer));
                    JSONObject data=new JSONObject(String.valueOf(buffer));
                    System.out.println("json"+data);
                    
                    return data;

                }




            } catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        /** This method is called after doInBackground method completes processing.
         Result from doInBackground is passed to this method. */
        @Override
        protected void onPostExecute(JSONObject response) {

            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            try {
                ArrayList<LatLng> array = getDirection(response);
                draw(array);
                array.clear();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
    
    /**
     * Json parsing is done and the polyline points of the legs in the route is obtained
    **/
    public ArrayList<LatLng> getDirection(JSONObject data) throws JSONException {

        ArrayList<LatLng> listGeopoints = new ArrayList<LatLng>();
        System.out.println("json"+data);
        JSONArray legs=null;
        JSONArray steps=null;
        JSONArray routes=data.getJSONArray("routes");
        for(int i=0;i<routes.length();i++) {

            legs = ((JSONObject) routes.get(i)).getJSONArray("legs");

            for (int j = 0; j < legs.length(); j++) {
                steps = ((JSONObject) legs.get(j)).getJSONArray("steps");

             

                for (int k = 0; k < steps.length(); k++) {
                    String polyline = "";
                    polyline = (String) ((JSONObject) ((JSONObject) steps
                            .get(k)).get("polyline")).get("points");
                    ArrayList<LatLng> arr = decodePoly(polyline);

                    for (int j1 = 0; j1 < arr.size(); j1++) {
                        listGeopoints.add(new LatLng(arr.get(j1).latitude, arr.get(j1).longitude));
                    }


                }
            }
        }
        return listGeopoints;
    }

    /**
     * The polyline points is parsed and decoded to obtain the latitute and longitude of the points on the route
     * */
    private ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }

        public void draw(ArrayList<LatLng> array)
        {
            PolylineOptions route = new PolylineOptions().width(15).color(Color.BLUE);

            for(int i = 0 ; i < array.size() ; i++) {
                route.add(array.get(i));
            }
            mMap.addPolyline(route);
            System.out.println(array.get(0));


        }

    class GetLocation extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
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
               
                String lng = l1[1];
                lat = lat.replaceAll("\\s", "");
                lng = lng.replaceAll("\\s", "");

                latitude1 = Double.parseDouble(lat);
                longitude1 = Double.parseDouble(lng);


                System.out.println("latitude= " + latitude1 + "longitude=  " + longitude1);

               

            } catch (IOException e) {
                e.printStackTrace();
            }

            


            String value = params[0];
            return value;
        }


        @Override
        protected void onPostExecute(String place) {

            setup(place);


        }
    }


}

