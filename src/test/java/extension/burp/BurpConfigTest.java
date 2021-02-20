package extension.burp;

import java.security.KeyStore;
import java.util.Enumeration;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        KeyStore result = BurpConfig.loadCACeart();
        Properties p = System.getProperties();
        Enumeration<String> e = result.aliases();
        while (e.hasMoreElements()) {
            // cacert
            String alias = e.nextElement();
            assertEquals("cacert", alias);
        }
    }

    @Test
    public void testSystemProperty() throws Exception {
        System.out.println("SystemProperty");
        Properties p = System.getProperties();
        p.list(System.out);
    }

}
