package extension.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class BurpUtilTest {

    public BurpUtilTest() {
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
     * Test of parseFilterPattern method, of class BurpUtil.
     */
    @Test
    public void testParseFilterPattern() {
        System.out.println("parseFilterPattern");
        String pattern = "jpg,png,gif,js.map";
        {
            String expResult = "test.jpg";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertTrue(m.find());
        }
        {
            String expResult = "test.jpg?xxx";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertFalse(m.matches());
        }
        {
            String expResult = "test.gif";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertTrue(m.find());
        }
        {
            String expResult = "test.js.map";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertTrue(m.find());
        }
    }


}
