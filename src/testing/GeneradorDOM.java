
package testing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Clase GeneradorDOM
 * Esta clase se encarga de generar un documento XML utilizando DOM.
 * 
 * @autor Eugenia
 */
public class GeneradorDOM {
    
    private Document documentoXML;
    
    /**
     * Constructor de la clase GeneradorDOM.
     * Inicializa el objeto Document que representará el XML.
     * 
     * @throws Exception Si ocurre un error al crear el DocumentBuilder.
     */
    public GeneradorDOM() throws Exception{
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        documentoXML = builder.newDocument();
    }
    
    /**
     * Genera el contenido del documento XML.
     */
    public void generarDocumento() {
        Element alumnos = documentoXML.createElement("alumnos");
        documentoXML.appendChild(alumnos);
        
        Element alumno = documentoXML.createElement("alumno");
        alumnos.appendChild(alumno);
        alumno.setAttribute("codigo", "Al001");
        
        Element nombre = documentoXML.createElement("nombre");
        alumno.appendChild(nombre);
        nombre.appendChild(documentoXML.createTextNode("Harry"));
        
        Element apellido = documentoXML.createElement("apellido");
        alumno.appendChild(apellido);
        apellido.appendChild(documentoXML.createTextNode("Potter"));
        
        Element ci = documentoXML.createElement("Carnet");
        alumno.appendChild(ci);
        ci.appendChild(documentoXML.createTextNode("96789"));
    }
    
    /**
     * Genera el archivo XML a partir del contenido del documento.
     * 
     * @throws Exception Si ocurre un error durante la generación del XML.
     */
    public void generarXML() throws Exception{
        generarDocumento();
        Source origen = new DOMSource(documentoXML);
        File ruta = new File("./alumnos.xml");
        Result resultado = new StreamResult(new OutputStreamWriter(new FileOutputStream(ruta)));
        TransformerFactory factoria = TransformerFactory.newInstance();
        Transformer transformer = factoria.newTransformer();
        transformer.transform(origen, resultado);
    }
    
    /**
     * Método principal para ejecutar la generación del documento XML.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String args[]){
        GeneradorDOM documento;
        try {
            documento = new GeneradorDOM();
            documento.generarXML();
        } catch (Exception ex) {
            Logger.getLogger(GeneradorDOM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
