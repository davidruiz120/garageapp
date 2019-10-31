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
}
