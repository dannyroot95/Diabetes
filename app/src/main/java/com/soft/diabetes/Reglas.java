package com.soft.diabetes;

public class Reglas extends Preguntas {

    public Reglas(){}

    public double cinturaMujeres(int indice){
        if (indice == 0){
            return  0.0;
        }
        else if (indice == 1){
            return  2.5;
        }
        else{
            return 5.0;
        }
    }

    public double edad(int indice){
        if (indice == 0){
            return  5.0;
        }
        else{
            return 2.5;
        }
    }

    public double nivel(int indice){
        if (indice == 0){
            return  5.0;
        }
        else if (indice == 1){
            return  2.5;
        }
        else{
            return 0.0;
        }
    }

    public double yesOrNot(int indice){
        if (indice == 0){
            return 5.0;
        }
        else{
            return 0.0;
        }
    }

}
