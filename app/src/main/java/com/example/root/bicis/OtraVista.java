package com.example.root.bicis;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.root.bicis.logica.Bicicleta;

/**
 * Created by root on 6/06/17.
 */

public class OtraVista extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otra_vista);
        Bicicleta bicicleta = new Bicicleta();
        bicicleta = (Bicicleta) getIntent().getSerializableExtra("bicicleta");
        Toast.makeText(this, bicicleta.getPlaca() ,Toast.LENGTH_SHORT).show();

    }
}
