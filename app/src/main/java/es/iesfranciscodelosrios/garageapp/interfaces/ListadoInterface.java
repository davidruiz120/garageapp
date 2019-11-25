package es.iesfranciscodelosrios.garageapp.interfaces;

public interface ListadoInterface {

    public interface View {
        void lanzarFormulario();
        void lanzarAcercaDe();
        void lanzarBuscar();
    }

    public interface Presenter {
        void onClickAdd();
        void onClickSobreGarageApp();
        void onClickBuscar();
    }
}
