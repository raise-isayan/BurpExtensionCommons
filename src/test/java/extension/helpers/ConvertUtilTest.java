package extension.helpers;

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
public class ConvertUtilTest {
    
    public ConvertUtilTest() {
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

//    /**
//     * Test of parseIntDefault method, of class ConvertUtil.
//     */
//    @Test
//    public void testParseIntDefault() {
//        System.out.println("parseIntDefault");
//        String value = "";
//        int defvalue = 0;
//        int expResult = 0;
//        int result = ConvertUtil.parseIntDefault(value, defvalue);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of parseLongDefault method, of class ConvertUtil.
//     */
//    @Test
//    public void testParseLongDefault() {
//        System.out.println("parseLongDefault");
//        String value = "";
//        long defvalue = 0L;
//        long expResult = 0L;
//        long result = ConvertUtil.parseLongDefault(value, defvalue);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of parseFloatDefault method, of class ConvertUtil.
//     */
//    @Test
//    public void testParseFloatDefault() {
//        System.out.println("parseFloatDefault");
//        String value = "";
//        float defvalue = 0.0F;
//        float expResult = 0.0F;
//        float result = ConvertUtil.parseFloatDefault(value, defvalue);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of parseDoubleDefault method, of class ConvertUtil.
//     */
//    @Test
//    public void testParseDoubleDefault() {
//        System.out.println("parseDoubleDefault");
//        String value = "";
//        double defvalue = 0.0;
//        double expResult = 0.0;
//        double result = ConvertUtil.parseDoubleDefault(value, defvalue);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of parseBooleanDefault method, of class ConvertUtil.
//     */
//    @Test
//    public void testParseBooleanDefault() {
//        System.out.println("parseBooleanDefault");
//        String value = "";
//        boolean defvalue = false;
//        boolean expResult = false;
//        boolean result = ConvertUtil.parseBooleanDefault(value, defvalue);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of parseEnumDefault method, of class ConvertUtil.
//     */
//    @Test
//    public void testParseEnumDefault() {
//        System.out.println("parseEnumDefault");
//        Enum expResult = null;
//        Enum result = ConvertUtil.parseEnumDefault(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toList method, of class ConvertUtil.
//     */
//    @Test
//    public void testToList() {
//        System.out.println("toList");
//        List expResult = null;
//        List result = ConvertUtil.toList(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toUniqList method, of class ConvertUtil.
//     */
//    @Test
//    public void testToUniqList_List() {
//        System.out.println("toUniqList");
//        List expResult = null;
//        List result = ConvertUtil.toUniqList(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toUniqList method, of class ConvertUtil.
//     */
//    @Test
//    public void testToUniqList_String_List() {
//        System.out.println("toUniqList");
//        String regex = "";
//        List<String> list = null;
//        List<String> expResult = null;
//        List<String> result = ConvertUtil.toUniqList(regex, list);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of toHexString method, of class ConvertUtil.
     */
    @Test
    public void testToHexString() {
        System.out.println("toHexString");
        final byte[] data = new byte [] { (byte)0x00, (byte)0x01, (byte)0x09, (byte)0x0a, (byte)0x0f, (byte)0x1f, (byte)0xaf, (byte)0xff };           
        {        
            String expResult = "0001090A0F1FAFFF";
            String result = ConvertUtil.toHexString(data);
            assertEquals(expResult, result);
        }
        {
            byte [] expResult = data; ;
            byte [] result = ConvertUtil.fromHexString("0001090A0F1FAFFF");
            assertArrayEquals(expResult, result);
        }
    }
    
}
