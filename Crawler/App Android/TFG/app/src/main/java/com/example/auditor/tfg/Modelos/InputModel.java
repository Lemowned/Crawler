package com.example.auditor.tfg.Modelos;



public class InputModel {

    // Modelo genérico que almacenan los parámetros para los formularios.

    private String id;
    private String name;

    public InputModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
