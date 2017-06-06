package com.example.root.bicis;

/**
 * Created by root on 5/06/17.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.bicis.logica.Bicicleta;
import com.example.root.bicis.logica.BicicletaAdapter;
import com.example.root.bicis.logica.Sitios;
import com.example.root.bicis.logica.jsonBicicletas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TabHost mTabHost = (TabHost)findViewById(R.id.tabHost);
        mTabHost.setup();
        //Lets add the first Tab
        TabHost.TabSpec mSpec = mTabHost.newTabSpec("Home");
        mSpec.setContent(R.id.first_Tab);
        mSpec.setIndicator("Home");
        mTabHost.addTab(mSpec);
        //Lets add the second Tab
        mSpec = mTabHost.newTabSpec("Actual");
        mSpec.setContent(R.id.second_Tab);
        mSpec.setIndicator("Actual");
        mTabHost.addTab(mSpec);
        //Lets add the third Tab
        mSpec = mTabHost.newTabSpec("Mis Prestamos");
        mSpec.setContent(R.id.third_Tab);
        mSpec.setIndicator("Mis Prestamos");
        mTabHost.addTab(mSpec);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        jsonBicicletas jsonBicicletas = new jsonBicicletas(mMap, getBaseContext());
        jsonBicicletas.execute("http://www.d-e-s-c-u-b-r-e.com/BicicletasSantaMarta.json");

        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(5));
    }


    public boolean onMarkerClick(final Marker marker) {
        Sitios sitio = (Sitios) marker.getTag();
        //Toast.makeText(this, sitio.getNombre(), Toast.LENGTH_SHORT).show();
        ListView bicis = (ListView) findViewById(R.id.Bdisponibles);
        TextView placa = (TextView) findViewById(R.id.b_placa);
        TextView tipo = (TextView) findViewById(R.id.b_tipo);
        ArrayList<Bicicleta> bS=new ArrayList<Bicicleta>();
        bS=sitio.getBicicletas();
        BicicletaAdapter adapter = new BicicletaAdapter(this, bS);
        bicis.setAdapter(adapter);

        /*for(int i=0;i<bS.size();i++){
            placa.setText(placa.getText()+bS.get(i).getPlaca());
            tipo.setText(tipo.getText()+bS.get(i).getTipo());
        }*/
        //placa.setText("Placa: "+sitio.getBicicletas().get(0).getPlaca());
        //tipo.setText("Tipo: "+sitio.getBicicletas().get(0).getTipo());
       /* Bicicleta bicicleta = (Bicicleta) marker.getTag();

        TextView placa = (TextView) findViewById(R.id.b_placa);
        TextView tipo = (TextView) findViewById(R.id.b_tipo);

        placa.setText("Placa: "+bicicleta.getPlaca());
        tipo.setText("Tipo: "+bicicleta.getTiempo());

        //Toast.makeText(this, marker.getTitle(),Toast.LENGTH_SHORT).show();
        return false;*/
       return false;
    }
}
