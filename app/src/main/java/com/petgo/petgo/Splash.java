package com.petgo.petgo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import kitaoka.Global;
import kitaoka.SQLite;

import static android.os.SystemClock.sleep;

public class Splash extends AppCompatActivity {
    private String sql;
    private SQLite sqlite;
    private Boolean ok_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        fin();

       /*
        sqlite = new SQLite(this);
        ok_sesion = false;

        sql = "CREATE TABLE IF NOT EXISTS interno_sesion (";
        sql += "idcliente INTEGER,";
        sql += "usuario TEXT,";
        sql += "correo TEXT,";
        sql += "telefono TEXT,";
        sql += "emergencia TEXT,";
        sql += "password TEXT)";
        if (sqlite.Query(sql)) {

            sql = "SELECT * FROM interno_sesion";
            if (sqlite.Query(sql)) {
                if (sqlite.rs.getCount() > 0) {
                    Global.idusuario = sqlite.rs.getInt(0);
                    Global.usuario = sqlite.rs.getString(1);
                    Global.correo = sqlite.rs.getString(2);
                    Global.phone = sqlite.rs.getString(3);
                    Global.emeregency_phone = sqlite.rs.getString(4);
                    Global.password = sqlite.rs.getString(5);
                    ok_sesion = true;
                    fin();
                } else {
                    fin();
                }
            } else {
                fin();
            }
        } else{
            fin();
        }


        */




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){

        }
        return false;
        // Disable back button..............
    }

   /* public void fin(){
        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                // que hacer despues de 5000 milisegundos
                if(ok_sesion) startActivity(new Intent(Splash.this, MainActivity.class));
                else startActivity(new Intent(Splash.this, welcome.class));
                Splash.this.finish();
            }
        }, 2000);
    }*/


public void fin() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)

                for (int i = 0; i < info.length; i++)

                if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                    //    System.out.println("********************************************************************" + info[i].getState());

                        //present your UI
                        android.os.Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // que hacer despues de 5000 milisegundos


                            /*  if (ok_sesion)
                                   startActivity(new Intent(Splash.this, MainActivity.class));
                               else startActivity(new Intent(Splash.this, welcome.class));
                               System.out.println("");
                               Splash.this.finish();*/

                                startActivity(new Intent(Splash.this, MainActivity.class));
                                Splash.this.finish();

                            }
                        }, 2000);

                    }

                  /* else if (info[i].getState() == NetworkInfo.State.DISCONNECTED) {
                   //     System.out.println("********************************************************************" + );

                        Toast.makeText(getApplicationContext(), ""+ info[i].getState(), Toast.LENGTH_SHORT).show();

                    }*/
                }



        } catch (Exception e) {
            e.printStackTrace();
        }



    }






}
