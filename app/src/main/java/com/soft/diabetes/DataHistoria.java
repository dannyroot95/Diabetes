package com.soft.diabetes;

import java.io.Serializable;

public class DataHistoria implements Serializable {

    String id;
    String nombres;
    String apellidos;
    String dni;
    String resultado;
    String porcentaje;

    public DataHistoria(){}


    public DataHistoria(String id, String nombres, String apellidos, String dni, String resultado, String porcentaje) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.resultado = resultado;
        this.porcentaje = porcentaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }
}
