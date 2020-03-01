package es.iesfranciscodelosrios.garageapp.presenters;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.interfaces.BuscarInterface;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.models.VehiculoModel;

public class BuscarPresenter implements BuscarInterface.Presenter {

    String TAG = "GarageApp/FormularioPresenter";
    private BuscarInterface.View view;
    private VehiculoModel model;

    public BuscarPresenter (BuscarInterface.View view){
        this.view = view;
        model = VehiculoModel.getInstance();
    }

    @Override
    public void onClickBuscar(Vehiculo vehiculo) {
        view.lanzarListado(model.getAllVehiculoFiltradoListadoView(vehiculo));
    }

    @Override
    public void onClickAyuda() {
        view.lanzarAyuda();
    }
}
