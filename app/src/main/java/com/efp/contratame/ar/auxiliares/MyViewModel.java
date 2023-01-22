package com.efp.contratame.ar.auxiliares;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efp.contratame.ar.modelo.Servicio;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Servicio> selected = new MutableLiveData<>();

    public void setSelected(Servicio servicio){
        selected.setValue(servicio);
    }

    public MutableLiveData<Servicio> getSelected() {
        return selected;
    }

}
