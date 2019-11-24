package es.iesfranciscodelosrios.garageapp.presenters;

import es.iesfranciscodelosrios.garageapp.interfaces.FormularioInterface;

public class FormularioPresenter implements FormularioInterface.Presenter {

    private FormularioInterface.View view;

    public FormularioPresenter (FormularioInterface.View view){
        this.view = view;
    }

    @Override
    public void onClickGuardar() {
        view.lanzarListado();
    }

    @Override
    public void onClickBorrar() {
        view.lanzarListado();
    }
}
