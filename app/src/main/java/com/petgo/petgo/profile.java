package com.petgo.petgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kitaoka.Dialogos;
import kitaoka.Global;
import kitaoka.MySQL;
import kitaoka.SQLite;


public class profile extends AppCompatActivity {

    EditText edit1;
    EditText edit2;
    EditText edit3;
    EditText edit4;
    EditText edit5;
    TextView edit6;

    Button boton_regresar;
    Button boton;
    private ProgressDialog dialog;
    private String sql;
    private SQLite sqlite;
    private java.sql.ResultSet rs;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edit1 = (EditText)findViewById(R.id.profile_phone);
        edit2 = (EditText)findViewById(R.id.profile_phone2);
        edit3 = (EditText)findViewById(R.id.profile_email);
        edit4 = (EditText)findViewById(R.id.profile_pass);
        edit5 = (EditText)findViewById(R.id.profile_pass2);
        edit6 = (TextView) findViewById(R.id.profile_usuario);//nuevo

        edit1.setText(Global.phone);
        edit2.setText(Global.emeregency_phone);
        edit3.setText(Global.correo);
        edit6.setText(Global.usuario);

        //Toast.makeText(getApplicationContext(), "Usuario "+  Global.usuario  , Toast.LENGTH_SHORT).show();

        boton_regresar = (Button) findViewById(R.id.profile_button_regresar);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this, configuracion.class));
                profile.this.finish();
            }
        });

        sqlite = new SQLite(this);

        boton = (Button) findViewById(R.id.profile_button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit4.getText().toString().equals(Global.password)||edit4.getText().toString().isEmpty()&&edit5.getText().toString().isEmpty())  {
                //    Toast.makeText(getApplicationContext(), "Adentro del if "+ edit4.getText().toString()  , Toast.LENGTH_SHORT).show();
                    System.out.println("Txt adentro: "+ (edit4.getText().toString()));
                    System.out.println("Global adentro: "+ Global.password );
                    if( edit4.getText().equals(Global.password) && edit5.getText().length()==0 ){
                        Dialogos.mensaje(profile.this, "The new password can not be empty.");
                    }
                    else {
                        dialog = ProgressDialog.show(profile.this, "", "Please wait, We are validating your registration.", true);
                        android.os.Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (MySQL.Conectar()) {
                                    sql = "UPDATE petgo_clientes SET ";
                                    sql += "Correo='" + edit3.getText().toString() + "',";
                                    sql += "Phone='" + edit1.getText().toString() + "',";
                                    sql += "Emergency_Phone='" + edit2.getText().toString() + "'";
                                    if(edit5.getText().length()==0 ) sql += " ";
                                    else {
                                        sql += ",Password='" + edit5.getText().toString() + "'";

                                        Global.password = edit5.getText().toString();
                                    }
                                    sql += " WHERE idcliente=" + Global.idusuario;
                                //    MySQL.Query(sql);
                                    MySQL.UpdateQuery(sql);  /// actualizar

                                    Global.correo = edit3.getText().toString();
                                    Global.phone =  edit1.getText().toString();
                                    Global.emeregency_phone = edit2.getText().toString();

                                    MySQL.cerrarConexion();
                                    dialog.dismiss();

                                    sqlite.Query("DELETE FROM interno_sesion");
                                    sql = "INSERT INTO interno_sesion VALUES (";
                                    sql += Global.idusuario + ",";
                                    sql += "'" + Global.usuario + "',";
                                    sql += "'" + Global.correo + "',";
                                    sql += "'" + Global.phone + "',";
                                    sql += "'" + Global.emeregency_phone + "',";
                                    sql += "'" + Global.password + "')";
                                    sqlite.Query(sql);

                                    startActivity(new Intent(profile.this, configuracion.class));
                                    profile.this.finish();

                                } else {
                                    // falla de conexion
                                    MySQL.cerrarConexion();
                                    dialog.dismiss();
                                    Dialogos.mensaje(profile.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                                }
                            }
                        }, 2000);
                    }
                }
                else{
                  //  Toast.makeText(getApplicationContext(), "Este es el ELSE: "+ edit4.getText().toString() , Toast.LENGTH_SHORT).show();
                    System.out.println("Txt fuera: "+ (edit4.getText().toString()));
                    System.out.println("Global fuera: "+ Global.password );
                    Dialogos.mensaje(profile.this, "The old password does not match the one already registered.");
                }
            }
     //   }
        });
    }
}
