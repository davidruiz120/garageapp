package es.iesfranciscodelosrios.garageapp.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import es.iesfranciscodelosrios.garageapp.R;
import es.iesfranciscodelosrios.garageapp.interfaces.BuscarInterface;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.presenters.BuscarPresenter;

public class BuscarActivity extends AppCompatActivity implements BuscarInterface.View{

    String TAG = "GarageApp/BuscarActivity";
    private BuscarInterface.Presenter presenter;

    TextInputLayout inputSearchModeloTIL;
    EditText inputSearchModelo;
    Spinner selectSearchCombustible;
    TextInputLayout inputSearchFechaMatriculacionTIL;
    EditText inputSearchFechaMatriculacion;
    Button inputSearchFechaMatriculacionBtn;

    Calendar fechaActual;
    int dia, mes, anyo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Agrego el Up Button, la funcionalidad del mismo se establece en AndroidManifest.xml
         * agregando parent android:parentActivityName=".views.ListadoActivity" en el
         * activity AcercaDeActivity
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Método para buscar los elementos del formulario
         */
        inicializarElementos();


        /**
         * En las siguientes líneas lo que se hace es crear un adaptador para poder cargar el array
         * del recurso res/values/selectSearchCombustible.xml y después adaptarlo al Spinner (conocido
         * como select en HTML) del formulario
         */
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.combo_search_combustible, android.R.layout.simple_spinner_dropdown_item);
        selectSearchCombustible.setAdapter(adaptador);



        /**
         * En las siguientes líneas se trabaja el campo para seleccionar la fecha, en el que
         * cuando hacemos click en el texto, aparecerá un calendario (con la fecha actual) en
         * el que podremos elegir la fecha, que en este caso, es la fecha de matriculacion del
         * vehículo que se le va a agregar
         */
        fechaActual = Calendar.getInstance();
        dia = fechaActual.get(Calendar.DAY_OF_MONTH);
        mes = fechaActual.get(Calendar.MONTH);
        anyo = fechaActual.get(Calendar.YEAR);

        inputSearchFechaMatriculacionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Accediendo al calendario...");
                DatePickerDialog datePickerDialog = new DatePickerDialog(BuscarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1; // Se le suma +1 porque aparece el mes anterior
                        inputSearchFechaMatriculacion.setText(dayOfMonth+"/"+month+"/"+year);
                        Log.d(TAG, "Fecha seleccionada...");
                    }
                }, anyo, mes, dia);
                datePickerDialog.show();
            }
        });





        presenter = new BuscarPresenter(this);

        Button searchBtnBuscar = findViewById(R.id.searchBtnBuscar);
        searchBtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pulsando boton Buscar...");
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setModelo(inputSearchModelo.getText().toString());
                vehiculo.setCombustible(selectSearchCombustible.getSelectedItem().toString());
                vehiculo.setFechamatriculacion(inputSearchFechaMatriculacion.getText().toString());
                presenter.onClickBuscar(vehiculo);
            }
        });
    }

    @Override
    public void inicializarElementos() {
        inputSearchModeloTIL = findViewById(R.id.inputSearchModeloTIL);
        inputSearchModelo = findViewById(R.id.inputSearchModelo);
        inputSearchFechaMatriculacionTIL = findViewById(R.id.inputSearchFechaMatriculacionTIL);
        inputSearchFechaMatriculacion = findViewById(R.id.inputSearchFechaMatriculacion);
        inputSearchFechaMatriculacionBtn = findViewById(R.id.inputSearchFechaMatriculacionBtn);
        selectSearchCombustible = findViewById(R.id.inputSearchCombustible);
    }

    /**@Override
    public void validarFormulario(){
        // Fecha de matriculación
        addTextChangedListener(inputSearchFechaMatriculacion, inputSearchFechaMatriculacionTIL);
    }

    public void addTextChangedListener(EditText input, final TextInputLayout layout) {
        Log.d(TAG, "Validando campo de formulario");
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!validateDate(s)) {
                    layout.setError("Formato incorrecto");
                    layout.setErrorEnabled(true);
                }
                else{
                    layout.setError(null);
                    layout.setErrorEnabled(false);
                }
                if (s.length() == 0) {
                    layout.setError("Campo obligatorio");
                    layout.setErrorEnabled(true);
                }
                else{
                    layout.setError(null);
                    layout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public boolean validateDate(CharSequence date){
        Log.d(TAG, "Validando la fecha introducida");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);

        try {
            format.parse(date.toString());
        } catch (ParseException e) {
            Log.d(TAG, "Fecha incorrecta");
            return false;
        }
        return true;
    }*/

    @Override
    public void lanzarListado(ArrayList<Vehiculo> vehiculos) {
        Log.d(TAG, "Lanzando listado...");
        // https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
        Intent resultIntent = new Intent();
        resultIntent.putExtra("arrayVehiculosFiltro", vehiculos);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Log.d(TAG,"Ejecutando onBlackPressed en ListadoActivity...");
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
