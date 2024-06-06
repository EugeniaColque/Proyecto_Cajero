package cajero;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Clase GestorIO
 * Esta clase gestiona la entrada y salida de datos en la consola.
 * 
 * @autor Eugenia
 */
public class GestorIO {

    private static BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Lee una línea de entrada desde la consola.
     *
     * @return La cadena leída desde la consola.
     */
    public String inString() {
        String entrada = null;
        try {
            entrada = b.readLine();
        } catch (Exception e) {
            this.salir();
        }
        return entrada;
    }

    /**
     * Lee un entero desde la consola.
     *
     * @return El entero leído desde la consola.
     */
    public int inInt() {
        int entrada = 0;
        try {
            entrada = Integer.parseInt(this.inString());
        } catch (Exception e) {
            this.salir();
        }
        return entrada;
    }

    /**
     * Muestra una cadena en la consola.
     *
     * @param salida La cadena a mostrar.
     */
    public void out(String salida) {
        System.out.println(salida);
    }

    /**
     * Muestra un mensaje de error y termina el programa.
     */
    private void salir() {
        System.out.println("ERROR de entrada/salida");
        System.exit(0);
    }
}
