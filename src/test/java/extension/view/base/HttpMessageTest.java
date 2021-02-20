package extension.view.base;

import extension.helpers.HttpMessage;
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
public class HttpMessageTest {
    
    public HttpMessageTest() {
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

    private final static String RES_CHARSET0_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Content-Type: text/html;\r\n"
        + "Connection: close\r\n"
        + "\r\n";

    private final static String RES_CHARSET1_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Content-Type: text/html; charset=UTF-8\r\n"
        + "Connection: close\r\n"
        + "\r\n";

    private final static String RES_CHARSET2_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Content-Type: text/html; charset=\"UTF-8\"\r\n"
        + "Connection: close\r\n"
        + "\r\n";

    private final static String RES_CHARSET3_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Content-Type: text/html; charset='UTF-8'\r\n"
        + "Connection: close\r\n"
        + "\r\n";

    private final static String RES_CHARSET10_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Connection: close\r\n"
        + "Content-Type: text/html;charset=UTF-8\r\n"
        + "\r\n";

    private final static String RES_CHARSET11_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Connection: close\r\n"
        + "Content-Type: text/html;charset=\"UTF-8\"\r\n"
        + "\r\n";

    private final static String RES_CHARSET12_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Connection: close\r\n"
        + "Content-Type: text/html;charset='UTF-8'\r\n"
        + "\r\n";

    private final static String RES_CHARSET21_FMT = "HTTP/1.1 200 OK\r\n"
        + "Date: Sat, 19 Jan 2019 02:49:15 GMT\r\n"
        + "Server: Apache/2.4.10 (Debian)\r\n"
        + "Set-Cookie: TestCookie=test\r\n"
        + "Content-Length: 116\r\n"
        + "Connection: close\r\n"
        + "Content-Type: text/html; charset=shift_jis\r\n"
        + "\r\n";
        
    /**
     * Test of getGuessCharset method, of class HttpMessage.
     */
    @Test
    public void testGetGuessCharset() {
        System.out.println("getGuessCharset");
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET0_FMT);
            String expResult = null;
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET1_FMT);
            String expResult = "UTF-8";
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET2_FMT);
            String expResult = "UTF-8";
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET3_FMT);
            String expResult = "UTF-8";
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET10_FMT);
            String expResult = "UTF-8";
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET21_FMT);
            String expResult = "Shift_JIS";
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET12_FMT);
            String expResult = "UTF-8";
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
        {
            HttpMessage instance = HttpMessage.parseHttpMessage(RES_CHARSET21_FMT);
            String expResult = "Shift_JIS";
            String result = instance.getGuessCharset();
            assertEquals(expResult, result);        
        }
    }
    
}
