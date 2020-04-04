package kitaoka;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.petgo.petgo.MainActivity;
import com.petgo.petgo.R;
import com.petgo.petgo.formulario_local;


/**
 * Created by marcos kitaoka castro on 15/09/2016.
 * */

public class Dialogos {
    public static void mensaje(Context instancia, String texto) {
        new AlertDialog.Builder(instancia)
                .setTitle("Message")
                .setMessage(texto)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...
                    }
                }).create().show();

    }

    public static void pet_sumary(Context instancia, String texto) {
        new AlertDialog.Builder(instancia)
                .setTitle("Warning")
                .setMessage(texto)
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("No salio de la vista");
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(new Intent(Dialogos.this, MainActivity.class));
                        //Dialogos.this.finish();
                    }

                }).create().show();

    }
}