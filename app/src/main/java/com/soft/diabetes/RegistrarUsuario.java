package com.soft.diabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarUsuario extends AppCompatActivity {

    TextInputEditText txtNombres , txtApellidos, txtDni , txtCorreo , txtPass , txtRePass;
    AuthProviders mAuth;
    ProviderMedico userMedico;
    Button btnRegitrar;
    private ProgressDialog mDialog;
    FirebaseAuth mX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        DesignToolbar.ShowToolbar(this,"Registrar médico",false);
        txtNombres   = findViewById(R.id.edtNombres);
        txtApellidos = findViewById(R.id.edtApellidos);
        txtDni       = findViewById(R.id.edtDni);
        txtCorreo    = findViewById(R.id.edtMail);
        txtPass      = findViewById(R.id.edtPassword);
        txtRePass    = findViewById(R.id.edtRepeatPassword);
        btnRegitrar  = findViewById(R.id.btnRegister);
        mAuth        = new AuthProviders();
        userMedico   = new ProviderMedico();
        mX   = FirebaseAuth.getInstance();
        mDialog      = new ProgressDialog(this, R.style.ThemeOverlay);
        btnRegitrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegistro();
            }
        });
    }

    private void clickRegistro() {
        final String nombres        = txtNombres.getText().toString();
        final String apellidos      = txtApellidos.getText().toString();
        final String dni            = txtDni.getText().toString();
        final String email          = txtCorreo.getText().toString();
        final String password       = txtPass.getText().toString();
        final String rePassword     = txtRePass.getText().toString();

        if(!nombres.isEmpty() && !apellidos.isEmpty() && !dni.isEmpty() && !email.isEmpty()
                && !password.isEmpty() && !rePassword.isEmpty()) {

            if (password.length() >= 6) {
                if (password.equals(rePassword)) {
                    mDialog.show();
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Registrando usuario...");
                    registrar(nombres,apellidos,dni,email,password);
                }
                else{
                    mDialog.dismiss();
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                mDialog.dismiss();
                Toast.makeText(this, "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            mDialog.dismiss();
            Toast.makeText(this, "Complete todos los campos!", Toast.LENGTH_SHORT).show();
        }

    }

    private void registrar(final String nombres, final String apellidos, final String email, final String dni, String password) {

        String em = txtCorreo.getText().toString();
        String pass = txtPass.getText().toString();

        mX.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    UserMedico user = new UserMedico(id,nombres,apellidos,dni,email);
                    mapping(user);
                }
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(RegistrarUsuario.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });

    }

    void mapping(UserMedico user) {
            userMedico.MappingAData(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mDialog.dismiss();
                    startActivity(new Intent(RegistrarUsuario.this, Menu.class));
                    finish();
                    Toast.makeText(RegistrarUsuario.this, "Usuario Creado!", Toast.LENGTH_SHORT).show();
                } else {
                    mDialog.dismiss();
                    Toast.makeText(RegistrarUsuario.this, "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                }
            });
    }


}