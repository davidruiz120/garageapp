package es.iesfranciscodelosrios.garageapp.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.iesfranciscodelosrios.garageapp.interfaces.FormularioInterface;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.models.VehiculoModel;

public class FormularioPresenter implements FormularioInterface.Presenter {

    String TAG = "GarageApp/FormularioPresenter";
    private FormularioInterface.View view;
    private VehiculoModel model;

    public FormularioPresenter (FormularioInterface.View view){
        this.view = view;
        model = VehiculoModel.getInstance();
    }

    @Override
    public void onClickAyuda() {
        view.lanzarAyuda();
    }

    @Override
    public void onClickGuardar(Vehiculo vehiculo) {
        Log.d(TAG,"Método onClickGuardar");
        if(model.addNew(vehiculo)){
            view.showToast("Se ha insertado el vehículo");
            view.lanzarListado();
        } else {
            view.showError("No se ha podido guardar el vehículo");
        }
    }

    @Override
    public void onClickActualizar(Vehiculo vehiculo) {
        Log.d(TAG,"Método onClickActualizar");
        if(model.updateVehiculo(vehiculo)){
            view.showToast("Se ha actualizado el vehículo");
            view.lanzarListado();
        } else {
            view.showError("No se ha podido actualizar el vehículo");
        }
    }

    @Override
    public void onClickBorrar(String id) {
        Log.d(TAG,"Método onClickBorrar");
        if(model.deleteVehiculo(id)){
            view.showToast("Se ha eliminado el vehículo");
            view.lanzarListado();
        } else {
            view.showError("No se ha podido eliminar el vehículo");
        }
    }

    @Override
    public void onClickImage(Context myContext) {
        int ReadPermision = ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.d(TAG, "Permisos: " + ReadPermision);

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
            Log.d(TAG, "Permiso aceptado");
            view.launchGallery();
        } else {
            // Permiso rechazado
            Log.d(TAG, "Permiso rechazado");
            view.showError("Sin permisos no puedes entrar");
        }
    }

    @Override
    public void onActivityResultImagen(int requestCode, int resultCode, @Nullable Intent data, ImageView imageView, Activity activity, Context context){
        if(resultCode == activity.RESULT_OK){
            Bitmap scaledBmp = null;
            Uri path = data.getData();

            InputStream imgStream = null;
            try {
                imgStream = context.getContentResolver().openInputStream(path);
            } catch (FileNotFoundException e){
                Log.d(TAG, "Error: " + e);
            }

            Bitmap imgBmp = BitmapFactory.decodeStream(imgStream);
            int nh = (int) ( imgBmp.getHeight() * (256.0 / imgBmp.getWidth()) );
            scaledBmp = Bitmap.createScaledBitmap(imgBmp, 256, nh, true);

            imageView.setImageBitmap(scaledBmp);
        }
    }

    @Override
    public Bitmap stringToBitmap(String imagen){
        byte[] decodedString = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap decodedBmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedBmp;
    }

    @Override
    public String bitmapToBase64(Bitmap imagen){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }



    @Override
    public List<String> getArrayCombustibles() {
        return model.getArrayCombustibles();
    }

    @Override
    public Vehiculo getVehiculoFromID(String id){
        return model.getVehiculoFromID(id);
    }

    /**
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
    */




}
