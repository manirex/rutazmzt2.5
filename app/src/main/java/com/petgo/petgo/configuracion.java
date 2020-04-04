package com.petgo.petgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import kitaoka.Global;

public class configuracion extends AppCompatActivity {

    TextView menu_text1;
    TextView menu_text2;
    TextView menu_text3;
    TextView menu_text4;
    View decorView;

    Button boton_regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boton_regresar = (Button) findViewById(R.id.configuracion_boton_regresar);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(configuracion.this, MainActivity.class));
                configuracion.this.finish();
            }
        });

        menu_text1 = (TextView) findViewById(R.id.setting_texto2);
        menu_text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(configuracion.this, profile.class));
                configuracion.this.finish();
            }
        });

       /* menu_text2 = (TextView) findViewById(R.id.setting_texto3);
        menu_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(configuracion.this, lost_item.class));
                configuracion.this.finish();
            }
        });*/

        menu_text3 = (TextView) findViewById(R.id.setting_texto4);
        menu_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                Global.opcion = 1;
                startActivity(new Intent(configuracion.this, terminos_condiciones.class));
                configuracion.this.finish();
            }
        });

        menu_text4 = (TextView) findViewById(R.id.setting_texto5);
        menu_text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(configuracion.this, close_sesion.class));
                configuracion.this.finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(configuracion.this, MainActivity.class));
            configuracion.this.finish();
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
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
