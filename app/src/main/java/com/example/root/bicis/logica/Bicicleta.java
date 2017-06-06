package com.example.root.bicis.logica;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 5/06/17.
 */

public class Bicicleta {

    private String placa;
    private String tipo;
    private String color;
    private String estado;

    public Bicicleta(JSONObject object) throws JSONException{
        this.placa = object.getString("placa");
        this.tipo = object.getString("tipo");
        this.color = object.getString("color");
        this.estado = object.getString("estado");
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
