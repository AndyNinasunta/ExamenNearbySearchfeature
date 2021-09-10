package com.example.examennearbysearchfeature.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examennearbysearchfeature.MarkerCallBack;
import com.example.examennearbysearchfeature.Modelo.Marcador;
import com.example.examennearbysearchfeature.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InfoWindow implements GoogleMap.InfoWindowAdapter {

    private View ventana;
    private Context contx;
    private List<Marcador> marcadores;


    public InfoWindow(Context contx, List<Marcador> marcadores) {
        this.contx = contx;
        this.marcadores = marcadores;
        ventana= LayoutInflater.from(contx).inflate(R.layout.info_window,null);
    }

    private void cargarInformación(Marker marker,View view){

        TextView nombre, ubicacion;
        ImageView img;

        nombre=(TextView)view.findViewById(R.id.txtNombre);
        ubicacion=(TextView)view.findViewById(R.id.txtUbicacion);
        img=(ImageView)view.findViewById(R.id.imgIco);

        //Cargar la información de los negocios
        try {
            for (Marcador marcadorNegocios : marcadores) {

                if (marcadorNegocios.getIdPlace().equals(marker.getTitle())) {

                    nombre.setText(marcadorNegocios.getNombre());
                    ubicacion.setText(marcadorNegocios.getDireccion());

                    Picasso.get()
                            .load(marcadorNegocios.getUrlIcon().toString())
                            .into(img, new MarkerCallBack(marker));

                }

            }

        }
        catch (Exception e){
            Toast.makeText(contx,"Error al cargar la vista: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        cargarInformación(marker,ventana);
        return ventana;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return ventana;
    }
}
