package extension.helpers;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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

    @Test
    public void testParseHttpDate() {
        System.out.println("parseHttpDate");
        {
            ZonedDateTime tm1 = HttpMessage.parseHttpDate("Sun, 08 May 2022 04:06:13 GMT");
            System.out.println("parseHttpDate:" + tm1.toString());
            Assert.assertEquals(2022, tm1.getYear());
            Assert.assertEquals(Month.MAY, tm1.getMonth());
            Assert.assertEquals(8, tm1.getDayOfMonth());
            Assert.assertEquals(4, tm1.getHour());
            Assert.assertEquals(6, tm1.getMinute());
            Assert.assertEquals(13, tm1.getSecond());

            ZonedDateTime tm2 = HttpMessage.parseHttpDate("Fri, 10-Jun-2022 01:55:24 GMT");
            System.out.println("parseHttpDate:" + tm2.toString());
            Assert.assertEquals(2022, tm2.getYear());
            Assert.assertEquals(Month.JUNE, tm2.getMonth());
            Assert.assertEquals(10, tm2.getDayOfMonth());
            Assert.assertEquals(1, tm2.getHour());
            Assert.assertEquals(55, tm2.getMinute());
            Assert.assertEquals(24, tm2.getSecond());

            ZonedDateTime tm3 = HttpMessage.parseHttpDate("Mon, 10-Jun-2999 00:55:10 GMT");
            System.out.println("parseHttpDate:" + tm3.toString());
            Assert.assertEquals(2999, tm3.getYear());
            Assert.assertEquals(Month.JUNE, tm3.getMonth());
            Assert.assertEquals(10, tm3.getDayOfMonth());
            Assert.assertEquals(0, tm3.getHour());
            Assert.assertEquals(55, tm3.getMinute());
            Assert.assertEquals(10, tm3.getSecond());

            ZonedDateTime tm4 = HttpMessage.parseHttpDate("Sun, 8 May 2022 4:6:3 GMT");
            System.out.println("parseHttpDate:" + tm1.toString());
            Assert.assertEquals(2022, tm4.getYear());
            Assert.assertEquals(Month.MAY, tm4.getMonth());
            Assert.assertEquals(8, tm4.getDayOfMonth());
            Assert.assertEquals(4, tm4.getHour());
            Assert.assertEquals(6, tm4.getMinute());
            Assert.assertEquals(3, tm4.getSecond());

            ZonedDateTime tm5 = HttpMessage.parseHttpDate(" Sun, 08 May 2022 04:06:13 GMT ");
            System.out.println("parseHttpDate:" + tm5.toString());
            Assert.assertEquals(2022, tm5.getYear());
            Assert.assertEquals(Month.MAY, tm5.getMonth());
            Assert.assertEquals(8, tm5.getDayOfMonth());
            Assert.assertEquals(4, tm5.getHour());
            Assert.assertEquals(6, tm5.getMinute());
            Assert.assertEquals(13, tm5.getSecond());

        }

    }

    @Test
    public void testParseHttpDateOffset() {
        System.out.println("parseHttpDateOffset");
        {
            ZonedDateTime tm1 = HttpMessage.parseHttpDate("Sat, 4 Jun 2022 11:20:00 +0900");
            System.out.println("parseHttpDate:" + tm1.toString());
            Assert.assertEquals(2022, tm1.getYear());
            Assert.assertEquals(Month.JUNE, tm1.getMonth());
            Assert.assertEquals(4, tm1.getDayOfMonth());
            Assert.assertEquals(11, tm1.getHour());
            Assert.assertEquals(20, tm1.getMinute());
            Assert.assertEquals(0, tm1.getSecond());
        }
    }

    @Test
    public void testParseHttpDateAsLocalTime() {
        System.out.println("parseHttpDateAsLocalTime");
        {
            LocalDateTime tm1 = HttpMessage.parseHttpDateAsLocal("Sun, 08 May 2022 04:06:13 GMT", ZoneId.of("Asia/Tokyo"));
            System.out.println("parseHttpDate:" + tm1.toString());
            Assert.assertEquals(2022, tm1.getYear());
            Assert.assertEquals(Month.MAY, tm1.getMonth());
            Assert.assertEquals(8, tm1.getDayOfMonth());
            Assert.assertEquals(4+9, tm1.getHour());
            Assert.assertEquals(6, tm1.getMinute());
            Assert.assertEquals(13, tm1.getSecond());

            LocalDateTime tm2 = HttpMessage.parseHttpDateAsLocal("Fri, 10-Jun-2022 01:55:24 GMT", ZoneId.of("Asia/Tokyo"));
            System.out.println("parseHttpDate:" + tm2.toString());
            Assert.assertEquals(2022, tm2.getYear());
            Assert.assertEquals(Month.JUNE, tm2.getMonth());
            Assert.assertEquals(10, tm2.getDayOfMonth());
            Assert.assertEquals(1+9, tm2.getHour());
            Assert.assertEquals(55, tm2.getMinute());
            Assert.assertEquals(24, tm2.getSecond());
        }
    }

}
