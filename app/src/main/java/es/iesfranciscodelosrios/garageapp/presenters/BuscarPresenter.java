package es.iesfranciscodelosrios.garageapp.presenters;

import es.iesfranciscodelosrios.garageapp.interfaces.BuscarInterface;

public class BuscarPresenter implements BuscarInterface.Presenter {

    private BuscarInterface.View view;

    public BuscarPresenter (BuscarInterface.View view){
        this.view = view;
    }

    @Override
    public void onClickBuscar() {
        view.lanzarListado();
    }
}
