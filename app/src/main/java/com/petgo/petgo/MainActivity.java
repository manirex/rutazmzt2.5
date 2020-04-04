package com.petgo.petgo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kitaoka.Dialogos;
import kitaoka.Global;
import kitaoka.MySQL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int PLACE_PICKER_REQUEST = 1;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker origen;
    ArrayList<Marker> carros;
    private static final String DEBUG_TAG = "NetworkStatusExample";

    LocationRequest mLocationRequest;
    Polyline line;
    Button boton_menu;
    Button boton_start_local;
    TextView menu_text1;
    TextView menu_text2;
    TextView menu_text3;
    TextView menu_text4;
    TextView menu_text5;
    TextView menu_text6;
    TextView nombre;
    TextView correo;




    private String sql;
    private java.sql.ResultSet rs;
    private  boolean ayudad;
    View decorView;
    DrawerLayout drawer;
    private String lugarGPS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final NavigationView navegador_menu = (NavigationView) findViewById(R.id.nav_view);
        navegador_menu.setNavigationItemSelectedListener(this);
        View headerView = navegador_menu.getHeaderView(0);
        View navHeader = navegador_menu.getHeaderView(0);
        /////Mostrar el nombre del usuario
      //  nombre = navHeader.findViewById(R.id.textView);  /// send name*/
       // nombre.setText(Global.usuario);
      //  correo = navHeader.findViewById(R.id.texCorreo);  /// send email*/
     //   correo.setText(Global.correo);
        lugarGPS = "";

        Global.ListMascotas.clear();


        menu_text4 = (TextView) headerView.findViewById(R.id.menu_text4);
        menu_text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                /* Toast.makeText(getApplicationContext(), "En proceso.....", Toast.LENGTH_SHORT).show();*/

            }
        });


        boton_menu = (Button) findViewById(R.id.boton_menu);
        boton_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        boton_start_local = (Button) findViewById(R.id.boton_start_local);
        boton_start_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.ListMascotas = new ArrayList<ArrayList>();
                startActivity(new Intent(MainActivity.this, formulario_local.class));
                MainActivity.this.finish();
            }
        });

        menu_text1 = (TextView) headerView.findViewById(R.id.menu_text1);
        menu_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(MainActivity.this, formulario_local.class));
                MainActivity.this.finish();
            }
        });

        menu_text3= (TextView)headerView.findViewById(R.id.menu_text3) ;
        menu_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, my_rides.class));
                MainActivity.this.finish();
            }
        });

        menu_text5 = (TextView) headerView.findViewById(R.id.menu_text5);
        //  menu_text2.setText("Your Text Here");
        menu_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(MainActivity.this, configuracion.class));
                MainActivity.this.finish();
            }
        });

       /* if (MySQL.Conectar()) {


        } else {
            // falla de conexion de la base de datos
            MySQL.cerrarConexion();
            Dialogos.mensaje(MainActivity.this, "There is a connection " +
                    "problem with your wifi or your data plan. Verify that your device has internet connection");
        }
carros = new ArrayList<>();*/

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
/*
    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                Toast.makeText(getApplicationContext(), "no hay internte ********************************", Toast.LENGTH_SHORT).show();
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    Toast.makeText(getApplicationContext(), "hay internet", Toast.LENGTH_SHORT).show();
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }*/

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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
        }

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

        }

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

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition position) {

                float zoom = (21 - position.zoom);
                int pixelSizeAtZoom0 = 8; //the size of the icon at zoom level 0
                int maxPixelSize = 160; //restricts the maximum size of the icon, otherwise the browser will choke at higher zoom levels trying to scale an image to millions of pixels

                int relativePixelSize = (int) (160 / zoom) * 4; // use 2 to the power of current zoom to calculate relative pixel size.  Base of exponent is 2 because relative size should double every time you zoom in

                if(relativePixelSize > maxPixelSize) //restrict the maximum size of the icon
                    relativePixelSize = maxPixelSize;

                if(relativePixelSize < pixelSizeAtZoom0) //restrict the maximum size of the icon
                    relativePixelSize = pixelSizeAtZoom0;
/*
                //change the size of the icon aqui se imprime la imagen del auto
                for(int point = 0; point < carros.size(); point++) {
                    Marker car = carros.get(point);
                    car.setIcon(
                            BitmapDescriptorFactory.fromBitmap(resizeMapIcons("carrito", relativePixelSize, relativePixelSize))
                    );
                    //car.setRotation(mLastLocation.getBearing());
                }*/
            }
        });

    }

    private void buildGoogleApiClient(){

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    private double rotarDireccion(LatLng latLng1,LatLng latLng2) {
        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }


    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Dialogos.mensaje(MainActivity.this, "Conexion Suspendida del Servicio de GPS");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Dialogos.mensaje(MainActivity.this, "Falla de Conexion al Servicio de GPS");
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //refreshPlacesData();
        if (origen != null) {
            origen.remove();
        }
        BitmapDescriptor icon;
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("From Position"); //de aqui se puede obtener la posision from (de) ya con esto se puede realizar un trazado de las dos ubicaicones a futuro
       // icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_from);
       // markerOptions.icon(icon);
        origen = mMap.addMarker(markerOptions);


        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


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
                    Dialogos.mensaje(MainActivity.this, "permission denied GPS");
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
    /*
    ConnectivityManager connectivityManager =   (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo info_wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    NetworkInfo info_datos = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    if(String.valueOf(info_wifi.getState()).equales ("CONNECTED")){

    }
*/
/*
    public void refreshPlacesData() {
        if (Global.origen != null) {
            Global.origen.remove();
        }
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                String locality = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();

                if(lugarGPS=="") {
                    lugarGPS = locality + ", " + state;
                    // aqui imprimeme que valor agarra el lugarGPS para saber que estamos recibiendo
                    System.out.println("lugar afuera: " + lugarGPS);
                    sql = "SELECT * FROM petgo_afiliados WHERE STATUS = 1 AND ciudad='" + lugarGPS + "' ";
                    try {
                        rs = MySQL.Query(sql);
                    } catch (SQLException e) {
                        MySQL.cerrarConexion();
                        Dialogos.mensaje(MainActivity.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                    }


                    try {
                        rs.first();
                        do {
                            System.out.println("lugar adentro: " + lugarGPS);
                            LatLng latLng1 = new LatLng(rs.getFloat("latitud"), rs.getFloat("longitud"));
                            System.out.println(latLng1);
                            float rotacion = rs.getFloat("rotacion");
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng1);
                            markerOptions.title(rs.getString("Nombre"));
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("carrito", 160, 160)));
                            Marker car;
                            car = mMap.addMarker(markerOptions);
                            car.setRotation(rotacion);
                            carros.add(car);
                        }
                        while (rs.next());
                        MySQL.cerrarConexion();

                    } catch (java.sql.SQLException e) {
                        Toast.makeText(getApplicationContext(), "En proceso.....", Toast.LENGTH_SHORT).show();
                        //   Dialogos.mensaje(MainActivity.this, "No hay autos disponibles");
                        e.printStackTrace();

                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/
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
        //Place current location marker

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

