package es.iesfranciscodelosrios.garageapp.presenters;

import es.iesfranciscodelosrios.garageapp.interfaces.ListadoInterface;

public class ListadoPresenter implements ListadoInterface.Presenter {

    private ListadoInterface.View view;

    public ListadoPresenter (ListadoInterface.View view){
        this.view = view;
    }

    @Override
    public void onClickAdd(){
        view.lanzarFormulario();
    }

    @Override
    public void onClickSobreGarageApp() {
        view.lanzarAcercaDe();
    }

}
