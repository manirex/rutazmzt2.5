package com.petgo.petgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kitaoka.Dialogos;
import kitaoka.Global;

public class lost_item extends AppCompatActivity {
    Button boton_regresar;
    Button boton_continuar;
    View decorView;
    EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item);

        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edit1 = (EditText)findViewById(R.id.lost_item_edit1);

        boton_regresar = (Button) findViewById(R.id.lost_item_button_regresar);
        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(lost_item.this, configuracion.class));
                lost_item.this.finish();
            }
        });

        boton_continuar = (Button) findViewById(R.id.lostitem_button2);
        boton_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit1.getText().length()==0)
                {
                    Dialogos.mensaje(lost_item.this, "You should not leave empty fields.");
                }
                else {
                    Global.comentarios = edit1.getText().toString();
                    Toast.makeText(getApplicationContext(), Global.comentarios = edit1.getText().toString(), Toast.LENGTH_SHORT).show();

                    //   startActivity(new Intent(lost_item.this, lost_item_confirmacion.class));
                //    lost_item.this.finish();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(lost_item.this, configuracion.class));
            lost_item.this.finish();
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
