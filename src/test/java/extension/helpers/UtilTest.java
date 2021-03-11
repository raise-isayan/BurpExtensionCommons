package extension.helpers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author isayan
 */
public class UtilTest {

    public UtilTest() {
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
     */
    @Test
    public void testCalc() {
        double pow = Math.pow(16, 20);
        System.out.println(String.format("%4.2f", Math.log(pow) / Math.log(2.0)));
    }
    
    /**
     */
    @Test
    public void testURL() {
        try {
            URL u = new URL("http://192.0.2.11/cgi-bin/multienc.cgi");
            System.out.println("url:" + u.toString());
            System.out.println("url:" + u.toExternalForm());

            URL u2 = new URL("http://192.0.2.11:80/cgi-bin/multienc.cgi");
            System.out.println("url2:" + u2.toString());
            System.out.println("url2:" + u2.toExternalForm());
            System.out.println("url3:" + u2.toURI().normalize().toString());
        } catch (URISyntaxException ex) {
            Logger.getLogger(UtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        } catch (MalformedURLException ex) {
            Logger.getLogger(UtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     */
    @Test
    public void testMatch() {
        Pattern p = Pattern.compile("http://192.0.2.11/cgi-bin/multienc.cgi");
        Matcher m = p.matcher("http://192.0.2.11/cgi-bin/multienc.cgi?charset=EUC-JP");
        if (m.matches()) {
            System.out.println("match");
        }
    }

}
