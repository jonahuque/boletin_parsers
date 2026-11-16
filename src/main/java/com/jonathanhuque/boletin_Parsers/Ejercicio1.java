package com.jonathanhuque.boletin_Parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;
import com.jonathanhuque.boletin_Parsers.Empleado;

import static javax.management.Query.or;

public class Ejercicio1 {
    public static void main(String[] args) {
        try {
            InputStream input = Ejercicio1.class.getResourceAsStream("/empleados.xml");

            if (input == null) {
                throw new IOException("No se encuentra empleados.xml");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(input);

            document.getDocumentElement().normalize();

            // Aquí continúa tu procesamiento XML

            input.close();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    public static Empleado[] parse(String path) throws IOException,
            ParserConfigurationException, SAXException{
        Empleado[] empleados = null;
        try(FileInputStream fis = new FileInputStream(path)){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(fis);

            dom.getDocumentElement().normalize();

            NodeList items = dom.getElementsByTagName("empleado");
            empleados = new Empleado[items.getLength()];

            for (int i = 0; i < items.getLength(); i++) {
                Element empleadoEtiqueta = (Element) items.item(i);
                String id = empleadoEtiqueta.getAttribute("id");
                String nombre = empleadoEtiqueta.getElementsByTagName("nombre").item(0).getTextContent();
                String departamento = empleadoEtiqueta.getElementsByTagName("departamento").item(0).getTextContent();
                Element salarioElemento = (Element) empleadoEtiqueta.getElementsByTagName("salario").item(0);
                String moneda = salarioElemento.getAttribute("moneda");
                double salario = Double.parseDouble(salarioElemento.getTextContent());
                String fechaAlta = empleadoEtiqueta.getElementsByTagName("fechaAlta").item(0).getTextContent();
                LocalDate fecha = LocalDate.parse(fechaAlta);
                empleados[i] = new Empleado(id, nombre, departamento, moneda, salario, fecha);
            }

        }
        return empleados;
    }



}