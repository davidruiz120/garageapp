package es.iesfranciscodelosrios.garageapp.interfaces;

import android.content.Context;

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
    }
}
