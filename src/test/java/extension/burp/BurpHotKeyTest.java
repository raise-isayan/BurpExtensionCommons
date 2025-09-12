package extension.burp;

import java.util.logging.Logger;
import burp.BurpPreferences;
import burp.MockMontoya;
import burp.api.montoya.MontoyaApi;
import extension.burp.BurpConfig.CharacterSetMode;
import extension.burp.BurpConfig.TargetScope;
import extension.burp.FilterProperty.FilterCategory;
import extension.helpers.FileUtil;
import extension.helpers.StringUtil;
import extension.helpers.json.JsonUtil;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
public class BurpHotKeyTest {

    private final static Logger logger = Logger.getLogger(BurpHotKeyTest.class.getName());

    public BurpHotKeyTest() {
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
    public void testParseHotkey() {
        System.out.println("testParseHotkey");
        {
            KeyStroke copyKey = BurpHotKey.parseKeyText("Ctrl+C");
            System.out.println("fromCopyKey:" + copyKey);
        }
        {
            KeyStroke spaceKey = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, java.awt.event.InputEvent.CTRL_DOWN_MASK);
            String text = BurpHotKey.toKeyText(spaceKey);
            System.out.println("toSpaceKey:" + text);
            KeyStroke parseKey = BurpHotKey.parseKeyText(text);
            System.out.println("toSpaceKey.toString:" + parseKey.toString() + ":" + parseKey.getKeyCode());
        }
    }

}
