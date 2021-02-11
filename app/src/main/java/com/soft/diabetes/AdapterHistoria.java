package com.soft.diabetes;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class AdapterHistoria extends RecyclerView.Adapter<AdapterHistoria.viewHolderHistoria> {

        ArrayList<DataHistoria> dataHistoria;

public AdapterHistoria(ArrayList<DataHistoria> data) {
        dataHistoria =  data;
        }

@NonNull
@Override
public viewHolderHistoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_historia,parent,false);
        viewHolderHistoria holder = new viewHolderHistoria(view);
        return holder;
        }

@Override
public void onBindViewHolder(@NonNull final viewHolderHistoria holder, int position) {

final DataHistoria ls = dataHistoria.get(position);



        holder.txtNombres.setText(ls.getNombres());
        holder.txtApellidos.setText(ls.getApellidos());
        holder.txtDNI.setText(ls.getDni());
        holder.txtResultado.setText(ls.getResultado());
        holder.txtPorcentaje.setText(ls.getPorcentaje());


        if ( holder.txtResultado.getText().toString().equals("Diabetes Tipo II")){
        holder.txtResultado.setTextColor(Color.parseColor("#2E86C1"));
        }

        if ( holder.txtResultado.getText().toString().equals("Diabetes Tipo I")){
        holder.txtResultado.setTextColor(Color.parseColor("#FFC300"));
        }

        if ( holder.txtResultado.getText().toString().equals("Sin diabetes")){
        holder.txtResultado.setTextColor(Color.parseColor("#5bbd00"));
        }

        }


public class viewHolderHistoria extends RecyclerView.ViewHolder {

    TextView txtNombres,txtApellidos,txtDNI,txtResultado,txtPorcentaje;

    public viewHolderHistoria(@NonNull View itemView) {
        super(itemView);

        txtNombres = itemView.findViewById(R.id.lsNombres);
        txtApellidos = itemView.findViewById(R.id.lsApellidos);
        txtDNI = itemView.findViewById(R.id.lsDNI);
        txtResultado = itemView.findViewById(R.id.lsResultado);
        txtPorcentaje = itemView.findViewById(R.id.lsPorcentaje);
    }
}


    @Override
    public int getItemCount() {
        return dataHistoria.size();
    }

}