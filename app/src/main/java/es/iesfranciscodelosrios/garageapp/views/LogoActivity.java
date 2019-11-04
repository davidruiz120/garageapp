package es.iesfranciscodelosrios.garageapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static java.lang.Thread.sleep;


public class LogoActivity extends AppCompatActivity {

    String TAG = "GarageApp/LogoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Log.d(TAG, "Iniciando app. Logo en pantalla. Esperando 5 segundos...");
            sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            Log.d(TAG, "Iniciando ListadoActivity...");
            Intent intent = new Intent(this, ListadoActivity.class);
            startActivity(intent);
            Log.d(TAG, "Finalizando LogoActivity");
            finish();
        }

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
