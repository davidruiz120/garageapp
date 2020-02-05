package es.iesfranciscodelosrios.garageapp.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import es.iesfranciscodelosrios.garageapp.models.Vehiculo;

public interface FormularioInterface {

    public interface View{
        void lanzarListado();
        void inicializarElementos();
        void requestPermission();
        void showError(String mensaje);
        void showToast(String mensaje);
        void launchGallery();
        void onEditVehiculo();
    }

    public interface Presenter{
        void onClickGuardar(Vehiculo vehiculo);
        void onClickActualizar(Vehiculo vehiculo);
        void onClickBorrar();
        void onClickImage(Context myContext);
        void resultPermision(int result);
        void onActivityResultImagen(int requestCode, int resultCode, @Nullable Intent data, ImageView imageView, Activity activity, Context context);
        List<String> getArrayCombustibles();
        Vehiculo getVehiculoFromID(String id);
        Bitmap stringToBitmap(String imagen);
        String bitmapToBase64(Bitmap imagen);
    }
}
