package es.iesfranciscodelosrios.garageapp.interfaces;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.models.Vehiculo;

public interface ListadoInterface {

    public interface View {
        void lanzarFormulario();
        void lanzarFormularioPorID(int id);
        void lanzarAcercaDe();
        void lanzarBuscar();
    }

    public interface Presenter {
        void onClickAdd();
        void onClickSobreGarageApp();
        void onClickBuscar();
        ArrayList<Vehiculo> getAllVehiculo();
        void deleteVehiculo(Vehiculo vehiculo);
        void onClickRecyclerView(int id);
    }
}
