package extension.burp;

import burp.IParameter;
import burp.IRequestInfo;
import java.io.IOException;
import java.net.MalformedURLException;
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
public class RequestInfoTest {

    public RequestInfoTest() {
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
     * Test of reqestInfo method, of class RequestInfo.
     */
    @Test
    public void testReqestInfo() {
        System.out.println("testReqestInfo");
        try {
            URL requestPath = RequestInfoTest.class.getResource("/resources/request.multipart.UTF-8");
            
            byte[] content = Files.readAllBytes(Path.of(requestPath.toURI()));
            final IRequestInfo expResult = new IRequestInfo() {
                @Override
                public String getMethod() {
                    return "POST";
                }

                @Override
                public URL getUrl() {
                    try {
                        return new URL("http://192.168.50.140:80/cgi-bin/multienc.cgi?charset=Shift_JIS&mode=disp");
                    } catch (MalformedURLException ex) {
                        return null;
                    }
                }

                @Override
                public List<String> getHeaders() {
                    List<String> headers = new ArrayList<>();
                    return headers;
                }

                @Override
                public List<IParameter> getParameters() {
                    List<IParameter> parameters = new ArrayList<>();
                    return parameters;
                }

                @Override
                public int getBodyOffset() {
                    return 703;
                }

                @Override
                public byte getContentType() {
                    return 2;
                }

            };
            RequestInfo reqInfo = new RequestInfo(expResult, content);
            assertEquals(expResult.getMethod(), reqInfo.getMethod());
            assertEquals(expResult.getBodyOffset(), reqInfo.getBodyOffset());
            System.out.println("==========");
            System.out.println(reqInfo.getHeader());
            System.out.println("==========");
            System.out.println(reqInfo.getBody(StandardCharsets.UTF_8));
            System.out.println("==========");

        } catch (IOException ex) {
            Logger.getLogger(RequestInfoTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(RequestInfoTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
