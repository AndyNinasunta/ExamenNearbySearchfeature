package com.example.examennearbysearchfeature;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;

import com.bumptech.glide.Glide;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.target.CustomTarget;
import com.example.examennearbysearchfeature.Adaptador.InfoWindow;
import com.example.examennearbysearchfeature.Modelo.Marcador;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button buscar;
    private Spinner spinner;
    List<Marcador> listaMarcadores=new ArrayList<>();


    private GoogleMap gMaps;
    private RequestQueue requestQue;
    private String url= "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private String key="AIzaSyB5MkIB5lNnQH1kC1tZ3ATeEsv7z66moKs";
    private String radio="100";
    private String tipoEstablecimiento="restaurant";
    private double latA=0;
    private double latB=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscar=(Button)findViewById(R.id.btnBuscar);
        spinner=(Spinner)findViewById(R.id.idSpinner);

        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this    );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMaps=googleMap;

        LatLng ltUteq= new LatLng(-1.0118506915092784, -79.46892724061738);


        //Activar los controles del Zoom
        gMaps.getUiSettings().setZoomControlsEnabled(true);

        //Enfocar el zoom del mapa en la UTEQ
        CameraUpdate camUpdUteq= CameraUpdateFactory.newLatLngZoom(ltUteq,17 );
        gMaps.moveCamera(camUpdUteq);




    }

    public void cargarMarcadores(View view){

        //Cargamos el objeto CameraPositicion
        CameraPosition position = gMaps.getCameraPosition();

        //Cargamos en las variables las latitudes
        latA=position.target.latitude;
        latB=position.target.longitude;

        //Obtenemos el valor del spinner
        tipoEstablecimiento=spinner.getSelectedItem().toString();

        //Preparamos la url para consumirla
        String urlApi=url +"?location="+latA+"%2C"+latB+"&radius="+radio+"&type="+tipoEstablecimiento+"&key="+key;
        System.out.println(urlApi);
        obtenerMarcadores(urlApi);

        cargarPersonalizacion(listaMarcadores);

    }
    private void cargarPersonalizacion(List<Marcador> lstM){

        gMaps.clear();
        //Agregamos los marcadores alojados en la lista
        for(Marcador marcadorNego : lstM) {

            LatLng ltNegocio= new LatLng(Float.parseFloat( marcadorNego.getLat())
                    ,Float.parseFloat(marcadorNego.getLng()));

            obtenerInformación(marcadorNego.getIdPlace(),marcadorNego);

            Glide.with(this)
                    .asBitmap()
                    .load(marcadorNego.getUrlIcon().toString())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {

                            gMaps.addMarker(new MarkerOptions()
                                    .position(ltNegocio)
                                    .title(marcadorNego.getIdPlace()).icon(BitmapDescriptorFactory.fromBitmap(resource)));

                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });

        }

        //Se aplica el diseño del diseno info window.xml
        gMaps.setInfoWindowAdapter(new InfoWindow(MainActivity.this,lstM));
    }

    private void obtenerMarcadores(String urlApi){

        JsonObjectRequest requestJson = new JsonObjectRequest(Request.Method.GET, urlApi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        enlistarMarcadores(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al conectarse:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQue = Volley.newRequestQueue(this);
        requestQue.add(requestJson);
    }

    private void enlistarMarcadores (JSONObject jObj){
        listaMarcadores=new ArrayList<>();

        try {
            JSONArray jArray = jObj.getJSONArray("results");

            for (int i=0;i<jArray.length();i++) {

                //Obtenemos el Objeto Json (negocio)
                JSONObject jsonObject= jArray.getJSONObject(i);

                //Obtenemos la ubicación del negocio (Lat y Lng).
                JSONObject jsonUbicacion=jsonObject.getJSONObject("geometry").getJSONObject("location");


                //Agregamos en una lista la información del negocio
                listaMarcadores.add(new Marcador(jsonObject.get("place_id").toString(),
                        "VACIO",
                        jsonUbicacion.get("lat").toString()
                        ,jsonUbicacion.get("lng").toString(),
                        "VACIO",
                        jsonObject.get("icon").toString()));

            }
        }catch (JSONException e){
            Toast.makeText(this,"Error al cargar los datos al objeto: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void obtenerInformación(String id_Place,Marcador marcadorN){

        String urlApi="https://maps.googleapis.com/maps/api/place/details/json?place_id="+id_Place+
                "&fields=name%2Crating%2Cformatted_phone_number%2Cvicinity%2Cicon&key="+key;
        JsonObjectRequest requestJson = new JsonObjectRequest(Request.Method.GET, urlApi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jobjet=response.getJSONObject("result");
                            marcadorN.setNombre(jobjet.get("name").toString());
                            marcadorN.setDireccion(jobjet.get("vicinity").toString());
                            System.out.println(marcadorN.getDireccion());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al conectarse:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQue = Volley.newRequestQueue(this);
        requestQue.add(requestJson);
    }
}