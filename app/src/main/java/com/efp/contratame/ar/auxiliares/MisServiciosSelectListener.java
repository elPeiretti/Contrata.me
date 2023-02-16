package com.efp.contratame.ar.auxiliares;



import com.efp.contratame.ar.modelo.Requerimiento;

public interface MisServiciosSelectListener {
    void navigateToModificar(Requerimiento req);
    void navigateToEliminar(Requerimiento req);

}
