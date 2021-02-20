package extension.helpers;

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
public class StringUtilTest {
  
    
    public StringUtilTest() {
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
     * Test of isNullOrEmpty method, of class StringUtilTest.
     */
    @Test
    public void testIsNullOrEmpty() {
        {
            boolean result = StringUtil.isNullOrEmpty(null);
            assertEquals(true, result);                
        }
        {
            boolean result = StringUtil.isNullOrEmpty("");
            assertEquals(true, result);        
        }
        {
            boolean result = StringUtil.isNullOrEmpty("a");
            assertEquals(false, result);        
        }
        {
            boolean result = StringUtil.isNullOrEmpty("\u0000");
            assertEquals(false, result);        
        }
    }
    
    @Test
    public void testString_0() {
        String rep0 = StringUtil.stringReplace("1234567890", 0, 0, "abc");
        System.out.println("testString_0:" + rep0);
        String rep1 = StringUtil.stringReplace("1234567890", 1, 3, "abc");
        System.out.println("testString_1_3:" + rep1);
    }
    
    @Test
    public void testGetAvailableEncodingList() {
        System.out.println("getAvailableEncodingList");
        String[] list = StringUtil.getAvailableEncodingList();
        for (String l : list) {
            System.out.println(l);
        }
    }
    
}
