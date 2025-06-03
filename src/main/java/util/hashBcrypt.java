/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Alor
 */
public class hashBcrypt {
    
    
    
    


    // Genera el hash para almacenar en la base de datos
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Compara la contraseña ingresada con la almacenada
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    

    public static void main(String[] args) {
        // Contraseña original
        String originalPassword = "123";

        // Generar hash de la contraseña
        String hashedPassword = hashBcrypt.hashPassword(originalPassword);

        // Mostrar el hash generado
        System.out.println("Hash generado: " + hashedPassword);

        // Simulación de login: contraseña ingresada
        String inputPassword = "123";

        // Verificar la contraseña ingresada contra el hash
        boolean match = hashBcrypt.checkPassword(inputPassword, hashedPassword);

        if (match) {
            System.out.println("Contraseña válida. Coincide con el hash.");
        } else {
            System.out.println("Contraseña inválida. No coincide con el hash.");
        }
    }


    
}
