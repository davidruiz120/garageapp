package es.iesfranciscodelosrios.garageapp.interfaces;

public interface FormularioInterface {

    public interface View{
        void lanzarListado();
        void inicializarElementos();
        void validarFormulario();
    }

    public interface Presenter{
        void onClickGuardar();
    }
}
