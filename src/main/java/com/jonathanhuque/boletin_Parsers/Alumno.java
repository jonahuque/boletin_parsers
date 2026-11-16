package com.jonathanhuque.boletin_Parsers;

public class Alumno {

    private String id;
    private final String nombre;
    private boolean matriculado;
    private String fechaNacimiento;
    private Nota[] notas;

    public Alumno(String id, String nombre, boolean matriculado,
                  String fechaNacimiento, Nota[] notas) {

        this.id = id;
        this.nombre = nombre;
        this.matriculado = matriculado;
        this.fechaNacimiento = fechaNacimiento;
        this.notas = notas;
    }

    public String getNombre() {
        return nombre;
    }

    public Nota[] getNotas() {
        return notas;
    }
}