package extension.burp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isayan
 */
public class ExtensionHelperTest {

    public ExtensionHelperTest() {
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
    public void testMultiLine() {
        System.out.println("testMultiLine");
        {
            String multilineURL = "https://www.example.com:8443\r\nhttps://www.example.co.jp:443\r\nhttp://www.example.co.jp:8080\nhttp://www.example.co.jp:80\rhttps://www.google.com/ localhost";
            String[] urls = ExtensionHelper.parseMultiLineURL(multilineURL, true);
            assertEquals(6, urls.length);
            assertEquals("https://www.example.com:8443", urls[0]);
            assertEquals("https://www.example.co.jp", urls[1]);
            assertEquals("http://www.example.co.jp:8080", urls[2]);
            assertEquals("http://www.example.co.jp", urls[3]);
            assertEquals("https://www.google.com/", urls[4]);
            assertEquals("localhost", urls[5]);
        }
        {
            String multilineURL = "https://www.example.com:8443\r\nhttps://www.example.co.jp:443\r\nhttp://www.example.co.jp:8080\nhttp://www.example.co.jp:80\rhttps://www.google.com/ localhost";
            String[] urls = ExtensionHelper.parseMultiLineURL(multilineURL, false);
            assertEquals(5, urls.length);
            assertEquals("https://www.example.com:8443", urls[0]);
            assertEquals("https://www.example.co.jp", urls[1]);
            assertEquals("http://www.example.co.jp:8080", urls[2]);
            assertEquals("http://www.example.co.jp", urls[3]);
            assertEquals("https://www.google.com/", urls[4]);
        }
        {
            String multilineURL = "https://www.example.com:8443\r\nhttps://www.example.co.jp:443\r\nhttp://www.example.co.jp:8080\nhttp://www.example.co.jp:80\rhttps://www.google.com/ localhost";
            String[] urls = ExtensionHelper.parseMultiLineNetloc(multilineURL, true);
            assertEquals(6, urls.length);
            assertEquals("www.example.com:8443", urls[0]);
            assertEquals("www.example.co.jp", urls[1]);
            assertEquals("www.example.co.jp:8080", urls[2]);
            assertEquals("www.example.co.jp", urls[3]);
            assertEquals("www.google.com", urls[4]);
            assertEquals("localhost", urls[5]);
        }
        {
            String multilineURL = "https://www.example.com:8443\r\nhttps://www.example.co.jp:443\r\nhttp://www.example.co.jp:8080\nhttp://www.example.co.jp:80\rhttps://www.google.com/ localhost";
            String[] urls = ExtensionHelper.parseMultiLineNetloc(multilineURL, false);
            assertEquals(5, urls.length);
            assertEquals("www.example.com:8443", urls[0]);
            assertEquals("www.example.co.jp", urls[1]);
            assertEquals("www.example.co.jp:8080", urls[2]);
            assertEquals("www.example.co.jp", urls[3]);
            assertEquals("www.google.com", urls[4]);
        }
    }


}
