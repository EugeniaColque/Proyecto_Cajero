
package testing;

/**
 * Clase prueba
 * Esta clase contiene un método principal que demuestra cómo convertir un array de caracteres en una cadena.
 * 
 * @autor Eugenia
 */
public class prueba {

    /**
     * Método principal que convierte un array de caracteres en una cadena y la imprime.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String args[]) {
        // Crear un array de caracteres de ejemplo
        char[] ejemplo = {'a', 'b', 'c'};
        
        // Convertir el array de caracteres a una cadena
        String cadena = String.valueOf(ejemplo);
        
        // Imprimir la cadena resultante
        System.out.println(cadena);
    }
}
