package extension.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     */
    @Test
    public void testAppandByte() {
        System.out.println("appandByte");
        byte [] base = new byte [] {1,2,3,4,5,6,7,8,9,10};
        {
            byte [] add = new byte [] {21,22,23};
            byte [] expResult = new byte [] {1,2,3,4,5,6,7,8,9,10,21,22,23};
            byte [] result = ConvertUtil.appandByte(base, add);
            assertArrayEquals(expResult, result);
        }
        {
            byte [] add = new byte [] {};
            byte [] result = ConvertUtil.appandByte(base, add);
            byte [] expResult = new byte [] {1,2,3,4,5,6,7,8,9,10};
            assertArrayEquals(expResult, result);
        }
    }

    public static byte[] byteReplace(byte[] base, int startPos, int endPos, byte[] replace) {
        ByteArrayOutputStream bstm = new ByteArrayOutputStream();
        try {
            bstm.write(Arrays.copyOfRange(base, 0, startPos));
            bstm.write(replace);
            bstm.write(Arrays.copyOfRange(base, endPos, base.length));
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
        return bstm.toByteArray();
    }

    /**
     */
    @Test
    public void testByteReplace_0() {
        System.out.println("byteReplace");
        byte [] base = new byte [] {1,2,3,4,5,6,7,8,9,10};
        byte [] replace = new byte [] {21,22,23};
        {
            byte [] expResult = new byte [] {21,22,23,1,2,3,4,5,6,7,8,9,10};
            byte [] testResult = byteReplace(base, 0, 0, replace);
            byte [] result = ConvertUtil.replaceByte(base, 0, 0, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte [] expResult = new byte [] {1,2,21,22,23,4,5,6,7,8,9,10};
            byte [] result = ConvertUtil.replaceByte(base, 2, 3, replace);
            byte [] testResult = byteReplace(base, 2, 3, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte [] expResult = new byte [] {1,21,22,23,4,5,6,7,8,9,10};
            byte [] result = ConvertUtil.replaceByte(base, 1, 3, replace);
            byte [] testResult = byteReplace(base, 1, 3, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte [] expResult = new byte [] {1,2,3,4,5,6,7,8,9,21,22,23,10};
            byte [] result = ConvertUtil.replaceByte(base, 9, 9, replace);
            byte [] testResult = byteReplace(base, 9, 9, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte [] expResult = new byte [] {1,2,3,4,5,6,7,8,9,10,21,22,23};
            byte [] result = ConvertUtil.replaceByte(base, 10, 10, replace);
            byte [] testResult = byteReplace(base, 10, 10, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
    }

    /**
     * Test of escapeXml method, of class ConvertUtil.
     */
    @Test
    public void testEscapeXml() throws Exception {
        System.out.println("escapeXml");
        String target = "<s a=\"x\">&";
        String expResult = "&lt;s a=\"x\"&gt;&amp;";
        String result = ConvertUtil.escapeXml(target);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegexQuote() {
        System.out.println("regexQuote");
        {
            String target = "aa.com";
            String expResult = "aa\\.com";
            String result = ConvertUtil.regexQuote(target);
            assertEquals(expResult, result);
            Pattern ptn1 = Pattern.compile(result);
            assertTrue(ptn1.matcher(target).matches());
        }
        {
            String target = "\\/{}<>[]()";
            String expResult = "\\\\/\\{\\}\\<\\>\\[\\]\\(\\)";
            String result = ConvertUtil.regexQuote(target);
            assertEquals(expResult, result);
            Pattern ptn2 = Pattern.compile(result);
            assertTrue(ptn2.matcher(target).matches());
        }
        {
            String target = ".\\+*?[^]$(){}=!<>|:-";
            String expResult = "\\.\\\\\\+\\*\\?\\[\\^\\]\\$\\(\\)\\{\\}\\=\\!\\<\\>\\|\\:\\-";
            String result = ConvertUtil.regexQuote(target);
            assertEquals(expResult, result);
            Pattern ptn3 = Pattern.compile(result);
            assertTrue(ptn3.matcher(target).matches());
        }

    }

    /**
     * Test of toInteger method, of class ConvertUtil.
     */
    @Test
    public void testToInteger() {
        System.out.println("toInteger");
        assertEquals(0x7fff, ConvertUtil.toInteger(new byte[]{(byte) 0x7f, (byte) 0xff}));
        assertEquals(0xff7f, ConvertUtil.toInteger(new byte[]{(byte) 0xff, (byte) 0x7f}));
        assertEquals(0x8080, ConvertUtil.toInteger(new byte[]{(byte) 0x80, (byte) 0x80}));
    }

    /**
     * Test of toBASE64Encoder method, of class ConvertUtil.
     */
    @Test
    public void testToBASE64Encoder() {
        try {
            System.out.println("toBASE64Encoder");
            assertEquals("PA==", ConvertUtil.toBase64Encode("<", "8859_1", true));
            assertEquals("dGVzdA==", ConvertUtil.toBase64Encode("test", "8859_1", true));
            assertEquals("ZnVnYWY=", ConvertUtil.toBase64Encode("fugaf", "8859_1", true));
            assertEquals("aG9nZWhv", ConvertUtil.toBase64Encode("hogeho", "8859_1", true));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConvertUtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of toBASE64Decode method, of class ConvertUtil.
     */
    @Test
    public void testToBASE64Decoder() {
        try {
            System.out.println("toBASE64Decoder");
            assertEquals("<", ConvertUtil.toBase64Decode("PA==", "8859_1"));
            assertEquals("hogeho", ConvertUtil.toBase64Decode("aG9nZWhv", "8859_1"));
            assertEquals("fugaf", ConvertUtil.toBase64Decode("ZnVnYWY=", "8859_1"));
            assertEquals("test", ConvertUtil.toBase64Decode("dGVzdA==", "8859_1"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConvertUtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
        try {
            System.out.println("toBASE64Decoder");
            System.out.println(ConvertUtil.toBase64Decode("absdadbd", "8859_1"));
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConvertUtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConvertUtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
       }
        try {
            System.out.println("toBASE64Decoder");
            System.out.println(ConvertUtil.toBase64Decode("!\"#$%&'()=~|", "8859_1"));
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConvertUtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
       }
    }





}
