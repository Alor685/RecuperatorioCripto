package util;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import dto.Medico;

public class GoogleAuthUtil {

    /**
     * Genera una clave secreta TOTP y la asigna al médico.
     * Devuelve la URL del código QR que puede ser convertida a imagen con Google Charts.
     *
     * @param medico El médico al que se le generará la clave TOTP.
     * @return URL del código QR para Google Authenticator.
    public static String generarQrTotp(Medico medico) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();

        // Asignamos el secreto TOTP al médico
        medico.setTotpSecret(key.getKey());

        // Generamos la URL para el QR (puede usarse en un <img>)
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("SistemaClinico", medico.getLogiMedi(), key);
    }
    */
}
