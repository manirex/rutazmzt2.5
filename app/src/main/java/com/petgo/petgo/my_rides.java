package com.petgo.petgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.WindowManager;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class my_rides extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback{
    Button boton_regresar;
    View decorView;
    DrawerLayout drawer;
    Button boton_menu;

    TextView menu_text1;
    TextView menu_text2;
    TextView menu_text3;
    TextView menu_text4;
    TextView menu_text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);


        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

/*        final NavigationView navegador_menu = (NavigationView) findViewById(R.id.nav_view);
        navegador_menu.setNavigationItemSelectedListener(this);
        View headerView = navegador_menu.getHeaderView(0);
        View navHeader = navegador_menu.getHeaderView(0);
*/

/*
        menu_text2 = (TextView) headerView.findViewById(R.id.menu_text2);
        //  menu_text2.setText("Your Text Here");
        menu_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                Toast.makeText(getApplicationContext(), "no disponible en este moento", Toast.LENGTH_SHORT).show();
            }
        });

        menu_text3= (TextView)headerView.findViewById(R.id.menu_text3) ;
        menu_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(my_rides.this, my_rides.class));
                my_rides.this.finish();
            }
        });

        menu_text5 = (TextView) headerView.findViewById(R.id.menu_text5);
        //  menu_text2.setText("Your Text Here");
        menu_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(my_rides.this, configuracion.class));
                my_rides.this.finish();
            }
        });
*/
  /*      boton_menu = (Button) findViewById(R.id.riders_boton_menu);
        boton_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // drawer.openDrawer(GravityCompat.START);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++Entre");
            }
        });
*/

        boton_regresar = (Button) findViewById(R.id.my_rides_button_regresar);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(my_rides.this, MainActivity.class));
                my_rides.this.finish();
            }
        });


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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
