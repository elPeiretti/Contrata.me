package com.efp.contratame.ar.auxiliares;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.efp.contratame.ar.modelo.Requerimiento;

public class MyViewModelRequerimiento extends ViewModel {
    private MutableLiveData<Requerimiento> selected = new MutableLiveData<>();

    public void setSelected(Requerimiento req){
        selected.setValue(req);
    }

    public MutableLiveData<Requerimiento> getSelected() {
        return selected;
    }

}
