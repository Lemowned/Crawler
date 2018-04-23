package com.example.auditor.tfg.Modelos;



public class FormModel {

    // Modelo para el objeto genérico que almacena los formularios

    private String action;
    private InputModel[] inputs;


    public FormModel(String action, InputModel[] inputs) {
        this.action = action;
        this.inputs = inputs;
    }

    public String getAction() {
        return action;
    }

    public InputModel[] getInputs() {
        return inputs;
    }
}
