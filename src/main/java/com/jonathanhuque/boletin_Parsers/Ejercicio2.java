
package com.jonathanhuque.boletin_Parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ejercicio2 {

    public static void main(String[] args) {

        try {
            // Leer archivo XML
            InputStream input = Ejercicio1.class
                    .getResourceAsStream("/empleados.xml");

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(input);
            document.getDocumentElement().normalize();

            // Obtener empleados del XML
            NodeList nodos = document.getElementsByTagName("empleado");

            List<Empleado> empleados = new ArrayList<>();

            for (int i = 0; i < nodos.getLength(); i++) {

                Element elemento = (Element) nodos.item(i);

                String id = elemento.getAttribute("id");

                String nombre = elemento.getElementsByTagName("nombre")
                        .item(0).getTextContent();

                String departamento = elemento
                        .getElementsByTagName("departamento")
                        .item(0).getTextContent();

                Element salarioElemento = (Element) elemento
                        .getElementsByTagName("salario").item(0);

                double salario = Double.parseDouble(
                        salarioElemento.getTextContent().trim());

                String moneda = salarioElemento.getAttribute("moneda");

                LocalDate fechaAlta = LocalDate.parse(
                        elemento.getElementsByTagName("fechaAlta")
                                .item(0).getTextContent());

                Empleado empleado = new Empleado(
                        id, nombre, departamento,
                        moneda, salario, fechaAlta);

                empleados.add(empleado);
            }

            input.close();

            // Calcular empleados y salarios por departamento
            Map<String, Integer> cantidadEmpleados = new HashMap<>();
            Map<String, Double> sumaSalarios = new HashMap<>();

            for (Empleado empleado : empleados) {

                String departamento = empleado.getDepartamento();

                cantidadEmpleados.put(departamento,
                        cantidadEmpleados.getOrDefault(departamento, 0) + 1);

                sumaSalarios.put(departamento,
                        sumaSalarios.getOrDefault(departamento, 0.0)
                                + empleado.getSalario());
            }

            // Mostrar resultados
            System.out.printf("%-20s %-15s %-15s%n",
                    "Departamento", "Nº empleados", "Salario medio");

            System.out.println("------------------------------------------------");

            for (String departamento : cantidadEmpleados.keySet()) {

                int cantidad = cantidadEmpleados.get(departamento);

                double media = sumaSalarios.get(departamento) / cantidad;

                System.out.printf("%-20s %-15d %.2f EUR%n",
                        departamento, cantidad, media);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
