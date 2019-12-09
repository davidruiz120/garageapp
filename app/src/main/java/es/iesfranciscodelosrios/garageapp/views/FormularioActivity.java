package es.iesfranciscodelosrios.garageapp.views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MenuItem;
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
import java.util.List;

import es.iesfranciscodelosrios.garageapp.R;
import es.iesfranciscodelosrios.garageapp.interfaces.FormularioInterface;
import es.iesfranciscodelosrios.garageapp.presenters.FormularioPresenter;

public class FormularioActivity extends AppCompatActivity implements FormularioInterface.View{

    String TAG = "GarageApp/FormularioActivity";
    private FormularioInterface.Presenter presenter;
    final Context c = this;
    private Context myContext;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;

    Button inputAddImagenBtn;
    TextInputLayout inputAddMarcaTIL;
    EditText inputAddMarca;
    TextInputLayout inputAddModeloTIL;
    EditText inputAddModelo;
    TextInputLayout inputAddAnyoTIL;
    EditText inputAddAnyo;
    TextInputLayout inputAddTraccionTIL;
    EditText inputAddTraccion;
    Spinner selectAddCombustible;
    List<String> selectAddCombustileArray = new ArrayList<>();
    Button selectAddCombustibleBtn;
    TextInputLayout inputAddFechaMatriculacionTIL;
    EditText inputAddFechaMatriculacion;
    Button inputAddFechaMatriculacionBtn;

    Calendar fechaActual;
    int dia, mes, anyo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContext = this;

        /**
         * Agrego el Up Button, la funcionalidad del mismo se establece en AndroidManifest.xml
         * agregando parent android:parentActivityName=".views.ListadoActivity" en el
         * activity FormularioActivity
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new FormularioPresenter(this);

        /**
         * Método para buscar los elementos del formulario
         */
        inicializarElementos();


        inputAddImagenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickImage(myContext);
            }
        });


        String editIdVehiculo = getIntent().getStringExtra("editIdVehiculo");
        inputAddMarca.setText(editIdVehiculo);

        /**
         * En las siguientes líneas lo que se hace es crear un adaptador para poder cargar el array
         * que ha inicializado y después adaptarlo al Spinner (conocido como select en HTML) del Formulario
         */
        //ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.combo_combustible, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selectAddCombustileArray);
        selectAddCombustible.setAdapter(adapter);

        /**
         * Al hacer click en el botón para añadir una opción en el Spinner, se abre un layout para poder
         * brindar la posibilidad de añadir una opción más en el Spinner. Al hacer click en
         * "Añadir" (@string/anyadir) se añade lo que ha escrito el usuario en el Array
         */
        selectAddCombustibleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.alert_dialog_select_add_combustible, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.anyadir), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            Log.d(TAG, "Añadiendo nueva opción en el Spinner");
                            selectAddCombustileArray.add(userInputDialogEditText.getText().toString());
                        }
                    })

                    .setNegativeButton(getString(R.string.cancelar),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                Log.d(TAG, "Pulsando boton Cancelar en el AlertDialog de Combustible");
                                dialogBox.cancel();
                            }
                        });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });


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

        inputAddFechaMatriculacionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Accediendo al calendario...");
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormularioActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1; // Se le suma +1 porque aparece el mes anterior
                        inputAddFechaMatriculacion.setText(dayOfMonth+"/"+month+"/"+year);
                        Log.d(TAG, "Fecha seleccionada...");
                    }
                }, anyo, mes, dia);
                datePickerDialog.show();
            }
        });

        /**
         * Método que se encarga de validar el formulario
         */
        validarFormulario();


        Button inputBtnGuardar = findViewById(R.id.inputBtnGuardar);
        inputBtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pulsando boton Guardar...");
                presenter.onClickGuardar();
            }
        });

        Button inputBtnBorrar = findViewById(R.id.inputBtnBorrar);
        inputBtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pulsando boton Borrar...");
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.alert_dialog_add_borrar, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.borrar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                        Log.d(TAG, "Click en borrar");
                        presenter.onClickBorrar();
                        }
                    })

                    .setNegativeButton(getString(R.string.cancelar),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                Log.d(TAG, "Pulsando boton Cancelar en el AlertDialog de Borrar");
                                dialogBox.cancel();
                            }
                        });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

    }

    @Override
    public void inicializarElementos(){
        Log.d(TAG, "Inicializando elementos...");
        inputAddImagenBtn = findViewById(R.id.inputAddImagenBtn);
        inputAddMarcaTIL = findViewById(R.id.inputAddMarcaTIL);
        inputAddMarca = findViewById(R.id.inputAddMarca);
        inputAddModeloTIL = findViewById(R.id.inputAddModeloTIL);
        inputAddModelo = findViewById(R.id.inputAddModelo);
        inputAddAnyoTIL = findViewById(R.id.inputAddAnyoTIL);
        inputAddAnyo = findViewById(R.id.inputAddAnyo);
        inputAddTraccionTIL = findViewById(R.id.inputAddTraccionTIL);
        inputAddTraccion = findViewById(R.id.inputAddTraccion);
        selectAddCombustible = findViewById(R.id.inputAddCombustible);
        selectAddCombustibleBtn = findViewById(R.id.inputAddCombustibleBtn);
        selectAddCombustileArray.add("Gasolina 98");
        selectAddCombustileArray.add("Gasolina 95");
        selectAddCombustileArray.add("Diésel/Gasóleo A");
        selectAddCombustileArray.add("Diésel/Gasóleo A+");
        selectAddCombustileArray.add("Gas Natural");
        inputAddFechaMatriculacionTIL = findViewById(R.id.inputAddFechaMatriculacionTIL);
        inputAddFechaMatriculacion = findViewById(R.id.inputAddFechaMatriculacion);
        inputAddFechaMatriculacionBtn = findViewById(R.id.inputAddFechaMatriculacionBtn);
    }


    @Override
    public void validarFormulario(){
        // Marca
        presenter.addTextChangedListener(inputAddMarca, inputAddMarcaTIL, false, false);
        // Modelo
        presenter.addTextChangedListener(inputAddModelo, inputAddModeloTIL, false, false);
        // Año
        presenter.addTextChangedListener(inputAddAnyo, inputAddAnyoTIL, false, true);
        // Tracción
        presenter.addTextChangedListener(inputAddTraccion, inputAddTraccionTIL, false, false);
        // Fecha de matriculación
        presenter.addTextChangedListener(inputAddFechaMatriculacion, inputAddFechaMatriculacionTIL, true, false);
    }

/**
    public void addTextChangedListener(EditText input, final TextInputLayout layout, final boolean esFecha, final boolean esSoloAnyo) {
        Log.d(TAG, "Validando campo de formulario");
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(esSoloAnyo){
                    try{
                        Integer anyo = Integer.parseInt(s.toString());
                    } catch (Exception e){}
                    if(anyo < 1903 || anyo > fechaActual.get(Calendar.YEAR) + 1){
                        layout.setError("Año incorrecto");
                        layout.setErrorEnabled(true);
                    } else {
                        layout.setError(null);
                        layout.setErrorEnabled(false);
                    }
                } else {
                    if(esFecha){
                        if (!validateDate(s)) {
                            layout.setError("Formato incorrecto");
                            layout.setErrorEnabled(true);
                        }
                        else{
                            layout.setError(null);
                            layout.setErrorEnabled(false);
                        }
                    }
                    else if (s.length() == 0) {
                        layout.setError("Campo obligatorio");
                        layout.setErrorEnabled(true);
                    }
                    else{
                        layout.setError(null);
                        layout.setErrorEnabled(false);
                    }
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
    public void lanzarListado(){
        Log.d(TAG, "Lanzando listado...");
        finish();
    }

    @Override
    public void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}
                , CODE_WRITE_EXTERNAL_STORAGE_PERMISSION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                presenter.resultPermision(grantResults[0]);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
