package es.iesfranciscodelosrios.garageapp.interfaces;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.models.Vehiculo;

public interface BuscarInterface {

    public interface View{
        void lanzarListado(ArrayList<Vehiculo> vehiculos);
        void inicializarElementos();
        //void validarFormulario();
    }

    public interface Presenter{
        void onClickBuscar(Vehiculo vehiculo);
    }

}
