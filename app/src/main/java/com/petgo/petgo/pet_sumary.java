package com.petgo.petgo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kitaoka.Global;
import kitaoka.item;






public class pet_sumary extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static ListView listaView;
    private ArrayAdapter<String> adaptador;
    private static ArrayList<item> lista;
    private Button boton_add;
    private Button boton_confirmacion;
    private Button pet_sumary_button_regresar;
    private static ViewGroup.LayoutParams params;
    private static Context mContext;
    private  Button Eliminar ;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_sumary);
        mContext = this;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pet_sumary);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ListaAdapterItem adapter = new ListaAdapterItem(this,lista);
        listaView = (ListView) findViewById(R.id.ListaMascotas);
        params = listaView.getLayoutParams();



        final NavigationView navegador_local = (NavigationView) findViewById(R.id.pet_sumary_nav);
        navegador_local.setNavigationItemSelectedListener(this);
        View headerView = navegador_local.getHeaderView(0);
        View navHeader = navegador_local.getHeaderView(0);

        nombre = navHeader.findViewById(R.id.textView);  /// send name*/
        nombre.setText(Global.usuario);
        correo = navHeader.findViewById(R.id.texCorreo);  /// send email*/
        correo.setText(Global.correo);

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
                startActivity(new Intent(pet_sumary.this, my_rides.class));
                pet_sumary.this.finish();
            }
        });

        menu_text5 = (TextView) headerView.findViewById(R.id.menu_text5);
        //  menu_text2.setText("Your Text Here");
        menu_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                startActivity(new Intent(pet_sumary.this, configuracion.class));
                pet_sumary.this.finish();
            }
        });


        pet_sumary_button_regresar  = (Button) findViewById(R.id.pet_sumary_button_regresar);
        pet_sumary_button_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Global.ListMascotas.isEmpty() || Global.ListMascotas == null) {
                    startActivity(new Intent(pet_sumary.this, MainActivity.class));
                    pet_sumary.this.finish();
                    System.out.println("Entre donode no");
                }
                else
                    new AlertDialog.Builder(pet_sumary.this)
                            .setTitle("Warning")
                            .setMessage("you are sure to leave you will lose all your data")
                            //.setCancelable(false)
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.out.println("No salio de la vista");
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(pet_sumary.this, MainActivity.class));
                                    Global.ListMascotas.clear();
                                    listaView = null;
                                    lista = null;

                                    pet_sumary.this.finish();
                                }
                            }).create().show();

            }
        });




        boton_confirmacion = (Button) findViewById(R.id.pet_sumary_boton);
        boton_confirmacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.ListMascotas.isEmpty()|| Global.ListMascotas == null||Global.ListMascotas.size()==0) {
                    boton_confirmacion.setBackgroundResource(R.drawable.boton_confirm_pets_desabilitado);

                    Toast.makeText(getApplicationContext(), "Ingrese una mascota", Toast.LENGTH_SHORT).show();

                }
                else {
                    boton_confirmacion.setEnabled(false);
                    startActivity(new Intent(pet_sumary.this, Main_From.class));
                    pet_sumary.this.finish();
                }
            }
        });

        boton_add = (Button) findViewById(R.id.local_reg_boton);
        boton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Global.ListMascotas.size()==4){
                    Toast.makeText(getApplicationContext(), "Solo 4 mascotas por viaje", Toast.LENGTH_SHORT).show();

                }
                else {
                    startActivity(new Intent(pet_sumary.this, formulario_local.class));
                    pet_sumary.this.finish();
                }
            }
        });
        actualizarLista();

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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void actualizarLista() {
        List<String> Pet = new ArrayList<String>();
         lista = new ArrayList<item>();
        params.height = dpToPx(90) * Global.ListMascotas.size();
        listaView.setLayoutParams(params);
        for(int i = 0; i < Global.ListMascotas.size(); i++) {
            Pet = Global.ListMascotas.get(i);


            String pet = "Dog";
            if (Pet.get(0).contentEquals("1")) pet = "Cat";
            String nombre = Pet.get(1).toString() + "    " + Pet.get(4).toString() + " lbs";
            String comentario = Pet.get(6).toString();
            lista.add(new item(nombre, pet, Pet.get(2), comentario));
        }

        ListaAdapterItem adapterItem = new ListaAdapterItem(mContext, lista);
        listaView.setAdapter(adapterItem);
        listaView.refreshDrawableState();


    }

    public static int dpToPx(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
