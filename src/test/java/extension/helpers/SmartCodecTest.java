package extension.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author t.isayama
 */
public class SmartCodecTest {

    private final static Logger logger = Logger.getLogger(SmartCodecTest.class.getName());

    public SmartCodecTest() {
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
     * Test of toHtmlEncode method, of class TransUtil.
     */
    @Test
    public void testToHtmlEncode() {
        System.out.println("testHtmlEncode");
        assertEquals("!&quot;#$%&amp;'()=~|`{}*+&lt;&gt;?_\\\r\nabcedf", HttpUtil.toHtmlEncode("!\"#$%&'()=~|`{}*+<>?_\\\r\nabcedf"));
    }

    @Test
    public void testSmartCodecHtmlDecode() {
        System.out.println("testSmartCodecHtmlDecode");
        assertEquals("!\"#$%&'()=~|`{}*+<>?_\\\r\nabcedf", SmartCodec.toHtmlDecode("!&quot;#$%&amp;'()=~|`{}*+&lt;&gt;?_\\\r\nabcedf"));
    }


    /**
     * Test of toHtmlDecode method, of class TransUtil.
     */
    @Test
    public void testToHtmlDecode() {
        System.out.println("toHtmlDecode");
        assertEquals("!\"#$%&'()=~|`{}*+<>?_\\abcedf", SmartCodec.toHtmlDecode("!&quot;#$%&amp;&#39;()=~|`{}*+&lt;&gt;?_\\abcedf"));
        assertEquals("'''", SmartCodec.toHtmlDecode("&#39;&#x27;&#X27;", SmartCodec.ENCODE_PATTERN_ALL));
        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals(x, SmartCodec.toHtmlDecode("&#106;&#107;&#102;&#131083;&#135741;&#103;&#104;&#105;&#135963;&#136302;&#137405;&#134047;&#136884;&#138804;&#143812;&#144836;&#97;&#98;&#122;&#48;&#49;&#57;", SmartCodec.ENCODE_PATTERN_ALL));
        assertEquals(x, SmartCodec.toHtmlDecode("&#x6a;&#x6b;&#x66;&#x2000b;&#x2123d;&#x67;&#x68;&#x69;&#x2131b;&#x2146e;&#x218bd;&#x20b9f;&#x216b4;&#x21e34;&#x231c4;&#x235c4;&#x61;&#x62;&#x7a;&#x30;&#x31;&#x39;", SmartCodec.ENCODE_PATTERN_ALL));
        assertEquals(x, SmartCodec.toHtmlDecode("&#X6A;&#X6B;&#X66;&#X2000B;&#X2123D;&#X67;&#X68;&#X69;&#X2131B;&#X2146E;&#X218BD;&#X20B9F;&#X216B4;&#X21E34;&#X231C4;&#X235C4;&#X61;&#X62;&#X7A;&#X30;&#X31;&#X39;", SmartCodec.ENCODE_PATTERN_ALL));
    }

    private final String entities_html = "&nbsp;&iexcl;&cent;&pound;&curren;&yen;&brvbar;&sect;&uml;&copy;&ordf;&laquo;&not;&shy;&reg;&macr;&deg;&plusmn;&sup2;&sup3;&acute;&micro;&para;&middot;&cedil;&sup1;&ordm;&raquo;&frac14;&frac12;&frac34;&iquest;&Agrave;&Aacute;&Acirc;&Atilde;&Auml;&Aring;&AElig;&Ccedil;&Egrave;&Eacute;&Ecirc;&Euml;&Igrave;&Iacute;&Icirc;&Iuml;&ETH;&Ntilde;&Ograve;&Oacute;&Ocirc;&Otilde;&Ouml;&times;&Oslash;&Ugrave;&Uacute;&Ucirc;&Uuml2;&Yacute;&THORN;&szlig;&agrave;&aacute;&acirc;&atilde;&auml;&aring;&aelig;&ccedil;&egrave;&eacute;&ecirc;&euml;&igrave;&iacute;&icirc;&iuml;&eth;&ntilde;&ograve;&oacute;&ocirc;&otilde;&ouml;&divide;&oslash;&ugrave;&uacute;&ucirc;&uuml;&yacute;&thorn;&yuml;";

    @Test
    public void testToHtmlEntityDecode() {
        char ch[] = new char[]{(char) 160, (char) 161, (char) 162, (char) 163, (char) 164, (char) 165, (char) 166, (char) 167, (char) 168, (char) 169, (char) 170, (char) 171, (char) 172, (char) 173, (char) 174, (char) 175, (char) 176, (char) 177, (char) 178, (char) 179, (char) 180, (char) 181, (char) 182, (char) 183, (char) 184, (char) 185, (char) 186, (char) 187, (char) 188, (char) 189, (char) 190, (char) 191, (char) 192, (char) 193, (char) 194, (char) 195, (char) 196, (char) 197, (char) 198, (char) 199, (char) 200, (char) 201, (char) 202, (char) 203, (char) 204, (char) 205, (char) 206, (char) 207, (char) 208, (char) 209, (char) 210, (char) 211, (char) 212, (char) 213, (char) 214, (char) 215, (char) 216, (char) 217, (char) 218, (char) 219, (char) 220, (char) 221, (char) 222, (char) 223, (char) 224, (char) 225, (char) 226, (char) 227, (char) 228, (char) 229, (char) 230, (char) 231, (char) 232, (char) 233, (char) 234, (char) 235, (char) 236, (char) 237, (char) 238, (char) 239, (char) 240, (char) 241, (char) 242, (char) 243, (char) 244, (char) 245, (char) 246, (char) 247, (char) 248, (char) 249, (char) 250, (char) 251, (char) 252, (char) 253, (char) 254, (char) 255};
        assertEquals(new String(ch), SmartCodec.toHtmlDecode(entities_html));
    }

    /**
     * Test of toHtmlDecEncode method, of class TransUtil.
     */
    @Test
    public void testToHtmlDecEncode() {
        System.out.println("toHtmlDecEncode");
        assertEquals("&#33;&#34;&#35;&#36;&#37;&#38;&#39;&#40;&#41;&#61;&#126;&#124;&#96;&#123;&#125;&#42;&#43;&#60;&#62;&#63;&#95;&#92;&#13;&#10;abcdef", SmartCodec.toHtmlDecEncode("!\"#$%&'()=~|`{}*+<>?_\\\r\nabcdef"));
        assertEquals("&#33;&#34;&#35;&#36;&#37;&#38;&#39;&#40;&#41;&#61;&#126;&#124;&#96;&#123;&#125;&#42;&#43;&#60;&#62;&#63;&#95;&#92;&#13;&#10;&#97;&#98;&#99;&#101;&#100;&#101;&#102;", SmartCodec.toHtmlDecEncode("!\"#$%&'()=~|`{}*+<>?_\\\r\nabcedef", SmartCodec.ENCODE_PATTERN_ALL));
        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals("jkf&#131083;&#135741;ghi&#135963;&#136302;&#137405;&#134047;&#136884;&#138804;&#143812;&#144836;abz019", SmartCodec.toHtmlDecEncode(x));
        assertEquals("&#106;&#107;&#102;&#131083;&#135741;&#103;&#104;&#105;&#135963;&#136302;&#137405;&#134047;&#136884;&#138804;&#143812;&#144836;&#97;&#98;&#122;&#48;&#49;&#57;", SmartCodec.toHtmlDecEncode(x, SmartCodec.ENCODE_PATTERN_ALL));
    }

    /**
     * Test of toHtmlUnicodeEncode method, of class TransUtil.
     */
    @Test
    public void testToHtmUnicodeEncode() {
        System.out.println("toHtmlUnicodeEncode");
        assertEquals("&#x21;&#x22;&#x23;&#x24;&#x25;&#x26;&#x27;&#x28;&#x29;&#x3d;&#x7e;&#x7c;&#x60;&#x7b;&#x7d;&#x2a;&#x2b;&#x3c;&#x3e;&#x3f;&#x5f;&#x5c;&#xd;&#xa;abcedf", SmartCodec.toHtmlUnicodeEncode("!\"#$%&'()=~|`{}*+<>?_\\\r\nabcedf", false));
        assertEquals("&#X21;&#X22;&#X23;&#X24;&#X25;&#X26;&#X27;&#X28;&#X29;&#X3D;&#X7E;&#X7C;&#X60;&#X7B;&#X7D;&#X2A;&#X2B;&#X3C;&#X3E;&#X3F;&#X5F;&#X5C;&#XD;&#XA;abcedf", SmartCodec.toHtmlUnicodeEncode("!\"#$%&'()=~|`{}*+<>?_\\\r\nabcedf", true));

        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        System.out.println("c:" + SmartCodec.toHtmlUnicodeEncode(x, false));
        assertEquals("jkf&#x2000b;&#x2123d;ghi&#x2131b;&#x2146e;&#x218bd;&#x20b9f;&#x216b4;&#x21e34;&#x231c4;&#x235c4;abz019", SmartCodec.toHtmlUnicodeEncode(x, false));
        assertEquals("jkf&#X2000B;&#X2123D;ghi&#X2131B;&#X2146E;&#X218BD;&#X20B9F;&#X216B4;&#X21E34;&#X231C4;&#X235C4;abz019", SmartCodec.toHtmlUnicodeEncode(x, true));
        assertEquals("&#x6a;&#x6b;&#x66;&#x2000b;&#x2123d;&#x67;&#x68;&#x69;&#x2131b;&#x2146e;&#x218bd;&#x20b9f;&#x216b4;&#x21e34;&#x231c4;&#x235c4;&#x61;&#x62;&#x7a;&#x30;&#x31;&#x39;", SmartCodec.toHtmlUnicodeEncode(x, SmartCodec.ENCODE_PATTERN_ALL, false));
    }

    /**
     * Test of URLDecode method, of class TransUtil.
     */
    @Test
    public void testDecodeUrl() {
        System.out.println("URLDecode");
        try {
            assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", SmartCodec.toUrlEncode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", "Shift_JIS", true));
            assertEquals("\r\n\t", SmartCodec.toUrlDecode("%0d%0a%09", "Shift_JIS"));
            assertEquals("\r\n\t", SmartCodec.toUrlDecode("%0D%0A%09", "Shift_JIS"));
            assertEquals("abc", SmartCodec.toUrlDecode("%61%62%63", "Shift_JIS"));
            assertEquals("テスト", SmartCodec.toUrlDecode("%83e%83X%83g", "Shift_JIS"));
            assertEquals(" + ", SmartCodec.toUrlDecode("%20%2B+", "Shift_JIS"));
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of URLEncode method, of class TransUtil.
     */
    @Test
    public void testEncodeUrl() {
        System.out.println("URLEncode");
        try {
            assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", SmartCodec.toUrlEncode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", "Shift_JIS", false));
            assertEquals("%61%62%63%64%65%66%67%68%69%6a%6b%6c%6d%6e%6f%70%71%72%73%74%75%76%77%78%79%7a%41%42%43%44%45%46%47%48%49%4a%4b%4c%4d%4e%4f%50%51%52%53%54%55%56%57%58%59%5a%30%31%32%33%34%35%36%37%38%39", SmartCodec.toUrlEncode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", "Shift_JIS", SmartCodec.ENCODE_PATTERN_ALL, false));
            assertEquals("%0D%0A%09", SmartCodec.toUrlEncode("\r\n\t", "Shift_JIS", true));
            assertEquals("%0d%0a%09", SmartCodec.toUrlEncode("\r\n\t", "Shift_JIS", false));
            assertEquals("%83e%83X%83g", SmartCodec.toUrlEncode("テスト", "Shift_JIS", false));
            assertEquals("+%2b+", SmartCodec.toUrlEncode(" + ", "Shift_JIS", false));
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    /**
     * Test of toUnocodeEncode method, of class TransUtil.
     */
    @Test
    public void testToUnocodeEncode() {
        System.out.println("toUnocodeEncode");
        assertEquals("abcdef\\u000d\\u000a\\u0021\\u0022ghi\\u0023\\u0024\\u0025jkf", SmartCodec.toUnocodeEncode("abcdef\r\n!\"ghi#$%jkf", false));
        assertEquals("\\u0061\\u0062\\u0063\\u0064\\u0065\\u0066\\u000d\\u000a\\u0021\\u0022\\u0067\\u0068\\u0069\\u0023\\u0024\\u0025\\u006a\\u006b\\u0066", SmartCodec.toUnocodeEncode("abcdef\r\n!\"ghi#$%jkf", SmartCodec.ENCODE_PATTERN_ALL, false));
        assertEquals("\\U0061\\U0062\\U0063\\U0064\\U0065\\U0066\\U000D\\U000A\\U0021\\U0022\\U0067\\U0068\\U0069\\U0023\\U0024\\U0025\\U006A\\U006B\\U0066", SmartCodec.toUnocodeEncode("abcdef\r\n!\"ghi#$%jkf", SmartCodec.ENCODE_PATTERN_ALL, true));

        assertEquals("\\u3042\\u3044\\u3046\\u3048\\u304a", SmartCodec.toUnocodeEncode("あいうえお", false));
        assertEquals("\\U3042\\U3044\\U3046\\U3048\\U304A", SmartCodec.toUnocodeEncode("あいうえお", true));

        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals("jkf\\ud840\\udc0b\\ud844\\ude3dghi\\ud844\\udf1b\\ud845\\udc6e\\ud846\\udcbd\\ud842\\udf9f\\ud845\\udeb4\\ud847\\ude34\\ud84c\\uddc4\\ud84d\\uddc4abz019", SmartCodec.toUnocodeEncode(x, false));
        assertEquals("jkf\\UD840\\UDC0B\\UD844\\UDE3Dghi\\UD844\\UDF1B\\UD845\\UDC6E\\UD846\\UDCBD\\UD842\\UDF9F\\UD845\\UDEB4\\UD847\\UDE34\\UD84C\\UDDC4\\UD84D\\UDDC4abz019", SmartCodec.toUnocodeEncode(x, true));

        assertEquals("\\u006a\\u006b\\u0066\\ud840\\udc0b\\ud844\\ude3d\\u0067\\u0068\\u0069\\ud844\\udf1b\\ud845\\udc6e\\ud846\\udcbd\\ud842\\udf9f\\ud845\\udeb4\\ud847\\ude34\\ud84c\\uddc4\\ud84d\\uddc4\\u0061\\u0062\\u007a\\u0030\\u0031\\u0039", SmartCodec.toUnocodeEncode(x, SmartCodec.ENCODE_PATTERN_ALL, false));
        assertEquals("\\U006A\\U006B\\U0066\\UD840\\UDC0B\\UD844\\UDE3D\\U0067\\U0068\\U0069\\UD844\\UDF1B\\UD845\\UDC6E\\UD846\\UDCBD\\UD842\\UDF9F\\UD845\\UDEB4\\UD847\\UDE34\\UD84C\\UDDC4\\UD84D\\UDDC4\\U0061\\U0062\\U007A\\U0030\\U0031\\U0039", SmartCodec.toUnocodeEncode(x, SmartCodec.ENCODE_PATTERN_ALL, true));

        //
        String surrogatePairDecode = "𠀋𣜿𦹀𠈓𠠇";
        String surrogatePairEncode = "\\ud840\\udc0b\\ud84d\\udf3f\\ud85b\\ude40\\ud840\\ude13\\ud842\\udc07"; // U+2000B
        assertEquals(surrogatePairEncode, SmartCodec.toUnocodeEncode(surrogatePairDecode, false));

    }

    /**
     * Test of toUnocodeEncode2 method, of class TransUtil.
     */
    @Test
    public void testToUnocodeEncode2() {
        System.out.println("toUnocodeEncode");
        assertEquals("abcdef$000d$000a$0021$0022ghi$0023$0024$0025jkf", SmartCodec.toUnocodeEncode("abcdef\r\n!\"ghi#$%jkf", "$", SmartCodec.ENCODE_PATTERN_ALPHANUM, false));
        assertEquals("$0061$0062$0063$0064$0065$0066$000d$000a$0021$0022$0067$0068$0069$0023$0024$0025$006a$006b$0066", SmartCodec.toUnocodeEncode("abcdef\r\n!\"ghi#$%jkf", "$", SmartCodec.ENCODE_PATTERN_ALL, false));

        assertEquals("$3042$3044$3046$3048$304a", SmartCodec.toUnocodeEncode("あいうえお", "$", SmartCodec.ENCODE_PATTERN_ALPHANUM, false));

        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals("jkf$d840$dc0b$d844$de3dghi$d844$df1b$d845$dc6e$d846$dcbd$d842$df9f$d845$deb4$d847$de34$d84c$ddc4$d84d$ddc4abz019", SmartCodec.toUnocodeEncode(x, "$", SmartCodec.ENCODE_PATTERN_ALPHANUM, false));
        assertEquals("$006a$006b$0066$d840$dc0b$d844$de3d$0067$0068$0069$d844$df1b$d845$dc6e$d846$dcbd$d842$df9f$d845$deb4$d847$de34$d84c$ddc4$d84d$ddc4$0061$0062$007a$0030$0031$0039", SmartCodec.toUnocodeEncode(x, "$", SmartCodec.ENCODE_PATTERN_ALL, false));
    }

    /**
     * Test of toUnocodeDecode method, of class TransUtil.
     */
    @Test
    public void testToUnocodeDecode() {
        System.out.println("toUnocodeDecode");
        assertEquals("abcdef!\"#$%", SmartCodec.toUnocodeDecode("abcdef\\u0021\\u0022\\u0023\\u0024\\u0025"));
        assertEquals("abcdef!\"#$%", SmartCodec.toUnocodeDecode("abcdef\\U0021\\U0022\\U0023\\U0024\\U0025"));

        assertEquals("あいうえお", SmartCodec.toUnocodeDecode("\\u3042\\u3044\\u3046\\u3048\\u304a"));
        assertEquals("あいうえお", SmartCodec.toUnocodeDecode("\\U3042\\U3044\\U3046\\U3048\\U304A"));

        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals(x, SmartCodec.toUnocodeDecode("jkf\\ud840\\udc0b\\ud844\\ude3dghi\\ud844\\udf1b\\ud845\\udc6e\\ud846\\udcbd\\ud842\\udf9f\\ud845\\udeb4\\ud847\\ude34\\ud84c\\uddc4\\ud84d\\uddc4abz019"));
        assertEquals(x, SmartCodec.toUnocodeDecode("jkf\\UD840\\UDC0B\\UD844\\UDE3Dghi\\UD844\\UDF1B\\UD845\\UDC6E\\UD846\\UDCBD\\UD842\\UDF9F\\UD845\\UDEB4\\UD847\\UDE34\\UD84C\\UDDC4\\UD84D\\UDDC4abz019"));

        //
        String surrogatePairDecode = "𠀋𣜿𦹀𠈓𠠇";
        String surrogatePairEncode = "\\ud840\\udc0b\\ud84d\\udf3f\\ud85b\\ude40\\ud840\\ude13\\ud842\\udc07"; // U+2000B
        assertEquals(surrogatePairDecode, SmartCodec.toUnocodeDecode(surrogatePairEncode));

    }

    /**
     * Test of toUnocodeDecode2 method, of class TransUtil.
     */
    @Test
    public void testToUnocodeDecode2() {
        System.out.println("toUnocodeDecode2");
        assertEquals("abcdef!\"#$%", SmartCodec.toUnocodeDecode("abcdef$0021$0022$0023$0024$0025", Pattern.quote("$")));
        assertEquals("あいうえお", SmartCodec.toUnocodeDecode("$3042$3044$3046$3048$304a", Pattern.quote("$")));
        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals(x, SmartCodec.toUnocodeDecode("jkf$d840$dc0b$d844$de3dghi$d844$df1b$d845$dc6e$d846$dcbd$d842$df9f$d845$deb4$d847$de34$d84c$ddc4$d84d$ddc4abz019", Pattern.quote("$")));
    }

    /**
     * Test of toUnocodeUrlEncode method, of class TransUtil.
     */
    @Test
    public void testToUnocodeUrlEncode() {
        System.out.println("toUnocodeUrlEncode");
        assertEquals("abcdef%u000d%u000a%u0021%u0022%u0023%u0024%u0025", SmartCodec.toUnocodeUrlEncode("abcdef\r\n!\"#$%", false));
        assertEquals("%u3042%u3044%u3046%u3048%u304a", SmartCodec.toUnocodeUrlEncode("あいうえお", false));
        assertEquals("%U3042%U3044%U3046%U3048%U304A", SmartCodec.toUnocodeUrlEncode("あいうえお", true));
        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals("jkf%ud840%udc0b%ud844%ude3dghi%ud844%udf1b%ud845%udc6e%ud846%udcbd%ud842%udf9f%ud845%udeb4%ud847%ude34%ud84c%uddc4%ud84d%uddc4abz019", SmartCodec.toUnocodeUrlEncode(x, false));
        assertEquals("jkf%UD840%UDC0B%UD844%UDE3Dghi%UD844%UDF1B%UD845%UDC6E%UD846%UDCBD%UD842%UDF9F%UD845%UDEB4%UD847%UDE34%UD84C%UDDC4%UD84D%UDDC4abz019", SmartCodec.toUnocodeUrlEncode(x, true));
//        assertEquals("%u006a%u006b%u0066%ud840%udc0b%ud844%ude3d%u0067%u0068%u0069%ud844%udf1b%ud845%udc6e%ud846%udcbd%ud842%udf9f%ud845%udeb4%ud847%ude34%ud84c%uddc4%ud84d%uddc4%u0061%u0062%u007a%u0030%u0031%u0039", TransUtil.toUnocodeUrlEncode(x, false));
    }

    /**
     * Test of toUnocodeUrlDecode method, of class TransUtil.
     */
    @Test
    public void testToUnocodeUrlDecode() {
        System.out.println("toUnocodeUrlDecode");
        assertEquals("abcdef!\"#$%", SmartCodec.toUnicodeUrlDecode("abcdef%u0021%u0022%u0023%u0024%u0025", SmartCodec.ENCODE_PATTERN_ALL));
        assertEquals("あいうえお", SmartCodec.toUnicodeUrlDecode("%u3042%u3044%u3046%u3048%u304a"));
        int ch[] = new int[]{(int) 'j', (int) 'k', (int) 'f', 0x2000B, 0x2123D, (int) 'g', (int) 'h', (int) 'i', 0x2131B, 0x2146E, 0x218BD, 0x20B9F, 0x216B4, 0x21E34, 0x231C4, 0x235C4, (int) 'a', (int) 'b', (int) 'z', (int) '0', (int) '1', (int) '9'};
        String x = new String(ch, 0, ch.length);
        assertEquals(x, SmartCodec.toUnicodeUrlDecode("jkf%ud840%udc0b%ud844%ude3dghi%ud844%udf1b%ud845%udc6e%ud846%udcbd%ud842%udf9f%ud845%udeb4%ud847%ude34%ud84c%uddc4%ud84d%uddc4abz019"));
    }

    @Test
    public void testSurrogatePair() {
        String surrogatePair = "𠀋𣜿𦹀𠈓𠠇";
        for (int i = 0; i < surrogatePair.length(); i = surrogatePair.offsetByCodePoints(i, 1)) {
            int codePoint = surrogatePair.codePointAt(i);
            char chHigh = Character.highSurrogate​(codePoint);
            char chLow = Character.lowSurrogate(codePoint);
            System.out.print("\\u" + Integer.toHexString(chHigh));
            System.out.print("\\u" + Integer.toHexString(chLow));
            System.out.println();
        }
        {
            char chHigh = (char) 0xd840;
            char chLow = (char) 0xdc0b;
            int codePoint = Character.toCodePoint(chHigh, chLow);
            System.out.println("ch:" + new String(new int[]{codePoint}, 0, 1));
        }
    }

    @Test
    public void testUnicodeEncode() {
        System.out.println("testUnicodeDecode");
        {
            String surrogatePair = "𠀋𣜿𦹀𠈓𠠇";
            String encode = SmartCodec.toUnocodeEncode(surrogatePair, false);
            assertEquals(encode, "\\ud840\\udc0b\\ud84d\\udf3f\\ud85b\\ude40\\ud840\\ude13\\ud842\\udc07");
        }
        {
            String surrogatePair = "abc𠀋𣜿𦹀𠈓𠠇xyz";
            String encode = SmartCodec.toUnocodeEncode(surrogatePair, false);
            assertEquals(encode, "abc\\ud840\\udc0b\\ud84d\\udf3f\\ud85b\\ude40\\ud840\\ude13\\ud842\\udc07xyz");
        }
        {
            String surrogatePair = "'\"<abc𠀋𣜿𦹀𠈓𠠇xyz>\"'";
            String encode = SmartCodec.toUnocodeEncode(surrogatePair, SmartCodec.ENCODE_PATTERN_LIGHT, false);
            assertEquals(encode, "'\"<abc\\ud840\\udc0b\\ud84d\\udf3f\\ud85b\\ude40\\ud840\\ude13\\ud842\\udc07xyz>\"'");
        }
    }

    @Test
    public void testUnicodeDecode() {
        System.out.println("testUnicodeDecode");
        {
            String surrogatePair = "\\ud840\\udc0b\\ud84d\\udf3f\\ud85b\\ude40\\ud840\\ude13\\ud842\\udc07";
            String decode = SmartCodec.toUnicodeDecode(surrogatePair);
            assertEquals(decode, "𠀋𣜿𦹀𠈓𠠇");
        }
        {
            String surrogatePair = "abc\\ud840\\udc0b\\ud84d\\udf3f\\ud85b\\ude40\\ud840\\ude13\\ud842\\udc07xyz";
            String decode = SmartCodec.toUnicodeDecode(surrogatePair);
            assertEquals(decode, "abc𠀋𣜿𦹀𠈓𠠇xyz");
        }
    }

    @Test
    public void testHtmlDecode() {
        {
            String html = "<html><h4>&#12510;&#12523;&#12481;&#12496;&#12452;&#12488;&#12486;&#12473;&#12488;</h4></html>";
            String decode = SmartCodec.toHtmlDecode(html);
            assertEquals(decode, "<html><h4>マルチバイトテスト</h4></html>");
        }
        {
            String html = "<html><h4>&#x30de;&#x30eb;&#x30c1;&#x30d0;&#x30a4;&#x30c8;&#x30c6;&#x30b9;&#x30c8;</h4></html>";
            String decode = SmartCodec.toHtmlDecode(html);
            assertEquals(decode, "<html><h4>マルチバイトテスト</h4></html>");
        }
        {
            String html = "<html><h4>&#x21;&#x22;&#x23;&#x24;&#x25;&#x26;&#x27;&#x28;&#x29;&#33;&#34;&#35;&#36;&#37;&#38;&#39;&#40;&#41;1234567890&lt;&#12510;&#12523;&#12481;&#12496;&#12452;&#12488;&#12486;&#12473;&#12488;abcdef&gt;</h4></html>";
            String decode = SmartCodec.toHtmlDecode(html);
            assertEquals(decode, "<html><h4>!\"#$%&'()!\"#$%&'()1234567890<マルチバイトテストabcdef></h4></html>");
        }
        {
            String html = "<html><h4>&#x21;&#x22;&#x23;&#x24;&#x25;&#x26;&#x27;&#x28;&#x29;&#33;&#34;&#35;&#36;&#37;&#38;&#39;&#40;&#41;1234567890&lt;&#12510;&#12523;&#12481;&#12496;&#12452;&#12488;&#12486;&#12473;&#12488;abcdef&gt;</h4></html>";
            String decode = SmartCodec.toHtmlDecode(html, SmartCodec.ENCODE_PATTERN_ASCII);
            assertEquals(decode, "<html><h4>&#x21;&#x22;&#x23;&#x24;&#x25;&#x26;&#x27;&#x28;&#x29;&#33;&#34;&#35;&#36;&#37;&#38;&#39;&#40;&#41;1234567890&lt;マルチバイトテストabcdef&gt;</h4></html>");
        }

    }

    @Test
    public void testHtmlDecodeCharset() {
        try {
            {
                String html = "<html><h4>&#x83;&#x7d;&#x83;&#x8b;&#x83;&#x60;&#x83;&#x6f;&#x83;&#x43;&#x83;&#x67;&#x83;&#x65;&#x83;&#x58;&#x83;&#x67;</h4></html>";
                String decode = SmartCodec.toHtmlDecode(html, "Shift_JIS");
                assertEquals(decode, "<html><h4>マルチバイトテスト</h4></html>");
            }
            {
                String html = "<html><h4>&#xe3;&#x83;&#x9e;&#xe3;&#x83;&#xab;&#xe3;&#x83;&#x81;&#xe3;&#x83;&#x90;&#xe3;&#x82;&#xa4;&#xe3;&#x83;&#x88;&#xe3;&#x83;&#x86;&#xe3;&#x82;&#xb9;&#xe3;&#x83;&#x88;</h4></html>";
                String decode = SmartCodec.toHtmlDecode(html, StandardCharsets.UTF_8.name());
                assertEquals(decode, "<html><h4>マルチバイトテスト</h4></html>");
            }
            {
                String html = "<html><h4>&#x30de;&#x30eb;&#x30c1;&#x30d0;&#x30a4;&#x30c8;&#x30c6;&#x30b9;&#x30c8;</h4></html>";
                String decode = SmartCodec.toHtmlUnicodeDecode(html);
                assertEquals(decode, "<html><h4>マルチバイトテスト</h4></html>");
            }
            {
                String html = "<html><h4>&#x21;&#x22;&#x23;&#x24;&#x25;&#x26;&#x27;&#x28;&#x29;&#33;&#34;&#35;&#36;&#37;&#38;&#39;&#40;&#41;1234567890&lt;&#12510;&#12523;&#12481;&#12496;&#12452;&#12488;&#12486;&#12473;&#12488;abcdef&gt;</h4></html>";
                String decode = SmartCodec.toHtmlUnicodeDecode(html);
                assertEquals(decode, "<html><h4>!\"#$%&'()!\"#$%&'()1234567890<マルチバイトテストabcdef></h4></html>");
            }
            {
                String html = "<html><h4>&#x21;&#x22;&#x23;&#x24;&#x25;&#x26;&#x27;&#x28;&#x29;&#33;&#34;&#35;&#36;&#37;&#38;&#39;&#40;&#41;1234567890&lt;&#12510;&#12523;&#12481;&#12496;&#12452;&#12488;&#12486;&#12473;&#12488;abcdef&gt;</h4></html>";
                String decode = SmartCodec.toHtmlUnicodeDecode(html, SmartCodec.ENCODE_PATTERN_STANDARD);
                assertEquals(decode, "<html><h4>&#x21;&#x22;#&#x24;%&#x26;&#x27;&#x28;&#x29;&#33;&#34;#&#36;%&#38;&#39;&#40;&#41;1234567890<マルチバイトテストabcdef></h4></html>");
            }
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    @Test
    public void testSmartDecode() {
        try {
            System.out.println("testSmartDecode");
            InputStream streamUTF8 = FileUtilTest.class.getResourceAsStream("/resources/encode.html");
            String contentRaw = StringUtil.getStringUTF8(FileUtil.readAllBytes(streamUTF8));
            String content = getBodyRaw(contentRaw, true);
            System.out.println("Content:" + content);
        } catch (IOException ex) {
            fail();
        }
    }

    public static String getBodyRaw(String content, boolean smartDecode) {
        if (smartDecode) {
            content = SmartCodec.toUnicodeDecode(content, SmartCodec.ENCODE_PATTERN_LIGHT);
            content = SmartCodec.toHtmlDecode(content, SmartCodec.ENCODE_PATTERN_LIGHT);
        }
        return content;
    }

}
