
package com.jonathanhuque.boletin_Parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;

public class Ejercicio3 {

    public static void main(String[] args) {

        try {
            // Cargar archivo XML
            InputStream input = Ejercicio2.class
                    .getResourceAsStream("/biblioteca.xml");

            if (input == null) {
                throw new RuntimeException("No se encuentra biblioteca.xml");
            }

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(input);
            document.getDocumentElement().normalize();

            input.close();

            // Obtener todos los libros
            NodeList libros = document.getElementsByTagName("libro");

            // APARTADO A: Mostrar títulos
            System.out.println("TITULOS DE LOS LIBROS");
            System.out.println("--------------------");

            for (int i = 0; i < libros.getLength(); i++) {

                Element libro = (Element) libros.item(i);

                String titulo = libro
                        .getElementsByTagName("titulo")
                        .item(0)
                        .getTextContent();

                System.out.println(titulo);
            }

            // APARTADO B: Contar libros por género
            HashMap<String, Integer> contadorGeneros = new HashMap<>();

            for (int i = 0; i < libros.getLength(); i++) {

                Element libro = (Element) libros.item(i);

                NodeList generos = libro
                        .getElementsByTagName("genero");

                for (int j = 0; j < generos.getLength(); j++) {

                    String genero = generos.item(j).getTextContent();

                    contadorGeneros.put(
                            genero,
                            contadorGeneros.getOrDefault(genero, 0) + 1
                    );
                }
            }

            // Mostrar cantidad de libros por género
            System.out.println("\nLIBROS POR GENERO");
            System.out.println("--------------------");

            for (String genero : contadorGeneros.keySet()) {

                System.out.println(
                        genero + ": " + contadorGeneros.get(genero)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
