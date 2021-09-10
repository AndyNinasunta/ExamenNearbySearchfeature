package com.example.examennearbysearchfeature.Modelo;

public class Marcador {

    private  String idPlace;
    private  String nombre;
    private  String lat;
    private  String lng;
    private String direccion;
    private  String urlIcon;

    public Marcador() {
    }


    public Marcador(String idPlace, String nombre, String lat, String lng, String direccion, String urlIcon) {
        this.idPlace = idPlace;
        this.nombre = nombre;
        this.lat = lat;
        this.lng = lng;
        this.direccion = direccion;
        this.urlIcon = urlIcon;
    }

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }
}
