package com.petgo.petgo;

import android.Manifest;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import extension_map.Distance;
import extension_map.Example;
import extension_map.RetrofitMaps;
import kitaoka.Dialogos;
import kitaoka.Global;
import kitaoka.MySQL;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class select_car extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    ArrayList<Marker> carros;

    Marker origen;
    Marker destino;
    View decorView;

    Polyline line;

    private LinearLayout pantalla;
    private LinearLayout.LayoutParams params;
    Button boton_continuar;
    TextView text1;
    TextView text2;
    Button select_car_button_regresar;
    Button boton_sedan;
    Button boton_htbk;
    Button boton_suv;
    Button botn_minivan;

    private String sql;
    private java.sql.ResultSet rs;
    private int acumulador;
    private Integer sum;
    private String lugarGPS;

    TextView nombre;
    TextView correo;
    Button boton_menu;
    DrawerLayout drawer;

    TextView menu_text1;
    TextView menu_text2;
    TextView menu_text3;
    TextView menu_text4;
    TextView menu_text5;
    TextView menu_text6;
    Button boton_start_local;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_select_car);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final NavigationView navegador_local = (NavigationView) findViewById(R.id.select_car_nav);
        navegador_local.setNavigationItemSelectedListener(this);
        View headerView = navegador_local.getHeaderView(0);
        View navHeader = navegador_local.getHeaderView(0);

        nombre = navHeader.findViewById(R.id.textView);  /// send name*/
        nombre.setText(Global.usuario);
        correo = navHeader.findViewById(R.id.texCorreo);  /// send email*/
        correo.setText(Global.correo);

        Display display = getWindowManager().getDefaultDisplay();
        int alto = (display.getHeight()) - 1050;
        lugarGPS = "";

        pantalla = (LinearLayout) findViewById(R.id.pantalla_from_mapa);
        params = (LinearLayout.LayoutParams) pantalla.getLayoutParams();
        params.height = alto;
        pantalla.setLayoutParams(params);

        text1 = (TextView) findViewById(R.id.texto1);
        text2 = (TextView) findViewById(R.id.texto2);

        text1.setText(Global.domicilio1);
        text2.setText(Global.domicilio2);

        boton_menu = (Button) findViewById(R.id.boton_menu);
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
                startActivity(new Intent(select_car.this, my_rides.class));
                select_car.this.finish();
            }
        });

        menu_text5 = (TextView) headerView.findViewById(R.id.menu_text5);
        //  menu_text2.setText("Your Text Here");
        menu_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(select_car.this, configuracion.class));
                select_car.this.finish();
            }
        });


        boton_continuar = (Button) findViewById(R.id.boton_continuar);
        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Global.tipoUnidad == 0){
                    Toast.makeText(select_car.this, "Eliga un auto",Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(select_car.this, cotizador.class));
                    select_car.this.finish();
                }
            }
        });

        select_car_button_regresar = (Button) findViewById(R.id.select_car_button_regresar);
        select_car_button_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(select_car.this, main_to.class));
                select_car.this.finish();
            }
        });


        for(int i=0; i<Global.ListMascotas.size(); i++ ){
            acumulador= Integer.parseInt( Global.ListMascotas.get(i).get(4).toString())+acumulador ;
        }

        if(Global.ListMascotas.size()<=2 && acumulador<=60) {
            Button sedanNegro = (Button) findViewById(R.id.boton_sedan);
            sedanNegro.setEnabled(true);
            sedanNegro.setBackground(getResources().getDrawable(R.drawable.petgo_icons_sedan_negro));
            boton_sedan = (Button) findViewById(R.id.boton_sedan);
            boton_sedan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Global.tipoUnidad = 1;

                    Toast.makeText(select_car.this, "Es el boton del SEDAN", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(Global.ListMascotas.size()<=2 && acumulador<=80) {
            Button htbkNegro = (Button) findViewById(R.id.boton_htbk);
            htbkNegro.setEnabled(true);
            htbkNegro.setBackground(getResources().getDrawable(R.drawable.petgo_icons_htbk_negro));
            boton_htbk = (Button) findViewById(R.id.boton_htbk);
            boton_htbk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Global.tipoUnidad = 2;
                    Toast.makeText(select_car.this, "Es el boton del HTBK", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(Global.ListMascotas.size()<=3 && acumulador<=120) {
            Button suvNegro = (Button) findViewById(R.id.boton_suv);
            suvNegro.setEnabled(true);
            suvNegro.setBackground(getResources().getDrawable(R.drawable.petgo_icons_suv_negro));
            boton_suv = (Button) findViewById(R.id.boton_suv);
            boton_suv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Global.tipoUnidad = 3;
                    Toast.makeText(select_car.this, "Es el boton del SUV", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(Global.ListMascotas.size()<=4 && acumulador<=200) {
            Button minVanNegro = (Button) findViewById(R.id.botn_minivan);
            minVanNegro.setEnabled(true);
            minVanNegro.setBackground(getResources().getDrawable(R.drawable.petgo_icons_minivan_negro));
            botn_minivan = (Button) findViewById(R.id.botn_minivan);
            botn_minivan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Global.tipoUnidad = 4;
                    Toast.makeText(select_car.this, "Es el boton del MiniVan", Toast.LENGTH_SHORT).show();
                }
            });
        }




        if (MySQL.Conectar()) {

            sql = "SELECT * FROM petgo_afiliados WHERE STATUS=1 AND ciudad='" + lugarGPS + "' ";

            // sql = "SELECT a.*,b.Nombre FROM petgzo_transito a LEFT JOIN petgo_afiliados b ON a.idafiliado=b.idafiliado ";
            // sql += "WHERE status=1";
            try {
                rs = MySQL.Query(sql);
            } catch (SQLException e) {
                MySQL.cerrarConexion();
                Dialogos.mensaje(select_car.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
            }
        } else {
            // falla de conexion
            MySQL.cerrarConexion();
            Dialogos.mensaje(select_car.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
        }

        carros = new ArrayList<>();

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

                //change the size of the icon
                for(int point = 0; point < carros.size(); point++) {
                    Marker car = carros.get(point);
                    car.setIcon(
                            BitmapDescriptorFactory.fromBitmap(resizeMapIcons("carrito", relativePixelSize, relativePixelSize))
                    );
                    //car.setRotation(mLastLocation.getBearing());
                }
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

    private double rotarDireccion(LatLng latLng1, LatLng latLng2) {
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        //Place current location marker
        try {
            rs.first();
            do{
                System.out.println("lugar adentro: " + lugarGPS);
                LatLng latLng1 = new LatLng(rs.getFloat("latitud"), rs.getFloat("longitud"));
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
            while(rs.next());
            MySQL.cerrarConexion();
        } catch (java.sql.SQLException e) {
            Dialogos.mensaje(select_car.this, e.getMessage());
            e.printStackTrace();
        }

        BitmapDescriptor icon;
        LatLng latLng = new LatLng(Global.origen.getPosition().latitude, Global.origen.getPosition().longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("From Position");
        icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_from);
        markerOptions.icon(icon);
        mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        BitmapDescriptor icon2;
        latLng = new LatLng(Global.destino.getPosition().latitude, Global.destino.getPosition().longitude);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(latLng);
        markerOptions2.title("To Position");
        icon2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_to);
        markerOptions2.icon(icon2);
        mMap.addMarker(markerOptions2);

        // stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        crearRuta("walking");


    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Dialogos.mensaje(select_car.this, "Conexion Suspendida del Servicio de GPS");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Dialogos.mensaje(select_car.this, "Falla de Conexion al Servicio de GPS");
    }

    @Override
    public void onLocationChanged(Location location) {
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

       /* if (mLastLocation.hasBearing()) {
            for(int point = 0; point < carros.size(); point++) {
                Marker car = carros.get(point);
                car.setRotation(mLastLocation.getBearing());
            }
        }*/
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
                    Dialogos.mensaje(select_car.this, "permission denied GPS");
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }



    protected void crearRuta(String type) {

        String url = "https://maps.googleapis.com/maps/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

        Call<Example> call = service.getDistanceDuration("metric", Global.origen.getPosition().latitude + "," + Global.origen.getPosition().longitude, Global.destino.getPosition().latitude + "," + Global.destino.getPosition().longitude, type);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                try {
                    Log.d("onResponse", "YA RESPONDI PARA EMPEZAR A TRAZAR LA LINEA **********************************************");
                    //Remove previous line from map
                    if (line != null) {
                        line.remove();
                    }

                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().toString();
                        //  ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);

                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);
                        line = mMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(7)
                                .color(Color.RED)
                                .geodesic(true)

                        );



                    }
                } catch (Exception e) {
                    Log.d("onResponse", "*************************************************************************************************There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", "*************************************************************************************************"+t.toString());
            }
        });
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
