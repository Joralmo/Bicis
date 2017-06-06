package com.example.root.bicis.logica;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by root on 5/06/17.
 */

public class jsonBicicletas extends AsyncTask<String, Integer, String> {

    private GoogleMap map;
    private Context ctx;

    public jsonBicicletas(GoogleMap map, Context ctx){
        this.map = map;
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        StringBuilder content = new StringBuilder();
        try{
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader br = new BufferedReader( new InputStreamReader(in, "UTF-8"));

            String line;
            while((line = br.readLine()) != null){
                content.append(line);
            }
            Log.i("json", content.toString());

        }catch (Exception e){
            Log.d("error", "json error");
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }

        return content.toString();
    }

    protected void onPostExecute(String result) {
        //Toast.makeText(this.ctx, result, Toast.LENGTH_LONG).show();

        try {
            //Toast.makeText(this.ctx, "Entro", Toast.LENGTH_LONG).show();


            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("sitios");



            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                ArrayList <Bicicleta> bicis= new ArrayList<Bicicleta>();
                JSONArray jsonArray1 = object.getJSONArray("bicicletas");
                for (int j=0; j<jsonArray1.length();j++){
                    bicis.add(new Bicicleta(jsonArray1.getJSONObject(j)));
                }

                Sitios sitio = new Sitios(object.getString("nombre"), object.getString("direccion"), object.getDouble("lat"), object.getDouble("lon"), bicis);

                //Bicicleta bicicleta = new Bicicleta(object);
                LatLng lat = new LatLng(sitio.getLat(), sitio.getLon());

                Marker marker = this.map.addMarker(new MarkerOptions().position(lat).title(sitio.getNombre()));
                //Toast.makeText(this.ctx, sitio.getNombre(), Toast.LENGTH_LONG).show();
                this.map.moveCamera(CameraUpdateFactory.newLatLng(lat));
                marker.setTag(sitio);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(lat, 16));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
