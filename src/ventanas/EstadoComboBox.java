
package ventanas;

/**
 * Clase EstadoComboBox
 * Esta clase gestiona el estado del índice seleccionado de un ComboBox.
 * Utiliza una variable estática para mantener el índice seleccionado.
 * 
 * @autor Eugenia
 */
public class EstadoComboBox {

    // Variable estática que almacena el índice seleccionado
    private static int indiceSeleccionado = 0;

    /**
     * Establece el índice seleccionado del ComboBox.
     * 
     * @param indice El índice que se desea establecer como seleccionado.
     */
    public static void setIndiceSeleccionado(int indice) {
        indiceSeleccionado = indice;
    }

    /**
     * Obtiene el índice actualmente seleccionado del ComboBox.
     * 
     * @return El índice seleccionado.
     */
    public static int getIndiceSeleccionado() {
        return indiceSeleccionado;
    }
}
