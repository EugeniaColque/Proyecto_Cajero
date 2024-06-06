
package cajero;

/**
 * Clase Usuario
 * Esta clase representa a un usuario del sistema de cajero automático.
 * 
 * @autor Eugenia
 */
public class Usuario {

    private String nombreUsuario;
    private String nombreTitular;
    private String contrasena;
    private String nroCuenta;
    private String divisa;
    private String monto;
    
    /**
     * Constructor de la clase Usuario.
     * 
     * @param nombreUsuario El nombre de usuario.
     * @param nombreTitular El nombre del titular de la cuenta.
     * @param contrasena La contraseña del usuario.
     * @param nroCuenta El número de cuenta del usuario.
     * @param divisa La divisa de la cuenta.
     * @param montoInicial El monto inicial de la cuenta.
     */
    public Usuario(String nombreUsuario, String nombreTitular, String contrasena, String nroCuenta, String divisa, String montoInicial){
        this.nombreUsuario = nombreUsuario;
        this.nombreTitular = nombreTitular;
        this.contrasena = contrasena;
        this.nroCuenta = nroCuenta;
        this.divisa = divisa;
        this.monto = montoInicial;
    }
    
    /**
     * Obtiene el nombre de usuario.
     * 
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Obtiene el nombre del titular de la cuenta.
     * 
     * @return El nombre del titular.
     */
    public String getNombreTitular() {
        return nombreTitular;
    }

    /**
     * Obtiene el número de cuenta del usuario.
     * 
     * @return El número de cuenta.
     */
    public String getNumeroCuenta() {
        return nroCuenta;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return La contraseña.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Obtiene el tipo de divisa de la cuenta.
     * 
     * @return El tipo de divisa en minúsculas.
     */
    public String getTipoDivisa() {
        return divisa.toLowerCase();
    }
    
    /**
     * Obtiene el monto de la cuenta.
     * 
     * @return El monto de la cuenta.
     */
    public String getMonto() {
        return monto;
    }
    
    /**
     * Procesa un array de caracteres de contraseña y lo convierte en una cadena.
     * 
     * @param contrasena El array de caracteres de la contraseña.
     * @return La contraseña como cadena.
     */
    private String procesarContrasena(char[] contrasena){
        String contrasenaCadena = "";
        for(int i = 0; i < contrasena.length; i++){
            contrasenaCadena += "" + contrasena[i];
        }
        return contrasenaCadena;
    }
}
