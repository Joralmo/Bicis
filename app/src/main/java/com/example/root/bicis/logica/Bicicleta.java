package com.example.root.bicis.logica;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by root on 5/06/17.
 */

public class Bicicleta implements Serializable{

    private String placa;
    private String tipo;
    private String estado;

    public Bicicleta(JSONObject object) throws JSONException{
        this.placa = object.getString("placa");
        this.tipo = object.getString("tipo");
        this.estado = object.getString("estado");
    }

    public Bicicleta(){

    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
