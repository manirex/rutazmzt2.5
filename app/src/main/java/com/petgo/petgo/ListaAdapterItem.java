package com.petgo.petgo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kitaoka.Global;
import kitaoka.item;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.petgo.petgo.pet_sumary.*;

/**
 * Created by Marcos Kitaoka Castro on 02/10/2016.
 */

public class ListaAdapterItem extends ArrayAdapter<item>{

    private Context context;
    private ArrayList<item> lista;

    public ListaAdapterItem(Context context,ArrayList<item> lista){
        super(context,0,lista);
        this.context = context;
        this.lista = lista;
    }
        pet_sumary ps = new pet_sumary();

    public View getView(final int position, View convertView, ViewGroup parent){
        item itemPosition = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item,null);

        TextView textTexto1 = (TextView) convertView.findViewById(R.id.item_texto1);
        textTexto1.setText(itemPosition.getNombre());

        TextView textTexto2 = (TextView) convertView.findViewById(R.id.item_texto2);
        textTexto2.setText(itemPosition.getTipo());

        TextView textTexto3 = (TextView) convertView.findViewById(R.id.item_texto3);
        textTexto3.setText(itemPosition.getRaza());

        TextView textTexto4 = (TextView) convertView.findViewById(R.id.item_texto4);
        textTexto4.setText(itemPosition.getComentario());

        Button boton_lista = (Button) convertView.findViewById(R.id.lista_boton1);
        boton_lista.setTag(new Integer(position));
        boton_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.ListMascotas.remove(position);
                pet_sumary.actualizarLista();



            }
        });

        return convertView;

    }



}
