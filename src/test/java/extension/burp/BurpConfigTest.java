package extension.burp;

import java.io.File;
import java.security.KeyStore;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author isayan
 */
public class BurpConfigTest {
    
    public BurpConfigTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadCACeart method, of class BurpConfig.
     */
    @Test
    public void testLoadCACeart() throws Exception {
        System.out.println("loadCACeart");
        BurpConfig instance = new BurpConfig();
        KeyStore expResult = null;
        KeyStore result = instance.loadCACeart();
    }

    @Test
    public void testSystemProperty() throws Exception {
        System.out.println("SystemProperty");
        Properties p = System.getProperties();
        p.list(System.out);
    }

}
