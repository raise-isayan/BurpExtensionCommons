package extension.helpers.jws;

import com.google.gson.JsonElement;
import extension.helpers.ConvertUtil;
import extension.helpers.StringUtil;
import extension.helpers.json.JsonUtil;
import extension.view.base.CaptureItem;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isayan
 */
public class FlaskTokenTest {

    private final static Logger logger = Logger.getLogger(FlaskTokenTest.class.getName());

    public FlaskTokenTest() {
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


    @Test
    public void testJSON() {
        System.out.println("testJWTFormat");
        String json = JsonUtil.prettyJson(JsonToken.decodeBase64UrlSafe("192"));
        JsonElement element = JsonUtil.parseJson(JsonToken.decodeBase64UrlSafe("192"));
        System.out.println("elements:" + element.getAsString());
    }


    private final String JWT_NONE01 = "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJtYWluIjoieHh4eHh4Iiwic3ViIjoi44OG44K544OIIn0.";

    @Test
    public void testJWTFormat() {
        System.out.println("testJWTFormat");
        CaptureItem[] items = JWSUtil.findTokenFormat(JWT_NONE01);
        assertEquals(items.length, 1);
        assertEquals(items[0].getCaptureValue(), JWT_NONE01);

    }

    private final String FLASK_TOKEN_COMPRESS = "eJwdk0luHUEMQ-_Say80D75OEHgT2ECQrAzf3U_Gx0dXV5dEimR9Pr8-_r__-_33efWX58_H-9vz-kT4rMqajld0szCfKnZ5jw2dnXJrX8npXKtdCXPfFI_WkJbdKKnpjrTyWjdL6xlKdnQy5t49gtPTFHlry7hNzm5xwEI1tM1koyV5ZojueG8DkL0RFZp6mxVSrCLLusDoFJVZE-00BUnoBngBm7ZaCwORK4-YFJptMuTofaDR8JoN_aBXFUzdIiOgVTk35AgdGfkqowzSPCpcG2TLhnfycWSb0jCpmvtN1hGabcATnqF0KUkZ-FTrVO52ICujCtrpj5SMdYPzAR42GmCgWlcytJyYtm0c5_CPnCkibldOZ-DtWLWqMKjpbppY4drVLgdA9-lJc1YsGNUGR4hCAVUQdkUNMxtk8cbnsRgEoRr_D3Im9IhRt9O7DvOwAq5tvcmIYwD6ovigRaTgxpQGElM7xYTlykj0QEKkQxBLKUyHtm_3MQTgRl8HT1EQQhlexXjE56QLIwhkDz5n5ynul1byNannQzYIRuZoBFeCAMG6XACCAHRBoDlDo4v4k_fTigqMvFxaccyd1JSTa-xJJ9Qh_HeMvT4Kwj2iKfKQUsianILwXIYXrlWJGbvkjwRYatWycXcOCRaRNQiLGpTrNJyL2BJwtEa3Ft4BAK8Yi8vKZ6HM9AQIPCAXGMltHsFybNnn6xvqBNT8";

    /* qazwsx */
    private final String FLASK_TOKEN02 = "eyJjb3VudGVyIjoxfQ.X8LPTw.0KxUHImTzlIt9gMmU5pLpVJqy_M";
    /* qazwsx compress */
    private final String FLASK_TOKEN03 = ".eJwdk0luHUEMQ-_Say80D75OEHgT2ECQrAzf3U_Gx0dXV5dEimR9Pr8-_r__-_33efWX58_H-9vz-kT4rMqajld0szCfKnZ5jw2dnXJrX8npXKtdCXPfFI_WkJbdKKnpjrTyWjdL6xlKdnQy5t49gtPTFHlry7hNzm5xwEI1tM1koyV5ZojueG8DkL0RFZp6mxVSrCLLusDoFJVZE-00BUnoBngBm7ZaCwORK4-YFJptMuTofaDR8JoN_aBXFUzdIiOgVTk35AgdGfkqowzSPCpcG2TLhnfycWSb0jCpmvtN1hGabcATnqF0KUkZ-FTrVO52ICujCtrpj5SMdYPzAR42GmCgWlcytJyYtm0c5_CPnCkibldOZ-DtWLWqMKjpbppY4drVLgdA9-lJc1YsGNUGR4hCAVUQdkUNMxtk8cbnsRgEoRr_D3Im9IhRt9O7DvOwAq5tvcmIYwD6ovigRaTgxpQGElM7xYTlykj0QEKkQxBLKUyHtm_3MQTgRl8HT1EQQhlexXjE56QLIwhkDz5n5ynul1byNannQzYIRuZoBFeCAMG6XACCAHRBoDlDo4v4k_fTigqMvFxaccyd1JSTa-xJJ9Qh_HeMvT4Kwj2iKfKQUsianILwXIYXrlWJGbvkjwRYatWycXcOCRaRNQiLGpTrNJyL2BJwtEa3Ft4BAK8Yi8vKZ6HM9AQIPCAXGMltHsFybNnn6xvqBNT8.X8LPqg.vWUjL4MVbUhK7OQlEwsmt4eKlMc";

    @Test
    public void testFindTokenFormat() {
        System.out.println("testFindTokenFormat");
        {
            CaptureItem[] items = JWSUtil.findTokenFormat(FLASK_TOKEN02, JWSUtil.INCLUDE_SIGNATURE);
            assertEquals(items.length, 1);
            assertEquals(items[0].getCaptureValue(), FLASK_TOKEN02);
        }
        {
            CaptureItem[] items = JWSUtil.findTokenFormat(FLASK_TOKEN02, JWSUtil.INCLUDE_SIGNATURE | JWSUtil.FLASK_COMPRESS);
            assertEquals(items.length, 1);
            assertEquals(items[0].getCaptureValue(), FLASK_TOKEN02);
        }
        {
            CaptureItem[] items = JWSUtil.findTokenFormat(FLASK_TOKEN03, JWSUtil.INCLUDE_SIGNATURE | JWSUtil.FLASK_COMPRESS);
            assertEquals(items.length, 1);
            assertEquals(items[0].getCaptureValue(), FLASK_TOKEN03);
        }
        {
            CaptureItem[] items = JWSUtil.findTokenFormat(FLASK_TOKEN03, JWSUtil.INCLUDE_SIGNATURE);
            assertEquals(items.length, 1);
            assertEquals(items[0].getCaptureValue(), FLASK_TOKEN03.substring(1));
        }
    }

    @Test
    public void testFindTokenFormatMessage() {
        System.out.println("testFindTokenFormatMessage");
        {
            CaptureItem[] items = JWSUtil.findTokenFormat(REQ_MESSAGE_FLESK_TOKEN00, JWSUtil.INCLUDE_SIGNATURE | JWSUtil.FLASK_COMPRESS);
            assertEquals(items.length, 0);
        }
    }


    @Test
    public void testDecompress() {
        System.out.println("testDecompress");
        String expResult = "{\"counter\":3,\"long\":\"4438910921836477921238663893644941898632739058759269904233950347140709946068774526369322527889059818548252734414087471371708328589968542411417220947052205401983797070579446415101986406510456276905750108920175215270411890682552916954800415144850794953868195481048953577744575667343245444246584714801183959495462996546643171082572095594809732442066868688561527897151550141395605080416718659974926379036914606869097077494542814257137765562052732972369606713715000326914080206282577110538219952026233052790200413878523041878424288321096571697131575222844537407824846502042371508841257183298799306842619972937339355204879488854509168614734841867746316201830952448652506070219397723048327655934121139453543661059686379429056361254895789737592899851885657309232865579948573368004977784637623871084768930582790328868094726784331956398107753344404449823987563606470495086511392084459689916093660229206811672516692296638095924814515126576339380825989225734870082049398656586682502482163614684696109779803303069\"}";
        byte[] decode = JsonToken.decodeBase64UrlSafeByte(FLASK_TOKEN_COMPRESS);
        String result = StringUtil.getStringUTF8(ConvertUtil.decompressZlib(decode));
        System.out.println("decompress:" + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of passiveScanCheck method, of class JWTWeakToken.
     */
    @Test
    public void testSignatureEqual_args4() {
        System.out.println("signatureEqual_args4");
        boolean expResult = true;
        String value = "eyJjb3VudGVyIjoxfQ.X7uUCQ";
        String salt = "cookie-session";
        String signature = "52Tl1UEi2Ua8sLB96g2inBO0WL4";
        String secret = "qazwsx";
        {
            boolean result = FlaskToken.signatureEqual(value, JsonToken.decodeBase64UrlSafeByte(signature), secret, salt);
            assertEquals(expResult, result);
        }
        {
            boolean result = FlaskToken.signatureEqual(value, JsonToken.decodeBase64UrlSafeByte(signature), secret, secret);
            assertNotEquals(expResult, result);
        }
    }

    /**
     * Test of isTokenFormat method, of class FlaskToken.
     */
    @Test
    public void testContainsTokenFormat() {
        System.out.println("testContainsTokenFormat");
        {
            String value = FLASK_TOKEN02;
            boolean expResult = true;
            boolean result = FlaskToken.containsTokenFormat(value);
            assertEquals(expResult, result);
        }
//        {
//            String value = FLASK_TOKEN03;
//            boolean expResult = true;
//            boolean result = FlaskToken.isTokenFormat(value);
//            assertEquals(expResult, result);
//        }
//        /* URL Encode */
//        {
//            String value = "eyJjb3VudGVyIjoxfQ%2eX8LPTw%2e0KxUHImTzlIt9gMmU5pLpVJqy%5fM";
//            boolean expResult = true;
//            boolean result = FlaskToken.isTokenFormat(value);
//            assertEquals(expResult, result);
//        }
//        {
//            String value = "%2eeJwdk0luHUEMQ%2d%5fSay80D75OEHgT2ECQrAzf3U%5fGx0dXV5dEimR9Pr8%2d%5fr%5f%5f%2d%5f33efWX58%5fH%2d9vz%2dkT4rMqajld0szCfKnZ5jw2dnXJrX8npXKtdCXPfFI%5fWkJbdKKnpjrTyWjdL6xlKdnQy5t49gtPTFHlry7hNzm5xwEI1tM1koyV5ZojueG8DkL0RFZp6mxVSrCLLusDoFJVZE%2d00BUnoBngBm7ZaCwORK4%2dYFJptMuTofaDR8JoN%5faBXFUzdIiOgVTk35AgdGfkqowzSPCpcG2TLhnfycWSb0jCpmvtN1hGabcATnqF0KUkZ%2dFTrVO52ICujCtrpj5SMdYPzAR42GmCgWlcytJyYtm0c5%5fCPnCkibldOZ%2dDtWLWqMKjpbppY4drVLgdA9%2dlJc1YsGNUGR4hCAVUQdkUNMxtk8cbnsRgEoRr%5fD3Im9IhRt9O7DvOwAq5tvcmIYwD6ovigRaTgxpQGElM7xYTlykj0QEKkQxBLKUyHtm%5f3MQTgRl8HT1EQQhlexXjE56QLIwhkDz5n5ynul1byNannQzYIRuZoBFeCAMG6XACCAHRBoDlDo4v4k%5ffTigqMvFxaccyd1JSTa%2dxJJ9Qh%5fHeMvT4Kwj2iKfKQUsianILwXIYXrlWJGbvkjwRYatWycXcOCRaRNQiLGpTrNJyL2BJwtEa3Ft4BAK8Yi8vKZ6HM9AQIPCAXGMltHsFybNnn6xvqBNT8%2eX8LPqg%2evWUjL4MVbUhK7OQlEwsmt4eKlMc";
//            boolean expResult = true;
//            boolean result = FlaskToken.isTokenFormat(value);
//            assertEquals(expResult, result);
//        }
//
    }

    private final FlaskToken flaskinstance = new FlaskToken();

    @Test
    public void testParseFlaskObject_json() {
        System.out.println("parseFlaskObject");
        {
            String expResult1 = "{\"counter\":1}";
            FlaskToken token = flaskinstance.parseToken(FLASK_TOKEN02, true);
            assertEquals(false, token.getSessionData().isCompress());
            assertEquals("X8LPTw", token.getTimestamp().getPart());
            assertEquals("X8LPTw", token.getTimestampPart());
            assertEquals(expResult1, token.getPayload().toJSON(false));
            assertEquals("0KxUHImTzlIt9gMmU5pLpVJqy_M", token.getSignature().getPart());
            assertEquals("0KxUHImTzlIt9gMmU5pLpVJqy_M", token.getSignaturePart());
        }
        {
            String expResult1 = "{\"counter\":3,\"long\":\"4438910921836477921238663893644941898632739058759269904233950347140709946068774526369322527889059818548252734414087471371708328589968542411417220947052205401983797070579446415101986406510456276905750108920175215270411890682552916954800415144850794953868195481048953577744575667343245444246584714801183959495462996546643171082572095594809732442066868688561527897151550141395605080416718659974926379036914606869097077494542814257137765562052732972369606713715000326914080206282577110538219952026233052790200413878523041878424288321096571697131575222844537407824846502042371508841257183298799306842619972937339355204879488854509168614734841867746316201830952448652506070219397723048327655934121139453543661059686379429056361254895789737592899851885657309232865579948573368004977784637623871084768930582790328868094726784331956398107753344404449823987563606470495086511392084459689916093660229206811672516692296638095924814515126576339380825989225734870082049398656586682502482163614684696109779803303069\"}";
            FlaskToken token = flaskinstance.parseToken(FLASK_TOKEN03, true);
            assertEquals(true, token.getSessionData().isCompress());
            assertEquals("X8LPqg", token.getTimestamp().getPart());
            assertEquals("X8LPqg", token.getTimestampPart());
            System.out.println("json:" + token.getPayload().toJSON(false));
            assertEquals(FLASK_TOKEN_COMPRESS, token.getPayload().getPart());
            assertEquals(FLASK_TOKEN_COMPRESS, token.getPayloadPart());
            assertEquals(expResult1, token.getPayload().toJSON(false));
            assertEquals("vWUjL4MVbUhK7OQlEwsmt4eKlMc", token.getSignature().getPart());
            assertEquals("vWUjL4MVbUhK7OQlEwsmt4eKlMc", token.getSignaturePart());
        }
        /* URL Encode */
        {
            String expResult1 = "{\"counter\":1}";
            FlaskToken token = flaskinstance.parseToken("eyJjb3VudGVyIjoxfQ%2eX8LPTw%2e0KxUHImTzlIt9gMmU5pLpVJqy%5fM", true);
            assertEquals(false, token.getSessionData().isCompress());
            assertEquals("X8LPTw", token.getTimestamp().getPart());
            assertEquals("X8LPTw", token.getTimestampPart());
            assertEquals(expResult1, token.getPayload().toJSON(false));
            assertEquals("0KxUHImTzlIt9gMmU5pLpVJqy_M", token.getSignature().getPart());
            assertEquals("0KxUHImTzlIt9gMmU5pLpVJqy_M", token.getSignaturePart());
        }
        {
            String expResult1 = "{\"counter\":3,\"long\":\"4438910921836477921238663893644941898632739058759269904233950347140709946068774526369322527889059818548252734414087471371708328589968542411417220947052205401983797070579446415101986406510456276905750108920175215270411890682552916954800415144850794953868195481048953577744575667343245444246584714801183959495462996546643171082572095594809732442066868688561527897151550141395605080416718659974926379036914606869097077494542814257137765562052732972369606713715000326914080206282577110538219952026233052790200413878523041878424288321096571697131575222844537407824846502042371508841257183298799306842619972937339355204879488854509168614734841867746316201830952448652506070219397723048327655934121139453543661059686379429056361254895789737592899851885657309232865579948573368004977784637623871084768930582790328868094726784331956398107753344404449823987563606470495086511392084459689916093660229206811672516692296638095924814515126576339380825989225734870082049398656586682502482163614684696109779803303069\"}";
            FlaskToken token = flaskinstance.parseToken("%2eeJwdk0luHUEMQ%2d%5fSay80D75OEHgT2ECQrAzf3U%5fGx0dXV5dEimR9Pr8%2d%5fr%5f%5f%2d%5f33efWX58%5fH%2d9vz%2dkT4rMqajld0szCfKnZ5jw2dnXJrX8npXKtdCXPfFI%5fWkJbdKKnpjrTyWjdL6xlKdnQy5t49gtPTFHlry7hNzm5xwEI1tM1koyV5ZojueG8DkL0RFZp6mxVSrCLLusDoFJVZE%2d00BUnoBngBm7ZaCwORK4%2dYFJptMuTofaDR8JoN%5faBXFUzdIiOgVTk35AgdGfkqowzSPCpcG2TLhnfycWSb0jCpmvtN1hGabcATnqF0KUkZ%2dFTrVO52ICujCtrpj5SMdYPzAR42GmCgWlcytJyYtm0c5%5fCPnCkibldOZ%2dDtWLWqMKjpbppY4drVLgdA9%2dlJc1YsGNUGR4hCAVUQdkUNMxtk8cbnsRgEoRr%5fD3Im9IhRt9O7DvOwAq5tvcmIYwD6ovigRaTgxpQGElM7xYTlykj0QEKkQxBLKUyHtm%5f3MQTgRl8HT1EQQhlexXjE56QLIwhkDz5n5ynul1byNannQzYIRuZoBFeCAMG6XACCAHRBoDlDo4v4k%5ffTigqMvFxaccyd1JSTa%2dxJJ9Qh%5fHeMvT4Kwj2iKfKQUsianILwXIYXrlWJGbvkjwRYatWycXcOCRaRNQiLGpTrNJyL2BJwtEa3Ft4BAK8Yi8vKZ6HM9AQIPCAXGMltHsFybNnn6xvqBNT8%2eX8LPqg%2evWUjL4MVbUhK7OQlEwsmt4eKlMc", true);
            assertEquals(true, token.getSessionData().isCompress());
            assertEquals("X8LPqg", token.getTimestamp().getPart());
            assertEquals("X8LPqg", token.getTimestampPart());
            System.out.println("json:" + token.getPayload().toJSON(false));
            assertEquals(FLASK_TOKEN_COMPRESS, token.getPayload().getPart());
            assertEquals(FLASK_TOKEN_COMPRESS, token.getPayloadPart());
            assertEquals(expResult1, token.getPayload().toJSON(false));
            assertEquals("vWUjL4MVbUhK7OQlEwsmt4eKlMc", token.getSignature().getPart());
            assertEquals("vWUjL4MVbUhK7OQlEwsmt4eKlMc", token.getSignaturePart());
        }

    }

    private final static String REQ_MESSAGE_FLESK_TOKEN00
            = "POST /cgi-bin/multienc.cgi?charset=Shift_JIS&mode=disp HTTP/1.1\r\n"
            + "Host: 192.168.0.1\r\n"
            + "Content-Length: 60\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Origin: http://192.168.0.1\r\n"
            + "Content-Type: application/x-www-form-urlencoded\r\n"
            + "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n"
            + "Accept-Encoding: gzip, deflate\r\n"
            + "Accept-Language: ja,en-US;q=0.9,en;q=0.8\r\n"
            + "Cookie: session=0KxUHImTzlIt9gMmU5pLpVJqy_M\r\n"
            + "Connection: close\r\n"
            + "\r\n"
            + "text=%82%A0%82%A2%82%A4%82%A6%82%A8&OS=win&submit=%91%97%90M\r\n";

    private final static String REQ_MESSAGE_FLESK_TOKEN01
            = "POST /cgi-bin/multienc.cgi?charset=Shift_JIS&mode=disp HTTP/1.1\r\n"
            + "Host: 192.168.0.1\r\n"
            + "Content-Length: 60\r\n"
            + "Cache-Control: max-age=0\r\n"
            + "Origin: http://192.168.0.1\r\n"
            + "Content-Type: application/x-www-form-urlencoded\r\n"
            + "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\r\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n"
            + "Auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtYWluIjoiYWJjZGVmIiwic3ViIjoiaG9nZSIsInllYXIiOjIwMjB9.bfk79BN28BVvW6lRnITaEULZ7URDBcem4jalLOW5diM\r\n"
            + "Accept-Encoding: gzip, deflate\r\n"
            + "Accept-Language: ja,en-US;q=0.9,en;q=0.8\r\n"
            + "Cookie: session=eyJjb3VudGVyIjoxfQ.X8LPTw.0KxUHImTzlIt9gMmU5pLpVJqy_M\r\n"
            + "Connection: close\r\n"
            + "\r\n"
            + "text=%82%A0%82%A2%82%A4%82%A6%82%A8&OS=win&submit=%91%97%90M\r\n";

    private final static String RES_MESSAGE_FLESK_TOKEN01 = "HTTP/1.1 200 OK\r\n"
            + "Date: Sat, 03 Feb 2018 02:22:53 GMT\r\n"
            + "Server: Apache/2.4.10 (Debian)\r\n"
            + "Set-Cookie: TestCookie=test\r\n"
            + "Set-Cookie: BIGipServer2092=1677787402.36895.0000\r\n"
            + "Cookie: session=.eJwdk0luHUEMQ-_Say80D75OEHgT2ECQrAzf3U_Gx0dXV5dEimR9Pr8-_r__-_33efWX58_H-9vz-kT4rMqajld0szCfKnZ5jw2dnXJrX8npXKtdCXPfFI_WkJbdKKnpjrTyWjdL6xlKdnQy5t49gtPTFHlry7hNzm5xwEI1tM1koyV5ZojueG8DkL0RFZp6mxVSrCLLusDoFJVZE-00BUnoBngBm7ZaCwORK4-YFJptMuTofaDR8JoN_aBXFUzdIiOgVTk35AgdGfkqowzSPCpcG2TLhnfycWSb0jCpmvtN1hGabcATnqF0KUkZ-FTrVO52ICujCtrpj5SMdYPzAR42GmCgWlcytJyYtm0c5_CPnCkibldOZ-DtWLWqMKjpbppY4drVLgdA9-lJc1YsGNUGR4hCAVUQdkUNMxtk8cbnsRgEoRr_D3Im9IhRt9O7DvOwAq5tvcmIYwD6ovigRaTgxpQGElM7xYTlykj0QEKkQxBLKUyHtm_3MQTgRl8HT1EQQhlexXjE56QLIwhkDz5n5ynul1byNannQzYIRuZoBFeCAMG6XACCAHRBoDlDo4v4k_fTigqMvFxaccyd1JSTa-xJJ9Qh_HeMvT4Kwj2iKfKQUsianILwXIYXrlWJGbvkjwRYatWycXcOCRaRNQiLGpTrNJyL2BJwtEa3Ft4BAK8Yi8vKZ6HM9AQIPCAXGMltHsFybNnn6xvqBNT8.X8LPqg.vWUjL4MVbUhK7OQlEwsmt4eKlMc\r\n"
            + "Vary: Accept-Encoding\r\n"
            + "Content-Length: 115\r\n"
            + "Connection: close\r\n"
            + "Content-Type: text/html; charset=UTF-8\r\n\r\n";

    @Test
    public void testPatern() {
        Pattern p = Pattern.compile("(POST)");
        Matcher m = p.matcher(REQ_MESSAGE_FLESK_TOKEN00);
        if (m.find()) {
            System.out.println("start:" + m.start(1));
            System.out.println("end:" + m.end(1));

        }
    }

    @Test
    public void testFlaskTokenNothing() {
        System.out.println("testFlaskTokenNothing");
        {
            boolean expResult = false;
            boolean result = FlaskToken.containsTokenFormat(REQ_MESSAGE_FLESK_TOKEN00);
            assertEquals(expResult, result);
        }
//        {
//            CaptureItem[] token = FlaskToken.findToken(REQ_MESSAGE_FLESK_TOKEN00);
//            assertEquals(0, token.length);
//        }
    }

    @Test
    public void testFlaskTokenRequestMessage() {
        System.out.println("testFlaskTokenRequestMessage");
        {
            boolean expResult = true;
            boolean result = FlaskToken.containsTokenFormat(REQ_MESSAGE_FLESK_TOKEN01);
            assertEquals(expResult, result);
        }
//        {
//            String expResult = "eyJjb3VudGVyIjoxfQ.X8LPTw.0KxUHImTzlIt9gMmU5pLpVJqy_M";
//            CaptureItem[] token = FlaskToken.findToken(REQ_MESSAGE_FLESK_TOKEN01);
//            for (CaptureItem item : token) {
//                assertEquals(expResult, item.getCaptureValue());
//                System.out.println(item.getCaptureValue());
//            }
//        }
    }

    @Test
    public void testMaxDate() {
        System.out.println("testMaxDate");
        // Calendarインスタンスを取得
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
        cal.set(1970, Calendar.JANUARY, 1, 1, 0, 0);
        System.out.println(cal.getTimeInMillis());
        Date dateSt = cal.getTime();
        System.out.println(dateSt.getTime());
        cal.setTimeInMillis(0);
        Date date0 = cal.getTime();
        System.out.println(date0);

        // 年・月・日を指定（※月は0始まり：1月=0, 2月=1, ...）
        cal.set(9999, Calendar.DECEMBER, 31, 23, 59, 59);
        Date dateUTC = cal.getTime();
        System.out.println(cal.getTimeInMillis());
        System.out.println(dateUTC.getTime());

        LocalDateTime ltm = LocalDateTime.of(9999, Month.DECEMBER, 31, 23, 59,59);
        LocalDate dt = ltm.toLocalDate();
    }


    @Test
    public void testFlaskTokenRequest() {
        System.out.println("testFlaskTokenRequest");
        {
            FlaskToken flaskInstance = new FlaskToken();
            String expResult = "eyJjb3VudGVyIjoxfQ.X8LPTw.0KxUHImTzlIt9gMmU5pLpVJqy_M";
            FlaskToken result = flaskInstance.parseToken(REQ_MESSAGE_FLESK_TOKEN01, false);
            assertEquals(false, result.getSessionData().isCompress());
            assertEquals("eyJjb3VudGVyIjoxfQ", result.getPayload().getPart());
            assertEquals("eyJjb3VudGVyIjoxfQ", result.getPayloadPart());
            assertEquals("X8LPTw", result.getTimestamp().getPart());
            assertEquals("X8LPTw", result.getTimestampPart());
            assertEquals("0KxUHImTzlIt9gMmU5pLpVJqy_M", result.getSignature().getPart());
            assertEquals("0KxUHImTzlIt9gMmU5pLpVJqy_M", result.getSignaturePart());
            assertEquals("{\"counter\":1}", result.getPayload().toJSON(false));
            assertEquals(expResult, result.getToken());
        }
    }

    @Test
    public void testFlaskTokenResponse() {
        System.out.println("testFlaskTokenResponse");
        {
            FlaskToken flaskInstance = new FlaskToken();
            String expResult = ".eJwdk0luHUEMQ-_Say80D75OEHgT2ECQrAzf3U_Gx0dXV5dEimR9Pr8-_r__-_33efWX58_H-9vz-kT4rMqajld0szCfKnZ5jw2dnXJrX8npXKtdCXPfFI_WkJbdKKnpjrTyWjdL6xlKdnQy5t49gtPTFHlry7hNzm5xwEI1tM1koyV5ZojueG8DkL0RFZp6mxVSrCLLusDoFJVZE-00BUnoBngBm7ZaCwORK4-YFJptMuTofaDR8JoN_aBXFUzdIiOgVTk35AgdGfkqowzSPCpcG2TLhnfycWSb0jCpmvtN1hGabcATnqF0KUkZ-FTrVO52ICujCtrpj5SMdYPzAR42GmCgWlcytJyYtm0c5_CPnCkibldOZ-DtWLWqMKjpbppY4drVLgdA9-lJc1YsGNUGR4hCAVUQdkUNMxtk8cbnsRgEoRr_D3Im9IhRt9O7DvOwAq5tvcmIYwD6ovigRaTgxpQGElM7xYTlykj0QEKkQxBLKUyHtm_3MQTgRl8HT1EQQhlexXjE56QLIwhkDz5n5ynul1byNannQzYIRuZoBFeCAMG6XACCAHRBoDlDo4v4k_fTigqMvFxaccyd1JSTa-xJJ9Qh_HeMvT4Kwj2iKfKQUsianILwXIYXrlWJGbvkjwRYatWycXcOCRaRNQiLGpTrNJyL2BJwtEa3Ft4BAK8Yi8vKZ6HM9AQIPCAXGMltHsFybNnn6xvqBNT8.X8LPqg.vWUjL4MVbUhK7OQlEwsmt4eKlMc";
            FlaskToken result = flaskInstance.parseToken(RES_MESSAGE_FLESK_TOKEN01, false);
            assertEquals(true, result.getSessionData().isCompress());
            assertEquals("eJwdk0luHUEMQ-_Say80D75OEHgT2ECQrAzf3U_Gx0dXV5dEimR9Pr8-_r__-_33efWX58_H-9vz-kT4rMqajld0szCfKnZ5jw2dnXJrX8npXKtdCXPfFI_WkJbdKKnpjrTyWjdL6xlKdnQy5t49gtPTFHlry7hNzm5xwEI1tM1koyV5ZojueG8DkL0RFZp6mxVSrCLLusDoFJVZE-00BUnoBngBm7ZaCwORK4-YFJptMuTofaDR8JoN_aBXFUzdIiOgVTk35AgdGfkqowzSPCpcG2TLhnfycWSb0jCpmvtN1hGabcATnqF0KUkZ-FTrVO52ICujCtrpj5SMdYPzAR42GmCgWlcytJyYtm0c5_CPnCkibldOZ-DtWLWqMKjpbppY4drVLgdA9-lJc1YsGNUGR4hCAVUQdkUNMxtk8cbnsRgEoRr_D3Im9IhRt9O7DvOwAq5tvcmIYwD6ovigRaTgxpQGElM7xYTlykj0QEKkQxBLKUyHtm_3MQTgRl8HT1EQQhlexXjE56QLIwhkDz5n5ynul1byNannQzYIRuZoBFeCAMG6XACCAHRBoDlDo4v4k_fTigqMvFxaccyd1JSTa-xJJ9Qh_HeMvT4Kwj2iKfKQUsianILwXIYXrlWJGbvkjwRYatWycXcOCRaRNQiLGpTrNJyL2BJwtEa3Ft4BAK8Yi8vKZ6HM9AQIPCAXGMltHsFybNnn6xvqBNT8", result.getPayload().getPart());
            assertEquals("vWUjL4MVbUhK7OQlEwsmt4eKlMc", result.getSignature().getPart());
            assertEquals("vWUjL4MVbUhK7OQlEwsmt4eKlMc", result.getSignaturePart());
            assertEquals("{\"counter\":3,\"long\":\"4438910921836477921238663893644941898632739058759269904233950347140709946068774526369322527889059818548252734414087471371708328589968542411417220947052205401983797070579446415101986406510456276905750108920175215270411890682552916954800415144850794953868195481048953577744575667343245444246584714801183959495462996546643171082572095594809732442066868688561527897151550141395605080416718659974926379036914606869097077494542814257137765562052732972369606713715000326914080206282577110538219952026233052790200413878523041878424288321096571697131575222844537407824846502042371508841257183298799306842619972937339355204879488854509168614734841867746316201830952448652506070219397723048327655934121139453543661059686379429056361254895789737592899851885657309232865579948573368004977784637623871084768930582790328868094726784331956398107753344404449823987563606470495086511392084459689916093660229206811672516692296638095924814515126576339380825989225734870082049398656586682502482163614684696109779803303069\"}", result.getPayload().toJSON(false));
            assertEquals(expResult, result.getToken());
        }
    }

    @Test
    public void testTimestamp() {
        System.out.println("Timestamp");
        {
            String value = "X8LPTw";
            byte[] decode = Base64.getUrlDecoder().decode(value);
            long decodeLong = ConvertUtil.bytesToLong(decode, ByteOrder.BIG_ENDIAN);
            byte[] decodeByte = ConvertUtil.longToBytes(decodeLong, ByteOrder.BIG_ENDIAN);
            assertArrayEquals(decode, decodeByte);
        }
    }

    /**
     * Test of testSignatureSign method, of class JWTWeakToken.
     */
    @Test
    public void testSignatureSign() {
        System.out.println("signatureSign");
        // eyJjb3VudGVyIjoxfQ.X8LPTw.0KxUHImTzlIt9gMmU5pLpVJqy_M
        String expResult = "0KxUHImTzlIt9gMmU5pLpVJqy_M";
        long tm = ConvertUtil.bytesToLong(Base64.getDecoder().decode("X8LPTw"), ByteOrder.BIG_ENDIAN);
        String payload = "eyJjb3VudGVyIjoxfQ";
        try {
            {
                byte[] sign = FlaskToken.sign(payload, tm, StringUtil.getBytesRaw("qazwsx"));
                String result = JsonToken.encodeBase64UrlSafe(sign);
                assertEquals(expResult, result);
            }
        } catch (NoSuchAlgorithmException ex) {
            fail(ex);
        }
    }

}
