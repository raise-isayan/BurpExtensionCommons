package extension.burp;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import javax.swing.KeyStroke;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    public final String [] HOTKEY_TEXT = {
        "Ctrl+R",
        "Ctrl+I",
        "Ctrl+F",
        "Ctrl+T",
        "Ctrl+Space",
        "Ctrl+Shift+D",
        "Ctrl+Shift+T",
        "Ctrl+Shift+P",
        "Ctrl+Shift+I",
        "Ctrl+Shift+R",
        "Ctrl+Shift+L",
        "Ctrl+Minus",
        "Ctrl+Equals",
        "Ctrl+X",
        "Ctrl+C",
        "Ctrl+V",
        "Ctrl+Z",
        "Ctrl+Y",
        "Ctrl+A",
        "Ctrl+S",
        "Ctrl+Comma",
        "Ctrl+Period",
        "Ctrl+Shift+U",
        "Ctrl+U",
        "Ctrl+Shift+H",
        "Ctrl+H",
        "Ctrl+Shift+B",
        "Ctrl+B",
        "Ctrl+Shift+J",
        "Ctrl+Shift+O",
        "Ctrl+Shift+M",
        "Ctrl+Backspace",
        "Ctrl+Delete",
        "Ctrl+D",
        "Ctrl+Left",
        "Ctrl+Shift+Left",
        "Ctrl+Right",
        "Ctrl+Shift+Right",
        "Ctrl+Up",
        "Ctrl+Shift+Up",
        "Ctrl+Down",
        "Ctrl+Shift+Down",
        "Ctrl+Home",
        "Ctrl+Shift+Home",
        "Ctrl+End",
        "Ctrl+Shift+End"
    };

    @Test
    public void testParseEmptyHotkey() {
        System.out.println("testParseEmptyHotkey");
        KeyStroke emptyKey = BurpHotKey.parseKeyText("");
        System.out.println("fromEmptyKeyCode:" + emptyKey.getKeyChar());
        assertEquals(emptyKey.getKeyChar(), KeyEvent.CHAR_UNDEFINED);
        assertEquals(emptyKey.getModifiers(), 0);
    }

    @Test
    public void testParseHotkey() {
        System.out.println("testParseHotkey");
        {
            KeyStroke fromKey = BurpHotKey.parseKeyText("Ctrl+C");
            System.out.println("fromCopyKey:" + fromKey);
        }
        {
            KeyStroke spaceKey = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, java.awt.event.InputEvent.CTRL_DOWN_MASK);
            String text = BurpHotKey.toKeyText(spaceKey);
            System.out.println("toSpaceKey:" + text);
            KeyStroke parseKey = BurpHotKey.parseKeyText(text);
            System.out.println("toSpaceKey.toString:" + parseKey.toString() + ":" + parseKey.getKeyCode());
        }
        {
            for (String hk : HOTKEY_TEXT) {
                KeyStroke fromKey = BurpHotKey.parseKeyText(hk);
                System.out.println("fromKey:" + fromKey);
                String toKey = BurpHotKey.toKeyText(fromKey);
                System.out.println("toKey:" + toKey);
                assertEquals(toKey, hk);
            }
        }
    }
}
