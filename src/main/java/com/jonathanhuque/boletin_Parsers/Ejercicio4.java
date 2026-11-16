
package com.jonathanhuque.boletin_Parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Scanner;

public class Ejercicio4 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            // 1. Pedir ID del pedido
            System.out.println("Introduce el ID del pedido:");
            String idPedido = sc.nextLine();

            // 2. Cargar archivo XML
            InputStream input = Ejercicio4.class
                    .getResourceAsStream("/pedidos.xml");

            if (input == null) {
                throw new RuntimeException("No se encuentra pedidos.xml");
            }

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(input);
            document.getDocumentElement().normalize();

            input.close();

            // 3. Obtener todos los pedidos
            NodeList pedidos =
                    document.getElementsByTagName("pedido");

            boolean encontrado = false;

            // 4. Recorrer los pedidos
            for (int i = 0; i < pedidos.getLength(); i++) {

                Element pedido = (Element) pedidos.item(i);

                String id = pedido.getAttribute("id");

                // Comprobar si coincide el ID
                if (idPedido.equals(id)) {

                    encontrado = true;

                    System.out.println("\nPedido: " + id);

                    // Obtener nombre del cliente
                    String cliente = pedido
                            .getElementsByTagName("nombre")
                            .item(0).getTextContent();

                    System.out.println("Cliente: " + cliente);

                    System.out.println("\nARTICULOS");
                    System.out.println("-----------------------------");

                    // 5. Obtener artículos del pedido
                    NodeList items =
                            pedido.getElementsByTagName("item");

                    double totalCalculado = 0;

                    // 6. Recorrer los artículos
                    for (int j = 0; j < items.getLength(); j++) {

                        Element item = (Element) items.item(j);

                        String sku = item.getAttribute("sku");

                        String descripcion = item
                                .getElementsByTagName("descripcion")
                                .item(0).getTextContent();

                        int cantidad = Integer.parseInt(
                                item.getElementsByTagName("cantidad")
                                        .item(0).getTextContent()
                        );

                        double precio = Double.parseDouble(
                                item.getElementsByTagName("precioUnitario")
                                        .item(0).getTextContent()
                        );

                        // Calcular subtotal
                        double subtotal = cantidad * precio;

                        // Acumular subtotales
                        totalCalculado += subtotal;

                        // Mostrar información
                        System.out.println("SKU: " + sku);
                        System.out.println("Producto: " + descripcion);
                        System.out.println("Cantidad: " + cantidad);
                        System.out.println("Precio: " + precio + " EUR");
                        System.out.println("Subtotal: " + subtotal + " EUR");
                        System.out.println("-----------------------------");
                    }

                    // 7. Obtener total del XML
                    double totalXML = Double.parseDouble(
                            pedido.getElementsByTagName("total")
                                    .item(0).getTextContent()
                    );

                    System.out.println("Total calculado: "
                            + String.format("%.2f", totalCalculado) + " EUR");

                    System.out.println("Total XML: "
                            + String.format("%.2f", totalXML) + " EUR");

                    // 8. Comprobar si coinciden
                    if (Math.abs(totalCalculado - totalXML) < 0.01) {

                        System.out.println("\nEl total coincide.");

                    } else {

                        System.out.println("\nEl total NO coincide.");
                    }
                }
            }

            // Si no encontramos el pedido
            if (!encontrado) {
                System.out.println("No se ha encontrado el pedido: "
                        + idPedido);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
