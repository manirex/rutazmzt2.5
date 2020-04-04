package com.petgo.petgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import kitaoka.Global;

public class welcome extends AppCompatActivity {

    Button boton_sesion;
    Button boton_newuser;
    TextView boton_recuperar;
    View decorView;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        boton_sesion = (Button) findViewById(R.id.button2);
        boton_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(welcome.this, sesion_usuario.class));
                welcome.this.finish();
            }
        });

        boton_newuser = (Button) findViewById(R.id.button3);
        boton_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.opcion = 0;
                startActivity(new Intent(welcome.this, terminos_condiciones.class));
                welcome.this.finish();
            }
        });


        boton_recuperar = (TextView) findViewById(R.id.welcome_textView);
        boton_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                Toast.makeText(getApplicationContext(), "Proximamente.... ", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(welcome.this, recuperacion_clave.class));
              //  welcome.this.finish();
            }
        });
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

}
