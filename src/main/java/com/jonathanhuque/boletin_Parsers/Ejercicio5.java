
package com.jonathanhuque.boletin_Parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Ejercicio5 {

    public static void main(String[] args) {

        try {

            // 1. Cargar el archivo JSON desde resources
            InputStream input = Ejercicio5.class
                    .getResourceAsStream("/alumnos.json");

            if (input == null) {
                throw new RuntimeException("No se encuentra alumnos.json");
            }

            // 2. Crear el lector
            InputStreamReader reader = new InputStreamReader(input);

            // 3. Leer el JSON como un array
            JsonArray alumnos = JsonParser.parseReader(reader)
                    .getAsJsonArray();

            // 4. Mostrar el listado
            System.out.println("LISTADO DE ALUMNOS");
            System.out.println("------------------------------");

            // 5. Recorrer todos los alumnos
            for (JsonElement elemento : alumnos) {

                // Convertir cada elemento en un objeto
                JsonObject alumno = elemento.getAsJsonObject();

                // Obtener nombre
                String nombre = alumno.get("nombre").getAsString();

                // Obtener fecha de nacimiento
                String fechaNacimiento = alumno
                        .get("fechaNacimiento")
                        .getAsString();

                // Mostrar información
                System.out.println(nombre + " - " + fechaNacimiento);
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
