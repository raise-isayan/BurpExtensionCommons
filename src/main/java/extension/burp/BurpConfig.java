package extension.burp;

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
public class BurpConfig {
    private static final String CA_PASSWORD = "/burp/media/ps.p12";
    
    public KeyStore loadCACeart() throws KeyStoreException {
        try {
            final KeyStore ks;
            ks = KeyStore.getInstance("PKCS12");
            Preferences prefs = Preferences.userNodeForPackage(burp.IBurpExtender.class);
            byte[] caCartByte = Base64.getDecoder().decode(prefs.get("caCert", ""));
            ks.load(new ByteArrayInputStream(caCartByte), CA_PASSWORD.toCharArray());
            return ks;
        } catch (IOException ex) {
            throw new KeyStoreException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new KeyStoreException(ex);
        } catch (CertificateException ex) {
            throw new KeyStoreException(ex);
        }
    }
    
    public static String getUserHomePath() {
        return System.getProperties().getProperty("user.home");
    }

    public static File getUserHomeFile() {
        final File homePath = new File(getUserHomePath());
        return homePath;
    }
    
    public static String getUserDirPath() {
        return System.getProperties().getProperty("user.dir");
    }

    public static File getUserDirFile() {
        final File userDir = new File(getUserDirPath());
        return userDir;
    }
    
    
    public static File getUserConfig() {
        final File userDir = new File(getUserDirPath());
        return userDir;
    }
        
    /* Burp built in PayloadStrings */ 
    
    private static final String BUILT_IN_PASSWORDS_SIGNATURE = "/resources/PayloadStrings/Passwords.pay";
    private static final String BUILT_IN_USERNAMES_SIGNATURE = "/resources/PayloadStrings/Usernames.pay";
    private static final String BUILT_IN_SHORT_WORDS_SIGNATURE = "/resources/PayloadStrings/Short words.pay";
    private static final String BUILT_IN_3_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/3 letter words.pay";
    private static final String BUILT_IN_4_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/4 letter words.pay";
    private static final String BUILT_IN_5_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/5 letter words.pay";
    private static final String BUILT_IN_6_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/6 letter words.pay";
    private static final String BUILT_IN_7_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/7 letter words.pay";
    private static final String BUILT_IN_8_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/8 letter words.pay";
    private static final String BUILT_IN_9_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/9 letter words.pay";
    private static final String BUILT_IN_10_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/10 letter words.pay";
    private static final String BUILT_IN_11_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/11 letter words.pay";
    private static final String BUILT_IN_12_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/12 letter words.pay";    
    
}
