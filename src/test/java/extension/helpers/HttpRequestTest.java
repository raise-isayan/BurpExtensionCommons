/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package extension.helpers;

import java.text.ParseException;
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
public class HttpRequestTest {

    final String GET_PROTOCOL_1_0 = "GET /jvuln/XSSVuln?mode=simple&name=name&age=20&tel=xxx HTTP/1.0\r\n" +
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36\r\n" +
    "Cookie: JSESSIONID=F072094178BFD73F1415FE165A3C5F3D; PHPSESSID=5eda0a0eed13fe75fa1343deb234f322\r\n" +
    "Connection: close\r\n";    
        
    final String GET_URLENCODE = "GET /jvuln/XSSVuln?mode=simple&name=name&age=20&tel=xxx HTTP/1.1\r\n" +
    "Host:  192.0.2.11:10000\r\n" +
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36\r\n" +
    "Cookie: JSESSIONID=F072094178BFD73F1415FE165A3C5F3D; PHPSESSID=5eda0a0eed13fe75fa1343deb234f322\r\n" +
    "Connection: close\r\n";    
    
    final String POST_URLENCODE = "POST /vuln/registerJSON.php?mode=register HTTP/1.1\r\n" +
    "Host: 192.0.2.11\r\n" +
    "Content-Length: 47\r\n" +
    "Origin: http://192.0.2.11r\n" +
    "Content-Type: application/x-www-form-urlencoded\r\n" +
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36\r\n" +
    "Connection: close\r\n" +
    "\r\n" +
    "nickname=xx&email=aa%40example.com&address=test";

    final String POST_URLENCODE_CHARSET1 = "POST /vuln/registerJSON.php?mode=register HTTP/1.1\r\n" +
    "Host: 192.0.2.11:80\r\n" +
    "Content-Length: 47\r\n" +
    "Content-Type: application/x-www-form-urlencoded; charset=UTF-8;\r\n" +
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36\r\n" +
    "\r\n" +
    "nickname=xx&email=aa%40example.com&address=test";

    final String POST_URLENCODE_CHARSET2 = "POST /vuln/registerJSON.php?mode=register HTTP/1.1\r\n" +
    "Host: 192.0.2.11:80\r\n" +
    "Content-Length: 47\r\n" +
    "Content-Type: application/x-www-form-urlencoded; charset='UTF-8';\r\n" +
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36\r\n" +
    "\r\n" +
    "nickname=xx&email=aa%40example.com&address=test";
    
    final String MULTI_PART1 = "POST /cgi-bin/sendto.cgi?mode=sendto HTTP/1.1\r\n" +
    "Host: 192.0.2.11:1234\r\n" +
    "Content-Length: 1715\r\n" +
    "Origin: http://192.0.2.11:1234\r\n" +
    "Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36\r\n" +
    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
    "Referer: http://192.0.2.11:1234/sendto.html\r\n" +
    "Accept-Encoding: gzip, deflate\r\n" +
    "Cookie: PHPSESSID=5eda0a0eed13fe75fa1343deb234f322\r\n" +
    "Connection: close\r\n" +
    "\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"host\"\r\n" +
    "\r\n" +
    "\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"port\"\r\n" +
    "\r\n" +
    "\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"protocol\"\r\n" +
    "\r\n" +
    "http\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"url\"\r\n" +
    "\r\n" +
    "\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"comment\"\r\n" +
    "\r\n" +
    "\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"request\"; filename=\"sqli-logic.txt\"\r\n" +
    "Content-Type: text/plain\r\n" +
    "\r\n" +
    "test\r\n" +
    "\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"response\"; filename=\"\"\r\n" +
    "Content-Type: application/octet-stream\r\n" +
    "\r\n" +
    "\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE\r\n" +
    "Content-Disposition: form-data; name=\"encoding\"\r\n" +
    "\r\n" +
    "UTF-8\r\n" +
    "------WebKitFormBoundaryNXJdwcqdorc9eqtE--";
    
    private final static String POST_MESSAGE_URLENCODE =
        "POST /cgi-bin/multienc.cgi?charset=Shift_JIS&mode=disp HTTP/1.1\r\n" +
        "Host: 192.168.0.1\r\n" +
        "Content-Length: 60\r\n" +
        "Cache-Control: max-age=0\r\n" +
        "Origin: http://192.168.0.1\r\n" +
        "Content-Type: application/x-www-form-urlencoded\r\n" +
        "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n" +
        "Referer: http://192.168.50.140/cgi-bin/multienc.cgi?charset=Shift_JIS&mode=input\r\n" +
        "Accept-Encoding: gzip, deflate\r\n" +
        "Accept-Language: ja,en-US;q=0.9,en;q=0.8\r\n" +
        "Connection: close\r\n" +
        "\r\n" +
        "text=%82%A0%82%A2%82%A4%82%A6%82%A8&OS=win&submit=%91%97%90M\r\n";

