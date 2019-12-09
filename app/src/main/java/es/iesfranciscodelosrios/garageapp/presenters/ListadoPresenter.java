package es.iesfranciscodelosrios.garageapp.presenters;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.interfaces.ListadoInterface;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.models.VehiculoModel;

public class ListadoPresenter implements ListadoInterface.Presenter {

    private ListadoInterface.View view;
    private VehiculoModel vehiculo;

    public ListadoPresenter (ListadoInterface.View view){
        this.view = view;
        this.vehiculo = new VehiculoModel();
    }

    @Override
    public ArrayList<Vehiculo> getAllVehiculo(){
        return vehiculo.getAllVehiculo();
    }

    @Override
    public void deleteVehiculo(Vehiculo vehiculo) {
        VehiculoModel.deleteVehiculo(vehiculo);
    }

    @Override
    public void onClickRecyclerView(int id) {
        view.lanzarFormularioPorID(id);
    }

    @Override
    public void onClickAdd(){
        view.lanzarFormulario();
    }

    @Override
    public void onClickSobreGarageApp() {
        view.lanzarAcercaDe();
    }

    @Override
    public void onClickBuscar() {
        view.lanzarBuscar();
    }

}
