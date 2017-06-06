package com.example.root.bicis.logica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.bicis.R;

import java.util.ArrayList;

/**
 * Created by root on 6/06/17.
 */

public class BicicletaAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Bicicleta> bicicletas;

    public BicicletaAdapter(Context context, ArrayList<Bicicleta> bicicletas){
        super(context, R.layout.bicicleta, bicicletas);
        this.context=context;
        this.bicicletas=bicicletas;
    }

    @Override
    public View getView(int position, View conView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.bicicleta,null);

        TextView tipo = (TextView) item.findViewById(R.id.b_tipo);
        TextView placa = (TextView) item.findViewById(R.id.b_placa);
        TextView estado = (TextView) item.findViewById(R.id.b_estado);

        tipo.setText(bicicletas.get(position).getTipo());
        placa.setText(bicicletas.get(position).getPlaca());
        estado.setText(bicicletas.get(position).getEstado());

        return item;
    }



}
