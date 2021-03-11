package extension.burp;

import extension.helpers.ConvertUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TargetScopeItemTest {

    public TargetScopeItemTest() {
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
     * Test of parseURL method, of class TargetScopeItem.
     */
    @Test
    public void testParseURL() {
        System.out.println("parseURL");
        try {
            URL url = new URL("https://www.google.com/aaa?test=bbb#xyz");
            TargetScopeItem result = TargetScopeItem.parseURL(url);
            {
                String expResult = "^\\Qwww.google.com\\E$";
                System.out.println(url.getHost());
                assertEquals(expResult, result.getHost());
            }
            {
                String expResult = "^\\Q/aaa?test=bbb\\E.*";
                System.out.println(result.getFile());
                assertEquals(expResult, result.getFile());
            }
            {
                int expResult = 443;
                System.out.println(result.getPort());
                assertEquals(expResult, ConvertUtil.parseIntDefault(result.getPort(), -1));
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(TargetScopeItemTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        try {
            URL url = new URL("https://www.google.com");
            System.out.println(">" + url.getFile());
            URL url2 = new URL("https://www.google.com/");
            System.out.println(">" + url.getFile());
        } catch (MalformedURLException ex) {
            Logger.getLogger(TargetScopeItemTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

}
