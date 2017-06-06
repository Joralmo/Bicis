package com.example.root.bicis.logica;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 5/06/17.
 */

public class Sitios implements Serializable{
    private String nombre;
    private String direccion;
    private double lat;
    private double lon;
    private ArrayList <Bicicleta> bicicletas;

    public Sitios(String nombre, String direccion, double lat, double lon, ArrayList<Bicicleta> bicicletas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.lat = lat;
        this.lon = lon;
        this.bicicletas = bicicletas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public ArrayList<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(ArrayList<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }
}
