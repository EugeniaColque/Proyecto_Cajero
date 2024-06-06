
package cajero;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * La clase {@code Evento} representa un evento relacionado con una cuenta bancaria,
 * incluyendo el número de cuenta, una descripción, un monto y el saldo.
 * 
 * Proporciona métodos para obtener estos valores y un método para obtener la fecha actual.
 * 
 * @autor Eugenia
 */
public class Evento {

    private String nroCuenta;
    private String descripcion;
    private String monto;
    private String saldo;

    /**
     * Crea un nuevo evento.
     * 
     * @param nroCuenta el número de cuenta
     * @param descripcion la descripción del evento
     * @param monto el monto del evento
     * @param saldo el saldo después del evento
     */
    public Evento(String nroCuenta, String descripcion, String monto, String saldo) {
        this.nroCuenta = nroCuenta;
        this.descripcion = descripcion;
        this.monto = monto;
        this.saldo = saldo;
    }

    /**
     * Obtiene el número de cuenta.
     * 
     * @return el número de cuenta
     */
    public String getNroCuenta() {
        return nroCuenta;
    }

    /**
     * Obtiene la descripción del evento.
     * 
     * @return la descripción del evento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el monto del evento.
     * 
     * @return el monto del evento
     */
    public String getMonto() {
        return monto;
    }

    /**
     * Obtiene el saldo después del evento.
     * 
     * @return el saldo después del evento
     */
    public String getSaldo() {
        return saldo;
    }

    /**
     * Obtiene la fecha actual en el formato "yyyy/MM/dd".
     * 
     * @return la fecha actual como una cadena
     */
    public String getFecha() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaFormateada = fechaActual.format(formato);
        return fechaFormateada;
    }
}
