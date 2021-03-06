package es.iesfranciscodelosrios.garageapp.interfaces;

import android.app.Activity;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.models.Vehiculo;

public interface ListadoInterface {

    public interface View {
        void lanzarFormulario();
        void lanzarFormularioPorID(int id);
        void lanzarAcercaDe();
        void lanzarBuscar();
        void lanzarAyuda();
        void cargarListado(boolean desdeBusqueda);
    }

    public interface Presenter {
        void onClickAdd();
        void onClickSobreGarageApp();
        void onClickBuscar();
        void onClickAyuda();
        ArrayList<Vehiculo> getAllVehiculo();
        ArrayList<Vehiculo> getAllVehiculoListadoView();
        Vehiculo getVehiculoFromID(String id);
        void deleteVehiculo(Activity activity, Vehiculo vehiculo);
        void onClickRecyclerView(int id);
        void cargarListado(boolean desdeBusqueda);
    }
}
