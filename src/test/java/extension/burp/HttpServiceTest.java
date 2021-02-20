package extension.burp;

import burp.IHttpRequestResponse;
import burp.IHttpService;
import java.net.MalformedURLException;
import java.net.URL;
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
public class HttpServiceTest {
    
    public HttpServiceTest() {
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
     * Test of getURLString method, of class HttpService.
     */
    @Test
    public void testGetURL() {
        System.out.println("getURL");
        try {
            IHttpService httpService = HttpService.getHttpService("www.example.jp", 80, "http");
            String expResult = "http://www.example.jp/";
            HttpService result = new HttpService(new URL(expResult));
            assertEquals("www.example.jp", result.getHost());
            assertEquals(80, result.getPort());
            assertEquals("http", result.getProtocol());
        } catch (MalformedURLException ex) {
            fail();
        }
    }



    /**
     * Test of getURLString method, of class HttpService.
     */
    @Test
    public void testGetURLString() {
        System.out.println("getURLString");
        {
            IHttpService httpService = HttpService.getHttpService("www.example.jp", 80, "http");
            String expResult = "http://www.example.jp/";
            String result = HttpService.getURLString(httpService);
            assertEquals(expResult, result);        
        }
        {
            IHttpService httpService = HttpService.getHttpService("www.example.jp", 8080, "http");
            String expResult = "http://www.example.jp:8080/";
            String result = HttpService.getURLString(httpService);
            assertEquals(expResult, result);                
        }
        {
            IHttpService httpService = HttpService.getHttpService("www.example.jp", 443, "https");
            String expResult = "https://www.example.jp/";
            String result = HttpService.getURLString(httpService);
            assertEquals(expResult, result);                
        }
        {
            IHttpService httpService = HttpService.getHttpService("www.example.jp", 8443, "https");
            String expResult = "https://www.example.jp:8443/";
            String result = HttpService.getURLString(httpService);
            assertEquals(expResult, result);                
        }
    }

    /**
     * Test of getHttpService method, of class HttpService.
     */
    @Test
    public void testGetHttpService_3args_1() {
        System.out.println("getHttpService");
        String host = "www.exampe.com";
        int port = 443;
        String protocol = "https";
        IHttpService expResult = null;
        IHttpService result = HttpService.getHttpService(host, port, protocol);
        assertEquals(host, result.getHost());
        assertEquals(443, result.getPort());
        assertEquals(protocol, result.getProtocol());
        
        HttpService httpService = new HttpService(result);
        assertEquals(host, httpService.getHost());
        assertEquals(443, httpService.getPort());
        assertEquals("https", httpService.getProtocol());
        assertTrue(httpService.isHttps());
    }

    /**
     * Test of getHttpService method, of class HttpService.
     */
    @Test
    public void testGetHttpService_IHttpRequestResponse() {
        System.out.println("getHttpService");
        IHttpRequestResponse messageInfo = null;
        IHttpService expResult = null;
        IHttpService result = HttpService.getHttpService(messageInfo);
        assertEquals(expResult, result);
    }

    /**
     * Test of getHttpService method, of class HttpService.
     */
    @Test
    public void testGetHttpService_3args_2() {
        System.out.println("getHttpService");
        String host = "www.exampe.com";
        int port = 8080;
        boolean useHttps = false;
        IHttpService result = HttpService.getHttpService(host, port, useHttps);
        assertEquals(host, result.getHost());
        assertEquals(8080, result.getPort());
        assertEquals("http", result.getProtocol());

        HttpService httpService = new HttpService(result);
        assertEquals(host, httpService.getHost());
        assertEquals(8080, httpService.getPort());
        assertEquals("http", httpService.getProtocol());
        assertFalse(httpService.isHttps());

    }
    
}
