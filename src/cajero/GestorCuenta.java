
package cajero;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import cajero.Usuario;
import java.util.ArrayList;
import java.util.List;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase GestorCuenta: Maneja la creación y gestión de cuentas de usuario, así como los eventos asociados a las cuentas.
 * @author Eugenia
 */
public class GestorCuenta {

    // Directorio donde se almacenan los archivos de usuarios y logs
    private File directorioUsuarios;
    private File directorioLog;
    
    // Documento XML utilizado para almacenar los datos de los usuarios
    private Document documentoXML;
    
    // Elemento que representa el perfil del usuario actual
    private Element perfilUsuario;
    
    // Elemento que representa la cuenta actual
    private Element cuentaActual;

    /**
     * Constructor de la clase GestorCuenta.
     * Inicializa los directorios y verifica si existen, en caso contrario, los crea.
     */
    public GestorCuenta() {
        directorioUsuarios = new File(System.getProperty("user.dir") + "/src/usuarios/");
        directorioLog = new File(System.getProperty("user.dir") + "/src/logUsuarios/");
        if (!directorioUsuarios.exists()) {
            directorioUsuarios.mkdir();
        }
    }

    /**
     * Método para crear un evento y almacenarlo en el archivo de logs.
     * @param evento El evento a crear y almacenar.
     */
    public void crearEvento(Evento evento) {
        try {
            File directorio = new File(directorioLog + "/logs.xml");
            escribirDatosEvento(evento, directorio);
        } catch (IOException ex) {
            Logger.getLogger(GestorCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para escribir los datos de un evento en el archivo de logs.
     * @param evento El evento a escribir.
     * @param ruta La ruta del archivo de logs.
     * @throws IOException Si ocurre un error de E/S.
     */
    private void escribirDatosEvento(Evento evento, File ruta) throws IOException {
        // Si el archivo no existe, se genera uno nuevo y se agrega el evento
        if (!ruta.exists()) {
            generarDocumento(evento, ruta);
        } else {
            // Si el archivo existe, se añade el evento al archivo existente
            anadirEvento(evento, ruta);
        }
    }
    
    /**
     * Método para añadir un evento al archivo de logs existente.
     * @param evento El evento a añadir.
     * @param ruta La ruta del archivo de logs.
     */
    private void anadirEvento(Evento evento, File ruta) {
        Document documento = getDocumento(ruta);
        Node eventos = documento.getElementsByTagName("logs").item(0);
        eventos.appendChild(escribirDatosEvento(evento, documento));
        guardarCambiosEnXML(new File(directorioLog + "/logs.xml"), documento);
    }

    /**
     * Método para generar un nuevo documento XML y escribir en él los datos del evento.
     * @param evento El evento a escribir.
     * @param ruta La ruta del archivo de logs.
     */
    private void generarDocumento(Evento evento, File ruta) {
        Document documento = generarNuevoDocumento();
        Element logs = documento.createElement("logs");
        documento.appendChild(logs);
        logs.appendChild(escribirDatosEvento(evento, documento));
        guardarCambiosEnXML(ruta, documento);

    }

    /**
     * Método para escribir los datos de un evento en un elemento XML.
     * @param evento El evento a escribir.
     * @param documento El documento XML.
     * @return El elemento que representa el evento.
     */
    private Element escribirDatosEvento(Evento evento, Document documento) {
        Element log = documento.createElement("log");

        Element nroCuenta = documento.createElement("nroCuenta");
        nroCuenta.appendChild(documento.createTextNode(evento.getNroCuenta()));
        log.appendChild(nroCuenta);

        Element fecha = documento.createElement("fecha");
        fecha.appendChild(documento.createTextNode(evento.getFecha()));
        log.appendChild(fecha);

        Element descripcion = documento.createElement("descripcion");
        descripcion.appendChild(documento.createTextNode(evento.getDescripcion()));
        log.appendChild(descripcion);

        Element monto = documento.createElement("monto");
        monto.appendChild(documento.createTextNode(evento.getMonto()));
        log.appendChild(monto);

        Element saldo = documento.createElement("saldo");
        saldo.appendChild(documento.createTextNode(evento.getSaldo()));
        log.appendChild(saldo);

        return log;
    }

    /**
     * Método para añadir una cuenta al perfil de usuario.
     * @param divisa La divisa de la cuenta.
     * @param nroCuenta El número de cuenta.
     * @param montoInicial El monto inicial de la cuenta.
     */
    public void anadirCuenta(String divisa, String nroCuenta, String montoInicial) {
        Element cuenta = documentoXML.createElement("cuenta");

        Element numeroCuenta = documentoXML.createElement("nroCuenta");
        numeroCuenta.appendChild(documentoXML.createTextNode(nroCuenta));
        cuenta.appendChild(numeroCuenta);

        Element div = documentoXML.createElement("divisa");
        div.appendChild(documentoXML.createTextNode(divisa));
        cuenta.appendChild(div);

        Element monto = documentoXML.createElement("monto");
        monto.appendChild(documentoXML.createTextNode(montoInicial));
        cuenta.appendChild(monto);

        perfilUsuario.appendChild(cuenta);
        guardarCambiosEnXML(new File(directorioUsuarios + "/usuarios.xml"), documentoXML);
    }

    public List<Evento> getEventos() {
        Document documento = getDocumento(new File(directorioLog + "/logs.xml"));
        NodeList logs = documento.getElementsByTagName("log");
        List<Evento> listaLogs = new ArrayList<Evento>();
        for (int i = 0; i < logs.getLength(); i++) {
            Node nodoLog = logs.item(i);
            Element log = (Element) nodoLog;
            String nroCuenta = log.getElementsByTagName("nroCuenta").item(0).getTextContent();
            if (nroCuenta.equals(getNroCuenta())) {
                String fecha = log.getElementsByTagName("fecha").item(0).getTextContent();
                String descripcion = log.getElementsByTagName("descripcion").item(0).getTextContent();
                String monto = log.getElementsByTagName("monto").item(0).getTextContent();
                String saldo = log.getElementsByTagName("saldo").item(0).getTextContent();
                listaLogs.add(new Evento(getNroCuenta(), descripcion, monto, saldo));
            }
        }

        return listaLogs;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void crearCuenta(Usuario usuario) {

        try {
            File directorio = new File(directorioUsuarios + "/usuarios.xml");
            escribirDatosUsuario(usuario, directorio);
        } catch (IOException ex) {
            Logger.getLogger(GestorCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void escribirDatosUsuario(Usuario usuario, File ruta) throws IOException {
        //en caso el fichero no exista
        if (!ruta.exists()) {
            generarDocumento(usuario, ruta);

        } else {
            anadirUsuario(usuario, ruta);
        }
    }
    
    private void anadirUsuario(Usuario usuario, File ruta) {
        Document documento = getDocumento(ruta);
        Node usuarios = documento.getElementsByTagName("usuarios").item(0);
        usuarios.appendChild(escribirDatosUsuario(usuario, documento));
        guardarCambiosEnXML(ruta, documento);

    }

    private void generarDocumento(Usuario usuario, File ruta) {
        Document documento = generarNuevoDocumento();
        Element usuarios = documento.createElement("usuarios");
        documento.appendChild(usuarios);
        usuarios.appendChild(escribirDatosUsuario(usuario, documento));
        guardarCambiosEnXML(ruta, documento);

    }

    private Document getDocumento(File archivoXML) {
        Document document = null;
        try {
            if (archivoXML.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(archivoXML);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    private Document generarNuevoDocumento() {
        Document nuevoDocumento = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            nuevoDocumento = builder.newDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuevoDocumento;
    }

    private Element escribirDatosUsuario(Usuario cuentaUsuario, Document documento) {

        Element usuario = documento.createElement("usuario");
        usuario.setAttribute("id", cuentaUsuario.getNombreUsuario());

        Element nombreTitular = documento.createElement("nombreTitular");
        nombreTitular.appendChild(documento.createTextNode(cuentaUsuario.getNombreTitular()));
        usuario.appendChild(nombreTitular);

        Element contrasena = documento.createElement("contrasena");
        contrasena.appendChild(documento.createTextNode(Seguridad.cifrar(cuentaUsuario.getContrasena())));
        usuario.appendChild(contrasena);

        Element cuenta = documento.createElement("cuenta");
        usuario.appendChild(cuenta);

        Element nroCuenta = documento.createElement("nroCuenta");
        nroCuenta.appendChild(documento.createTextNode(cuentaUsuario.getNumeroCuenta()));
        cuenta.appendChild(nroCuenta);

        Element divisa = documento.createElement("divisa");
        divisa.appendChild(documento.createTextNode(cuentaUsuario.getTipoDivisa()));
        cuenta.appendChild(divisa);

        Element monto = documento.createElement("monto");
        monto.appendChild(documento.createTextNode(String.valueOf(cuentaUsuario.getMonto())));
        cuenta.appendChild(monto);

        return usuario;
    }

    private void guardarCambiosEnXML(File ruta, Document documento) {

        try {
            Source origen = new DOMSource(documento);
            Result resultado = new StreamResult(new OutputStreamWriter(new FileOutputStream(ruta)));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(origen, resultado);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
     * Método para generar un número de cuenta aleatorio.
     * @return El número de cuenta generado.
     */
    public int generarNumeroDeCuenta() {
        SecureRandom sr = null;
        int min = 20000000;
        int max = 30000000;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(GestorCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sr.nextInt(max - min) + min;
    }

    /**
     * Método para verificar si un usuario existe.
     * @param nombreUsuario El nombre del usuario a verificar.
     * @return true si el usuario existe, false de lo contrario.
     */
    public boolean existeUsuario(String nombreUsuario) {
        Document documentoXML = getDocumento(new File(directorioUsuarios + "/usuarios.xml"));
        String id = "";
        if (documentoXML != null) {
            NodeList listaCuentas = documentoXML.getElementsByTagName("usuario");
            int numeroNodo = 0;
            while (!id.equals(nombreUsuario) && numeroNodo < listaCuentas.getLength()) {
                Node nodo = listaCuentas.item(numeroNodo);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    id = elemento.getAttribute("id");
                }
                numeroNodo++;
            }
        }
        return id.equals(nombreUsuario);
    }

    /**
     * Método para verificar si la contraseña coincide con la almacenada.
     * @param contrasena La contraseña a verificar.
     * @return true si la contraseña coincide, false de lo contrario.
     */
    public boolean contrasenaCoincide(char[] contrasena) {
        String contrasenaEncriptada = perfilUsuario.getElementsByTagName("contrasena").item(0).getTextContent();
        String contrasenaActual= Seguridad.descifrar(contrasenaEncriptada);
        String nuevaContrasena = String.copyValueOf(contrasena);
        return contrasenasCoinciden(nuevaContrasena, contrasenaActual);
    }

    /**
     * Método para verificar si dos contraseñas coinciden.
     * @param nuevaContrasena La nueva contraseña.
     * @param contrasenaActual La contraseña actual.
     * @return true si las contraseñas coinciden, false de lo contrario.
     */
    public boolean contrasenasCoinciden(String nuevaContrasena, String contrasenaActual) {
        return nuevaContrasena.equals(contrasenaActual);
    }

    /**
     * Método para obtener el saldo disponible en la cuenta actual.
     * @return El saldo disponible.
     */
    public String saldoDisponible() {
        return cuentaActual.getElementsByTagName("monto").item(0).getTextContent();
    }

    /**
     * Método para depositar dinero en la cuenta actual.
     * @param divisa La divisa del monto a depositar.
     * @param montoDeposito El monto a depositar.
     */
    public void depositar(String divisa, double montoDeposito) {
        double montoADepositar = getMontoConvertido(divisa, montoDeposito);
        depositar(montoADepositar, getNroCuenta());
    }

    /**
     * Método para depositar dinero en una cuenta específica.
     * @param divisa La divisa del monto a depositar.
     * @param montoDeposito El monto a depositar.
     * @param nroCuenta El número de cuenta destino.
     */
    public void depositar(String divisa, double montoDeposito, String nroCuenta) {
        double montoADepositar = getMontoConvertido(divisa, montoDeposito, nroCuenta);
        depositar(montoADepositar, nroCuenta);
    }

    /**
     * Método privado para depositar dinero en una cuenta.
     * @param montoADepositar El monto a depositar.
     * @param nroCuenta El número de cuenta destino.
     */
    private void depositar(double montoADepositar, String nroCuenta) {// solo deposito
        NodeList cuentas = perfilUsuario.getElementsByTagName("cuenta");

        for (int i = 0; i < cuentas.getLength(); i++) {
            Element cuenta = (Element) cuentas.item(i);
            String nroCuentaAEncontrar = cuenta.getElementsByTagName("nroCuenta").item(0).getTextContent();
            if (nroCuentaAEncontrar.equals(nroCuenta)) {
                Node montoNode = cuenta.getElementsByTagName("monto").item(0);
                double montoExistente = Double.parseDouble(montoNode.getTextContent());
                montoExistente += montoADepositar;
                montoNode.setTextContent(String.valueOf(montoExistente)); // Actualiza el nodo monto
            }
        }
        guardarCambiosEnXML(new File(directorioUsuarios + "/usuarios.xml"), documentoXML);

    }

    public void retirar(String divisa, double montoRetiro) {
        double montoARetirar = getMontoConvertido(divisa, montoRetiro);
        retirar(montoARetirar);
    }

    private void retirar(double montoARetirar) {
        NodeList cuentas = perfilUsuario.getElementsByTagName("cuenta");

        for (int i = 0; i < cuentas.getLength(); i++) {
            Element cuenta = (Element) cuentas.item(i);
            String nroCuentaAEncontrar = cuenta.getElementsByTagName("nroCuenta").item(0).getTextContent();
            if (nroCuentaAEncontrar.equals(getNroCuenta())) {
                Node montoNode = cuenta.getElementsByTagName("monto").item(0);
                double montoExistente = Double.parseDouble(montoNode.getTextContent());
                if (montoExistente >= montoARetirar) {
                    montoExistente -= montoARetirar;
                    montoNode.setTextContent(String.valueOf(montoExistente));
                }
            }
        }
        guardarCambiosEnXML(new File(directorioUsuarios + "/usuarios.xml"), documentoXML);
    }

    public void transferir(String divisa, double montoATransferir, String numeroCuentaDestino) {
        retirar(divisa, montoATransferir);
        perfilUsuario = getUsuario(numeroCuentaDestino);
        depositar(divisa, montoATransferir, numeroCuentaDestino);
        guardarCambiosEnXML(new File(directorioUsuarios + "/usuarios.xml"), documentoXML);
        perfilUsuario = getUsuario(getNroCuenta());
    }

    private String getDivisa(String numeroCuenta) {
        NodeList listaUsuarios = documentoXML.getElementsByTagName("usuario");

        String divisa = "";
        String nroCuenta = "";
        for (int i = 0; !nroCuenta.equals(numeroCuenta) && i < listaUsuarios.getLength(); i++) {
            Node nodo = listaUsuarios.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element usuario = (Element) nodo;
                NodeList listaCuentas = usuario.getElementsByTagName("cuenta");
                for (int j = 0; !nroCuenta.equals(numeroCuenta) && j < listaCuentas.getLength(); j++) {
                    Element cuenta = (Element) listaCuentas.item(j);
                    nroCuenta = cuenta.getElementsByTagName("nroCuenta").item(0).getTextContent();
                    divisa = cuenta.getElementsByTagName("divisa").item(0).getTextContent();
                }
            }
        }
        return divisa;
    }

    private Element getUsuario(String numeroCuenta) {//devuelve etiqueta usuario dado nroCuenta.
        NodeList listaUsuarios = documentoXML.getElementsByTagName("usuario");

        Element usuario = null;
        String nroCuenta = "";
        for (int i = 0; !nroCuenta.equals(numeroCuenta) && i < listaUsuarios.getLength(); i++) {
            Node nodo = listaUsuarios.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                usuario = (Element) nodo;
                NodeList listaCuentas = usuario.getElementsByTagName("cuenta");
                for (int j = 0; !nroCuenta.equals(numeroCuenta) && j < listaCuentas.getLength(); j++) {
                    Element cuenta = (Element) listaCuentas.item(j);
                    nroCuenta = cuenta.getElementsByTagName("nroCuenta").item(0).getTextContent();
                }
            }
        }
        return usuario;
    }

    public double getTasaCambio(String divisaOrigen, String divisaDestino) {
        double tasaCambio = 1;
        if (!divisaOrigen.equals(divisaDestino)) {
            if (divisaOrigen.equals("bolivianos") && divisaDestino.equals("dolares")) {
                tasaCambio = 0.14;
            } else if (divisaOrigen.equals("bolivianos") && divisaDestino.equals("euros")) {
                tasaCambio = 0.13;
            } else if (divisaOrigen.equals("dolares") && divisaDestino.equals("bolivianos")) {
                tasaCambio = 6.96;
            } else if (divisaOrigen.equals("dolares") && divisaDestino.equals("euros")) {
                tasaCambio = 0.93;
            } else if (divisaOrigen.equals("euros") && divisaDestino.equals("dolares")) {
                tasaCambio = 1.08;
            } else if (divisaOrigen.equals("euros") && divisaDestino.equals("bolivianos")) {
                tasaCambio = 7.44;
            }
        }
        return tasaCambio;
    }

    public boolean cambiarContrasena(char[] nuevaCont, char[] confirmacionCont) {
        boolean contrasenasCoinciden = contrasenasCoinciden(String.copyValueOf(nuevaCont), String.copyValueOf(confirmacionCont));
        if (contrasenasCoinciden) {
            perfilUsuario.getElementsByTagName("contrasena").item(0).setTextContent(Seguridad.cifrar(String.copyValueOf(nuevaCont)));
            guardarCambiosEnXML(new File(directorioUsuarios + "/usuarios.xml"), documentoXML);
        }
        return contrasenasCoinciden;
    }

    public void extraerCuentaUsuario(String nombreUsuario) {
        documentoXML = getDocumento(new File(directorioUsuarios + "/usuarios.xml"));
        Element usuario = null;
        NodeList listaUsuarios = documentoXML.getElementsByTagName("usuario");
        int numeroNodo = 0;
        String id = "";
        while (!id.equals(nombreUsuario) && numeroNodo < listaUsuarios.getLength()) {
            Node nodo = listaUsuarios.item(numeroNodo);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                usuario = (Element) nodo;
                id = usuario.getAttribute("id");
            }
            numeroNodo++;
        }
        perfilUsuario = usuario;
    }

    public Cuenta[] getCuentas() {
        NodeList cuentas = perfilUsuario.getElementsByTagName("cuenta");
        Cuenta[] listaCuentas = new Cuenta[cuentas.getLength()];
        for (int i = 0; i < cuentas.getLength(); i++) {
            Node nodoCuenta = cuentas.item(i);
            Element cuenta = (Element) nodoCuenta;
            String nroCuenta = cuenta.getElementsByTagName("nroCuenta").item(0).getTextContent();
            String divisa = cuenta.getElementsByTagName("divisa").item(0).getTextContent();
            String monto = cuenta.getElementsByTagName("monto").item(0).getTextContent();
            listaCuentas[i] = new Cuenta(nroCuenta, divisa, monto);
        }
        return listaCuentas;
    }

    public void establecerCuenta(String numeroCuenta) {
        NodeList cuentas = perfilUsuario.getElementsByTagName("cuenta");
        Element cuenta = null;
        String nroCuenta = "";
        for (int i = 0; !nroCuenta.equals(numeroCuenta) && i < cuentas.getLength(); i++) {
            Node nodoCuenta = cuentas.item(i);
            if (nodoCuenta.getNodeType() == Node.ELEMENT_NODE) {
                cuenta = (Element) nodoCuenta;
                nroCuenta = cuenta.getElementsByTagName("nroCuenta").item(0).getTextContent();
            }
        }
        cuentaActual = cuenta;
    }

    public String getMonto() {
        return cuentaActual.getElementsByTagName("monto").item(0).getTextContent();
    }

    public String getDivisa() {
        return cuentaActual.getElementsByTagName("divisa").item(0).getTextContent();
    }

    public String getNroCuenta() {
        return cuentaActual.getElementsByTagName("nroCuenta").item(0).getTextContent();

    }

    public double getMontoConvertido(String divisa, double monto) {
        double tasaCambio = getTasaCambio(divisa, getDivisa(getNroCuenta()));
        double montoConvertido = tasaCambio * monto;
        return montoConvertido;
    }

    public double getMontoConvertido(String divisa, double monto, String nroCuenta) {
        double tasaCambio = getTasaCambio(divisa, getDivisa(nroCuenta));
        double montoConvertido = tasaCambio * monto;
        return montoConvertido;
    }

    public String titularCuenta() {
        return perfilUsuario.getElementsByTagName("nombreTitular").item(0).getTextContent();
    }

    public static void main(String args[]) {
        GestorCuenta gestorCuenta = new GestorCuenta();
        gestorCuenta.extraerCuentaUsuario("daniel87");
        gestorCuenta.establecerCuenta("80789");
        System.out.println(gestorCuenta.getMonto());
        System.out.println(gestorCuenta.getDivisa());
        gestorCuenta.retirar("bolivianos", 100);
        System.out.println(gestorCuenta.getMonto());
        gestorCuenta.depositar("dolares", 100);
        System.out.println(gestorCuenta.getMonto());
 

    }

}
