package extension.burp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class HostNameTest {

    public HostNameTest() {
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
    public void testParseHostsIPv4() {
        try {
            System.out.println("testParseHostsIPv4");
            URL hostFile = HostNameTest.class.getResource("/resources/hosts_ipv4");
            List<String> lines = Files.readAllLines(Path.of(hostFile.toURI()), StandardCharsets.UTF_8);
            HostName hostname = HostName.parseHosts(lines.stream());
            List<HostNameEntry> entryList = hostname.getHostNameList();
            for (HostNameEntry entry : entryList) {
                System.out.println("entry:" + (entry.isIPv4() ? "IPv4" : "IPv6") + "\t" + entry.getIPAddress() + "\t" + entry.asInetAddress().getHostAddress() + "\t" + entry.getHostName());
            }
        } catch (IOException ex) {
            fail(ex);
        } catch (URISyntaxException ex) {
            fail(ex);
        }
    }

    @Test
    public void testParseHostsMix() throws Exception {
        System.out.println("testParseHostsMix");
        URL hostFile = HostNameTest.class.getResource("/resources/hosts_mix");
        List<String> lines = Files.readAllLines(Path.of(hostFile.toURI()), StandardCharsets.UTF_8);
        HostName hostname = HostName.parseHosts(lines.stream());
        List<HostNameEntry> entryList = hostname.getHostNameList();
        for (HostNameEntry entry : entryList) {
            System.out.println("entry:" + (entry.isIPv4() ? "IPv4" : "IPv6") + "\t" + entry.getIPAddress() + "\t" + entry.asInetAddress().getHostAddress() + "\t" + entry.getHostName());
        }
    }


    @Test
    public void testIPv6() throws Exception {
        System.out.println("testIPv6");
        URL hostFile = HostNameTest.class.getResource("/resources/hosts_mix");
        List<String> lines = Files.readAllLines(Path.of(hostFile.toURI()), StandardCharsets.UTF_8);
        {
            HostName hostname = HostName.parseHosts(lines.stream());
            HostNameEntry entry = hostname.resolvInetAddress("2001:db8:0:0:0:0:0:1");
            System.out.println(entry.getHostName() + "/" + entry.getIPAddress());
            assertEquals("www.example.co.jp", entry.getHostName());
        }
        {
            HostName hostname = HostName.parseHosts(lines.stream());
            HostNameEntry entry = hostname.resolvInetAddress("2001:db8::1");
            System.out.println(entry.getHostName() + "/" + entry.getIPAddress());
            assertEquals("www.example.co.jp", entry.getHostName());
        }
    }

}
