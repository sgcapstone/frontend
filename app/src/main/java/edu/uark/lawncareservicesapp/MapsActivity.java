package edu.uark.lawncareservicesapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uark.lawncareservicesapp.models.api.Provider;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Provider provider;
    private double lat;
    private double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private class GetLocationLatLong extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... urls) {
            StringBuilder builder = new StringBuilder();
            String content = "", line;
            try {
                // Replace the key value with your own google api key.
                URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + urls[0] + "&sensor=false&key=AIzaSyBuGVsbcnf6N8ceEuMec-RGGyi5AFpz_LY");
                Log.d("URL", "https://maps.googleapis.com/maps/api/geocode/json?address=" + urls[0] + "&sensor=false&key=AIzaSyBuGVsbcnf6N8ceEuMec-RGGyi5AFpz_LY");
                HttpURLConnection client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                client.setRequestProperty("Key", "Value");
                client.setReadTimeout(5000);
                client.setConnectTimeout(5000);
                client.setDoOutput(true);
                client.connect();
                BufferedReader rd = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while ((line = rd.readLine()) != null) {
                    content += line + "\n";
                }
            } catch (Exception e) {
            }

            try {
                JSONObject jsonObject = new JSONObject(content);
                setLatLong(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return content;
        }

        protected void onPostExecute(String results) {
            Log.d("LATLONG", results);
        }

    }

    public  boolean setLatLong(JSONObject jsonObject) {

        try {

            this.longi = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            this.lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (JSONException e) {
            return false;

        }

        return true;
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
        int index = getIntent().getIntExtra("index", 0);
        this.provider = ApplicationState.getProviderList().get(index);
        Log.d("Provider add", provider.getProviderName());
        String address = provider.getAddress() + "+" + provider.getCity() + "+" + provider.getState() + "+" + provider.getZip();

        String address2 = address.replaceAll("\\s", "");
        new GetLocationLatLong().execute(address2);


        mMap = googleMap;

        try {
            LatLng location = new LatLng(this.lat, this.longi);
            mMap.addMarker(new MarkerOptions().position(location).title(provider.getProviderName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
        catch (Exception e) {
            new AlertDialog.Builder(this).
                    setTitle(R.string.failed_to_load_map).
                    setMessage(R.string.failed_to_load_map).
                    setPositiveButton(
                            R.string.button_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            }
                    ).
                    create().
                    show();
        }
    }
}
