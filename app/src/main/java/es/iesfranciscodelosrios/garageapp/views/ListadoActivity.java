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

import android.view.View;
import android.widget.TextView;

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

        // Modifico el texto del toolbar
        getSupportActionBar().setTitle("GarageApp");

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

    @Override // Este método ya no se usa, porque el método que lanza el formularioactivity está desarrollado en el presenter
    public void lanzarFormulario() {
        Log.d(TAG, "Lanzando formulario...");
        Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class); //Comunicamos las 2 actividades
        startActivity(intent);
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
