package com.example.root.bicis.logica;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by root on 7/06/17.
 */

public class DirectionFinder {
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private String originLat;
    private String originLon;
    private String destinationLat;
    private String destinationLon;
    private GoogleMap map;

    public DirectionFinder(String originLat,String originLon, String destinationLat, String destinationLon, GoogleMap map) {
        this.originLat = originLat;
        this.originLon = originLon;
        this.destinationLat = destinationLat;
        this.destinationLon = destinationLon;
        this.map = map;
    }

    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {

        return DIRECTION_URL_API + "origin=" + originLat + ","+originLon+"&destination=" + destinationLat + ","+destinationLon;
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;

        List<Route> routes = new ArrayList<Route>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        JSONObject jsonRoute = jsonRoutes.getJSONObject(0);
        JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
        JSONObject jsonLeg = jsonLegs.getJSONObject(0);
        JSONArray jsonSteps = jsonLeg.getJSONArray("steps");
        for (int i = 0; i < jsonSteps.length(); i++){
            Route route = new Route();
            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");
            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            route.points = decodePolyLine(overview_polylineJson.getString("points"));
            routes.add(route);
        }
        LatLng latLng = new LatLng(routes.get(0).startLocation.latitude, routes.get(0).startLocation.longitude);
        LatLng latLng1;
        for(Route route1:routes){
            for(int j=0;j<route1.points.size()-1;j++){
                latLng1 = new LatLng(route1.points.get(j).latitude, route1.points.get(j).longitude);
                latLng = new LatLng(route1.points.get(j+1).latitude, route1.points.get(j+1).longitude);
                map.addPolyline(new PolylineOptions().add(latLng, latLng1).width(15).color(Color.RED));
            }
            latLng = new LatLng(route1.startLocation.latitude, route1.startLocation.longitude);
        }
    }
    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }
        return decoded;
    }
}
