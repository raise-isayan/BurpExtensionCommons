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
public class HttpRequestWapperTest {

    public HttpRequestWapperTest() {
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
    public void testGetUrlPath() {
        System.out.println("getUrlPath");
        {
            String url = "https://www.example.com/?aaa=zzz&bbbb=yyy";
            String expResult = "https://www.example.com/";
            String result = HttpRequestWapper.getUrlPath(url);
            assertEquals(expResult, result);
        }
        {
            String url = "https://www.example.com/";
            String result = HttpRequestWapper.getUrlPath(url);
            String expResult = "https://www.example.com/";
            assertEquals(expResult, result);
        }
        {
            String url = "https://www.example.com/?";
            String expResult = "https://www.example.com/";
            String result = HttpRequestWapper.getUrlPath(url);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getQueryParameter method, of class HttpRequestWapper.
     */
    @Test
    public void testGetQueryParameter() {
        System.out.println("getQueryParameter");
        {
            String url = "https://www.example.com/?aaa=zzz&bbbb=yyy";
            String expResult = "aaa=zzz&bbbb=yyy";
            String result = HttpRequestWapper.getQueryParameter(url);
            assertEquals(expResult, result);
        }
        {
            String url = "https://www.example.com/";
            String expResult = null;
            String result = HttpRequestWapper.getQueryParameter(url);
            assertEquals(expResult, result);
        }
        {
            String url = "https://www.example.com/?";
            String expResult = null;
            String result = HttpRequestWapper.getQueryParameter(url);
            assertEquals(expResult, result);
        }
    }

}
