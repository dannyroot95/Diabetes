package com.soft.diabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button btnLogin , btnRegistrar;
    AuthProviders mAuth;
    TextInputEditText edtEmail,edtPassword;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DesignToolbar.ShowToolbar(this,"Iniciar sesión",false);
        mDialog             = new ProgressDialog(Login.this,R.style.ThemeOverlay);
        edtEmail            = findViewById(R.id.edtEmail);
        edtPassword         = findViewById(R.id.edtPassword);
        btnLogin            = findViewById(R.id.btnLogin);
        mAuth               = new AuthProviders();
        btnRegistrar        = findViewById(R.id.btnRegistrarUsuario);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegistrarUsuario.class));
            }
        });
    }

    private void login() {
        String email = edtEmail.getText().toString();
        String passsword = edtPassword.getText().toString();
        if(!email.isEmpty() && !passsword.isEmpty()){
            if (passsword.length()>=6)
            {
                mDialog.show();
                mDialog.setCancelable(false);
                mDialog.setMessage("Iniciando sesión...");

                mAuth.Login(email, passsword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            Intent intent = new Intent(Login.this, Menu.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(Login.this,  "Contraseña incorrecta ó Error de servidor!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        mDialog.dismiss();
                        Toast.makeText(Login.this,  "Contraseña incorrecta ó Error de servidor!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        String id = mAuth.getId();
        if (id !=null){
            startActivity(new Intent(Login.this, Menu.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else {
        }
    }

}