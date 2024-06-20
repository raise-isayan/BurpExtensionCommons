package extension.helpers;

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
public class HttpMessageTest {

    public HttpMessageTest() {
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
     * Test of httpMessage method, of class HttpMessage.
     */
    @Test
    public void testHttpMessage_String_String() {
        System.out.println("httpMessage");
        String header = "header";
        String body = "body";
        HttpMessage result = HttpMessage.httpMessage(header, body);
        assertEquals(header, result.getHeader());
        assertEquals(body, result.getBody());
    }

    /**
     * Test of httpMessage method, of class HttpMessage.
     */
    @Test
    public void testHttpMessage_String1() {
        System.out.println("httpMessage");
        String message
                = "GET / HTTP/1.1\r\nUser-Agent: UA\r\nHost: test\r\n"
                + "\r\n"
                + "bodyString";
        HttpMessage result = HttpMessage.httpMessage(message);
        assertEquals("GET / HTTP/1.1\r\nUser-Agent: UA\r\nHost: test\r\n", result.getHeader());
        assertEquals("bodyString", result.getBody());
        System.out.println("httpMessage:" + result.getMessage() + "\r\n\r\n");
        assertEquals(message, result.getMessage());
        assertFalse(result.isModifiedHeader());
        assertFalse(result.isModifiedBody());
        result.setHeader("update heder");
        assertTrue(result.isModifiedHeader());
        assertFalse(result.isModifiedBody());
        result.setBody("update body");
        assertTrue(result.isModifiedBody());
    }

    /**
     * Test of httpMessage method, of class HttpMessage.
     */
    @Test
    public void testHttpMessage_String2() {
        System.out.println("httpMessage");
        String message
                = "GET / HTTP/1.1\r\nUser-Agent: UA\r\nHost: test\r\n"
                + "\r\n";
        HttpMessage result = HttpMessage.httpMessage(message);
        assertEquals("GET / HTTP/1.1\r\nUser-Agent: UA\r\nHost: test\r\n", result.getHeader());
        assertEquals("", result.getBody());
        System.out.println("httpMessage:" + result.getMessage() + "\r\n\r\n");
        assertEquals(message, result.getMessage());
        assertFalse(result.isModifiedHeader());
        assertFalse(result.isModifiedBody());
        result.setHeader("update heder");
        assertTrue(result.isModifiedHeader());
        assertFalse(result.isModifiedBody());
        result.setBody("update body");
        assertTrue(result.isModifiedBody());
    }

}
