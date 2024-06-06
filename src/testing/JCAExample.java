
package testing;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Clase JCAExample
 * Este ejemplo demuestra cómo utilizar la API de cifrado de Java (JCA) para encriptar y desencriptar mensajes utilizando el algoritmo AES.
 * 
 * @autor Eugenia
 */
public class JCAExample {

    public static void main(String[] args) {
        try {
            String message = "Welcome to Educative Answers!";
            String key = "SecretKey12345678"; // Clave actualizada para tener una longitud válida (16 bytes)

            // Truncar o rellenar la clave a 16 bytes
            byte[] keyBytes = Arrays.copyOf(key.getBytes(), 16);

            // Generar una clave secreta a partir de los bytes de la clave
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

            // Crear un objeto Cipher para cifrado AES
            Cipher cipher = Cipher.getInstance("AES");

            // Inicializar el cipher en modo de cifrado con la clave secreta
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Cifrar el mensaje
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());

            // Imprimir el mensaje cifrado
            System.out.println("Encrypted message: " + bytesToHex(encryptedBytes));

            // Inicializar el cipher en modo de descifrado con la clave secreta
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Descifrar el mensaje
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Imprimir el mensaje descifrado
            System.out.println("Decrypted message: " + new String(decryptedBytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    // Array de caracteres que representan dígitos hexadecimales
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * Convierte un array de bytes a una cadena hexadecimal.
     * 
     * @param bytes El array de bytes a convertir.
     * @return Una cadena que representa los bytes en formato hexadecimal.
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            // Obtener el nibble alto y convertirlo a un carácter hexadecimal
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            // Obtener el nibble bajo y convertirlo a un carácter hexadecimal
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
