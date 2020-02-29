package es.iesfranciscodelosrios.garageapp.presenters;

import java.net.InetAddress;

import es.iesfranciscodelosrios.garageapp.interfaces.AyudaInterface;

public class AyudaPresenter implements AyudaInterface.Presenter {

    private AyudaInterface.View view;

    public AyudaPresenter (AyudaInterface.View view){
        this.view = view;
    }

    @Override
    public void lanzarAyuda(String enlace) {
        view.lanzarAyuda(enlace);
    }

    @Override
    public void lanzarListado() {
        view.showToast("Error: Sin conexión a internet");
        view.lanzarListado();
    }
}
