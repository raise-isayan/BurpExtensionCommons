/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
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

    
}
