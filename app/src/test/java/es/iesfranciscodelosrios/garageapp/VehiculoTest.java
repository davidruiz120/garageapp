package es.iesfranciscodelosrios.garageapp;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;

import es.iesfranciscodelosrios.garageapp.models.Vehiculo;

import static org.junit.Assert.*;

public class VehiculoTest {

    private Vehiculo vtest;

    @Before
    public void setUp() {
        this.vtest = new Vehiculo();
    }

    @Test
    public void cpVehiculoMarca() {
        assertEquals(false, vtest.setMarca(""));
        assertEquals(true, vtest.setMarca("Renault"));
        assertEquals(false, vtest.setMarca("Renaultttttttttttttttttttttttttt"));
        assertEquals(false, vtest.getMarca().equals(""));
    }

    @Test
    public void cpVehiculoModelo() {
        assertEquals(false, vtest.setModelo(""));
        assertEquals(true, vtest.setModelo("Megane"));
        assertEquals(false, vtest.setModelo("Meganeeeeeeeeeeeeeeeeeeeeeeeeeeeee"));
        assertEquals(false, vtest.getModelo().equals(""));
    }

    @Test
    public void cpVehiculoAnyo() {
        assertEquals(false, vtest.setAnyo(0));
        assertEquals(true, vtest.setAnyo(2020));
        assertEquals(false, vtest.setAnyo(1492));
        assertEquals(false, vtest.setAnyo(2030));
        assertEquals(2020, vtest.getAnyo());
    }

    @Test
    public void cpVehiculoTraccion() {
        assertEquals(false, vtest.setTraccion(""));
        assertEquals(true, vtest.setTraccion("Delantera"));
        assertEquals(false, vtest.setTraccion("Delanteraaaaaaaaaaaaaaaaaaaaaaaaa"));
        assertEquals(false, vtest.getTraccion().equals(""));
    }

    @Test
    public void cpVehiculoFechaMatriculacion() {
        assertEquals(false, vtest.setFechamatriculacion(""));
        assertEquals(true, vtest.setFechamatriculacion("18/02/2020"));
        assertEquals(false, vtest.setFechamatriculacion("18/15/2020"));
        assertEquals(false, vtest.setFechamatriculacion("32/02/2020"));
        assertEquals(false, vtest.setFechamatriculacion("18/02"));
        assertEquals(false, vtest.setFechamatriculacion("18/02/202020"));
        assertEquals(false, vtest.setFechamatriculacion("2020"));
        assertEquals(false, vtest.getFechamatriculacion().equals(""));
    }
}
