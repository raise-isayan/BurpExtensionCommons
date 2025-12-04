package extension.burp;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import javax.swing.KeyStroke;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
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
