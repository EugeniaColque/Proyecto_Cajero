package cajero;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Clase Seguridad
 * Esta clase proporciona métodos para cifrar y descifrar datos utilizando el algoritmo AES.
 * 
 * @autor Eugenia
 */
public class Seguridad {

    /**
     * Algoritmo utilizado para el cifrado y descifrado.
     */
    private static final String ALGORITMO = "AES";
    
    /**
     * Clave secreta utilizada para el cifrado y descifrado.
     */
    private static final String CLAVE_SECRETA = "miclavesecreta12";

    /**
     * Obtiene la clave secreta para el cifrado y descifrado.
     *
     * @return La clave secreta como un objeto SecretKey.
     */
    private static SecretKey getSecretKey() {
        byte[] keyBytes = CLAVE_SECRETA.getBytes();
        return new SecretKeySpec(keyBytes, ALGORITMO);
    }

    /**
     * Cifra una cadena de texto utilizando el algoritmo AES.
     *
     * @param data La cadena de texto a cifrar.
     * @return La cadena cifrada en Base64.
     */
    public static String cifrar(String data) {
        byte[] encryptedBytes = null;
        try {
            SecretKey secretKey = getSecretKey();
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedBytes = cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Descifra una cadena de texto cifrada en Base64 utilizando el algoritmo AES.
     *
     * @param encryptedData La cadena cifrada en Base64.
     * @return La cadena descifrada.
     */
    public static String descifrar(String encryptedData) {
        byte[] decryptedBytes = null;
        try {
            SecretKey secretKey = getSecretKey();
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            decryptedBytes = cipher.doFinal(decodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decryptedBytes);
    }
}
