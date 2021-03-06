package es.iesfranciscodelosrios.garageapp.views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.iesfranciscodelosrios.garageapp.R;
import es.iesfranciscodelosrios.garageapp.interfaces.FormularioInterface;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.presenters.FormularioPresenter;

public class FormularioActivity extends AppCompatActivity implements FormularioInterface.View{

    String TAG = "GarageApp/FormularioActivity";
    private FormularioInterface.Presenter presenter;
    final Context c = this;
    private Context myContext;
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;

    Vehiculo vehiculo = new Vehiculo();

    ImageView inputAddImagen;
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
    Switch inputAddEdicionEspecial;
    Button inputBtnGuardar;
    Button inputBtnBorrar;

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
        this.inicializarElementos();


        inputAddImagenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickImage(myContext);
            }
        });


        /**
         * En las siguientes líneas lo que se hace es crear un adaptador para poder cargar el array
         * que ha inicializado y después adaptarlo al Spinner (conocido como select en HTML) del Formulario
         */
        //ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.combo_combustible, android.R.layout.simple_spinner_dropdown_item);
        this.selectAddCombustileArray = presenter.getArrayCombustibles();
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
         * Si se ha pulsado en un registro en el listado, cargará los datos que se le ha
         * pasado por el intent
         */
        this.onEditVehiculo();


        inputBtnGuardar = findViewById(R.id.inputBtnGuardar);
        inputBtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Pulsando boton Guardar...");
                boolean ok = true;
                //Vehiculo vehiculo = new Vehiculo();
                vehiculo.setImagen(presenter.bitmapToBase64(((BitmapDrawable)inputAddImagen.getDrawable()).getBitmap()));

                if(!vehiculo.setMarca(inputAddMarca.getText().toString())){
                    inputAddMarcaTIL.setError(getString(R.string.form_campo_oblig));
                    inputAddMarcaTIL.setErrorEnabled(true);
                    ok = false;
                } else {
                    inputAddMarcaTIL.setError("");
                    inputAddMarcaTIL.setErrorEnabled(false);
                }

                if(!vehiculo.setModelo(inputAddModelo.getText().toString())){
                    inputAddModeloTIL.setError(getString(R.string.form_campo_oblig));
                    inputAddModeloTIL.setErrorEnabled(true);
                    ok = false;
                } else {
                    inputAddModeloTIL.setError("");
                    inputAddModeloTIL.setErrorEnabled(false);
                }

                try {
                    if(!vehiculo.setAnyo(Integer.parseInt(inputAddAnyo.getText().toString()))){
                        inputAddAnyoTIL.setError(getString(R.string.form_anyo_incorrecto));
                        inputAddAnyoTIL.setErrorEnabled(true);
                        ok = false;
                    } else {
                        inputAddAnyoTIL.setError("");
                        inputAddAnyoTIL.setErrorEnabled(false);
                    }
                } catch (NumberFormatException e){
                    inputAddAnyoTIL.setError(getString(R.string.form_anyo_incorrecto));
                    inputAddAnyoTIL.setErrorEnabled(true);
                    ok = false;
                }

                if(!vehiculo.setTraccion(inputAddTraccion.getText().toString())){
                    inputAddTraccionTIL.setError(getString(R.string.form_campo_oblig));
                    inputAddTraccionTIL.setErrorEnabled(true);
                    ok = false;
                } else {
                    inputAddTraccionTIL.setError("");
                    inputAddTraccionTIL.setErrorEnabled(false);
                }

                vehiculo.setCombustible(selectAddCombustible.getSelectedItem().toString());

                if(!vehiculo.setFechamatriculacion(inputAddFechaMatriculacion.getText().toString())){
                    inputAddFechaMatriculacionTIL.setError(getString(R.string.form_fecha_incorrecta));
                    inputAddFechaMatriculacionTIL.setErrorEnabled(true);
                    ok = false;
                } else {
                    inputAddFechaMatriculacionTIL.setError("");
                    inputAddFechaMatriculacionTIL.setErrorEnabled(false);
                }

                vehiculo.setEdicionespecial(inputAddEdicionEspecial.isChecked() ? 1 : 0);

                if(ok){
                    if(getIntent().getStringExtra("editIdVehiculo") == null){
                        presenter.onClickGuardar(vehiculo);
                    } else {
                        presenter.onClickActualizar(vehiculo);
                    }

                }

            }
        });


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
                        presenter.onClickBorrar(getIntent().getStringExtra("editIdVehiculo"));
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
        inputAddImagen = findViewById(R.id.inputAddImagen);
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
        inputAddFechaMatriculacionTIL = findViewById(R.id.inputAddFechaMatriculacionTIL);
        inputAddFechaMatriculacion = findViewById(R.id.inputAddFechaMatriculacion);
        inputAddFechaMatriculacionBtn = findViewById(R.id.inputAddFechaMatriculacionBtn);
        inputAddEdicionEspecial = findViewById(R.id.inputAddEdicionEspecial);
        inputBtnGuardar = findViewById(R.id.inputBtnGuardar);
        inputBtnBorrar = findViewById(R.id.inputBtnBorrar);

        /**
         * Si no existe la variable que le paso desde Listado,
         * que oculte el btn Borrar
         */
        if(getIntent().getStringExtra("editIdVehiculo") == null){
            inputBtnBorrar.setVisibility(View.GONE);
        } else {
            setTitle("Editar vehículo");
        }
    }

    @Override // Se añade el Toolbar personalizado
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override // Funcionalidad de las opciones del toolbar
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_help:
                presenter.onClickAyuda();
                break;
        }
        return true;
    }

    @Override
    public void lanzarAyuda(){
        Log.d(TAG, "Lanzando AyudaActivity...");
        Intent intent = new Intent(FormularioActivity.this, AyudaActivity.class);
        intent.putExtra("pagina", AyudaActivity.FORMULARIO);
        startActivity(intent);
    }


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
    public void showError(String mensaje){
        CoordinatorLayout formularioActivityLayout = findViewById(R.id.formularioActivityID);
        Snackbar.make(formularioActivityLayout, mensaje, Snackbar.LENGTH_LONG).setAction("Cerrar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setActionTextColor(getResources().getColor(R.color.colorWhite)).show();
    }

    @Override
    public void showToast(String mensaje){
        Toast.makeText(FormularioActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione una aplicación"), 10);
    }

    @Override
    public void onEditVehiculo() {

        String id = getIntent().getStringExtra("editIdVehiculo");

        Vehiculo vehiculoEdit = new Vehiculo();
        vehiculoEdit = presenter.getVehiculoFromID(id);
        vehiculo = vehiculoEdit;

        // Cargo los datos al formulario
        try{
            inputAddImagen.setImageBitmap(presenter.stringToBitmap(vehiculo.getImagen()));
        } catch (RuntimeException e){
            Log.d(TAG, "onEditVehiculo - error en imagen");
            e.printStackTrace();
        }
        inputAddMarca.setText(vehiculo.getMarca());
        inputAddModelo.setText(vehiculo.getModelo());
        inputAddAnyo.setText(Integer.toString(vehiculo.getAnyo()));
        inputAddTraccion.setText(vehiculo.getTraccion());
        selectAddCombustible.setSelection(selectAddCombustileArray.indexOf(vehiculo.getCombustible()));
        inputAddFechaMatriculacion.setText(vehiculo.getFechamatriculacion());
        try{
            if(vehiculo.getEdicionespecial() == 1){
                inputAddEdicionEspecial.setChecked(true);
            } else {
                inputAddEdicionEspecial.setChecked(false);
            }
        } catch (RuntimeException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResultImagen(requestCode, resultCode, data, inputAddImagen, this, this.c);
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
