package com.example.root.bicis;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.bicis.logica.Bicicleta;
import com.example.root.bicis.logica.DirectionFinder;
import com.example.root.bicis.logica.DirectionFinderListener;
import com.example.root.bicis.logica.Route;
import com.example.root.bicis.logica.Sitios;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by root on 6/06/17.
 */

public class OtraVista extends FragmentActivity implements OnMapReadyCallback{
    private GoogleMap map;
    private Sitios sitio;
    double lon, lat;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otra_vista);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bicicleta bicicleta = new Bicicleta();
        sitio = new Sitios();
        sitio = (Sitios) getIntent().getSerializableExtra("sitio");
        bicicleta = (Bicicleta) getIntent().getSerializableExtra("bicicleta");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        locationManager = (LocationManager) getSystemService(OtraVista.this.LOCATION_SERVICE);
        if(!verificarGpsEncendido()){
            Toast.makeText(this, "Gps desactivado", Toast.LENGTH_SHORT).show();
        }else{

            //System.out.print(lat);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));
            //map.setMyLocationEnabled(true);
            lat = location.getLatitude();
            lon = location.getLongitude();
            System.out.print(lat);
            System.out.print(lon);
            System.out.print(sitio.getLat());
            System.out.print(sitio.getLon());
            LatLng latLng2 = new LatLng(lat, lon);
            LatLng latLng = new LatLng(sitio.getLat(), sitio.getLon());
            Marker marker2 = this.map.addMarker(new MarkerOptions().position(latLng2).title("Mi ubicacion").icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 16));
            String origenLat = Double.toString(lat);
            String origenLon = Double.toString(lon);
            String destinoLat = Double.toString(sitio.getLat());
            String destinoLon = Double.toString(sitio.getLon());
            try {
                new DirectionFinder(origenLat,origenLon,destinoLat,destinoLon, map).execute();
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            LatLng latLng1 = new LatLng(sitio.getLat(), sitio.getLon());
            Marker marker = this.map.addMarker(new MarkerOptions().position(latLng1).title(sitio.getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bici)));
            /*
            Toast.makeText(this, Double.toString(lon), Toast.LENGTH_SHORT).show();
            */
/*            LatLng latLng = new LatLng(sitio.getLat(), sitio.getLon());
            Marker marker = this.map.addMarker(new MarkerOptions().position(latLng).title(sitio.getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.bici)));


            /*

            Polyline polyline = map.addPolyline(new PolylineOptions()
                .add(latLng2, latLng)
                .width(5)
            .color(Color.RED));*/


        }

    }

    public boolean verificarGpsEncendido(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lon=location.getLongitude();
            lat=location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
