package es.iesfranciscodelosrios.garageapp.interfaces;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.models.Vehiculo;

public interface BuscarInterface {

    public interface View{
        void lanzarListado(ArrayList<Vehiculo> vehiculos);
        void inicializarElementos();
        //void validarFormulario();
        void lanzarAyuda();
    }

    public interface Presenter{
        void onClickBuscar(Vehiculo vehiculo);
        void onClickAyuda();
    }

}
