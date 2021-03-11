package extension.burp;

import burp.ICookie;
import burp.IResponseInfo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
public class ResponseInfoTest {

    public ResponseInfoTest() {
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
     * Test of expResult method, of class ResponseInfo.
     */
    @Test
    public void testResponseInfo() {
        System.out.println("testResponseInfo");
        try {
            URL responsetPath = ResponseInfoTest.class.getResource("/resources/response.UTF-8");
            byte [] content = Files.readAllBytes(Path.of(responsetPath.toURI()));
            final IResponseInfo expResult = new IResponseInfo() {
                @Override
                public List<String> getHeaders() {
                    List<String> headers = new ArrayList<>();
                    return headers;
                }

                @Override
                public int getBodyOffset() {
                    return 191;
                }

                @Override
                public short getStatusCode() {
                    return 200;
                }

                @Override
                public List<ICookie> getCookies() {
                    List<ICookie> cookies = new ArrayList<>();
                    return cookies;
                }

                @Override
                public String getStatedMimeType() {
                    return "HTML";
                }

                @Override
                public String getInferredMimeType() {
                    return "HTML";
                }

            };
            ResponseInfo resInfo = new ResponseInfo(expResult, content);
            assertEquals(expResult.getStatusCode(), resInfo.getStatusCode());
            assertEquals(expResult.getBodyOffset(), resInfo.getBodyOffset());
            assertEquals(expResult.getInferredMimeType(), resInfo.getInferredMimeType());
            assertEquals(expResult.getStatedMimeType(), resInfo.getStatedMimeType());

            System.out.println("==========");
            System.out.println(resInfo.getHeader());
            System.out.println("==========");
            System.out.println(resInfo.getBody(StandardCharsets.UTF_8));
            System.out.println("==========");

        } catch (IOException ex) {
            Logger.getLogger(ResponseInfoTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResponseInfoTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

    }

}
