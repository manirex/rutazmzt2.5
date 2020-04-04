package com.petgo.petgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import kitaoka.Dialogos;
import kitaoka.Global;
import kitaoka.MySQL;
import kitaoka.SQLite;

public class registro_usuario extends AppCompatActivity {

    Button boton_regresar;
    Button boton_registrar;
    View decorView;
    EditText edit1;
    EditText edit2;
    EditText edit3;
    EditText edit4;
    EditText edit5;
    EditText edit6;

    private SQLite sqlite;
    private ProgressDialog dialog;
    private String sql;
    private java.sql.ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        sqlite = new SQLite(this);

        edit1 = (EditText)findViewById(R.id.registro_editText1);  // cliente
        edit2 = (EditText)findViewById(R.id.registro_editText2);  // correo
        edit3 = (EditText)findViewById(R.id.registro_editText3);  // pass
        edit4 = (EditText)findViewById(R.id.registro_editText5);  // phone
        edit5 = (EditText)findViewById(R.id.registro_editText6);  // emeregency
        edit6 = (EditText)findViewById(R.id.registro_editText4);  // repass

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boton_regresar = (Button) findViewById(R.id.button4);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registro_usuario.this, welcome.class));
                registro_usuario.this.finish();
            }
        });

        boton_registrar = (Button) findViewById(R.id.registro_button1);
        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit1.getText().toString().isEmpty()) {
                    Dialogos.mensaje(registro_usuario.this, "Do not leave the field empty");
                }
                else {
                    if (edit2.getText().toString().isEmpty()) {
                        Dialogos.mensaje(registro_usuario.this, "Please enter a valid Email");
                    } else {
                        if (edit3.getText().toString().isEmpty()) {
                            Dialogos.mensaje(registro_usuario.this, "Please enter a valid password");
                        } else {
                            if (edit3.getText().toString().equals(edit6.getText().toString())) {
                                nuevo_registro();
                            } else {
                                Dialogos.mensaje(registro_usuario.this, "The typed password does not match");
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(registro_usuario.this, welcome.class));
            registro_usuario.this.finish();
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

    private void nuevo_registro(){
        dialog = ProgressDialog.show(registro_usuario.this, "", "Please wait, We are validating your registration.", true);
        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MySQL.Conectar()) {
                    sql = "SELECT * FROM petgo_clientes WHERE Correo='" + edit2.getText().toString() + "'";
                    rs = MySQL.Query(sql);
                    try {
                        if (rs.last()) {
                            int rowcount = rs.getRow();
                            if (rowcount > 0) {
                                MySQL.cerrarConexion();
                                dialog.dismiss();
                                Dialogos.mensaje(registro_usuario.this, "Sorry, this registered email already exists in our database.");
                            } else {
                                InsertRegistroUsuario();
                            }
                        } else {
                            InsertRegistroUsuario();
                        }
                    } catch (SQLException e) {
                        MySQL.cerrarConexion();
                        dialog.dismiss();
                        Dialogos.mensaje(registro_usuario.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                    } catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    // falla de conexion
                    MySQL.cerrarConexion();
                    dialog.dismiss();
                    Dialogos.mensaje(registro_usuario.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                }
            }
        }, 1000);
    }

    private void InsertRegistroUsuario(){

        dialog = ProgressDialog.show(registro_usuario.this, "", "Wait, registering your data in PetGo.", true);
        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MySQL.Conectar()) {
                    sql = "INSERT INTO petgo_clientes VALUE(NULL,";
                    sql += "'"+ edit1.getText().toString() +"',";  // nombre
                    sql += "now(),";  // ingreso
                    sql += "'"+edit2.getText().toString() +"',";  // correo
                    sql += "'"+edit3.getText().toString()+"',";   // pass
                    sql += "'"+edit4.getText().toString()+"',";   // confirmacion pass
                    sql += "'"+edit5.getText().toString()+"')";   // num de telefom
                    MySQL.InsertQuery(sql);

                    sqlite.Query("DELETE FROM interno_sesion");
                    sql = "SELECT * FROM petgo_clientes WHERE ";
                    sql += "Correo='" + edit2.getText().toString() + "' AND ";
                    sql += "Password='" + edit3.getText().toString() + "'";
                    rs = MySQL.Query(sql);
                    try {
                        if (rs.first()) {
                            Global.idusuario = rs.getInt("idcliente");
                            Global.usuario = rs.getString("Cliente");
                            Global.correo = rs.getString("Correo");
                            Global.password = rs.getString("Password");

                            rs.close();
                            MySQL.cerrarConexion();
                            dialog.dismiss();

                            sqlite.Query("DELETE FROM interno_sesion");
                            sql = "INSERT INTO interno_sesion VALUES (";
                            sql += Global.idusuario + ",";
                            sql += "'" + Global.usuario +"',";
                            sql += "'" +  Global.correo +"',";
                            sql += "'" +  Global.password +"')";
                            sqlite.Query(sql);

                            startActivity(new Intent(registro_usuario.this, MainActivity.class));
                            registro_usuario.this.finish();
                        } else {
                            MySQL.cerrarConexion();
                            dialog.dismiss();
                            Dialogos.mensaje(registro_usuario.this, "Incorrect access, try again.");
                        }
                    } catch (SQLException e) {
                        MySQL.cerrarConexion();
                        dialog.dismiss();
                        Dialogos.mensaje(registro_usuario.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                    } catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    // falla de conexion
                    MySQL.cerrarConexion();
                    dialog.dismiss();
                    Dialogos.mensaje(registro_usuario.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                }
            }
        }, 1000);
    }

}
