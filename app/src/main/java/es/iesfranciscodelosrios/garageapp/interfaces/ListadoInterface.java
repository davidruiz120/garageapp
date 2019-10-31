package es.iesfranciscodelosrios.garageapp.interfaces;

public interface ListadoInterface {

    public interface View {
        void lanzarFormulario();
    }

    public interface Presenter {
        void onClickAdd();
    }
}
