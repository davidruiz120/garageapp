package es.iesfranciscodelosrios.garageapp.views;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import es.iesfranciscodelosrios.garageapp.interfaces.ListadoInterface;
import es.iesfranciscodelosrios.garageapp.presenters.ListadoPresenter;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import es.iesfranciscodelosrios.garageapp.R;

public class ListadoActivity extends AppCompatActivity implements ListadoInterface.View {

    String TAG = "GarageApp/ListadoActivity";
    private ListadoInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        presenter = new ListadoPresenter(this);

        FloatingActionButton fab = findViewById(R.id.listadoFb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Pulsando boton flotante...");
                presenter.onClickAdd();
            }
        });


    }

    @Override
    public void lanzarFormulario() {
        Log.d(TAG, "Lanzando formulario...");
        Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class); //Comunicamos las 2 actividades
        startActivity(intent);
    }

    @Override
    public void lanzarAcercaDe() {
        Log.d(TAG, "Lanzando AcercaDeActivity...");
        Intent intent = new Intent(ListadoActivity.this, AcercaDeActivity.class);
        startActivity(intent);
    }

    @Override
    public void lanzarBuscar() {
        Log.d(TAG, "Lanzando BuscarActivity...");
        Intent intent = new Intent(ListadoActivity.this, BuscarActivity.class);
        startActivity(intent);
    }

    @Override // Se añade el Toolbar personalizado
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listado, menu);
        return true;
    }

    @Override // Funcionalidad de las opciones del toolbar
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_buscar:
                presenter.onClickBuscar();
                break;
            case R.id.action_ordenar:
                Toast.makeText(ListadoActivity.this, "Sin funcionalidad por el momento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(ListadoActivity.this, "Sin funcionalidad por el momento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_acerca_de:
                presenter.onClickSobreGarageApp();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Log.d(TAG,"Ejecutando onBlackPressed en ListadoActivity...");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"Ejecutando onStart en ListadoActivity...");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"Ejecutando onRestart en ListadoActivity...");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"Ejecutando onResume en ListadoActivity...");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"Ejecutando onPause en ListadoActivity...");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"Ejecutando onStop en ListadoActivity...");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"Ejecutando onDestroy en ListadoActivity...");
    }

}
