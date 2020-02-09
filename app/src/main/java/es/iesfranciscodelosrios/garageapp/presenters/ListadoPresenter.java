package es.iesfranciscodelosrios.garageapp.presenters;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.interfaces.ListadoInterface;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.models.VehiculoModel;

public class ListadoPresenter implements ListadoInterface.Presenter {

    private ListadoInterface.View view;
    private VehiculoModel vehiculo;

    public ListadoPresenter (ListadoInterface.View view){
        this.view = view;
        this.vehiculo = VehiculoModel.getInstance();
    }

    @Override
    public ArrayList<Vehiculo> getAllVehiculo(){
        return vehiculo.getAllVehiculo();
    }

    @Override
    public ArrayList<Vehiculo> getAllVehiculoListadoView(){
        return vehiculo.getAllVehiculoListadoView();
    }

    @Override
    public Vehiculo getVehiculoFromID(String id){
        return this.vehiculo.getVehiculoFromID(id);
    }

    @Override
    public void deleteVehiculo(Activity activity, Vehiculo vehiculo) {
        Toast.makeText(activity, "Para borrar el elemento, accede a él", Toast.LENGTH_SHORT).show();
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

    @Override
    public void cargarListado(boolean desdeBusqueda){
        view.cargarListado(desdeBusqueda);
    }

}
