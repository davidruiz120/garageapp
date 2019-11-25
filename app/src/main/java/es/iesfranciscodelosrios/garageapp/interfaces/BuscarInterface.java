package es.iesfranciscodelosrios.garageapp.interfaces;

public interface BuscarInterface {

    public interface View{
        void lanzarListado();
        void inicializarElementos();
        void validarFormulario();
    }

    public interface Presenter{
        void onClickBuscar();
    }

}
