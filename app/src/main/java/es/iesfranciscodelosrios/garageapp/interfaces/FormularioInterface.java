package es.iesfranciscodelosrios.garageapp.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

public interface FormularioInterface {

    public interface View{
        void lanzarListado();
        void inicializarElementos();
        void validarFormulario();
        void requestPermission();
        void showError(String mensaje);
        void launchGallery();
    }

    public interface Presenter{
        void onClickGuardar();
        void onClickBorrar();
        void onClickImage(Context myContext);
        void resultPermision(int result);
        void onActivityResultImagen(int requestCode, int resultCode, @Nullable Intent data, ImageView imageView, Activity activity);
        void addTextChangedListener(EditText input, final TextInputLayout layout, final boolean esFecha, final boolean esSoloAnyo);
        boolean validateDate(CharSequence date);
    }
}
