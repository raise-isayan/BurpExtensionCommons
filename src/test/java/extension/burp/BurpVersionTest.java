package extension.burp;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.burpsuite.BurpSuite;
import java.util.Properties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author isayan
 */
public class BurpVersionTest {

    public BurpVersionTest() {
    }

    private MontoyaApi mockApi;
    private BurpSuite burpSuteApi;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockApi = Mockito.mock(MontoyaApi.class);
        this.burpSuteApi = Mockito.mock(BurpSuite.class);
        Mockito.when(this.mockApi.burpSuite()).thenReturn(this.burpSuteApi);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testProperty() {
        System.out.println("testProperty");
        Properties p = System.getProperties();
        p.list(System.out);
    }

    @Test
    public void testOS() {
        System.out.println("testOS");
        BurpVersion.OSType os = BurpVersion.getOSType();
        assertNotEquals(os, BurpVersion.OSType.UNKOWN);
    }

    @Test
    public void testArch() {
        System.out.println("testArch");
        BurpVersion.ArcType arch = BurpVersion.getArchType();
        assertNotEquals(arch, BurpVersion.ArcType.UNKOWN);
    }

//    /**
//     * Test of parseFreeVersion method, of class BurpVersion.
//     */
//    @Test
//    public void testParseFreeVersion() {
//        System.out.println("parseFreeVersion");
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Community Edition v2020.6");
//            assertEquals("2020", suite.getYear());
//            assertEquals("06", suite.getMajor());
//            assertEquals("01", suite.getMinor());
//            assertNull(suite.getBuild());
//            assertFalse(suite.isProfessional());
//            assertEquals("Burp Suite Community Edition", suite.getProductName());
//            assertEquals("Burp Suite Community Edition 2020.6", suite.getVersion());
//            System.out.println(suite.getBurpConfigHome());
//            System.out.println(suite.getBurpConfigFile());
//            assertTrue(BurpVersion.isUnsupportVersion(suite));
//        }
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Community Edition v2023.9.1");
//            assertEquals("2023", suite.getMajor());
//            assertEquals("9.1", suite.getMinor());
//            assertNull(suite.getBuild());
//            assertEquals(2023, suite.getMajorVersion());
//            assertEquals(9, suite.getMinorVersion());
//            assertFalse(suite.isProfessional());
//            assertEquals("Burp Suite Community Edition", suite.getProductName());
//            assertEquals("Burp Suite Community Edition 2023.9.1", suite.getVersion());
//            System.out.println(suite.getBurpConfigHome());
//            System.out.println(suite.getBurpConfigFile());
//            assertFalse(BurpVersion.isUnsupportVersion(suite));
//        }
//        {
//            BurpVersion suite = new BurpVersion("Burp Suite Community Edition v2023.10.3");
//            assertEquals("2023", suite.getMajor());
//            assertEquals("10.3", suite.getMinor());
//            assertNull(suite.getBuild());
//            assertEquals(2023, suite.getMajorVersion());
//            assertEquals(10, suite.getMinorVersion());
//            assertFalse(suite.isProfessional());
//            assertEquals("Burp Suite Community Edition", suite.getProductName());
//            assertEquals("Burp Suite Community Edition 2023.10.3", suite.getVersion());
//            System.out.println(suite.getBurpConfigHome());
//            System.out.println(suite.getBurpConfigFile());
//            assertFalse(BurpVersion.isUnsupportVersion(suite));
//        }
//    }


//    @Test
//    public void testSuiteMontoyaApiVersion() {
//        System.out.println("testSuiteMontoyaApiVersion");
//        {
//            Mockito.when(this.mockApi.burpSuite().version()).thenReturn(BURP_2020_9_5_VERSION_FREE);
//            BurpVersion suite = new BurpVersion(this.mockApi);
//            assertEquals("2020", suite.getMajor());
//            assertEquals("9.5", suite.getMinor());
//            assertEquals("16933", suite.getBuild());
//            assertEquals(2020, suite.getMajorVersion());
//            assertEquals(9, suite.getMinorVersion());
//            assertFalse(suite.isProfessional());
//            assertEquals("Burp Suite Community Edition", suite.getProductName());
//            assertEquals("Burp Suite Community Edition 2020.9.5", suite.getVersion());
//            System.out.println(suite.getBurpConfigHome());
//            System.out.println(suite.getBurpConfigFile());
//            assertTrue(BurpVersion.isUnsupportVersion(suite));
//        }
//        {
//            Mockito.when(this.mockApi.burpSuite().version()).thenReturn(BURP_2020_9_5_VERSION_PRO);
//            BurpVersion suite = new BurpVersion(this.mockApi);
//            assertEquals("2020", suite.getMajor());
//            assertEquals("9.5", suite.getMinor());
//            assertEquals("16933", suite.getBuild());
//            assertEquals(2020, suite.getMajorVersion());
//            assertEquals(9, suite.getMinorVersion());
//            assertTrue(suite.isProfessional());
//            assertEquals("Burp Suite Professional Edition", suite.getProductName());
//            assertEquals("Burp Suite Professional Edition 2020.9.5", suite.getVersion());
//            assertTrue(BurpVersion.isUnsupportVersion(suite));
//        }
//        {
//            Mockito.when(this.mockApi.burpSuite().version()).thenReturn(BURP_2023_1_1_VERSION_PRO);
//            BurpVersion suite = new BurpVersion(this.mockApi);
//            assertEquals("Burp Suite Professional", suite.getProductName());
//            assertEquals("2023", suite.getMajor());
//            assertEquals(2023, suite.getMajorVersion());
//            assertEquals("1.1", suite.getMinor());
//            assertTrue(suite.isProfessional());
//            assertEquals("18663", suite.getBuild());
//            assertTrue(BurpVersion.isUnsupportVersion(suite));
//        }
//        {
//            Mockito.when(this.mockApi.burpSuite().version()).thenReturn(BURP_2023_2_1_VERSION_COMMUNITY);
//            BurpVersion suite = new BurpVersion(this.mockApi);
//            assertEquals("Burp Suite Community Edition", suite.getProductName());
//            assertEquals("2023", suite.getMajor());
//            assertEquals(2023, suite.getMajorVersion());
//            assertEquals("2.1", suite.getMinor());
//            assertFalse(suite.isProfessional());
//            assertEquals("19050", suite.getBuild());
//            assertFalse(BurpVersion.isUnsupportVersion(suite));
//        }
//    }

//    private final static BurpVersion SUPPORT_BAMBDA = new BurpVersion("Burp Suite Support", "2023", "10.3", "");
//
//    @Test
//    public void testSuiteCompareApiVersion() {
//        System.out.println("testSuiteCompareApiVersion");
//        {
//            Mockito.when(this.mockApi.burpSuite().version()).thenReturn(BURP_2023_2_1_VERSION_COMMUNITY);
//            BurpVersion suite = new BurpVersion(this.mockApi);
//            System.out.println("compareTo:" + suite.compareTo(SUPPORT_BAMBDA));
//        }
//        {
//            Mockito.when(this.mockApi.burpSuite().version()).thenReturn(BURP_2023_10_3_VERSION_PRO);
//            BurpVersion suite = new BurpVersion(this.mockApi);
//            System.out.println("compareTo:" + suite.compareTo(SUPPORT_BAMBDA));
//        }
//        {
//            Mockito.when(this.mockApi.burpSuite().version()).thenReturn(BURP_2023_10_3_7_VERSION_PRO);
//            BurpVersion suite = new BurpVersion(this.mockApi);
//            System.out.println("compareTo:" + suite.compareTo(SUPPORT_BAMBDA));
//        }
//    }

}
