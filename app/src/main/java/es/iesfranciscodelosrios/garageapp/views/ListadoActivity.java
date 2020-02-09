package es.iesfranciscodelosrios.garageapp.views;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import es.iesfranciscodelosrios.garageapp.interfaces.ListadoInterface;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;
import es.iesfranciscodelosrios.garageapp.presenters.ListadoPresenter;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.R;

public class ListadoActivity extends AppCompatActivity implements ListadoInterface.View {

    String TAG = "GarageApp/ListadoActivity";
    private ListadoInterface.Presenter presenter;
    private ArrayList<Vehiculo> items;
    private ArrayList<Vehiculo> vehiculosFiltro;

    TextView contadorTextView;

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

        contadorTextView = findViewById(R.id.contadorTextView);


        /**
         * Carga el listado en el RecyclerView
         */
        presenter.cargarListado(false);



    }

    @Override
    public void lanzarFormulario() {
        Log.d(TAG, "Lanzando formulario...");
        Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class); //Comunicamos las 2 actividades
        startActivity(intent);
    }

    @Override
    public void lanzarFormularioPorID(int id) {
        Log.d(TAG, "Lanzando formulario por ID...");
        Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class); //Comunicamos las 2 actividades
        // bundle para encapsular el ID al activity
        intent.putExtra("editIdVehiculo", Integer.toString(id));
        startActivity(intent);
    }

    @Override
    public void cargarListado(boolean desdeBusqueda){

        items = new ArrayList<Vehiculo>();

        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listadoRecyclerView);

        // Crea el Adaptador con los datos de la lista anterior
        if(desdeBusqueda){
            items = vehiculosFiltro;
        } else{
            items = presenter.getAllVehiculoListadoView();

        }
        //items = presenter.getAllVehiculoListadoView();
        VehiculoAdapter adaptador = new VehiculoAdapter(items);

        // Se establece en pantalla, el número de elemenos de la lista
        contadorTextView.setText(adaptador.getItemCount() + " resultados encontrados");

        // Asocia el Adaptador al RecyclerView
        recyclerView.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = recyclerView.getChildAdapterPosition(v);
                Log.d(TAG, "Click en RV: " + position + " " + items.get(position).getId().toString());
                presenter.onClickRecyclerView(items.get(position).getId()); // El ID y el vehículo en concreto
            }
        });

        /**
         * Swipe del RecyclerView
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Integer id = viewHolder.getAdapterPosition();
                presenter.deleteVehiculo(ListadoActivity.this, items.get(id));
            }
        }).attachToRecyclerView(recyclerView);
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
        //startActivity(intent);
        startActivityForResult(intent, 0);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            vehiculosFiltro = (ArrayList<Vehiculo>) data.getSerializableExtra("arrayVehiculosFiltro");
            //this.cargarListado(true);
            Toast.makeText(ListadoActivity.this, "No se ha podido terminar de hacer la búsqueda por múltiples erroes", Toast.LENGTH_SHORT).show();
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

        presenter.cargarListado(false);

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
