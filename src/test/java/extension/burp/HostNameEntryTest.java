package extension.burp;

import extension.helpers.IpUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.text.ParseException;
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

    @Test
    public void testIPv4Address() {
        System.out.println("testIPv4Address");
        try {
            InetAddress ipaddr = InetAddress.getByAddress(IpUtil.parseIPAddressByte("192.168.0.2"));
            System.out.println(ipaddr.getHostAddress());
            byte[] expResult = InetAddress.getByName("192.168.0.2").getAddress();
            byte[] result = IpUtil.parseIPAddressByte("192.168.0.2");
            assertArrayEquals(expResult, result);
        } catch (ParseException ex) {
            fail(ex);
        } catch (UnknownHostException ex) {
            fail(ex);
        }
    }

    @Test
    public void testIPv6Address() {
        System.out.println("testIPv6Address");
        try {
            {
                InetAddress ipaddr = InetAddress.getByAddress(IpUtil.parseIPAddressByte("2001:db8:1234:5678::1"));
                System.out.println(ipaddr.getHostAddress());
                byte[] expResult = InetAddress.getByName("2001:db8:1234:5678::1").getAddress();
                byte[] result = IpUtil.parseIPAddressByte("2001:db8:1234:5678::1");
                assertArrayEquals(expResult, result);
            }
            {
                InetAddress ipaddr = InetAddress.getByAddress(IpUtil.parseIPAddressByte("2001:DB8:1234:5678::1"));
                System.out.println(ipaddr.getHostAddress());
                byte[] expResult = InetAddress.getByName("2001:DB8:1234:5678::1").getAddress();
                byte[] result = IpUtil.parseIPAddressByte("2001:db8:1234:5678::1");
                assertArrayEquals(expResult, result);
            }
        } catch (ParseException ex) {
            fail(ex);
        } catch (UnknownHostException ex) {
            fail(ex);
        }
    }

    @Test
    public void testEqualsInetAddress() {
        System.out.println("testEqualsInetAddress");
        {
            HostNameEntry entry = new HostNameEntry("192.0.2.2", "www.example.com");
            assertTrue(entry.equalsInetAddress("192.0.2.2"));
        }
        {
            HostNameEntry entry = new HostNameEntry("2001:0db8:0000:0000:0000:0000:0000:0001", "www.example.com");
            assertTrue(entry.equalsInetAddress("2001:db8:0:0:0:0:0:1"));
        }
        {
            HostNameEntry entry = new HostNameEntry("2001:db8:1234:5678::1", "www.example.com");
            assertTrue(entry.equalsInetAddress("2001:db8:1234:5678:0:0:0:1"));
        }
        {
            HostNameEntry entry = new HostNameEntry("2001:db8::", "www.example.com");
            assertTrue(entry.equalsInetAddress("2001:db8:0:0:0:0:0:0"));
        }
        {
            HostNameEntry entry = new HostNameEntry("2001:db8:0:0:1::1", "www.example.com");
            assertTrue(entry.equalsInetAddress("2001:db8:0:0:1:0:0:1"));
        }

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
                InetAddress inet = entry.asHostInetAddress();
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("192.0.2.2", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
                assertTrue(inet instanceof Inet4Address);
            }
            {
                HostNameEntry entry = new HostNameEntry("2001:0db8:0000:0000:0000:0000:0000:0001", "www.example.com");
                InetAddress inet = entry.asHostInetAddress();
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("2001:db8:0:0:0:0:0:1", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
                assertTrue(inet instanceof Inet6Address);
            }
        } catch (UnknownHostException ex) {
            fail(ex);
        }
    }

    @Test
    public void testIPv6() {
        System.out.println("testIPv6");
        try {
            {
                HostNameEntry entry = new HostNameEntry("2001:0db8:0000:0000:0000:0000:0000:0001", "www.example.com");
                InetAddress inet = entry.asHostInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("2001:db8:0:0:0:0:0:1", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
            }
            {
                HostNameEntry entry = new HostNameEntry("2001:db8::", "www.example.com");
                InetAddress inet = entry.asHostInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("2001:db8:0:0:0:0:0:0", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
            }
            {
                HostNameEntry entry = new HostNameEntry("2001:db8:1234:5678::1", "www.example.com");
                InetAddress inet = entry.asHostInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("2001:db8:1234:5678:0:0:0:1", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
            }
            {
                HostNameEntry entry = new HostNameEntry("2001:db8:0:0:1::1", "WWW.example.com");
                InetAddress inet = entry.asHostInetAddress();
                assertTrue(inet instanceof Inet6Address);
                System.out.println(inet.getHostName() + " -> " + inet.getHostAddress());
                assertEquals("2001:db8:0:0:1:0:0:1", inet.getHostAddress());
                assertEquals("www.example.com", inet.getHostName());
            }
        } catch (UnknownHostException ex) {
            fail(ex);
        }
    }

}
