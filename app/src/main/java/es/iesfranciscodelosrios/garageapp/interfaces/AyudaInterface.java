package es.iesfranciscodelosrios.garageapp.interfaces;

public interface AyudaInterface {

    public interface View {
        void lanzarAyuda(String enlace);
        void lanzarListado();
        void showToast(String mensaje);
    }

    public interface Presenter {
        void lanzarAyuda(String enlace);
        void lanzarListado();
    }

}
