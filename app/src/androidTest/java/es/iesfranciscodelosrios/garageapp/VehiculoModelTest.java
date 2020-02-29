package es.iesfranciscodelosrios.garageapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.models.VehiculoModel;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class VehiculoModelTest {

    // Repositorio extends SQLiteOpenHelper
    private VehiculoModel repositorio;

    @Before
    public void setUp(){
        //InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase(VehiculoModel.getInstance().getDatabaseName());
        //repositorio = VehiculoModel.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        repositorio = VehiculoModel.getInstance();
    }

    @After
    public void tearDown() {
        repositorio.close();
    }

    @Test
    public void pruebaInsertar(){
        Vehiculo vnuevo = new Vehiculo();
        assertEquals(true, repositorio.addNew(vnuevo));
    }

    @Test
    public void pruebaRecuperarSpinner(){
        List<String> combustibles = repositorio.getArrayCombustibles();
        assertEquals(5, combustibles.size());

        assertEquals("Gasolina 98", combustibles.get(0));
    }

    @Test
    public void pruebaGetAllVehiculo(){
        Vehiculo vnuevo = new Vehiculo();
        vnuevo.setMarca("Renault");
        assertEquals(true, repositorio.addNew(vnuevo));

        List<Vehiculo> list = repositorio.getAllVehiculo();
        System.out.println(list);
        assertEquals(1, list.size());
    }

    @Test
    public void pruebaGetAllVehiculoListadoView(){
        Vehiculo vnuevo = new Vehiculo();
        vnuevo.setMarca("Peugeot");
        assertEquals(true, repositorio.addNew(vnuevo));

        ArrayList<Vehiculo> list = repositorio.getAllVehiculoListadoView();
        assertEquals(1, list.size());
    }

    @Test
    public void pruebaGetVehiculoFromID(){
        Vehiculo vnuevo = new Vehiculo();
        vnuevo.setMarca("Citroen");
        assertEquals(true, repositorio.addNew(vnuevo));

        Vehiculo vehiculo = repositorio.getVehiculoFromID("1");
        int id = vehiculo.getId();
        assertEquals(1, id);
    }

    @Test
    public void pruebaGetAllVehiculoFiltradoListadoView(){
        Vehiculo vnuevo = new Vehiculo();
        vnuevo.setMarca("Dacia");
        assertEquals(true, repositorio.addNew(vnuevo));
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Dacia");

        ArrayList<Vehiculo> list = repositorio.getAllVehiculoFiltradoListadoView(vehiculo);
        assertEquals(1, list.size());
    }

    @Test
    public void pruebaEditar(){
        Vehiculo vnuevo = new Vehiculo();
        vnuevo.setMarca("Fiat");
        assertEquals(true, repositorio.addNew(vnuevo));

        Vehiculo vehiculo = repositorio.getVehiculoFromID("1");
        vehiculo.setMarca("Renault");
        assertEquals(true, repositorio.updateVehiculo(vehiculo));
    }

    @Test
    public void pruebaBorrar(){
        Vehiculo vnuevo = new Vehiculo();
        assertEquals(true, repositorio.addNew(vnuevo));

        assertEquals(true, repositorio.deleteVehiculo("1"));
    }


}
