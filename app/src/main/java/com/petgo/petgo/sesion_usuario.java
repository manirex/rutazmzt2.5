package com.petgo.petgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import kitaoka.Dialogos;
import kitaoka.Global;
import kitaoka.MySQL;
import kitaoka.SQLite;




public class sesion_usuario extends AppCompatActivity {

    Button button_sign_go;
    Button boton_regresar;

    EditText edit1;
    EditText edit2;

    View decorView;
    private ProgressDialog dialog;
    private String sql;
    private SQLite sqlite;
    private java.sql.ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_usuario);

        sqlite = new SQLite(this);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boton_regresar = (Button) findViewById(R.id.button4);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sesion_usuario.this, welcome.class));
                sesion_usuario.this.finish();
            }
        });

        edit1 = (EditText) findViewById(R.id.edit_sesion_email);
        edit2 = (EditText) findViewById(R.id.edit_sesion_pass);


        button_sign_go = (Button) findViewById(R.id.button_sign_go);

        button_sign_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = ProgressDialog.show(sesion_usuario.this, "", "Please wait, We are validating your registration.", true);
                android.os.Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (MySQL.Conectar()) {
                            sql = "SELECT * FROM petgo_clientes WHERE ";
                            sql += "Correo='" + edit1.getText().toString() + "' AND ";
                            sql += "Password='" + edit2.getText().toString() + "'";
                            rs = MySQL.Query(sql);
                            try {
                                if (rs.first()) {

                                    Global.idusuario = rs.getInt("idcliente");
                                    Global.usuario = rs.getString("Cliente");
                                    Global.correo = rs.getString("Correo");
                                    Global.phone = rs.getString("Phone");
                                    Global.emeregency_phone = rs.getString("Emergency_Phone");
                                    Global.password = rs.getString("Password");
                                    rs.close();
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

                                    startActivity(new Intent(sesion_usuario.this, MainActivity.class));
                                    sesion_usuario.this.finish();
                                } else {
                                    MySQL.cerrarConexion();
                                    dialog.dismiss();
                                    Dialogos.mensaje(sesion_usuario.this, "Incorrect access, try again.");
                                }
                            } catch (SQLException e) {
                                MySQL.cerrarConexion();
                                dialog.dismiss();
                                Dialogos.mensaje(sesion_usuario.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                            } catch (java.sql.SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // falla de conexion
                            MySQL.cerrarConexion();
                            dialog.dismiss();
                            Dialogos.mensaje(sesion_usuario.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                        }
                    }
                }, 2000);
            }
        });

    }
}