package com.chaimae.hellomap;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private int selectedStyleId = R.string.defecte;
    private String estil_triat = "estil_triat";
    private int idEstils[] = {
            R.string.retro,
            R.string.nit,
            R.string.grisos,
            R.string.pink,
            R.string.defecte

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            selectedStyleId = savedInstanceState.getInt(estil_triat);
        }
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(estil_triat, selectedStyleId);
        super.onSaveInstanceState(outState);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng seuVella = new LatLng(41.61808922775635, 0.6268751621246338);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seuVella, 14));
        setEstil();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Creem el menu per a poder seleccionar l'opcio desitjada
        getMenuInflater().inflate(R.menu.mapstiles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //si es selecciona l'item del menu es mostren les opcions d'estil
        if (item.getItemId() == R.id.style) {
            showOptions();
        }
        return true;
    }

    private void showOptions() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.choose_style));
        builder.setItems(R.array.estils, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedStyleId = idEstils[i];
                String message = getString(selectedStyleId);
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, message);
                setEstil();
            }
        });
        builder.show();
    }

    private void setEstil() {
        MapStyleOptions estil;
        switch (selectedStyleId) {
            case R.string.retro:
                estil = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_retro);
                break;
            case R.string.nit:
                // Sets the night style via raw resource JSON.
                estil = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_nit);
                break;
            case R.string.grisos:
                // Sets the grayscale style via raw resource JSON.
                estil = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_grisos);
                break;
            case R.string.pink:
                estil = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_pink);
                break;
            case R.string.defecte:
                estil = null;
                break;
            default:
                return;
        }
        mMap.setMapStyle(estil);
    }


}
