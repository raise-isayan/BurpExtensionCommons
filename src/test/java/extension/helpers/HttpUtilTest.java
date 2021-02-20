package extension.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class HttpUtilTest {
    
    public HttpUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of startsWithHttp method, of class HttpUtil.
     */
    @Test
    public void testStartsWithHttp() {
        System.out.println("startsWithHttp");
        assertEquals(true, HttpUtil.startsWithHttp("http://"));
        assertEquals(true, HttpUtil.startsWithHttp("https://"));
        assertEquals(false, HttpUtil.startsWithHttp("shttp://"));
        assertEquals(false, HttpUtil.startsWithHttp("httpx://"));
    }

    /**
     * Test of normalizeURL method, of class HttpUtil.
     */
    @Test
    public void testNormalizeURL() {
        System.out.println("normalizeURL");
        assertEquals("http://example.com/", HttpUtil.normalizeURL("http://example.com:80/"));
        assertEquals("http://example.com:8080/", HttpUtil.normalizeURL("http://example.com:8080/"));
        assertEquals("https://example.com:4438/", HttpUtil.normalizeURL("https://example.com:4438/"));
        assertEquals("https://example.com/", HttpUtil.normalizeURL("https://example.com:443/"));
        assertEquals("http://example.com/xxx", HttpUtil.normalizeURL("http://example.com:80/xxx"));
        assertEquals("https://example.com/xxx", HttpUtil.normalizeURL("https://example.com:443/xxx"));
        assertEquals("https://example.com:8443/xxx", HttpUtil.normalizeURL("https://example.com:8443/xxx"));
        assertEquals("https://example.com/xxx", HttpUtil.normalizeURL("https://example.com:443/xxx"));
    }

    /**
     * Test of testBaseName method, of class HttpUtil.
     */
    @Test
    public void testBaseName() {
        System.out.println("testBaseName");
        try {
            assertEquals("path.exe", HttpUtil.getBaseName(new URL("http://example.com/dir/file/path.exe?1328319481")));
            assertEquals("file", HttpUtil.getBaseName(new URL("http://example.com/dir/file/?1328319481")));
            assertEquals("dir", HttpUtil.getBaseName(new URL("http://example.com/dir?1328319481")));
            assertEquals("example.com", HttpUtil.getBaseName(new URL("http://example.com/?1328319481")));
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpUtilTest.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    
    /**
     * Test of buildGetRequestByte method, of class HttpUtil.
     */
    @Test
   public void testBuildGetRequestByte() {
       System.out.println("testBuildGetRequestByte");
       assertEquals("GET / HTTP/1.1\r\nHost: example.com\r\n", (StringUtil.getStringRaw(HttpUtil.buildGetRequestByte("http://example.com/"))));   
       assertEquals("GET / HTTP/1.1\r\nHost: example.com:443\r\n", (StringUtil.getStringRaw(HttpUtil.buildGetRequestByte("http://example.com:443/"))));   
       assertEquals("GET /?xxx=yyy&ccc=ddd HTTP/1.1\r\nHost: example.com:8443\r\n", (StringUtil.getStringRaw(HttpUtil.buildGetRequestByte("http://example.com:8443/?xxx=yyy&ccc=ddd"))));   
       assertEquals("GET /?abc=123 HTTP/1.1\r\nHost: example.com\r\n", (StringUtil.getStringRaw(HttpUtil.buildGetRequestByte("http://example.com/?abc=123"))));   
       assertEquals("GET /dir/file/?abc=123 HTTP/1.1\r\nHost: example.com\r\n", (StringUtil.getStringRaw(HttpUtil.buildGetRequestByte("http://example.com/dir/file/?abc=123"))));   
       assertEquals("GET /dir/file?abc=123 HTTP/1.1\r\nHost: example.com\r\n", (StringUtil.getStringRaw(HttpUtil.buildGetRequestByte("http://example.com/dir/file?abc=123"))));   
   }

    /**
     * Test of testGetParameter method, of class HttpUtil.
     */
    @Test
    public void testGetParameter() {
       System.out.println("testGetParameter");
       {
            Map.Entry keyval = HttpUtil.getParameter("abc");
            assertEquals("abc", keyval.getKey());               
            assertEquals("", keyval.getValue());               
       }
       {
            Map.Entry keyval = HttpUtil.getParameter("abc=edf");
            assertEquals("abc", keyval.getKey());               
            assertEquals("edf", keyval.getValue());               
       }
       {
            Map.Entry keyval = HttpUtil.getParameter("abc=edf=fgh");
            assertEquals("abc", keyval.getKey());               
            assertEquals("edf=fgh", keyval.getValue());               
       }
       {
            Map.Entry keyval = HttpUtil.getParameter("=");
            assertEquals("", keyval.getKey());               
            assertEquals("", keyval.getValue());               
       }
       {
            Map.Entry keyval = HttpUtil.getParameter("==");
            assertEquals("", keyval.getKey());               
            assertEquals("=", keyval.getValue());               
       }
    }

    /**
     * Test of testGetDefaultProtocol method, of class HttpUtil.
     */
    @Test
    public void testGetDefaultProtocol() {
        System.out.println("testGetDefaultProtocol");
        assertEquals("https", HttpUtil.getDefaultProtocol(true));               
        assertEquals("http", HttpUtil.getDefaultProtocol(false));               
    }

    /**
     * Test of testGetDefaultProtocol method, of class HttpUtil.
     */
    @Test
    public void testGetDefaultPort() {
        System.out.println("testGetDefaultPort");
        assertEquals(443, HttpUtil.getDefaultPort(true));               
        assertEquals(80, HttpUtil.getDefaultPort(false));               
    }

    /**
     * Test of testGetDefaultProtocol method, of class HttpUtil.
     */
    @Test
    public void testGetDefaultPort_String() {
        System.out.println("testGetDefaultPort_String");
        assertEquals(443, HttpUtil.getDefaultPort("https"));               
        assertEquals(80, HttpUtil.getDefaultPort("http"));               
        assertEquals(-1, HttpUtil.getDefaultPort("httpxxx"));               
    }

    /**
     * Test of testMultipart method, of class HttpUtil.
     */
    @Test
    public void testMultipart() {
        try {
            String boundary = HttpUtil.generateBoundary();
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpUtil.outMultipartText(boundary, out, "host", "www.exmple.com");
                String result = StringUtil.getStringUTF8(out.toByteArray());
                assertTrue(result.contains("www.exmple.com"));
            }
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpUtil.outMultipartText(boundary, out, "port", StringUtil.toString(8080));
                String result = StringUtil.getStringUTF8(out.toByteArray());
                assertTrue(result.contains("8080"));
            }
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpUtil.outMultipartText(boundary, out, "protocol", "https");
                String result = StringUtil.getStringUTF8(out.toByteArray());
                assertTrue(result.contains("https"));
            }
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpUtil.outMultipartText(boundary, out, "url", "https://www.example.com/");
                String result = StringUtil.getStringUTF8(out.toByteArray());
                assertTrue(result.contains("https://www.example.com/"));
            }
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpUtil.outMultipartText(boundary, out, "comment", "コメント", StandardCharsets.UTF_8);
                String result = StringUtil.getStringUTF8(out.toByteArray());
                assertTrue(result.contains("コメント"));
            }
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpUtil.outMultipartText(boundary, out, "comment", "コメント");
                String result = StringUtil.getStringUTF8(out.toByteArray());
                assertFalse(result.contains("コメント"));
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpUtilTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    /**
     * Test of testNormalizeCharset method, of class HttpUtil.
     */
    @Test
    public void testNormalizeCharset() {
        System.out.println("testNormalizeCharset");
        assertEquals("Shift_JIS", HttpUtil.normalizeCharset("Shift_JIS"));
        assertEquals("Shift_JIS", HttpUtil.normalizeCharset("Shift-JIS"));
        assertEquals("Shift_JIS", HttpUtil.normalizeCharset("shift_jis"));
        assertEquals("windows-31j", HttpUtil.normalizeCharset("Windows-31J"));
        assertEquals("windows-31j", HttpUtil.normalizeCharset("MS932"));
        assertEquals(StringUtil.DEFAULT_ENCODING, HttpUtil.normalizeCharset(StringUtil.DEFAULT_ENCODING));
        assertEquals(null, HttpUtil.normalizeCharset(null));
    }

    /**
     * Test of StaticProxySelector class, of class HttpUtil.
     */
    @Test
    public void testStaticProxySelector() {
        System.out.println("staticProxySelector");
        Proxy localProxy = new Proxy(Type.HTTP, new InetSocketAddress("localhost", 8080));
        try {
            ProxySelector proxySelector = HttpUtil.StaticProxySelector.of(localProxy);
            List proxy = proxySelector.select(new URI("https://localhost"));
            assertEquals(1, proxy.size());
            assertEquals(localProxy, proxy.get(0));
        } catch (URISyntaxException ex) {
            fail(ex.getMessage());
        }
        try {
            ProxySelector proxySelector = new HttpUtil.StaticProxySelector(localProxy);
            List proxy = proxySelector.select(new URI("https://localhost"));
            assertEquals(1, proxy.size());
            assertEquals(localProxy, proxy.get(0));
        } catch (URISyntaxException ex) {
            fail(ex.getMessage());
        }
        try {
            ProxySelector proxySelector = new HttpUtil.StaticProxySelector(null);
            List proxy = proxySelector.select(new URI("https://localhost"));
            assertEquals(1, proxy.size());
            assertEquals(Proxy.NO_PROXY, proxy.get(0));
        } catch (URISyntaxException ex) {
            fail(ex.getMessage());
        }
    }
    
}
