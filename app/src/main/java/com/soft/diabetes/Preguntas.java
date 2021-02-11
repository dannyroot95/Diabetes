package com.soft.diabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Preguntas extends AppCompatActivity {

    RadioGroup radioGroupMedidas, radioGroupFamilia,
               radioGroupContaminación, radioGroupAmbiente,
               radioGroupColesterol, radioGroupFumar,
               radioGroupAzucar, radioGroupAlcohol,
               radioGroupEdad, radioGroupAgua,
               radioGroupSed, radioGroupHambre,
               radioGroupPerdida, radioGroupMiccion,
               radioGroupPies, radioGroupPiel,
               radioGroupHerida, radioGroupEmbarazo,radioGroupNivel;
    RadioButton radioButton;
    double result[] = new double[20];
    Reglas reglas;
    Button btnProcesar;
    TextView txtResultado;
    EditText edtPeso,edtAltura;
    private Dialog mDialog;
    private Dialog mDialogHistoria;
    TextView texto1 , texto2 , porcentaje;
    Button cerrar , registrar , cerrarHistoria , guardarHistoria;
    ImageView image;
    EditText edtNombres , edtApellidos , edtDni , edtResultado, edtPorcentaje;
    String diagnostico = "";
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReference              = FirebaseDatabase.getInstance().getReference();
        radioGroupEdad          = findViewById(R.id.radioGroupEdad);
        radioGroupMedidas       = findViewById(R.id.radioGroupMedidas);
        radioGroupFamilia       = findViewById(R.id.radioGroupFamilia);
        radioGroupContaminación = findViewById(R.id.radioGroupContaminacion);
        radioGroupAmbiente      = findViewById(R.id.radioGroupAmbiente);
        radioGroupColesterol    = findViewById(R.id.radioGroupColesterol);
        radioGroupFumar         = findViewById(R.id.radioGroupFumar);
        radioGroupAzucar        = findViewById(R.id.radioGroupAzucar);
        radioGroupAlcohol       = findViewById(R.id.radioGroupAlcohol);
        radioGroupAgua          = findViewById(R.id.radioGroupAgua);
        radioGroupSed           = findViewById(R.id.radioGroupSed);
        radioGroupHambre        = findViewById(R.id.radioGroupHambre);
        radioGroupPerdida       = findViewById(R.id.radioGroupPerdida);
        radioGroupMiccion       = findViewById(R.id.radioGroupMiccion);
        radioGroupPies          = findViewById(R.id.radioGroupPies);
        radioGroupPiel          = findViewById(R.id.radioGroupPiel);
        radioGroupHerida        = findViewById(R.id.radioGroupHerida);
        radioGroupEmbarazo      = findViewById(R.id.radioGroupEmbarazo);
        radioGroupNivel         = findViewById(R.id.radioGroupANivel);
        btnProcesar             = findViewById(R.id.btnProcesar);
        txtResultado            = findViewById(R.id.txtResultado);
        edtPeso                 = findViewById(R.id.edtPeso);
        edtAltura               = findViewById(R.id.edtAltura);
        reglas                  = new Reglas();
        mDialog                 = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_analisis);
        texto1                  = (TextView)mDialog.findViewById(R.id.txtTexto);
        texto2                  = (TextView)mDialog.findViewById(R.id.txtResultado);
        porcentaje              = (TextView)mDialog.findViewById(R.id.txtPorcentaje);
        cerrar                  = (Button)mDialog.findViewById(R.id.btnClose);
        registrar               = (Button)mDialog.findViewById(R.id.btnHistoria);
        image                   = (ImageView)mDialog.findViewById(R.id.imageState);
        mDialogHistoria         = new Dialog(this);
        mDialogHistoria.setContentView(R.layout.historia);
        edtNombres              = (EditText)mDialogHistoria.findViewById(R.id.edtHistoriaNombre);
        edtApellidos            = (EditText)mDialogHistoria.findViewById(R.id.edtHistoriaApellidos);
        edtDni                  = (EditText)mDialogHistoria.findViewById(R.id.edtHistoriaDni);
        edtResultado            = (EditText)mDialogHistoria.findViewById(R.id.edtHistoriaResultado);
        edtResultado.setEnabled(false);
        edtPorcentaje           = (EditText)mDialogHistoria.findViewById(R.id.edtHistoriaPorcentaje);
        edtPorcentaje.setEnabled(false);
        cerrarHistoria          = (Button)mDialogHistoria.findViewById(R.id.btnCerrarHistoria);
        guardarHistoria         = (Button)mDialogHistoria.findViewById(R.id.btnGuardar);


        Button buttonApply      = findViewById(R.id.button_apply);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (    radioGroupEdad.getCheckedRadioButtonId() != -1 && radioGroupMedidas.getCheckedRadioButtonId() != -1
                        & radioGroupFamilia.getCheckedRadioButtonId() != -1 && radioGroupContaminación.getCheckedRadioButtonId() != -1
                        & radioGroupAmbiente.getCheckedRadioButtonId() != -1 && radioGroupColesterol.getCheckedRadioButtonId() != -1
                        & radioGroupFumar.getCheckedRadioButtonId() != -1 && radioGroupAzucar.getCheckedRadioButtonId() != -1
                        & radioGroupAlcohol.getCheckedRadioButtonId() != -1 && radioGroupAgua.getCheckedRadioButtonId() != -1
                        & radioGroupSed.getCheckedRadioButtonId() != -1 && radioGroupHambre.getCheckedRadioButtonId() != -1
                        & radioGroupPerdida.getCheckedRadioButtonId() != -1 && radioGroupMiccion.getCheckedRadioButtonId() != -1
                        & radioGroupPies.getCheckedRadioButtonId() != -1 && radioGroupPiel.getCheckedRadioButtonId() != -1
                        & radioGroupHerida.getCheckedRadioButtonId() != -1 && radioGroupEmbarazo.getCheckedRadioButtonId() != -1
                        & radioGroupNivel.getCheckedRadioButtonId() != -1 && !txtResultado.getText().toString().equals("")){

                    double total = 0.0;
                    for (int contador = 0; contador < result.length; contador++) {
                        total += result[contador];
                    }
                    String diagnosys = String.valueOf(total);
                    if(total < 32.0){
                        diagnostico = "Sin diabetes";
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialog.setCancelable(false);
                        mDialog.show();
                        texto1.setText("No tiene algún indicio de diabetes");
                        texto2.setText("Usted no tiene diabetes");
                        porcentaje.setText("");
                        image.setImageResource(R.drawable.sano);
                        cerrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                        registrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialogHistoria();
                            }
                        });
                    }
                    else if (total > 32.0 && total < 70.0)
                    {
                        diagnostico = "Diabetes Tipo I";
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialog.setCancelable(false);
                        mDialog.show();
                        texto1.setText("Tiene indicio de padecer diabetes");
                        texto2.setText("Un ");
                        porcentaje.setText(diagnosys+"%");
                        image.setImageResource(R.drawable.tipo1);
                        cerrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                        registrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialogHistoria();
                            }
                        });
                    }
                    else {
                        diagnostico = "Diabetes Tipo II";
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialog.setCancelable(false);
                        mDialog.show();
                        texto1.setText("Tiene indicio de padecer diabetes");
                        texto2.setText("Un ");
                        porcentaje.setText(diagnosys+"%");
                        image.setImageResource(R.drawable.tipo2);
                        cerrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                        registrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialogHistoria();
                            }
                        });
                    }
                    
                }
                else {
                    Toast.makeText(Preguntas.this, "Complete todo el formulario!", Toast.LENGTH_SHORT).show();
                }

                
            }
        });
        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarEstadoPeso();
            }
        });
        cleanAllGroupQuestions();
    }

    public void checkRadioEdad(View v) {
        int radioId = radioGroupEdad.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        View radioButtonView = radioGroupEdad.findViewById(radioId);
        int indice = radioGroupEdad.indexOfChild(radioButtonView);
        result [0] = reglas.edad(indice);
    }

    private void verificarEstadoPeso(){
        String stPeso = edtPeso.getText().toString();
        String stAltura = edtAltura.getText().toString();

        if (!stPeso.isEmpty() && !stAltura.isEmpty()){

            int peso = Integer.parseInt(stPeso);
            double altura = Double.parseDouble(stAltura);
            double div = peso/(altura*altura);

            if (div >= 30.0){
                txtResultado.setText("Obsesidad");
                txtResultado.setTextColor(Color.parseColor("#fc0000"));
                result [1] = 15.0;
            }
            else if (div >= 25.0 && div <= 29.9){
                txtResultado.setText("Sobre peso");
                txtResultado.setTextColor(Color.parseColor("#D4AC0D"));
                result [1] = 5.0;
            }
            else {
                txtResultado.setText("Peso normal");
                txtResultado.setTextColor(Color.parseColor("#239B56"));
                result [1] = 0.0;
            }
        }
        else {
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkRadioMedida(View v) {
        int radioId = radioGroupMedidas.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        View radioButtonView = radioGroupMedidas.findViewById(radioId);
        int indice = radioGroupMedidas.indexOfChild(radioButtonView);
        result [2] = reglas.cinturaMujeres(indice);
    }

    public void checkRadioFamilia(View v) {
        int radioId = radioGroupFamilia.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        View radioButtonView = radioGroupFamilia.findViewById(radioId);
        int indice = radioGroupFamilia.indexOfChild(radioButtonView);
        result[3] = reglas.yesOrNot(indice);
    }

    public void checkRadioContaminacion(View v){
        int radioId = radioGroupContaminación.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        View radioButtonView = radioGroupContaminación.findViewById(radioId);
        int indice = radioGroupContaminación.indexOfChild(radioButtonView);
        result [4] = reglas.yesOrNot(indice);
    }

    public void checkRadioAmbiente(View v){
        int radioId = radioGroupAmbiente.getCheckedRadioButtonId();
        View radioButtonView = radioGroupAmbiente.findViewById(radioId);
        int indice = radioGroupAmbiente.indexOfChild(radioButtonView);
        result [5] = reglas.yesOrNot(indice);
    }

    public void checkRadioColesterol(View v){
        int radioId = radioGroupColesterol.getCheckedRadioButtonId();
        View radioButtonView = radioGroupColesterol.findViewById(radioId);
        int indice = radioGroupColesterol.indexOfChild(radioButtonView);
        result [6] = reglas.yesOrNot(indice);
    }

    public void checkRadioFumar(View v){
        int radioId = radioGroupFumar.getCheckedRadioButtonId();
        View radioButtonView = radioGroupFumar.findViewById(radioId);
        int indice = radioGroupFumar.indexOfChild(radioButtonView);
        result [7] = reglas.yesOrNot(indice);
    }

    public void checkRadioAzucar(View v){
        int radioId = radioGroupAzucar.getCheckedRadioButtonId();
        View radioButtonView = radioGroupAzucar.findViewById(radioId);
        int indice = radioGroupAzucar.indexOfChild(radioButtonView);
        result [8] = reglas.yesOrNot(indice);
    }

    public void checkRadioAlcohol(View v){
        int radioId = radioGroupAlcohol.getCheckedRadioButtonId();
        View radioButtonView = radioGroupAlcohol.findViewById(radioId);
        int indice = radioGroupAlcohol.indexOfChild(radioButtonView);
        result [9] = reglas.yesOrNot(indice);
    }

    public void checkRadioAgua(View v){
        int radioId = radioGroupAgua.getCheckedRadioButtonId();
        View radioButtonView = radioGroupAgua.findViewById(radioId);
        int indice = radioGroupAgua.indexOfChild(radioButtonView);
        result [10] = reglas.yesOrNot(indice);
    }

    public void checkRadioSed(View v){
        int radioId = radioGroupSed.getCheckedRadioButtonId();
        View radioButtonView = radioGroupSed.findViewById(radioId);
        int indice = radioGroupSed.indexOfChild(radioButtonView);
        result [11] = reglas.yesOrNot(indice);
    }

    public void checkRadioHambre(View v){
        int radioId = radioGroupHambre.getCheckedRadioButtonId();
        View radioButtonView = radioGroupHambre.findViewById(radioId);
        int indice = radioGroupHambre.indexOfChild(radioButtonView);
        result [12] = reglas.yesOrNot(indice);
    }

    public void checkRadioPerdida(View v){
        int radioId = radioGroupPerdida.getCheckedRadioButtonId();
        View radioButtonView = radioGroupPerdida.findViewById(radioId);
        int indice = radioGroupPerdida.indexOfChild(radioButtonView);
        result [13] = reglas.yesOrNot(indice);
    }

    public void checkRadioMiccion(View v){
        int radioId = radioGroupMiccion.getCheckedRadioButtonId();
        View radioButtonView = radioGroupMiccion.findViewById(radioId);
        int indice = radioGroupMiccion.indexOfChild(radioButtonView);
        result [14] = reglas.yesOrNot(indice);
    }

    public void checkRadioPies(View v){
        int radioId = radioGroupPies.getCheckedRadioButtonId();
        View radioButtonView = radioGroupPies.findViewById(radioId);
        int indice = radioGroupPies.indexOfChild(radioButtonView);
        result [15] = reglas.yesOrNot(indice);
    }

    public void checkRadioPiel(View v){
        int radioId = radioGroupPiel.getCheckedRadioButtonId();
        View radioButtonView = radioGroupPiel.findViewById(radioId);
        int indice = radioGroupPiel.indexOfChild(radioButtonView);
        result [16] = reglas.yesOrNot(indice);
    }

    public void checkRadioHeridas(View v){
        int radioId = radioGroupHerida.getCheckedRadioButtonId();
        View radioButtonView = radioGroupHerida.findViewById(radioId);
        int indice = radioGroupHerida.indexOfChild(radioButtonView);
        result [17] = reglas.yesOrNot(indice);
    }

    public void checkRadioEmbarazo(View v){
        int radioId = radioGroupEmbarazo.getCheckedRadioButtonId();
        View radioButtonView = radioGroupEmbarazo.findViewById(radioId);
        int indice = radioGroupEmbarazo.indexOfChild(radioButtonView);
        result [18] = reglas.yesOrNot(indice);
    }

    public void checkRadioNivel(View v){
        int radioId = radioGroupNivel.getCheckedRadioButtonId();
        View radioButtonView = radioGroupNivel.findViewById(radioId);
        int indice = radioGroupNivel.indexOfChild(radioButtonView);
        result [19] = reglas.nivel(indice);
    }

    private void cleanAllGroupQuestions(){
        radioGroupEdad.clearCheck();
        radioGroupMedidas.clearCheck();
        radioGroupFamilia.clearCheck();
        radioGroupContaminación.clearCheck();
        radioGroupAmbiente.clearCheck();
        radioGroupColesterol.clearCheck();
        radioGroupFumar.clearCheck();
        radioGroupAzucar.clearCheck();
        radioGroupAlcohol.clearCheck();
        radioGroupAgua.clearCheck();
        radioGroupSed.clearCheck();
        radioGroupHambre.clearCheck();
        radioGroupPerdida.clearCheck();
        radioGroupMiccion.clearCheck();
        radioGroupPies.clearCheck();
        radioGroupPiel.clearCheck();
        radioGroupHerida.clearCheck();
        radioGroupEmbarazo.clearCheck();
        radioGroupNivel.clearCheck();
    }

    private void showDialogHistoria(){
        mDialogHistoria.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogHistoria.setCancelable(false);
        mDialogHistoria.show();
        edtResultado.setText(diagnostico);
        edtPorcentaje.setText(porcentaje.getText().toString());
        cerrarHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogHistoria.dismiss();
            }
        });
        guardarHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarHistoria();
            }
        });
    }

    private void registrarHistoria(){

        String nombres = edtNombres.getText().toString();
        String apellidos = edtApellidos.getText().toString();
        String dni = edtDni.getText().toString();
        String porcentaje = edtPorcentaje.getText().toString();

        if (!nombres.isEmpty() && !apellidos.isEmpty() && !dni.isEmpty() && !porcentaje.isEmpty() && !diagnostico.equals(""))

        {
            Map<String,Object> map = new HashMap<>();
            map.put("nombres",nombres);
            map.put("apellidos",apellidos);
            map.put("dni",dni);
            map.put("resultado",diagnostico);
            map.put("porcentaje",porcentaje);
            mReference.child("Historias").push().setValue(map);
            mDialogHistoria.dismiss();
            mDialog.dismiss();
            Toast.makeText(this, "Registrado!", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this, "Complete los campos!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Preguntas.this,Menu.class));
        finish();
    }
}