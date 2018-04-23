package com.example.auditor.tfg.Modelos;



public class WordModel {

    // Modelo que almacena las tuplas palbra y numero de repeticiones de la palabra.

    private String num;
    private String word;

    public WordModel(String num, String word) {
        this.num = num;
        this.word = word;
    }

    public String getNum() {
        return num;
    }

    public String getWord() {
        return word;
    }
}
