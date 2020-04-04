package com.petgo.petgo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import kitaoka.Dialogos;
import kitaoka.Global;

public class main_to extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    View decorView;
    private LinearLayout pantalla;
    private LinearLayout.LayoutParams params;
    Button boton_continuar;
    Button select_to_button_regresar;
    EditText edit1;
    TextView text1;


    DrawerLayout drawer;
    Button boton_menu;
    TextView menu_text1;
    TextView menu_text2;
    TextView menu_text3;
    TextView menu_text4;
    TextView menu_text5;
    TextView menu_text6;

    TextView nombre;
    TextView correo;

    // Constants
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int PLACE_PICKER_REQUEST = 1;

    // Member variables
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main_to);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        int alto = (display.getHeight()) - 750;

        final NavigationView navegador_local = (NavigationView) findViewById(R.id.main_to_nav);
        navegador_local.setNavigationItemSelectedListener(this);
        View headerView = navegador_local.getHeaderView(0);
        View navHeader = navegador_local.getHeaderView(0);

        nombre = navHeader.findViewById(R.id.textView);  /// send name*/
        nombre.setText(Global.usuario);
        correo = navHeader.findViewById(R.id.texCorreo);  /// send email*/
        correo.setText(Global.correo);

        boton_menu = (Button) findViewById(R.id.btn_menu_main_to);
        boton_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);

            }
        });

        menu_text3= (TextView)headerView.findViewById(R.id.menu_text3) ;
        menu_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main_to.this, my_rides.class));
                main_to.this.finish();
            }
        });

        menu_text5 = (TextView) headerView.findViewById(R.id.menu_text5);
        //  menu_text2.setText("Your Text Here");
        menu_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(main_to.this, configuracion.class));
                main_to.this.finish();
            }
        });


        pantalla = (LinearLayout) findViewById(R.id.pantalla_from_mapa);
        params = (LinearLayout.LayoutParams) pantalla.getLayoutParams();
        params.height = alto;
        pantalla.setLayoutParams(params);

        text1 = (TextView) findViewById(R.id.textFrom1);
        text1.setText(Global.domicilio1);

        edit1 = (EditText) findViewById(R.id.main_from_d1);
        edit1.setKeyListener(null);
        edit1.setText("To: Loading current location");
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verSearch();
            }
        });

        boton_continuar = (Button) findViewById(R.id.pet_sumary_boton);
        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(main_to.this, select_car.class));
                main_to.this.finish();
            }
        });

        select_to_button_regresar = (Button) findViewById(R.id.select_to_button_regresar);
        select_to_button_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(main_to.this, Main_From.class));
                main_to.this.finish();
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // startActivity(new Intent(mapa.this, lista_mascotas.class));
            // mapa.this.finish();
        }
        return false;
        // Disable back button..............
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    private void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Dialogos.mensaje(main_to.this, "Conexion Suspendida del Servicio de GPS");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Dialogos.mensaje(main_to.this, "Falla de Conexion al Servicio de GPS");
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        refreshPlacesData();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Dialogos.mensaje(main_to.this, "permission denied GPS");
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            if (place == null) {
                Log.i(TAG, "No place selected");
                return;
            }

            double latitude = place.getLatLng().latitude;
            double longitude = place.getLatLng().longitude;
            mLastLocation.setLongitude(longitude);
            mLastLocation.setLatitude(latitude);
            refreshPlacesData();
        }
    }

    public void onRingerPermissionsClicked(View view) {
        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        startActivity(intent);
    }

    public void verSearch(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.need_location_permission_message), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }
    }

    public void refreshPlacesData() {
        if (Global.destino != null) {
            Global.destino.remove();
        }
        BitmapDescriptor icon;
        //Place current location marker
        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("To Position");
        icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_to);
        markerOptions.icon(icon);
        Global.destino = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        Global.domicilio2 =  "";
        /*----------to get City-Name from coordinates ------------- */
        Geocoder gcd = new Geocoder(getBaseContext(),Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String locality = addresses.get(0).getLocality();
                String subLocality = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                if (subLocality != null) {
                    edit1.setText("To: " + address);
                    Global.domicilio2 =  edit1.getText().toString();
                }
                else {
                    if (subLocality != null) {
                        edit1.setText("To: " + locality + "," + subLocality);
                        Global.domicilio2 =  edit1.getText().toString();
                    } else {
                        edit1.setText("To: " + locality);
                        Global.domicilio2 =  edit1.getText().toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