    private final static String POST_MESSAGE_URLENCODE2 =
        "POST /jvuln/CSRFVuln HTTP/1.1\n" +
        "Host: 192.168.0.1\r\n" +
        "Content-Length: 35\r\n" +
        "Content-Type: application/x-www-form-urlencoded\r\n" +
        "test: test\r\n" +
        "Accept-Encoding: gzip, deflate\r\n" +
        "Accept-Language: ja,en-US;q=0.9,en;q=0.8\r\n" +
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36\r\n" +
        "Connection: close\r\n" +
        "\r\n" +
        "comment=%E3%83%86%E3%82%B9%E3%83%88";

    private final static String POST_MESSAGE_JSON =
        "POST /jvuln/CSRFVuln HTTP/1.1\n" +
        "Host: 192.168.0.1\r\n" +
        "Content-Length: 35\r\n" +
        "Content-Type: application/json; charset=UTF-8\r\n" +
        "test: test\r\n" +
        "Accept-Encoding: gzip, deflate\r\n" +
        "Accept-Language: ja,en-US;q=0.9,en;q=0.8\r\n" +
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36\r\n" +
        "Connection: close\r\n" +
        "\r\n" +
        "{\"comment\":\"テスト\"}";

    private final static String POST_MESSAGE_MULTIPART =
        "POST /cgi-bin/sendto.cgi?mode=sendto HTTP/1.1\r\n" +
        "Host: 192.168.0.1\r\n" +
        "Content-Length: 922\r\n" +
        "Cache-Control: max-age=0\r\n" +
        "Origin: http://192.168.0.1\r\n" +
        "Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\r\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n" +
        "Referer: http://192.168.0.1/sendto.html\r\n" +
        "Accept-Encoding: gzip, deflate\r\n" +
        "Accept-Language: ja,en-US;q=0.9,en;q=0.8\r\n" +
        "Connection: close\r\n" +
        "\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"host\"\r\n" +
        "\r\n" +
        "てすと\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"port\"\r\n" +
        "\r\n" +
        "\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"protocol\"\r\n" +
        "\r\n" +
        "http\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"url\"\r\n" +
        "\r\n" +
        "\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"comment\"\r\n" +
        "\r\n" +
        "てすと\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"request\"; filename=\"\"\r\n" +
        "Content-Type: application/octet-stream\r\n" +
        "\r\n" +
        "\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"response\"; filename=\"\"\r\n" +
        "Content-Type: application/octet-stream\r\n" +
        "\r\n" +
        "\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH\r\n" +
        "Content-Disposition: form-data; name=\"encoding\"\r\n" +
        "\r\n" +
        "UTF-8\r\n" +
        "------WebKitFormBoundaryOw7BuAmQWEc7jVWH--\r\n" +
        "\r\n";
    
