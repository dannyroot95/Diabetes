package com.soft.diabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    CardView crdDiagnostico , crdLogout, crdRegisro , crdListaHistoria;
    AuthProviders mAuth;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        DesignToolbar.ShowToolbar(this,"Menú principal",false);
        mDialog      = new ProgressDialog(this, R.style.ThemeOverlay);
        crdDiagnostico = findViewById(R.id.btnDiagnostico);
        crdLogout      = findViewById(R.id.btnCerrar);
        crdRegisro     = findViewById(R.id.btnRegistro);
        crdListaHistoria = findViewById(R.id.btnHistorial);
        mAuth          = new AuthProviders();

        crdDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                mDialog.setCancelable(false);
                mDialog.setMessage("Cargando...");
                startActivity(new Intent(Menu.this, Preguntas.class));
                finish();
            }
        });
        crdLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.Logout();
                startActivity(new Intent(Menu.this,Login.class));
                finish();
            }
        });
        crdRegisro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,RegistrarUsuario.class));
            }
        });
        crdListaHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,ListaHistoria.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}