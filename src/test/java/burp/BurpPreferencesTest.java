package burp;

import extension.helpers.CertUtil;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author isayan
 */
public class BurpPreferencesTest {

    public BurpPreferencesTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSysEnv() throws Exception {
        System.out.println("testSysEnv");
        System.out.println("Data:" + System.getenv("APPDATA"));
    }

    @Test
    public void testloadBurpCa() throws Exception {
        System.out.println("testloadBurpCa");
        KeyStore ks = BurpPreferences.loadCACeart();
        HashMap<String, Map.Entry<Key, X509Certificate>> mapCert = CertUtil.loadFromKeyStore(ks, BurpPreferences.getCAPassword());
        assertFalse(mapCert.isEmpty());
    }

    @Test
    public void testloadBurpKeyPair() throws Exception {
        System.out.println("testloadBurpKeyPair");
        KeyPair caKey = BurpPreferences.loadCAKeyPair();
        assertNotNull(caKey);
    }

}
