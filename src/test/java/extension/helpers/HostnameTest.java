package extension.helpers;

import java.io.File;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.util.HashMap;
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
public class HostnameTest {

    public HostnameTest() {
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
     * Test of parseFile method, of class Hostname.
     */
    @Test
    public void testParseFile() {
        System.out.println("parseFile");
        String windir = System.getenv("windir");
        File file = Paths.get(windir, "System32", "drivers", "etc", "hosts").toFile();
        HashMap<String, InetAddress> expResult = null;
        HashMap<String, InetAddress> result = Hostname.parseFile(file);
        for (String key : result.keySet()) {
            System.out.println("host:" + key + " -> " + result.get(key).getHostAddress());
        }

//        assertEquals(expResult, result);
    }

}
