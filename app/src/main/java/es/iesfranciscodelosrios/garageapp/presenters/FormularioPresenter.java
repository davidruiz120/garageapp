package es.iesfranciscodelosrios.garageapp.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.iesfranciscodelosrios.garageapp.interfaces.FormularioInterface;

public class FormularioPresenter implements FormularioInterface.Presenter {

    String TAG = "GarageApp/FormularioPresenter";
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
            view.launchGallery();
        }
    }

    @Override
    public void resultPermision(int result) {
        if (result == PackageManager.PERMISSION_GRANTED) {
            // Permiso aceptado
            Log.d("GarageApp", "Permiso aceptado");
            view.launchGallery();
        } else {
            // Permiso rechazado
            Log.d("GarageApp", "Permiso rechazado");
            view.showError("Sin permisos no puedes entrar");
        }
    }

    @Override
    public void onActivityResultImagen(int requestCode, int resultCode, @Nullable Intent data, ImageView imageView, Activity activity){
        if(resultCode == activity.RESULT_OK){
            Uri path = data.getData();
            imageView.setImageURI(path);
        }
    }

    @Override
    public void addTextChangedListener(EditText input, final TextInputLayout layout, final boolean esFecha, final boolean esSoloAnyo) {
        Log.d(TAG, "Validando campo de formulario");
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(esSoloAnyo){
                    try{
                        Integer anyo = Integer.parseInt(s.toString());
                    } catch (Exception e){}
                    Calendar fechaActual;
                    int anyo;
                    fechaActual = Calendar.getInstance();
                    anyo = fechaActual.get(Calendar.YEAR);
                    if(anyo < 1903 || anyo > fechaActual.get(Calendar.YEAR) + 1){
                        layout.setError("Año incorrecto");
                        layout.setErrorEnabled(true);
                    } else {
                        layout.setError(null);
                        layout.setErrorEnabled(false);
                    }
                } else {
                    if(esFecha){
                        if (!validateDate(s)) {
                            layout.setError("Formato incorrecto");
                            layout.setErrorEnabled(true);
                        }
                        else{
                            layout.setError(null);
                            layout.setErrorEnabled(false);
                        }
                    }
                    else if (s.length() == 0) {
                        layout.setError("Campo obligatorio");
                        layout.setErrorEnabled(true);
                    }
                    else{
                        layout.setError(null);
                        layout.setErrorEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public boolean validateDate(CharSequence date) {
        Log.d(TAG, "Validando la fecha introducida");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);

        try {
            format.parse(date.toString());
        } catch (ParseException e) {
            Log.d(TAG, "Fecha incorrecta");
            return false;
        }
        return true;
    }


}
