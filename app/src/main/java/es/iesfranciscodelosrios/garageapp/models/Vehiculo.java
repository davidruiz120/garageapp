package es.iesfranciscodelosrios.garageapp.models;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Vehiculo {

    private Integer id;
    private String imagen = "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";
    private String marca;
    private String modelo;
    private int anyo;
    private String traccion;
    private String combustible;
    private String fechamatriculacion;
    private int edicionespecial = 0;

    public Vehiculo() {
    }

    public boolean setMarca(String marca) {
        if(marca.equals("") || marca.length() >= 30){
            return false;
        } else{
            this.marca = marca;
            return true;
        }
    }

    public boolean setModelo(String modelo) {
        if(modelo.equals("") || modelo.length() >= 30){
            return false;
        } else {
            this.modelo = modelo;
            return true;
        }
    }

    public boolean setAnyo(int anyo) {
        Calendar fechaActual;
        fechaActual = Calendar.getInstance();
        if(anyo < 1903 || anyo > fechaActual.get(Calendar.YEAR) + 1){
            return false;
        } else {
            this.anyo = anyo;
            return true;
        }
    }

    public boolean setTraccion(String traccion) {
        if(traccion.equals("") || traccion.length() >= 30){
            return false;
        } else {
            this.traccion = traccion;
            return true;
        }

    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public boolean setFechamatriculacion(String fechamatriculacion) {
        Calendar fechaActual;
        fechaActual = Calendar.getInstance();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);

        try {
            format.parse(fechamatriculacion);
            if(format.getCalendar().get(Calendar.YEAR) > fechaActual.get(Calendar.YEAR) + 1){
                return false;
            } else {
                this.fechamatriculacion = fechamatriculacion;
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    public void setEdicionespecial(int edicionespecial) {
        this.edicionespecial = edicionespecial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnyo() {
        return anyo;
    }

    public String getTraccion() {
        return traccion;
    }

    public String getCombustible() {
        return combustible;
    }

    public String getFechamatriculacion() {
        return fechamatriculacion;
    }

    public int getEdicionespecial() {
        return edicionespecial;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", imagen='" + imagen + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}
