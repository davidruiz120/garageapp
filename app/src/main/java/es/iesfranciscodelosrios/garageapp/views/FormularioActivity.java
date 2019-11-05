package es.iesfranciscodelosrios.garageapp.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import es.iesfranciscodelosrios.garageapp.R;
import es.iesfranciscodelosrios.garageapp.interfaces.FormularioInterface;
import es.iesfranciscodelosrios.garageapp.presenters.FormularioPresenter;

public class FormularioActivity extends AppCompatActivity implements FormularioInterface.View{

    String TAG = "GarageApp/FormularioActivity";
    private FormularioInterface.Presenter presenter;
    Spinner selectAddCombustible;
    TextView imputAddFechaMatriculacion;
    Calendar fechaActual;
    int dia, mes, anyo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Agrego el Up Button, la funcionalidad del mismo se establece en AndroidManifest.xml
         * agregando parent android:parentActivityName=".views.ListadoActivity" en el
         * activity FormularioActivity
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * En las siguientes líneas lo que se hace es crear un adaptador para poder cargar el array
         * que he creado en res/values/selectAddCombustible.xml y después adaptarlo al Spinner (conocido
         * como select en HTML) del Formulario
         */
        selectAddCombustible = findViewById(R.id.imputAddCombustible);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.combo_combustible, android.R.layout.simple_spinner_dropdown_item);
        selectAddCombustible.setAdapter(adaptador);

        /**
         * En las siguientes líneas se trabaja el campo para seleccionar la fecha, en el que
         * cuando hacemos click en el texto, aparecerá un calendario (con la fecha actual) en
         * el que podremos elegir la fecha, que en este caso, es la fecha de matriculacion del
         * vehículo que se le va a agregar
         */
        imputAddFechaMatriculacion = findViewById(R.id.imputAddFechaMatriculacion);
        fechaActual = Calendar.getInstance();
        dia = fechaActual.get(Calendar.DAY_OF_MONTH);
        mes = fechaActual.get(Calendar.MONTH);
        anyo = fechaActual.get(Calendar.YEAR);

        imputAddFechaMatriculacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Accediendo al calendario...");
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormularioActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1; // Se le suma +1 porque aparece el mes anterior
                        imputAddFechaMatriculacion.setText(dayOfMonth+"/"+month+"/"+year);
                        Log.d(TAG, "Fecha seleccionada...");
                    }
                }, anyo, mes, dia);
                datePickerDialog.show();
            }
        });


        /**
         *
         */
        presenter = new FormularioPresenter(this);

        Button imputBtnGuardar = findViewById(R.id.imputBtnGuardar);
        imputBtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pulsando boton Guardar...");
                presenter.onClickGuardar();
            }
        });

    }


    public void lanzarListado(){
        Log.d(TAG, "Lanzando listado...");
        Intent intent = new Intent(FormularioActivity.this, ListadoActivity.class); //Comunicamos las 2 actividades
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"Ejecutando onStart en FormularioActivity...");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"Ejecutando onRestart en FormularioActivity...");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"Ejecutando onResume en FormularioActivity...");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"Ejecutando onPause en FormularioActivity...");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"Ejecutando onStop en FormularioActivity...");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"Ejecutando onDestroy en FormularioActivity...");
    }

}
