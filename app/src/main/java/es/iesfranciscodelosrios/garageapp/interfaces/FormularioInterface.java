package es.iesfranciscodelosrios.garageapp.interfaces;

import android.content.Context;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public interface FormularioInterface {

    public interface View{
        void lanzarListado();
        void inicializarElementos();
        void validarFormulario();
        void requestPermission();
    }

    public interface Presenter{
        void onClickGuardar();
        void onClickBorrar();
        void onClickImage(Context myContext);
        void resultPermision(int result);

        void addTextChangedListener(EditText input, final TextInputLayout layout, final boolean esFecha, final boolean esSoloAnyo);
        boolean validateDate(CharSequence date);
    }
}
