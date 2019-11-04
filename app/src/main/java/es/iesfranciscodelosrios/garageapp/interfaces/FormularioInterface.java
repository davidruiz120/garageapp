package es.iesfranciscodelosrios.garageapp.interfaces;

public interface FormularioInterface {

    public interface View{
        void lanzarListado();
    }

    public interface Presenter{
        void onClickGuardar();
    }
}
