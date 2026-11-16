
package com.jonathanhuque.boletin_Parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ejercicio6 {

    public static void main(String[] args) {

        try {
            // Cargar el archivo JSON
            InputStream input = Ejercicio6.class
                    .getResourceAsStream("/alumnos.json");

            if (input == null) {
                throw new RuntimeException("No se encuentra alumnos.json");
            }

            InputStreamReader reader = new InputStreamReader(input);

            JsonArray alumnosJson = JsonParser
                    .parseReader(reader)
                    .getAsJsonArray();

            // Crear lista de alumnos
            ArrayList<Alumno> alumnos = new ArrayList<>();

            // Recorrer los alumnos del JSON
            for (JsonElement elemento : alumnosJson) {

                JsonObject alumnoJson = elemento.getAsJsonObject();

                String id = alumnoJson.get("id").getAsString();
                String nombre = alumnoJson.get("nombre").getAsString();

                boolean matriculado = alumnoJson
                        .get("matriculado").getAsBoolean();

                String fechaNacimiento = alumnoJson
                        .get("fechaNacimiento").getAsString();

                // Obtener las notas del alumno
                JsonArray notasJson = alumnoJson
                        .get("notas").getAsJsonArray();

                Nota[] notas = new Nota[notasJson.size()];

                int i = 0;

                // Crear objetos Nota
                for (JsonElement notaElemento : notasJson) {

                    JsonObject notaJson = notaElemento.getAsJsonObject();

                    String asignatura = notaJson
                            .get("asignatura").getAsString();

                    double calificacion = notaJson
                            .get("nota").getAsDouble();

                    Nota nota = new Nota(asignatura, calificacion);

                    notas[i] = nota;
                    i++;
                }

                // Crear objeto Alumno
                Alumno alumno = new Alumno(
                        id,
                        nombre,
                        matriculado,
                        fechaNacimiento,
                        notas
                );

                alumnos.add(alumno);
            }

            reader.close();

            // APARTADO A: Nota más alta
            double notaMaxima = -1;
            String nombreAlumno = "";
            String asignaturaMaxima = "";

            for (Alumno alumno : alumnos) {

                for (Nota nota : alumno.getNotas()) {

                    double calificacion = nota.getNota();

                    if (calificacion > notaMaxima) {

                        notaMaxima = calificacion;
                        nombreAlumno = alumno.getNombre();
                        asignaturaMaxima = nota.getAsignatura();
                    }
                }
            }

            System.out.println("APARTADO A: NOTA MÁS ALTA");
            System.out.println("------------------------------");
            System.out.println("Alumno: " + nombreAlumno);
            System.out.println("Asignatura: " + asignaturaMaxima);
            System.out.println("Nota: " + notaMaxima);


            // APARTADO B: Media más alta
            double mediaMaxima = -1;
            String alumnoMejorMedia = "";

            for (Alumno alumno : alumnos) {

                double sumaNotas = 0;

                for (Nota nota : alumno.getNotas()) {

                    sumaNotas += nota.getNota();
                }

                double media = sumaNotas / alumno.getNotas().length;

                if (media > mediaMaxima) {

                    mediaMaxima = media;
                    alumnoMejorMedia = alumno.getNombre();
                }
            }

            System.out.println();
            System.out.println("APARTADO B: MEJOR MEDIA");
            System.out.println("------------------------------");
            System.out.println("Alumno: " + alumnoMejorMedia);
            System.out.printf("Media: %.2f%n", mediaMaxima);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
