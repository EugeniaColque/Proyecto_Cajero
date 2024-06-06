package cajero;

/**
 * La clase {@code Cuenta} representa una cuenta bancaria con un número de cuenta,
 * una divisa y un monto.
 * 
 * Proporciona métodos para obtener estos valores y un método {@code toString}
 * para representar la cuenta como una cadena.
 * 
 * @author Eugenia
 */
public class Cuenta {

    private String nroCuenta;
    private String divisa;
    private String monto;

    /**
     * Crea una nueva cuenta bancaria.
     * 
     * @param nroCuenta el número de cuenta
     * @param divisa la divisa de la cuenta
     * @param monto el monto de la cuenta
     */
    public Cuenta(String nroCuenta, String divisa, String monto) {
        this.nroCuenta = nroCuenta;
        this.divisa = divisa;
        this.monto = monto;
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
     * Obtiene la divisa de la cuenta.
     * 
     * @return la divisa de la cuenta
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * Obtiene el monto de la cuenta.
     * 
     * @return el monto de la cuenta
     */
    public String getMonto() {
        return monto;
    }

    /**
     * Devuelve una cadena que representa la cuenta.
     * 
     * @return una cadena con el formato "nroCuenta (divisa)"
     */
    @Override
    public String toString() {
        return nroCuenta + " (" + getDivisa() + ")";
    }
}
