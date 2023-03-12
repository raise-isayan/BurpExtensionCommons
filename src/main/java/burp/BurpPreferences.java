package burp;

import extension.burp.BurpVersion;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.prefs.Preferences;

/**
 *
 * @author isayan
 */
public class BurpPreferences {

    /*
     * %APPDATA%/BurpSuite/**
     */
    private static final String USER_CONFIG_COMMUNITY = "UserConfigCommunity.json";
    private static final String USER_CONFIG_PRO = "UserConfigPro.json";

    private static final String CA_PASSWORD = "/burp/media/ps.p12";

    public static String getCAPassword() {
        return CA_PASSWORD;
    }

    public static KeyStore loadCACeart() throws KeyStoreException {
        try {
            final KeyStore ks;
            ks = KeyStore.getInstance("PKCS12");
            Preferences prefs = Preferences.userNodeForPackage(burp.BurpPreferences.class);
            byte[] caCartByte = Base64.getDecoder().decode(prefs.get("caCert", ""));
            ks.load(new ByteArrayInputStream(caCartByte), CA_PASSWORD.toCharArray());
            return ks;
        } catch (IOException | NoSuchAlgorithmException | CertificateException ex) {
            throw new KeyStoreException(ex);
        }
    }


}
