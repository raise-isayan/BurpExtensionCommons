package extension.helpers;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Marker;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.ContentType;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.requests.HttpTransformation;
import java.util.List;
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
