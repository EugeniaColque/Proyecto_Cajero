
package testing;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase pruebaXML
 * Esta clase contiene un método principal que demuestra cómo leer un archivo XML y 
 * procesar los elementos de usuario contenidos en él.
 * 
 * @autor Eugenia
 */
public class pruebaXML {

    /**
     * Método principal que lee un archivo XML y procesa los elementos de usuario.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String args[]) {
        // Definir la ruta del archivo XML
        File archivoXML = new File(System.getProperty("user.dir") + "/src/usuarios/usuarios.xml");

        try {
            // Crear una fábrica y un constructor de documentos
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Parsear el archivo XML
            Document documentoXML = builder.parse(archivoXML);

            // Obtener una lista de todos los elementos "usuario"
            NodeList listaCuentas = documentoXML.getElementsByTagName("usuario");
            System.out.println("NroCuentas: " + listaCuentas.getLength());

            // Variables para el procesamiento de los nodos
            int numeroNodo = 0;
            String id = "";
            Element elemento = null;

            // Iterar a través de los nodos hasta encontrar el usuario con id "daniel87"
            while (!id.equals("daniel87") && numeroNodo < listaCuentas.getLength()) {
                Node nodo = listaCuentas.item(numeroNodo);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    elemento = (Element) nodo;
                    id = elemento.getAttribute("id");
                    System.out.println("Nodo procesado: " + id);
                }
                numeroNodo++;
            }

            // Mostrar el resultado de la búsqueda
            System.out.println("Se encontró el usuario: " + id);
            System.out.println("usuario: " + elemento.getElementsByTagName("divisa").item(0).getTextContent());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
