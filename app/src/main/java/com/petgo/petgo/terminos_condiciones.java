package com.petgo.petgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import kitaoka.Dialogos;
import kitaoka.Global;
import kitaoka.MySQL;

public class terminos_condiciones extends AppCompatActivity {

    CheckBox chk;
    Button boton_continuar;
    Button boton_regresar;
    TextView terminos;

    private String sql;
    private java.sql.ResultSet rs;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos_condiciones);
        boton_continuar = (Button) findViewById(R.id.button5);

        terminos = (TextView)findViewById(R.id.terminos_textView1);  // cliente

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        chk = (CheckBox) findViewById(R.id.checkBox);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chk.isChecked()==true){
                    boton_continuar.setBackgroundResource(R.drawable.boton_continue_on);
                }
                else{
                    boton_continuar.setBackgroundResource(R.drawable.boton_continue_off);
                }
            }
        });

        boton_regresar = (Button) findViewById(R.id.terminos_button4);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( Global.opcion ==1) {
                    startActivity(new Intent(terminos_condiciones.this, configuracion.class));
                    terminos_condiciones.this.finish();
                }
                else{
                    startActivity(new Intent(terminos_condiciones.this, welcome.class));
                    terminos_condiciones.this.finish();
                }
            }
        });

        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chk.isChecked()==true) {
                    startActivity(new Intent(terminos_condiciones.this, registro_usuario.class));
                    terminos_condiciones.this.finish();
                }
                else{
                    if( Global.opcion ==1) {
                        startActivity(new Intent(terminos_condiciones.this, configuracion.class));
                        terminos_condiciones.this.finish();
                    }
                }
            }
        });

        if( Global.opcion ==1){
            chk.setVisibility(View.INVISIBLE);
            boton_continuar.setBackgroundResource(R.drawable.boton_continue_on);
        }

        dialog = ProgressDialog.show(terminos_condiciones.this, "", "Wait, loading terms and conditions.", true);
        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MySQL.Conectar()) {
                    sql = "SELECT * FROM petgo_condiciones_cliente LIMIT 1";
                    rs = MySQL.Query(sql);
                    try {
                        dialog.dismiss();
                        if(rs.first()) {
                            terminos.setText(rs.getString("terminos"));
                            rs.close();
                        }
                        MySQL.cerrarConexion();
                    } catch (SQLException e) {
                        MySQL.cerrarConexion();
                        dialog.dismiss();
                        Dialogos.mensaje(terminos_condiciones.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                    } catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    // falla de conexion
                    MySQL.cerrarConexion();
                    dialog.dismiss();
                    Dialogos.mensaje(terminos_condiciones.this, "There is a connection problem with your wifi or your data plan. Verify that your device has internet connection");
                }
            }
        }, 1000);
    }
}
