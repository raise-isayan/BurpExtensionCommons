package burp;

import extension.helpers.CertUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author isayan
 */
public class BurpPreferences {

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

    public static KeyPair loadCAKeyPair() throws KeyStoreException {
        try {
            KeyStore caKeyStore = loadCACeart();
            Enumeration<String> emu = caKeyStore.aliases();
            while (emu.hasMoreElements()) {
                String alias = emu.nextElement();
                KeyPair keyPair = new KeyPair(caKeyStore.getCertificate(alias).getPublicKey(), (PrivateKey)caKeyStore.getKey(alias, CA_PASSWORD.toCharArray()));
                return keyPair;
            }
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException ex) {
        }
        return null;
    }


}
