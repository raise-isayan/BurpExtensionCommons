package extension.burp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author isayan
 */
public class BurpUtilTest {

    public BurpUtilTest() {
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
     * Test of parseFilterPattern method, of class BurpUtil.
     */
    @Test
    public void testParseFilterPattern() {
        System.out.println("parseFilterPattern");
        String pattern = "jpg,png,gif,js.map";
        {
            String result = BurpUtil.parseFilterPattern(pattern);
            System.out.println("pattern:" + result);
            assertEquals("\\.(\\Qjpg\\E|\\Qpng\\E|\\Qgif\\E|\\Qjs.map\\E)$", result);
        }
        {
            String expResult = "test.jpg";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertTrue(m.find());
        }
        {
            String expResult = "test.jpg?xxx";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertFalse(m.matches());
        }
        {
            String expResult = "test.gif";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertTrue(m.find());
        }
        {
            String expResult = "test.js.map";
            String result = BurpUtil.parseFilterPattern(pattern);
            Pattern p = Pattern.compile(result);
            Matcher m = p.matcher(expResult);
            assertTrue(m.find());
        }
    }

    @Test
    public void testSuiteVersion() {
        System.out.println("testSuiteVersion");
        {
            // v2021.12.1 -
            BurpVersion suite = new BurpVersion("Burp Suite Professional", 20211201000000000L);
            assertEquals("Burp Suite Professional", suite.getProductName());
            assertEquals("2021", suite.getYear());
            assertEquals("12", suite.getMajor());
            assertEquals("01", suite.getMinor());
            assertEquals("000", suite.getRevision());
            assertEquals("000000", suite.getBuild());
        }
        {
            // v2022.12.6 -
            BurpVersion suite = new BurpVersion("Burp Suite Professional", 20221206000000000L);
            assertEquals("Burp Suite Professional", suite.getProductName());
            assertEquals("2022", suite.getYear());
            assertEquals("12", suite.getMajor());
            assertEquals("06", suite.getMinor());
            assertEquals("000", suite.getRevision());
            assertEquals("000000", suite.getBuild());
        }
        {
            // v2023.1.1-18663
            BurpVersion suite = new BurpVersion("Burp Suite Professional", 20230101000018663L);
            assertEquals("Burp Suite Professional", suite.getProductName());
            assertEquals("2023", suite.getYear());
            assertEquals("01", suite.getMajor());
            assertEquals("01", suite.getMinor());
            assertEquals("000", suite.getRevision());
            assertEquals("018663", suite.getBuild());
        }
        {
            // v2020.7
            BurpVersion suite = new BurpVersion("Burp Suite Community Edition", 20200701000000000L);
            assertEquals("Burp Suite Community Edition", suite.getProductName());
            assertEquals("2020", suite.getYear());
            assertEquals("07", suite.getMajor());
            assertEquals("01", suite.getMinor());
            assertEquals("000", suite.getRevision());
            assertEquals("000000", suite.getBuild());
        }
        {
            // v2023.2.1-19050
            BurpVersion suite = new BurpVersion("Burp Suite Community Edition", 20230201000019050L);
            assertEquals("Burp Suite Community Edition", suite.getProductName());
            assertEquals("2023", suite.getYear());
            assertEquals("02", suite.getMajor());
            assertEquals("01", suite.getMinor());
            assertEquals("000", suite.getRevision());
            assertEquals("019050", suite.getBuild());
        }
        {
            // v2023.1.2
            BurpVersion suite = new BurpVersion("Burp Suite Support", 20230102000000000L);
            assertEquals("Burp Suite Support", suite.getProductName());
            assertEquals("2023", suite.getYear());
            assertEquals("01", suite.getMajor());
            assertEquals("02", suite.getMinor());
            assertEquals("000", suite.getRevision());
            assertEquals("000000", suite.getBuild());
        }
        {
            // v2023.9 -
            BurpVersion suite = new BurpVersion("Burp Suite Professional", 20230901000000000L);
            assertEquals("Burp Suite Professional", suite.getProductName());
            assertEquals("2023", suite.getYear());
            assertEquals("09", suite.getMajor());
            assertEquals("01", suite.getMinor());
            assertEquals("000", suite.getRevision());
            assertEquals("000000", suite.getBuild());
        }

        {
            // Professional / Community 2024.2.1.3 build:28102 BuldNumber:20240201003028102
            BurpVersion suite = new BurpVersion("Burp suite Community Edition", 20240201003028102L);
            assertEquals("Burp suite Community Edition", suite.getProductName());
            assertEquals("2024", suite.getYear());
            assertEquals("02", suite.getMajor());
            assertEquals("01", suite.getMinor());
            assertEquals("003", suite.getRevision());
            assertEquals("028102", suite.getBuild());
        }

    }

    @Test
    public void testCompareSuiteVersion() {
        System.out.println("testCompareSuiteVersion");
        {
            BurpVersion suite = new BurpVersion("Burp suite Community Edition", 20240201003028102L);
            assertFalse(BurpVersion.isUnsupportVersion(suite));
        }
        {
            BurpVersion suite = new BurpVersion("Burp suite Community Edition", 20240101004027446L);
            assertTrue(BurpVersion.isUnsupportVersion(suite));
        }

    }


//    @Test
//    public void testCompareSuiteVersion() {
//        System.out.println("testCompareSuiteVersion");
//        // https://portswigger.net/burp/releases/professional-community-2023-1
//        // montoya Api 1.0.0
//        final BurpVersion SUPPORT_MIN_VERSION = new BurpVersion("Burp Suite Support v2023.1.2");
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Community Edition v2023.1-18440 ");
//            assertEquals(-1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Community Edition v2023.2.1-19050 ");
//            assertEquals(1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Professional v2023.1.1 - ");
//            assertEquals(-1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Professional v2023.1.2 - ");
//            assertEquals(0, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Professional v2023.1.3 - ");
//            assertEquals(1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Professional v2023.9 - ");
//            assertEquals(8, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//
//    }

//    @Test
//    public void testCompareSuiteMontoyaVersion() {
//        System.out.println("testCompareSuiteMontoyaVersion");
//        final BurpVersion SUPPORT_MIN_VERSION = new BurpVersion("Burp suite v2024.2.1.3", 20240201003028102L);
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_1_1_VERSION_COMMUNITY);
//            assertEquals(-1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_1_2_VERSION_COMMUNITY);
//            assertEquals(0, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_1_3_VERSION_COMMUNITY);
//            assertEquals(1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_2_1_VERSION_COMMUNITY);
//            assertEquals(1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_1_1_VERSION_PRO);
//            assertEquals(-1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_1_2_VERSION_PRO);
//            assertEquals(0, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_1_3_VERSION_PRO);
//            assertEquals(1, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//        {
//            BurpVersion suite = new BurpVersion(BurpVersionTest.BURP_2023_9_VERSION_PRO);
//            assertEquals(8, suite.compareTo(SUPPORT_MIN_VERSION));
//        }
//    }

//    @Test
//    public void testCompareMinor() {
//        System.out.println("testCompareMinor");
//        assertEquals(-1, BurpVersion.compareMinor("1.7", "1.7.2"));
//        assertEquals(0, BurpVersion.compareMinor("1.1", "1.1"));
//        assertEquals(-1, BurpVersion.compareMinor("1", "1.2"));
//        assertEquals(-1, BurpVersion.compareMinor("1.7.1", "1.7.2"));
//        assertEquals(1, BurpVersion.compareMinor("1.7.2", "1.7.1"));
//        assertEquals(-2, BurpVersion.compareMinor("1.7.1", "1.7.3"));
//        assertEquals(2, BurpVersion.compareMinor("1.7.3", "1.7.1"));
//        assertEquals(1, BurpVersion.compareMinor("1.7.1", "1.7"));
//        assertEquals(1, BurpVersion.compareMinor("1.7.1", "1"));
//        assertEquals(-1, BurpVersion.compareMinor("1", "1.7.1"));
//    }

}
