package cajero;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Hora
 * Esta clase proporciona la hora actual formateada.
 * 
 * @autor Eugenia
 */
public class Hora {
    
    /**
     * Obtiene la hora actual formateada como una cadena.
     *
     * @return La hora actual en formato "yyyy/MM/dd".
     */
    public static String hora() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String horaFormateada = now.format(formato);
        return horaFormateada;
    }
}
