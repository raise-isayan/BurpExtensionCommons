package burp;

import extension.helpers.CertUtil;
import java.io.File;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
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
        File app_data = new File(System.getenv("APPDATA"));
        System.out.println("Data:" + app_data);
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

    @Test
    public void testWorkDir() throws Exception {
        System.out.println("testWorkDir");
        File work_dir = BurpPreferences.getWorkingDir();
        System.out.println("Work:" + work_dir);
        assertNotNull(work_dir);
    }

    @Test
    public void testBurpKeys() throws Exception {
        System.out.println("testBurpKeys");
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(burp.BurpPreferences.class);
        for (String k : prefs.keys()) {
            System.out.println("key:" + k);
        }
    }

    @Test
    public void testExtensoinKeys() throws Exception {
        System.out.println("testExtensoinKeys");
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(burp.BurpPreferences.class);
        String[] childNames = prefs.childrenNames();
        for (String ch : childNames) {
            System.out.println("ch:" + ch);
        }
        Preferences exts = prefs.node("extensions");
        for (String e : exts.childrenNames()) {
            System.out.println("ext:" + e);
            Preferences ext = exts.node(e);
            for (String k : ext.keys()) {
                System.out.println("\tk:" + k);
            }
        }
    }

    @Test
    public void testMontoyaPref() throws Exception {
        System.out.println("testMontoyaPref");
        burp.api.montoya.persistence.Preferences pref = BurpPreferences.extensions("FakeDnsServerExtension");
        for (String k : pref.stringKeys()) {
            System.out.println("k:" + k + " -> " + pref.getString(k));
        }
        for (String k : pref.byteKeys()) {
            System.out.println("k:" + k + " -> " + pref.getByte(k));
        }
        for (String k : pref.integerKeys()) {
            System.out.println("k:" + k + " -> " + pref.getInteger(k));
        }
        for (String k : pref.longKeys()) {
            System.out.println("k:" + k + " -> " + pref.getLong(k));
        }
        for (String k : pref.booleanKeys()) {
            System.out.println("k:" + k + " -> " + pref.getBoolean(k));
        }
    }

}
