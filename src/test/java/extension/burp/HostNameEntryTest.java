package extension.burp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Inet4Address;
import java.net.Inet6Address;
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
public class HostNameEntryTest {

    public HostNameEntryTest() {
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
     * Test of isIPv6 method, of class HostNameEntry.
     */
    @Test
    public void testIPv4() {
        System.out.println("testIPv4");
        try {
            {
                HostNameEntry entry = new HostNameEntry("192.0.2.2", "www.example.com");
                InetAddress inet = entry.asInetAddress();
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("192.0.2.2", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
                assertTrue(inet instanceof Inet4Address);
            }
            {
                HostNameEntry entry = new HostNameEntry("198.51.100.100", "WWW.example.com");
                InetAddress inet = entry.asInetAddress();
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("198.51.100.100", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
                assertTrue(inet instanceof Inet4Address);
            }
        } catch (UnknownHostException ex) {
        }
    }

    @Test
    public void testIPv6() {
        System.out.println("testIPv6");
        try {
            {
                HostNameEntry entry = new HostNameEntry("2001:0db8:0000:0000:0000:0000:0000:0001", "www.example.com");
                InetAddress inet = entry.asInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
            }
            {
                HostNameEntry entry = new HostNameEntry("2001:db8::", "www.example.com");
                InetAddress inet = entry.asInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());

            }
            {
                HostNameEntry entry = new HostNameEntry("2001:db8:1234:5678::1", "www.example.com");
                InetAddress inet = entry.asInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
            }
            {
                HostNameEntry entry = new HostNameEntry("2001:db8:0:0:1::1 ", "WWW.example.com");
                InetAddress inet = entry.asInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
            }
        } catch (UnknownHostException ex) {
        }
    }

}
