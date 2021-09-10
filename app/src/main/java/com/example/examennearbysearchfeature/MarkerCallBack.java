package com.example.examennearbysearchfeature;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;

public class MarkerCallBack implements com.squareup.picasso.Callback {

    Marker marker=null;

    public MarkerCallBack(Marker marker){
        this.marker=marker;
    }

    @Override
    public void onSuccess() {
        if (marker!= null && marker.isInfoWindowShown()){
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
    }

    @Override
    public void onError(Exception e) {
        Log.e(getClass().getSimpleName(),"Error al cargar");
    }
}
