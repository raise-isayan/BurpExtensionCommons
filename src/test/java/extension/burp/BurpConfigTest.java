package extension.burp;

import burp.BurpPreferences;
import extension.helpers.FileUtil;
import extension.helpers.StringUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
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
public class BurpConfigTest {

    public BurpConfigTest() {
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
     * Test of loadCACeart method, of class BurpConfig.
     */
    @Test
    public void testLoadCACeart() {
        try {
            System.out.println("loadCACeart");
            KeyStore result = BurpPreferences.loadCACeart();
            Properties p = System.getProperties();
            Enumeration<String> e = result.aliases();
            while (e.hasMoreElements()) {
                // cacert
                String alias = e.nextElement();
                assertEquals("cacert", alias);
            }
        } catch (KeyStoreException ex) {
            fail();
        }
    }

    @Test
    public void testSystemProperty() {
        System.out.println("SystemProperty");
        Properties p = System.getProperties();
        p.list(System.out);
    }

    @Test
    public void testUpdateHostnameResolution() {
        System.out.println("testUpdateHostnameResolution");
        try {
            String configFile = BurpConfigTest.class.getResource("/resources/hostname_resolution.json").getPath();
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            List<BurpConfig.HostnameResolution> hosts = new ArrayList<>();
            hosts.add(new BurpConfig.HostnameResolution(true, "newhost", "192.0.2.11"));
            System.out.println("loadConfig:" + config);
            String updateConfig = BurpConfig.updateHostnameResolution(config, hosts);
            System.out.println("updateConfig:" + updateConfig);
            String removeConfig = BurpConfig.updateHostnameResolution(updateConfig, hosts, true);
            System.out.println("removeConfig:" + removeConfig);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateHostnameResolutionEmpty() {
        System.out.println("testUpdateHostnameResolutionEmpty");
        try {
            String configFile = BurpConfigTest.class.getResource("/resources/hostname_resolution_empty.json").getPath();
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            List<BurpConfig.HostnameResolution> hosts = new ArrayList<>();
            hosts.add(new BurpConfig.HostnameResolution(true, "newhost", "192.0.2.11"));
            System.out.println("loadConfig:" + config);
            String updateConfig = BurpConfig.updateHostnameResolution(config, hosts);
            System.out.println("updateConfig:" + updateConfig);
            String removeConfig = BurpConfig.updateHostnameResolution(updateConfig, hosts, true);
            System.out.println("removeConfig:" + removeConfig);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testUpdateSSLPassThroughRules() {
        System.out.println("testUpdateSSLPassThroughRules");
        try {
            String configFile = BurpConfigTest.class.getResource("/resources/ssl_pass_through_rules.json").getPath();
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            {
                List<BurpConfig.SSLPassThroughRule> rules = new ArrayList<>();
                rules.add(new BurpConfig.SSLPassThroughRule(true, "192.0.2.11", 443));
                System.out.println("loadConfig:" + config);
                String updateConfig = BurpConfig.updateSSLPassThroughRules(config, rules);
                System.out.println("updateConfig:" + updateConfig);
                String removeConfig = BurpConfig.updateSSLPassThroughRules(updateConfig, rules, true);
                System.out.println("removeConfig:" + removeConfig);
            }
            {
                String paste = "https://www.example.com:8433/\thttp://foo.bar/";
                URL[] urls = TargetScopeItem.parseMultilineURL(paste);
                List<BurpConfig.SSLPassThroughRule> rules = new ArrayList<>();
                for (URL u : urls) {
                    System.out.println("url:" + u.toExternalForm());
                    rules.add(new BurpConfig.SSLPassThroughRule(true, u.getHost(), u.getPort() > 0 ? u.getPort() : u.getDefaultPort()));
                }
                String updateConfig = BurpConfig.updateSSLPassThroughRules(config, rules, false);
                System.out.println("updateConfig2:" + updateConfig);
            }
        } catch (Exception e) {
            fail();
        }
    }

}
