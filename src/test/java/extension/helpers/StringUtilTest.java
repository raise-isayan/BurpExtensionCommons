package extension.helpers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author isayan
 */
public class StringUtilTest {

    public StringUtilTest() {
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

    /**
     * Test of isNullOrEmpty method, of class StringUtilTest.
     */
    @Test
    public void testLiteralEscape() {
        System.out.println("testLiteralEscape");
        {
            String result = StringUtil.literalEscape("\\");
            assertEquals("\\\\", result);
        }
        {
            String result = StringUtil.literalEscape("\"");
            assertEquals("\\\"", result);
        }
        {
            String result = StringUtil.literalEscape("'");
            assertEquals("'", result);
        }
        {
            String result = StringUtil.literalEscape("01234");
            assertEquals("01234", result);
        }
        {
            String result = StringUtil.literalEscape("01\\23\"4");
            assertEquals("01\\\\23\\\"4", result);
        }
    }

    /**
     * Test of isNullOrEmpty method, of class StringUtilTest.
     */
    @Test
    public void testIsNullOrEmpty() {
        System.out.println("testIsNullOrEmpty");
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
    public void testPrintable() {
        System.out.println("isPrintable");
        assertTrue(StringUtil.isPrintable("abcdef0123456789"));
        assertTrue(StringUtil.isPrintable("\r\n\t "));
        assertTrue(StringUtil.isPrintable(ConvertUtil.encodeJsLangQuote("\tabcdef0123456789\r\n", true)));
        assertTrue(StringUtil.isPrintable(ConvertUtil.encodeJsLangQuote("\tabcdef0123456789\r\n0123456789", true)));
        assertFalse(StringUtil.isPrintable("\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u001a"));
        assertTrue(StringUtil.isPrintable("あいうえお"));
    }

    @Test
    public void testPrintableAscii() {
        System.out.println("isPrintableAscii");
        assertTrue(StringUtil.isPrintableAscii("abcdef0123456789"));
        assertTrue(StringUtil.isPrintableAscii("\r\n\t "));
        assertTrue(StringUtil.isPrintableAscii(ConvertUtil.encodeJsLangQuote("\tabcdef0123456789\r\n", true)));
        assertTrue(StringUtil.isPrintableAscii(ConvertUtil.encodeJsLangQuote("\tabcdef0123456789\r\n0123456789", true)));
        assertFalse(StringUtil.isPrintableAscii("\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008\u0009\u001a"));
        assertFalse(StringUtil.isPrintableAscii("あいうえお"));
    }

    @Test
    public void testString_0() {
        System.out.println("testString_0");
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
