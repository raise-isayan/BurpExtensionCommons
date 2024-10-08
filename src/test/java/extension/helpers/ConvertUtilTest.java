package extension.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class ConvertUtilTest {

    private final static Logger logger = Logger.getLogger(ConvertUtilTest.class.getName());

    public ConvertUtilTest() {
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
     */
    @Test
    public void testAppandByte() {
        System.out.println("appandByte");
        byte[] base = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        {
            byte[] add = new byte[]{21, 22, 23};
            byte[] expResult = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 21, 22, 23};
            byte[] result = ConvertUtil.appandByte(base, add);
            assertArrayEquals(expResult, result);
        }
        {
            byte[] add = new byte[]{};
            byte[] result = ConvertUtil.appandByte(base, add);
            byte[] expResult = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
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
        byte[] base = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        byte[] replace = new byte[]{21, 22, 23};
        {
            byte[] expResult = new byte[]{21, 22, 23, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            byte[] testResult = byteReplace(base, 0, 0, replace);
            byte[] result = ConvertUtil.replaceByte(base, 0, 0, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte[] expResult = new byte[]{1, 2, 21, 22, 23, 4, 5, 6, 7, 8, 9, 10};
            byte[] result = ConvertUtil.replaceByte(base, 2, 3, replace);
            byte[] testResult = byteReplace(base, 2, 3, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte[] expResult = new byte[]{1, 21, 22, 23, 4, 5, 6, 7, 8, 9, 10};
            byte[] result = ConvertUtil.replaceByte(base, 1, 3, replace);
            byte[] testResult = byteReplace(base, 1, 3, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte[] expResult = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 21, 22, 23, 10};
            byte[] result = ConvertUtil.replaceByte(base, 9, 9, replace);
            byte[] testResult = byteReplace(base, 9, 9, replace);
            assertArrayEquals(expResult, result);
            assertArrayEquals(testResult, result);
        }
        {
            byte[] expResult = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 21, 22, 23};
            byte[] result = ConvertUtil.replaceByte(base, 10, 10, replace);
            byte[] testResult = byteReplace(base, 10, 10, replace);
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
     * Test of toInteger method, of class ConvertUtil.
     */
    @Test
    public void testToHexString() {
        System.out.println("toHexString");
        byte[] hex = BigInteger.valueOf(0).toByteArray();
        assertEquals(hex.length, 1);
        {
            assertEquals("00", ConvertUtil.toHexString(0x00, true));
            assertEquals("0F", ConvertUtil.toHexString(0x0f, true));
            assertEquals("7F", ConvertUtil.toHexString(0x7f, true));
            assertEquals("FF", ConvertUtil.toHexString(0xff, true));
            assertEquals("7FFF", ConvertUtil.toHexString(0x7fff, true));
            assertEquals("FF7F", ConvertUtil.toHexString(0xff7f, true));
            assertEquals("0FF0", ConvertUtil.toHexString(new byte[]{(byte) 0x0f, (byte) 0xf0}, true));
            assertEquals("7FFF", ConvertUtil.toHexString(new byte[]{(byte) 0x7f, (byte) 0xff}, true));
            assertEquals("FF7F", ConvertUtil.toHexString(new byte[]{(byte) 0xff, (byte) 0x7f}, true));
            assertEquals("8080", ConvertUtil.toHexString(new byte[]{(byte) 0x80, (byte) 0x80}, true));
        }
        {
            assertEquals("00", ConvertUtil.toHexString(0x00, false));
            assertEquals("0f", ConvertUtil.toHexString(0x0f, false));
            assertEquals("7f", ConvertUtil.toHexString(0x7f, false));
            assertEquals("ff", ConvertUtil.toHexString(0xff, false));
            assertEquals("7fff", ConvertUtil.toHexString(0x7fff, false));
            assertEquals("ff7f", ConvertUtil.toHexString(0xff7f, false));
            assertEquals("0ff0", ConvertUtil.toHexString(new byte[]{(byte) 0x0f, (byte) 0xf0}, false));
            assertEquals("7fff", ConvertUtil.toHexString(new byte[]{(byte) 0x7f, (byte) 0xff}, false));
            assertEquals("ff7f", ConvertUtil.toHexString(new byte[]{(byte) 0xff, (byte) 0x7f}, false));
            assertEquals("8080", ConvertUtil.toHexString(new byte[]{(byte) 0x80, (byte) 0x80}, false));
        }
    }

    @Test
    public void testBase64() {

        assertEquals("!\"#$%&'()=~", ConvertUtil.toBase64Decode("ISIjJCUmJygpPX4=", StandardCharsets.ISO_8859_1));
        assertEquals("qwertyuiopASDFGHJKL", ConvertUtil.toBase64Decode("cXdlcnR5dWlvcEFTREZHSEpLTA==", StandardCharsets.ISO_8859_1));

        assertEquals(ConvertUtil.toBase64Encode("12345667890q", StandardCharsets.ISO_8859_1, false), ConvertUtil.toBase64Encode("12345667890q", StandardCharsets.ISO_8859_1, false));
        assertEquals(ConvertUtil.toBase64Encode("!\"#$%&'()=", StandardCharsets.ISO_8859_1, false), ConvertUtil.toBase64Encode("!\"#$%&'()=", StandardCharsets.ISO_8859_1, false));
        assertEquals(ConvertUtil.toBase64Encode("qwertyuiopASDFGHJKL", StandardCharsets.ISO_8859_1, false), ConvertUtil.toBase64Encode("qwertyuiopASDFGHJKL", StandardCharsets.ISO_8859_1, false));

        assertEquals("", ConvertUtil.toBase64Encode("", StandardCharsets.ISO_8859_1, true));
        assertEquals("Zg==", ConvertUtil.toBase64Encode("f", StandardCharsets.ISO_8859_1, true));
        assertEquals("Zm8=", ConvertUtil.toBase64Encode("fo", StandardCharsets.ISO_8859_1, true));
        assertEquals("Zm9v", ConvertUtil.toBase64Encode("foo", StandardCharsets.ISO_8859_1, true));
        assertEquals("Zm9vYg==", ConvertUtil.toBase64Encode("foob", StandardCharsets.ISO_8859_1, true));
        assertEquals("Zm9vYmE=", ConvertUtil.toBase64Encode("fooba", StandardCharsets.ISO_8859_1, true));
        assertEquals("Zm9vYmFy", ConvertUtil.toBase64Encode("foobar", StandardCharsets.ISO_8859_1, true));

        assertEquals("", ConvertUtil.toBase64Encode("", StandardCharsets.ISO_8859_1, false));
        assertEquals("Zg", ConvertUtil.toBase64Encode("f", StandardCharsets.ISO_8859_1, false));
        assertEquals("Zm8", ConvertUtil.toBase64Encode("fo", StandardCharsets.ISO_8859_1, false));
        assertEquals("Zm9v", ConvertUtil.toBase64Encode("foo", StandardCharsets.ISO_8859_1, false));
        assertEquals("Zm9vYg", ConvertUtil.toBase64Encode("foob", StandardCharsets.ISO_8859_1, false));
        assertEquals("Zm9vYmE", ConvertUtil.toBase64Encode("fooba", StandardCharsets.ISO_8859_1, false));
        assertEquals("Zm9vYmFy", ConvertUtil.toBase64Encode("foobar", StandardCharsets.ISO_8859_1, false));

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
            logger.log(Level.SEVERE, ex.getMessage(), ex);
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
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            fail(ex.getMessage());
        }
        try {
            System.out.println("toBASE64Decoder");
            System.out.println(ConvertUtil.toBase64Decode("absdadbd", "8859_1"));
        } catch (IllegalArgumentException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            fail(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            fail(ex.getMessage());
        }
        try {
            System.out.println("toBASE64Decoder");
            System.out.println(ConvertUtil.toBase64Decode("!\"#$%&'()=~|", "8859_1"));
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    /**
     */
    @Test
    public void testBytesToInt() {
        System.out.println("bytesToInt");
        {
            byte[] bytes = {(byte) 7, (byte) 91, (byte) 205, (byte) 21};
            int result = ConvertUtil.bytesToInt(bytes, ByteOrder.BIG_ENDIAN);
            assertEquals(123456789, result);
        }
        {
            byte[] bytes = {(byte) 21, (byte) 205, (byte) 91, (byte) 7};
            int result = ConvertUtil.bytesToInt(bytes, ByteOrder.LITTLE_ENDIAN);
            assertEquals(123456789, result);
        }
        {
            byte[] bytes = {(byte) 0};
            int result = ConvertUtil.bytesToInt(bytes, ByteOrder.BIG_ENDIAN);
            assertEquals(0, result);
        }
        {
            byte[] bytes = {(byte) 0};
            int result = ConvertUtil.bytesToInt(bytes, ByteOrder.LITTLE_ENDIAN);
            assertEquals(0, result);
        }
    }

    /**
     */
    @Test
    public void testIntToBytes() {
        System.out.println("intToBytes");
        {
            byte[] bytes = {(byte) 7, (byte) 91, (byte) 205, (byte) 21};
            byte[] result = ConvertUtil.intToBytes(123456789, ByteOrder.BIG_ENDIAN);
            assertArrayEquals(bytes, result);
        }
        {
            byte[] bytes = {(byte) 21, (byte) 205, (byte) 91, (byte) 7};
            byte[] result = ConvertUtil.intToBytes(123456789, ByteOrder.LITTLE_ENDIAN);
            assertArrayEquals(bytes, result);
        }
    }

    /**
     */
    @Test
    public void testBytesToLong() {
        System.out.println("bytesToLong");
        {
            byte[] bytes = {0, 0, 0, 0, (byte) 7, (byte) 91, (byte) 205, (byte) 21};
            long result = ConvertUtil.bytesToLong(bytes, ByteOrder.BIG_ENDIAN);
            assertEquals(123456789L, result);
        }
        {
            byte[] bytes = {(byte) 7, (byte) 91, (byte) 205, (byte) 21};
            long result = ConvertUtil.bytesToLong(bytes, ByteOrder.BIG_ENDIAN);
            assertEquals(123456789L, result);
        }
        {
            byte[] bytes = {(byte) 0};
            long result = ConvertUtil.bytesToLong(bytes, ByteOrder.BIG_ENDIAN);
            assertEquals(0, result);
        }
        {
            byte[] bytes = {(byte) 0};
            long result = ConvertUtil.bytesToLong(bytes, ByteOrder.LITTLE_ENDIAN);
            assertEquals(0, result);
        }
        {
            String value = "X8LPTw";
            byte[] decode = Base64.getDecoder().decode(value);
            long result = ConvertUtil.bytesToLong(decode, ByteOrder.BIG_ENDIAN);
            assertEquals(1606602575L, result);
        }
        {
            byte[] bytes = {(byte) 21, (byte) 205, (byte) 91, (byte) 7, 0, 0, 0, 0};
            long result = ConvertUtil.bytesToLong(bytes, ByteOrder.LITTLE_ENDIAN);
            assertEquals(123456789L, result);
        }
        {
            byte[] bytes = {(byte) 21, (byte) 205, (byte) 91, (byte) 7};
            long result = ConvertUtil.bytesToLong(bytes, ByteOrder.LITTLE_ENDIAN);
            assertEquals(123456789L, result);
        }
        {
            byte[] bytes = ConvertUtil.longToBytes(987654321L, ByteOrder.LITTLE_ENDIAN);
            long longValue = ConvertUtil.bytesToLong(bytes, ByteOrder.LITTLE_ENDIAN);
            assertEquals(987654321L, longValue);
            String encode = Base64.getEncoder().withoutPadding().encodeToString(bytes);
            assertEquals(encode, "sWjeOg");
            System.out.println("LITTLE_ENDIAN:" + encode);
        }
        {
            byte[] bytes0 = ConvertUtil.longToBytes(987654321L, ByteOrder.BIG_ENDIAN);
            byte[] bytes = ConvertUtil.longToBytes(987654321L, ByteOrder.BIG_ENDIAN);
            long longValue = ConvertUtil.bytesToLong(bytes, ByteOrder.BIG_ENDIAN);
            assertEquals(987654321L, longValue);
            String encode = Base64.getEncoder().withoutPadding().encodeToString(bytes);
            assertEquals(encode, "Ot5osQ");
            System.out.println("BIG_ENDIAN:" + encode);
        }
        {
            byte[] bytes = ConvertUtil.longToBytes(0L, ByteOrder.LITTLE_ENDIAN);
            long longValue = ConvertUtil.bytesToLong(bytes, ByteOrder.LITTLE_ENDIAN);
            assertEquals(0L, longValue);
            String encode = Base64.getEncoder().withoutPadding().encodeToString(bytes);
//            assertEquals(encode, "sWjeOg");
            System.out.println("LITTLE_ENDIAN0:" + encode);
        }
        {
            byte[] bytes = ConvertUtil.longToBytes(0L, ByteOrder.BIG_ENDIAN);
            long longValue = ConvertUtil.bytesToLong(bytes, ByteOrder.BIG_ENDIAN);
            assertEquals(0L, longValue);
            String encode = Base64.getEncoder().withoutPadding().encodeToString(bytes);
//            assertEquals(encode, "Ot5osQ");
            System.out.println("BIG_ENDIAN0:" + encode);
        }
        {
            byte[] decode = Base64.getDecoder().decode("AA");
            long result = ConvertUtil.bytesToLong(decode, ByteOrder.BIG_ENDIAN);
            assertEquals(0L, result);
        }
        {
            byte[] decode = Base64.getDecoder().decode("AA");
            long result = ConvertUtil.bytesToLong(decode, ByteOrder.LITTLE_ENDIAN);
            assertEquals(0L, result);
        }
    }

    /**
     */
    @Test
    public void testLongToBytes() {
        System.out.println("longToBytes");
        {
            byte[] bytes = {(byte) 7, (byte) 91, (byte) 205, (byte) 21};
            byte[] result = ConvertUtil.longToBytes(123456789L, ByteOrder.BIG_ENDIAN);
            assertArrayEquals(bytes, result);
        }
        {
            byte[] bytes = {(byte) 21, (byte) 205, (byte) 91, (byte) 7};
            byte[] result = ConvertUtil.longToBytes(123456789L, ByteOrder.LITTLE_ENDIAN);
            assertArrayEquals(bytes, result);
        }
        {
            byte[] result = ConvertUtil.longToBytes(1606602575L, ByteOrder.BIG_ENDIAN);
            byte[] decode = Base64.getDecoder().decode("X8LPTw");
            assertArrayEquals(decode, result);
        }
        {
            byte[] decode = Base64.getDecoder().decode("sWjeOg");
            long longValue = ConvertUtil.bytesToLong(decode, ByteOrder.LITTLE_ENDIAN);
            assertEquals(987654321L, longValue);
            System.out.println("LITTLE_ENDIAN:" + longValue);
            byte[] result = ConvertUtil.longToBytes(longValue, ByteOrder.LITTLE_ENDIAN);
            assertArrayEquals(decode, result);
        }
        {
            byte[] decode = Base64.getDecoder().decode("Ot5osQ");
            long longValue = ConvertUtil.bytesToLong(decode, ByteOrder.BIG_ENDIAN);
            assertEquals(987654321L, longValue);
            System.out.println("BIG_ENDIAN:" + longValue);
            byte[] result = ConvertUtil.longToBytes(longValue, ByteOrder.BIG_ENDIAN);
            assertArrayEquals(decode, result);
        }
        {
            byte[] result = ConvertUtil.longToBytes(0L, ByteOrder.BIG_ENDIAN);
            String encode = Base64.getEncoder().withoutPadding().encodeToString(result);
            assertEquals("AA", encode);
            System.out.println("BIG_ENDIAN:" + encode);
        }
        {
            byte[] result = ConvertUtil.longToBytes(0L, ByteOrder.LITTLE_ENDIAN);
            String encode = Base64.getEncoder().withoutPadding().encodeToString(result);
            assertEquals("AA", encode);
            System.out.println("LITTLE_ENDIAN:" + encode);
        }
    }

    /**
     */
    @Test
    public void testCalcStlength() {
        System.out.println("calcStlength");
        System.out.println(ConvertUtil.calcStlength(16, 20));

    }

    /**
     * Test of decode/encode/StandardLangMeta method, of class TransUtil.
     */
    @Test
    public void testStandardLangMeta() {
        System.out.println("decode/encode/StandardLangMeta");
        assertEquals("a\tb\rc\nd", ConvertUtil.decodeStandardLangMeta("a\\tb\\rc\\nd"));
        assertEquals("a\\b\\c\\\\d", ConvertUtil.decodeStandardLangMeta("a\\b\\c\\\\d"));
        assertEquals("a\\tb\\rc\\nd", ConvertUtil.encodeStandardLangMeta("a\tb\rc\nd"));
        assertEquals("a\\\\d", ConvertUtil.encodeStandardLangMeta("a\\\\d"));
    }

    /**
     * Test of decode/encode/StandardLangMeta method, of class TransUtil.
     */
    @Test
    public void testJsonMeta() {
        System.out.println("decode/encode/testJsonMeta");
        assertEquals("a\tb\rc\nd", ConvertUtil.decodeJsonLiteral("a\\tb\\rc\\nd", true));
        assertEquals("a\b\\c\\d", ConvertUtil.decodeJsonLiteral("a\\b\\c\\\\d", true));
        assertEquals("a\\tb\\rc\\nd", ConvertUtil.encodeJsonLiteral("a\tb\rc\nd", true));
        assertEquals("a\\\\\\\\d", ConvertUtil.encodeJsonLiteral("a\\\\d", true));

        assertEquals("test\\/\\r\\n\\t\\b\\f\\\"\\\\u0027testu0027", ConvertUtil.encodeJsonLiteral("test/\r\n\t\b\f\"\\u0027testu0027", true));
        assertEquals("test/\r\n\t\b\f\"\\u0027testu0027", ConvertUtil.decodeJsonLiteral("test\\/\\r\\n\\t\\b\\f\\\"\\\\u0027testu0027", true));
    }

    /**
     * Test of encodeJsLangMeta,decodeJsLangMeta method, of class TransUtil.
     */
    @Test
    public void testJsLangMeta() {
        System.out.println("JsLangMeta");
        assertEquals("abc\rdef\nghi\tfgh\\IJK", ConvertUtil.decodeJsLangMeta("abc\\rdef\\nghi\\tfgh\\\\IJK"));
        assertEquals("abc" + Character.toString((char) 0x08) + "def" + Character.toString((char) 0x0c) + "ghi" + Character.toString((char) 0x0b) + "IJK", ConvertUtil.decodeJsLangMeta("abc\\bdef\\fghi\\vIJK"));
        assertEquals("abcdefghi\tIJK", ConvertUtil.decodeJsLangMeta("\\x61bcdefghi\\x09I\\x4A\\x4b"));
        assertEquals("abcdefghi\tIJK", ConvertUtil.decodeJsLangMeta("\\u0061bcdefghi\\u0009I\\u004A\\u004b"));
        assertEquals("あいうえお", ConvertUtil.decodeJsLangMeta("\\u3042\\u3044\\u3046\\u3048\\u304a"));
        assertEquals("あ\r\nいうえお", ConvertUtil.decodeJsLangMeta("\\u3042\\r\\n\\u3044\\u3046\\u3048\\u304a"));
        // regexp
        assertEquals("\\$1", Matcher.quoteReplacement(ConvertUtil.decodeJsLangMeta("$1")));
        assertEquals("\\\\\\$1", Matcher.quoteReplacement(ConvertUtil.decodeJsLangMeta("\\$1")));
        assertEquals("\r\\\\\\$1", Matcher.quoteReplacement(ConvertUtil.decodeJsLangMeta("\\r\\$1")));
    }

    /**
     * Test of encodeJsLangQuote,decodeJsLangQuote method, of class TransUtil.
     */
    @Test
    public void testJsLangQuote() {
        System.out.println("testJsLangQuote");
        assertEquals("host", ConvertUtil.encodeJsLangQuote("host", false));
        assertEquals("\\\\123\\\"456\\\\", ConvertUtil.encodeJsLangQuote("\\123\"456\\", false));
        assertEquals("\\\\123\\\'456\\\\", ConvertUtil.encodeJsLangQuote("\\123'456\\", false));

        assertEquals("host", ConvertUtil.decodeJsLangQuote("host", false));
        assertEquals("\\123\"456\\", ConvertUtil.decodeJsLangQuote("\\\\123\\\"456\\\\", false));
        assertEquals("\\123\'456\\", ConvertUtil.decodeJsLangQuote("\\\\123\\\'456\\\\", false));

        // metachar
        assertEquals("\\r\\nhost\\t", ConvertUtil.encodeJsLangQuote("\r\nhost\t", true));
        assertEquals("ho\\\\st", ConvertUtil.encodeJsLangQuote("ho\\st", true));
        assertEquals("\\\\123\\\"456\\\\", ConvertUtil.encodeJsLangQuote("\\123\"456\\", true));
        assertEquals("\\\\123\\\'456\\\\", ConvertUtil.encodeJsLangQuote("\\123'456\\", true));

        assertEquals("\r\nhost\t", ConvertUtil.decodeJsLangQuote("\\r\\nhost\\t", true));
        assertEquals("ho\\st", ConvertUtil.decodeJsLangQuote("ho\\\\st", true));
        assertEquals("\\123\"456\\", ConvertUtil.decodeJsLangQuote("\\\\123\\\"456\\\\", true));
        assertEquals("\\123\'456\\", ConvertUtil.decodeJsLangQuote("\\\\123\\\'456\\\\", true));
    }

    /**
     * Test of encodeCLangQuote,decodeCLangQuote method, of class TransUtil.
     */
    @Test
    public void testCLangQuote() {
        System.out.println("testCLangQuote");
        assertEquals("host", ConvertUtil.encodeCLangQuote("host", false));
        assertEquals("\\\\123\\\"456\\\\", ConvertUtil.encodeCLangQuote("\\123\"456\\", false));
        assertEquals("\\\\123'456\\\\", ConvertUtil.encodeCLangQuote("\\123'456\\", false));

        assertEquals("host", ConvertUtil.decodeCLangQuote("host", false));
        assertEquals("\\123\"456\\", ConvertUtil.decodeCLangQuote("\\\\123\\\"456\\\\", false));
        assertEquals("\\123'456\\", ConvertUtil.decodeCLangQuote("\\\\123'456\\\\", false));

        // metachar
        assertEquals("\\r\\nhost\\t", ConvertUtil.encodeCLangQuote("\r\nhost\t", true));
        assertEquals("ho\\\\st", ConvertUtil.encodeCLangQuote("ho\\st", true));
        assertEquals("\\\\123\\\"456\\\\", ConvertUtil.encodeCLangQuote("\\123\"456\\", true));
        assertEquals("\\\\123'456\\\\", ConvertUtil.encodeCLangQuote("\\123'456\\", true));

        assertEquals("\r\nhost\t", ConvertUtil.decodeCLangQuote("\\r\\nhost\\t", true));
        assertEquals("ho\\st", ConvertUtil.decodeCLangQuote("ho\\\\st", true));
        assertEquals("\\123\"456\\", ConvertUtil.decodeCLangQuote("\\\\123\\\"456\\\\", true));
        assertEquals("\\123'456\\", ConvertUtil.decodeCLangQuote("\\\\123'456\\\\", true));
    }

    /**
     * Test of encodeSQLLangQuote,decodeSQLLangQuote method, of class TransUtil.
     */
    @Test
    public void testSQLLangQuote() {
        System.out.println("testSQLLangQuote");
        assertEquals("host", ConvertUtil.encodeSQLLangQuote("host", false));
        assertEquals("ho\\st", ConvertUtil.encodeSQLLangQuote("ho\\st", false));
        assertEquals("\\123''456\\", ConvertUtil.encodeSQLLangQuote("\\123'456\\", false));
        assertEquals("\\123\"456\\", ConvertUtil.encodeSQLLangQuote("\\123\"456\\", false));

        // metachar
        assertEquals("\\r\\nhost\\t", ConvertUtil.encodeSQLLangQuote("\r\nhost\t", true));
        assertEquals("ho\\st", ConvertUtil.encodeSQLLangQuote("ho\\st", true));
        assertEquals("\\\\123'456\\\\", ConvertUtil.decodeSQLangQuote("\\\\123''456\\\\", true));
        assertEquals("\\123\"456\\", ConvertUtil.encodeSQLLangQuote("\\123\"456\\", true));

        assertEquals("\r\nhost\t", ConvertUtil.decodeSQLangQuote("\\r\\nhost\\t", true));
        assertEquals("ho\\st", ConvertUtil.decodeSQLangQuote("ho\\st", true));
        assertEquals("\\\\123'456\\\\", ConvertUtil.decodeSQLangQuote("\\\\123''456\\\\", true));
        assertEquals("\\123\"456\\", ConvertUtil.decodeSQLangQuote("\\123\"456\\", true));
    }

    @Test
    public void testToRegexEncodeDecode() {
        System.out.println("testToRegexEncode");
        String expValue = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        {
            String regex = ConvertUtil.toRegexEncode(expValue, false);
            Pattern ptn = Pattern.compile(regex);
            String expResult = ConvertUtil.toRegexDecode(regex, false);
            assertEquals(expResult, expValue);
        }
    }

}
