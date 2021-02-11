package com.soft.diabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListaHistoria extends AppCompatActivity {

    DatabaseReference mDatabaseReference;
    ArrayList<DataHistoria> dataHistorias;
    RecyclerView recyclerView;
    SearchView search;
    AdapterHistoria adapterHistoria;
    private ProgressDialog mDialogActualizeData;
    private RecyclerView.LayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_historia);
        DesignToolbar.ShowToolbar(this,"Historial médico",true);

        mDialogActualizeData = new ProgressDialog(this,R.style.DialogHistoria);
        mDialogActualizeData.setCancelable(false);
        mDialogActualizeData.show();
        mDialogActualizeData.setContentView(R.layout.dialog_data);
        mDialogActualizeData.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        linearLayoutManager  = new LinearLayoutManager(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Historias");
        recyclerView = findViewById(R.id.recyclerHistoria);
        search= findViewById(R.id.buscarHistoria);
        search.setBackgroundColor(Color.WHITE);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataHistorias = new ArrayList<>();
        adapterHistoria = new AdapterHistoria(dataHistorias);
        recyclerView.setAdapter(adapterHistoria);
        recyclerView.setHasFixedSize(true);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    dataHistorias.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataHistoria pd = snapshot.getValue(DataHistoria.class);
                        dataHistorias.add(pd);
                    }

                    Collections.reverse(dataHistorias);
                    adapterHistoria.notifyDataSetChanged();
                    mDialogActualizeData.dismiss();
                }
                else {
                    Toast.makeText(ListaHistoria.this, "No hay historias!", Toast.LENGTH_SHORT).show();
                    mDialogActualizeData.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialogActualizeData.dismiss();
                Toast.makeText(ListaHistoria.this, "Error de base de datos", Toast.LENGTH_SHORT).show();
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String texto) {
                buscar(texto);
                return true;
            }
        });

    }

    private void buscar(String texto) {
        ArrayList<DataHistoria> lista = new ArrayList<>();
        for(DataHistoria object : dataHistorias){
            if (object.getDni().toLowerCase().contains(texto.toLowerCase()))
            {
                lista.add(object);
            }
            else {
            }
        }
        AdapterHistoria adapter = new AdapterHistoria(lista);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}