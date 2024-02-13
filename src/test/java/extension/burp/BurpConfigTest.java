package extension.burp;

import burp.BurpPreferences;
import burp.MockMontoya;
import burp.api.montoya.MontoyaApi;
import extension.burp.BurpConfig.CharacterSetMode;
import extension.burp.BurpConfig.TargetScope;
import extension.helpers.FileUtil;
import extension.helpers.StringUtil;
import extension.helpers.json.JsonUtil;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.KeyStroke;
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
    private final static Logger logger = Logger.getLogger(BurpConfigTest.class.getName());

    public BurpConfigTest() {
    }

    private MockMontoya montoya;
    private MontoyaApi api;
    private MockMontoya.MockMontoyaObjectFactory mockFactory;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        this.montoya = new MockMontoya();
        this.api = montoya.api();
        this.mockFactory = this.montoya.instance(MockMontoya.MockMontoyaObjectFactory.class);
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
    public void testGetCharacterSets() {
        System.out.println("testGetCharacterSets");
        try {
            BurpConfig.CharacterSets charsets = BurpConfig.getCharacterSets(api);
            System.out.println("CharacterSets.Mode:" + charsets.getMode());
            assertEquals(CharacterSetMode.RECOGNIZE_AUTO.toIdent(), charsets.getMode());
            assertNull(charsets.getCharacterSet());
        } catch (Exception e) {
            fail();
        }
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
    public void testConfigSSLPassThroughRules() {
        System.out.println("testConfigSSLPassThroughRules");
        try {
            List<BurpConfig.SSLPassThroughRule> rules = new ArrayList<>();
            BurpConfig.configSSLPassThroughRules(api, rules);
        } catch (Exception e) {
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

    @Test
    public void testGetMatchReplaceRulesApi() {
        System.out.println("testGetMatchReplaceRulesApi");
        List<BurpConfig.MatchReplaceRule> rules = BurpConfig.getMatchReplaceRules(api);
        try {
            for (BurpConfig.MatchReplaceRule rule : rules) {
                System.out.println("==========");
                System.out.println("rule.isSimpleMatch:" + rule.isSimpleMatch());
                System.out.println("rule.getStringMatch:" + rule.getStringMatch());
                System.out.println("rule.getRuleType:" + rule.getRuleType());
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetMatchReplaceRules() {
        System.out.println("testGetMatchReplaceRules");
        String configFile = BurpConfigTest.class.getResource("/resources/match_replace_rules.json").getPath();
        try {
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            List<BurpConfig.MatchReplaceRule> rules = BurpConfig.getMatchReplaceRules(config);
            assertEquals(12, rules.size());
            for (BurpConfig.MatchReplaceRule rule : rules) {
                System.out.println("==========");
                System.out.println("rule.isSimpleMatch:" + rule.isSimpleMatch());
                System.out.println("rule.getStringMatch:" + rule.getStringMatch());
                System.out.println("rule.getRuleType:" + rule.getRuleType());
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testUpdateSocksProxy() {
        try {
            System.out.println("testUpdateSocksProxy(user)");
            {
                String configFile = BurpConfigTest.class.getResource("/resources/user_socks_proxy.json").getPath();
                String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
                System.out.println("loadConfig:" + config);
                String updateConfig = BurpConfig.updateSocksProxy(config, new BurpConfig.SocksProxy(true, "192.168.1.1", 8123, "user", "pass", false), false);
                System.out.println("updateConfig:" + updateConfig);

            }
            System.out.println("testUpdateSocksProxy(project)");
            {
                String configFile = BurpConfigTest.class.getResource("/resources/project_socks_proxy.json").getPath();
                String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
                System.out.println("loadConfig:" + config);
                String updateConfig = BurpConfig.updateSocksProxy(config, new BurpConfig.SocksProxy(true, "192.168.1.1", 8123, "user", "pass", false), true);
                System.out.println("updateConfig:" + updateConfig);
            }
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testGetTargetScope() {
        System.out.println("testGetTargetScope");
        {
            try {
                BurpConfig.TargetScope targetScope = new BurpConfig.TargetScope();
                List<BurpConfig.TargetScopeURL> targetURL = targetScope.getIncludeURL();
                targetURL.add(BurpConfig.TargetScopeURL.parseTargetURL("https://www.example,com/"));
                for (int i = 0; i < targetURL.size(); i++) {
                    System.out.println("target:" + targetURL.get(i).getPrefix());
                }
                assertTrue(targetURL.contains(BurpConfig.TargetScopeURL.parseTargetURL("https://www.example,com/")));
            } catch (MalformedURLException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    @Test
    public void testGetTargetScopeURL() {
        System.out.println("testGetTargetScopeURL");
        try {
            {
                BurpConfig.TargetScopeURL target_scope_url = BurpConfig.TargetScopeURL.parseTargetURL("https://www.example.com/path?search=test");
                assertTrue(target_scope_url.isEnabled());
                assertFalse(target_scope_url.getIncludeSubdomains());
                assertEquals("https://www.example.com/path", target_scope_url.getPrefix());
            }
            {
                BurpConfig.TargetScopeURL target_scope_url = BurpConfig.TargetScopeURL.parseTargetURL("http://www.example.com/path?search=test");
                assertTrue(target_scope_url.isEnabled());
                assertFalse(target_scope_url.getIncludeSubdomains());
                assertEquals("http://www.example.com/path", target_scope_url.getPrefix());
            }
            {
                BurpConfig.TargetScopeURL target_scope_url = BurpConfig.TargetScopeURL.parseTargetURL("https://www.example.com:8443/path/?search=test");
                assertTrue(target_scope_url.isEnabled());
                assertFalse(target_scope_url.getIncludeSubdomains());
                assertEquals("https://www.example.com:8443/path/", target_scope_url.getPrefix());
            }
        } catch (MalformedURLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Test
    public void testTargetScopeAdvance() {
        System.out.println("testTargetScopeAdvance");
        try {
            {
                BurpConfig.TargetScopeAdvance target_scope_url = BurpConfig.TargetScopeAdvance.parseTargetURL("https://www.example.com/path?search=test");
                assertTrue(target_scope_url.isEnabled());
                assertEquals("https", target_scope_url.getProtocol());
                assertEquals("^www\\.example\\.com$", target_scope_url.getHost());
                assertEquals("^443$", target_scope_url.getPort());
                assertEquals("^/path/.*", target_scope_url.getFile());
            }
            {
                BurpConfig.TargetScopeAdvance target_scope_url = BurpConfig.TargetScopeAdvance.parseTargetURL("http://www.example.com/path?search=test");
                assertTrue(target_scope_url.isEnabled());
                assertEquals("http", target_scope_url.getProtocol());
                assertEquals("^www\\.example\\.com$", target_scope_url.getHost());
                assertEquals("^80$", target_scope_url.getPort());
                assertEquals("^/path/.*", target_scope_url.getFile());
            }
            {
                BurpConfig.TargetScopeAdvance target_scope_url = BurpConfig.TargetScopeAdvance.parseTargetURL("https://www.example.com:8443/path/?search=test");
                assertTrue(target_scope_url.isEnabled());
                assertEquals("https", target_scope_url.getProtocol());
                assertEquals("^www\\.example\\.com$", target_scope_url.getHost());
                assertEquals("^8443$", target_scope_url.getPort());
                assertEquals("^/path/.*", target_scope_url.getFile());
            }
        } catch (MalformedURLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Test
    public void testGetTargetScopeAdvanceApi() {
        System.out.println("testGetTargetScopeApi");
        try {
            TargetScope target_scope = BurpConfig.getTargetScope(api);
            assertFalse(target_scope.isAdvancedMode());
            assertEquals(target_scope.getIncludeURL().size(), 2);
            System.out.println("GetTagetScope:" + JsonUtil.prettyJson(target_scope.getJson(), true));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testUpdateTargetScopeAdvance() {
        System.out.println("testUpdateTargetScopeAdvance");
        String configFile = BurpConfigTest.class.getResource("/resources/target_scope_advance.json").getPath();
        try {
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            TargetScope targetScope = new TargetScope();
            targetScope.setAdvancedMode(true);
            List<BurpConfig.TargetScopeAdvance> include_Advance = new ArrayList<>();
            include_Advance.add(new BurpConfig.TargetScopeAdvance(true, "/*", "www.example.ne.jp", "80$", "http"));
            targetScope.setIncludeAdvance(include_Advance);
            List<BurpConfig.TargetScopeAdvance> exclude_Advance = new ArrayList<>();
            targetScope.setExcludeAdvance(exclude_Advance);
            String update_scope = BurpConfig.updateTargetScope(config, targetScope);
            System.out.println("updateTagetScope:" + update_scope);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testUpdateTargetScopeURL() {
        System.out.println("testUpdateTargetScopeURL");
        String configFile = BurpConfigTest.class.getResource("/resources/target_scope_url.json").getPath();
        try {
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            TargetScope targetScope = new TargetScope();
            targetScope.setAdvancedMode(false);
            List<BurpConfig.TargetScopeURL> include_URL = new ArrayList<>();
            include_URL.add(new BurpConfig.TargetScopeURL(true, false, "https://www.example.com/"));
            targetScope.setIncludeURL(include_URL);
            List<BurpConfig.TargetScopeURL> exclude_URL = new ArrayList<>();
            targetScope.setExcludeURL(exclude_URL);
            String update_scope = BurpConfig.updateTargetScope(config, targetScope);
            System.out.println("updateTagetScope:" + update_scope);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConfigTargetScopeURL() {
        TargetScope targetScope = new TargetScope();
        targetScope.getIncludeURL().add(new BurpConfig.TargetScopeURL(true, false, "https://www.example.com/"));
        BurpConfig.configTargetScope(api, targetScope);
    }

    @Test
    public void testGetBambda() {
        System.out.println("testGetBambda");
        String bambda = BurpConfig.getBambda(api);
        assertEquals("return true;", bambda);
        System.out.println("getBambda:" + bambda);
    }

    @Test
    public void testHttpHistoryDisplayFilter() {
        System.out.println("testHttpHistoryDisplayFilter");
        String configFile = BurpConfigTest.class.getResource("/resources/http_history_display_filter.json").getPath();
        try {
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            FilterProperty filter = new FilterProperty();
            filter.setFilterMode(FilterProperty.FilterMode.BAMBDA);
            filter.setBambda("return false;");
            String update_filter = BurpConfig.updateBambda(config, filter, true);
            System.out.println("updateFilter:" + update_filter);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetHotkey() {
        System.out.println("getHotkey");
        List<BurpConfig.Hotkey> hotkeys = BurpConfig.getHotkey(api);
        assertEquals(45, hotkeys.size());
    }

    @Test
    public void testParseHotkey() {
        System.out.println("testParseHotkey");
        {
            KeyStroke copyKey = BurpConfig.Hotkey.parseHotkey("Ctrl+C");
            System.out.println("fromCopyKey:" + copyKey);
        }
        {
            KeyStroke copyKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK);
            System.out.println("toCopyKey:" + BurpConfig.Hotkey.toHotkeyText(copyKey));
        }
    }

    @Test
    public void testUpdateHotkey() {
        try {
            System.out.println("testUpdateHotkey");
            String configFile = BurpConfigTest.class.getResource("/resources/user_hotkey.json").getPath();
            String config = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(configFile)));
            List<BurpConfig.Hotkey> hotkeys = new ArrayList<>();
            hotkeys.add(new BurpConfig.Hotkey(BurpConfig.Hotkey.HotkeyAction.EDITOR_COPY, KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK)));
            hotkeys.add(new BurpConfig.Hotkey(BurpConfig.Hotkey.HotkeyAction.EDITOR_CUT, KeyStroke.getKeyStroke(KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK)));
            hotkeys.add(new BurpConfig.Hotkey(BurpConfig.Hotkey.HotkeyAction.EDITOR_PASTE, KeyStroke.getKeyStroke(KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK)));
            String updateConfig = BurpConfig.updateHotkey(config, hotkeys);
            System.out.println("updateConfig:" + updateConfig);
        } catch (IOException ex) {
            fail();
        }
    }
}
