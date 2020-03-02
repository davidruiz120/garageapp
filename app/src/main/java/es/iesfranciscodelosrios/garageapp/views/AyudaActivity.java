package es.iesfranciscodelosrios.garageapp.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import es.iesfranciscodelosrios.garageapp.R;
import es.iesfranciscodelosrios.garageapp.interfaces.AyudaInterface;
import es.iesfranciscodelosrios.garageapp.presenters.AyudaPresenter;

public class AyudaActivity extends AppCompatActivity implements AyudaInterface.View {

    String TAG = "GarageApp/AyudaActivity";
    private AyudaInterface.Presenter presenter;
    private WebView mWebview ;
    public static final String LISTADO = "listado";
    public static final String FORMULARIO = "addmodifdel";
    public static final String BUSCAR = "buscar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Agrego el Up Button, la funcionalidad del mismo se establece en AndroidManifest.xml
         * agregando parent android:parentActivityName=".views.ListadoActivity" en el
         * activity AyudaActivity
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new AyudaPresenter(this);


        /**
         * Con un Switch compruebo que valor recibe el intent para después
         * lanzar la web con un enlace en específico
         */
        switch (getIntent().getStringExtra("pagina")){
            case LISTADO:
                presenter.lanzarAyuda(LISTADO);
                break;
            case BUSCAR:
                presenter.lanzarAyuda(BUSCAR);
                break;
            case FORMULARIO:
                presenter.lanzarAyuda(FORMULARIO);
                break;
        }


    }

    @Override
    public void lanzarAyuda(String enlace) {
        mWebview  = new WebView(this);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                //onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                presenter.lanzarListado();
            }
        });

        mWebview .loadUrl("https://davidruiz120.github.io/garageapp/" + enlace + ".html");
        setContentView(mWebview );
    }

    @Override
    public void showToast(String mensaje){
        Toast.makeText(AyudaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void lanzarListado(){
        Log.d(TAG, "Lanzando listado...");
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
