package es.iesfranciscodelosrios.garageapp.presenters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;


import es.iesfranciscodelosrios.garageapp.interfaces.FormularioInterface;

public class FormularioPresenter implements FormularioInterface.Presenter {

    private FormularioInterface.View view;

    public FormularioPresenter (FormularioInterface.View view){
        this.view = view;
    }

    @Override
    public void onClickGuardar() {
        view.lanzarListado();
    }

    @Override
    public void onClickBorrar() {
        view.lanzarListado();
    }

    @Override
    public void onClickImage(Context myContext) {
        int ReadPermision = ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.d("GarageApp", "Permisos: " + ReadPermision);

        if(ReadPermision != PackageManager.PERMISSION_GRANTED){
            view.requestPermission();
        } else{
            //view.launchGallery();
        }
    }

    @Override
    public void resultPermision(int result) {
        if (result == PackageManager.PERMISSION_GRANTED) {
            // Permiso aceptado
            Log.d("GarageApp", "Permiso aceptado");
            // view.launchGallery();
        } else {
            // Permiso rechazado
            Log.d("GarageApp", "Permiso rechazado");
            //view.showError("Sin permisos no puedes entrar");
        }
    }
}
