package com.petgo.petgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kitaoka.Dialogos;
import kitaoka.Global;

public class formulario_local extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button boton_add;
    EditText edit1;
    EditText edit2;
    EditText edit3;
    EditText edit4;
    EditText edit5;
    EditText edit6;
    RadioButton radio1;
    RadioButton radio2;
    Button boton_regresar;
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


    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_local);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_form_local);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        radio1 = (RadioButton) findViewById(R.id.local_reg_r1);
        radio2 = (RadioButton) findViewById(R.id.local_reg_r2);

        edit1 = (EditText) findViewById(R.id.local_reg_d1);
        edit2 = (EditText) findViewById(R.id.local_reg_d2);
        edit3 = (EditText) findViewById(R.id.local_reg_d3);
        edit4 = (EditText) findViewById(R.id.local_reg_d4);
        edit5 = (EditText) findViewById(R.id.local_reg_d5);
        edit6 = (EditText) findViewById(R.id.local_reg_d6);


        final NavigationView navegador_local = (NavigationView) findViewById(R.id.local_nav);
        navegador_local.setNavigationItemSelectedListener(this);
        View headerView = navegador_local.getHeaderView(0);
        View navHeader = navegador_local.getHeaderView(0);

        nombre = navHeader.findViewById(R.id.textView);  /// send name*/
        nombre.setText(Global.usuario);
        correo = navHeader.findViewById(R.id.texCorreo);  /// send email*/
        correo.setText(Global.correo);

        boton_menu = (Button) findViewById(R.id.btn_Regresar_form_Local);
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
                startActivity(new Intent(formulario_local.this, my_rides.class));
                formulario_local.this.finish();
            }
        });

        menu_text5 = (TextView) headerView.findViewById(R.id.menu_text5);
        //  menu_text2.setText("Your Text Here");
        menu_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(formulario_local.this, configuracion.class));
                formulario_local.this.finish();
            }
        });


        boton_add = (Button) findViewById(R.id.local_reg_boton);
        boton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit1.getText().length() > 0 && edit3.getText().length() > 0 &&  edit4.getText().length() > 0 &&
                        edit5.getText().length() > 0 ) {
                    if (Integer.parseInt(edit4.getText().toString()) <= 200) {

                        ArrayList<String> datos = new ArrayList<String>();

                        if (radio1.isChecked()) datos.add("0");
                        else datos.add("1");

                        datos.add(edit1.getText().toString());
                        datos.add(edit2.getText().toString());
                        datos.add(edit3.getText().toString());
                        datos.add(edit4.getText().toString());
                        datos.add(edit5.getText().toString());
                        datos.add(edit6.getText().toString());

                        Global.ListMascotas.add(datos);

                        startActivity(new Intent(formulario_local.this, pet_sumary.class));
                        formulario_local.this.finish();
                    }
                    else{
                        Toast.makeText(formulario_local.this , "El peso no debe de sobre pasar las 200 libras " ,Toast.LENGTH_SHORT).show();
                    }
                    } else {
                        Dialogos.mensaje(formulario_local.this, "Please fill in the required fields.");
                    }

            }
        });

        menu_text4 = (TextView) headerView.findViewById(R.id.menu_text4);
        menu_text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                 Toast.makeText(getApplicationContext(), "En proceso.....", Toast.LENGTH_SHORT).show();

            }
        });


        boton_regresar = (Button) findViewById(R.id.local_button_regresar);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(Global.ListMascotas.isEmpty()|| Global.ListMascotas == null){
                    startActivity(new Intent(formulario_local.this, MainActivity.class));
                    formulario_local.this.finish();
                 //   Global.ListMascotas=null;
                }
                else
                   startActivity(new Intent(formulario_local.this, pet_sumary.class));
                System.out.println("Esto es lo que tiene la lista:  " +Global.ListMascotas);
                formulario_local.this.finish();


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}