    public HttpRequestTest() {
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

    @Test
    public void testParseGettProtocol_1_0() throws Exception {
        System.out.println("testParseGettUrlencode");
        HttpRequest instance = HttpRequest.parseHttpRequest(GET_PROTOCOL_1_0);
        String method = instance.getMethod();
        assertEquals("GET", method);
        String protover = instance.getProtocolVersion();
        assertEquals("HTTP/1.0", protover);
        String uri = instance.getUri();
        assertEquals("/jvuln/XSSVuln?mode=simple&name=name&age=20&tel=xxx", uri);
        String hostHeader = instance.getHostHeader();
        assertEquals(null, hostHeader);
        int port = instance.getPort();
        assertEquals(-1, port);
        String contentType = instance.getEnctype();
        assertEquals(null, contentType);
        String charSet = instance.getGuessCharset();
        assertEquals(null, charSet);
        String body = instance.getBody();
        assertEquals("", body);
    }
    
    @Test
    public void testParseGettUrlencode() throws Exception {
        System.out.println("testParseGettUrlencode");
        HttpRequest instance = HttpRequest.parseHttpRequest(GET_URLENCODE);
        String method = instance.getMethod();
        assertEquals("GET", method);
        String protover = instance.getProtocolVersion();
        assertEquals("HTTP/1.1", protover);
        String uri = instance.getUri();
        assertEquals("/jvuln/XSSVuln?mode=simple&name=name&age=20&tel=xxx", uri);
        boolean hasQueryParameter = instance.hasQueryParameter();
        assertEquals(true, hasQueryParameter);
        String hostHeader = instance.getHostHeader();
        assertEquals("192.0.2.11:10000", hostHeader);
        int port = instance.getPort();
        assertEquals(10000, port);
        String contentType = instance.getEnctype();
        assertEquals(null, contentType);
        String charSet = instance.getGuessCharset();
        assertEquals(null, charSet);
        String body = instance.getBody();
        assertEquals("", body);
    }
    
    @Test
    public void testParsePostUrlencode() throws Exception {
        System.out.println("testParsePostUrlencode");
        HttpRequest instance = HttpRequest.parseHttpRequest(POST_URLENCODE);
        String method = instance.getMethod();
        assertEquals("POST", method);
        String protover = instance.getProtocolVersion();
        assertEquals("HTTP/1.1", protover);
        String uri = instance.getUri();
        assertEquals("/vuln/registerJSON.php?mode=register", uri);
        String contentType = instance.getEnctype();
        String hostHeader = instance.getHostHeader();
        assertEquals("192.0.2.11", hostHeader);
        int port = instance.getPort();
        assertEquals(80, port);        
        assertEquals("application/x-www-form-urlencoded", contentType);
        String charSet = instance.getGuessCharset();
        assertEquals(null, charSet);
        String body = instance.getBody();
        assertEquals("nickname=xx&email=aa%40example.com&address=test", body);
    }

    public void testParseUrlencodeCharset1() throws Exception {
        System.out.println("testParseUrlencodeCharset1");
        HttpRequest instance = HttpRequest.parseHttpRequest(POST_URLENCODE_CHARSET1);
        String method = instance.getMethod();
        assertEquals("POST", method);
        String contentType = instance.getEnctype();
        String hostHeader = instance.getHostHeader();
        assertEquals("192.0.2.11:80", hostHeader);
        int port = instance.getPort();
        assertEquals(80, port);        
        assertEquals("application/x-www-form-urlencoded", contentType);
        String charSet = instance.getGuessCharset();
        assertEquals("UTF-8", charSet);
        String body = instance.getBody();
        assertEquals("nickname=xx&email=aa%40example.com&address=test", body);
    }

    public void testParseUrlencodeCharset2() throws Exception {
        System.out.println("testParseUrlencodeCharset1");
        HttpRequest instance = HttpRequest.parseHttpRequest(POST_URLENCODE_CHARSET2);
        String method = instance.getMethod();
        assertEquals("POST", method);
        String contentType = instance.getEnctype();
        String hostHeader = instance.getHostHeader();
        assertEquals("192.0.2.11:80", hostHeader);
        int port = instance.getPort();
        assertEquals(80, port);        
        assertEquals("application/x-www-form-urlencoded", contentType);
        String charSet = instance.getGuessCharset();
        assertEquals("UTF-8", charSet);
        String body = instance.getBody();
        assertEquals("nickname=xx&email=aa%40example.com&address=test", body);
    }
    
    @Test
    public void testParseMultipart() throws Exception {
        System.out.println("testParseMultipart");
        HttpRequest instance = HttpRequest.parseHttpRequest(MULTI_PART1);
        String method = instance.getMethod();
        assertEquals("POST", method);
        String contentType = instance.getEnctype();
        assertEquals("multipart/form-data", contentType);
        String header = instance.getHeader();
        System.out.println(header);
    }

        
    /**
     * Test of getContentMimeType method, of class HttpResponse.
     */
    @Test
    public void testGetEncType() {
        try {
            System.out.println("getEncType");
            {
                HttpRequest req = HttpRequest.parseHttpRequest(POST_MESSAGE_URLENCODE);
                String result = req.getEnctype();
                String expResult = "application/x-www-form-urlencoded";
                assertEquals(expResult, result);
            }
            {
                HttpRequest req = HttpRequest.parseHttpRequest(POST_MESSAGE_URLENCODE2);
                String result = req.getEnctype();
                String expResult = "application/x-www-form-urlencoded";
                assertEquals(expResult, result);
            }
            {
                HttpRequest req = HttpRequest.parseHttpRequest(POST_MESSAGE_JSON);
                String result = req.getEnctype();
                String expResult = "application/json";
                assertEquals(expResult, result);
            }
            {
                final Pattern CONTENT_TYPE = Pattern.compile("^Content-Type:\\s*([^;]+?)(?:;\\s*charset=[\"\']?([\\w_-]+)[\"\']?)?\\s*$", Pattern.MULTILINE);
                Matcher m = CONTENT_TYPE.matcher(POST_MESSAGE_URLENCODE2);
                if (m.find()) {
                    System.out.println("grp1" + m.group(1));
                    System.out.println("grp2" + m.group(2));
                }
            }

        } catch (ParseException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of parseHttpRequest method, of class HttpRequest.
     */
    @Test
    public void testParseHttpRequest_urlencoded() {
        try {
            System.out.println("makeGetRequest");
            {
                String expResult = "POST";
                HttpRequest result = HttpRequest.parseHttpRequest(POST_MESSAGE_URLENCODE);
                assertEquals(expResult, result.getMethod());
                String expCharset = null;
                assertEquals(expCharset, result.getGuessCharset());
                System.out.println(result.getGuessCharset());
            }
        } catch (ParseException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of parseHttpRequest method, of class HttpRequest.
     */
    @Test
    public void testParseHttpRequest_multipart() {
        try {
            System.out.println("makeGetRequest");
            String expResult = "POST";
            HttpRequest result = HttpRequest.parseHttpRequest(StringUtil.getBytesUTF8(POST_MESSAGE_MULTIPART));
            assertEquals(expResult, result.getMethod());
            System.out.println(result.getHeader());
            System.out.println(result.getBody());
            assertEquals("UTF-8", result.getGuessCharset());
        } catch (ParseException ex) {
            fail(ex.getMessage());
        }
    }

    
}
