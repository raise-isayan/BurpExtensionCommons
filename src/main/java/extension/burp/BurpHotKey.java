package extension.burp;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.swing.KeyStroke;

/**
 *
 * @author isayan
 */
public class BurpHotKey {

    private final static Logger logger = Logger.getLogger(BurpHotKey.class.getName());

    private final static Map<Integer, String> mapKey = new HashMap<>();

    private final static Map<String, Integer> mapRverseKey = new HashMap<>();

    static {
        mapKey.put(KeyEvent.VK_ENTER, "Enter");
        mapKey.put(KeyEvent.VK_BACK_SPACE, "Backspace");
        mapKey.put(KeyEvent.VK_TAB, "Tab");
        mapKey.put(KeyEvent.VK_CANCEL, "Cancel");
        mapKey.put(KeyEvent.VK_CLEAR, "Clear");
        mapKey.put(KeyEvent.VK_COMPOSE, "Compose");
        mapKey.put(KeyEvent.VK_PAUSE, "Pause");
        mapKey.put(KeyEvent.VK_CAPS_LOCK, "Caps Lock");
        mapKey.put(KeyEvent.VK_ESCAPE, "Escape");
        mapKey.put(KeyEvent.VK_SPACE, "Space");
        mapKey.put(KeyEvent.VK_PAGE_UP, "Page Up");
        mapKey.put(KeyEvent.VK_PAGE_DOWN, "Page Down");
        mapKey.put(KeyEvent.VK_END, "End");
        mapKey.put(KeyEvent.VK_HOME, "Home");
        mapKey.put(KeyEvent.VK_LEFT, "Left");
        mapKey.put(KeyEvent.VK_UP, "Up");
        mapKey.put(KeyEvent.VK_RIGHT, "Right");
        mapKey.put(KeyEvent.VK_DOWN, "Down");
        mapKey.put(KeyEvent.VK_BEGIN, "Begin");

        // modifiers
        mapKey.put(KeyEvent.VK_SHIFT, "Shift");
        mapKey.put(KeyEvent.VK_CONTROL, "Control");
        mapKey.put(KeyEvent.VK_ALT, "Alt");
        mapKey.put(KeyEvent.VK_META, "Meta");
        mapKey.put(KeyEvent.VK_ALT_GRAPH, "Alt Graph");

        // punctuation
        mapKey.put(KeyEvent.VK_COMMA, "Comma");
        mapKey.put(KeyEvent.VK_PERIOD, "Period");
        mapKey.put(KeyEvent.VK_SLASH, "Slash");
        mapKey.put(KeyEvent.VK_SEMICOLON, "Semicolon");
        mapKey.put(KeyEvent.VK_EQUALS, "Equals");
        mapKey.put(KeyEvent.VK_OPEN_BRACKET, "Open Bracket");
        mapKey.put(KeyEvent.VK_BACK_SLASH, "Back Slash");
        mapKey.put(KeyEvent.VK_CLOSE_BRACKET, "Close Bracket");

        // numpad numeric keys handled below
        mapKey.put(KeyEvent.VK_MULTIPLY, "NumPad *");
        mapKey.put(KeyEvent.VK_ADD, "NumPad +");
        mapKey.put(KeyEvent.VK_SEPARATOR, "NumPad ,");
        mapKey.put(KeyEvent.VK_SUBTRACT, "NumPad -");
        mapKey.put(KeyEvent.VK_DECIMAL, "NumPad .");
        mapKey.put(KeyEvent.VK_DIVIDE, "NumPad /");
        mapKey.put(KeyEvent.VK_DELETE, "Delete");
        mapKey.put(KeyEvent.VK_NUM_LOCK, "Num Lock");
        mapKey.put(KeyEvent.VK_SCROLL_LOCK, "Scroll Lock");

        mapKey.put(KeyEvent.VK_WINDOWS, "Windows");
        mapKey.put(KeyEvent.VK_CONTEXT_MENU, "Context Menu");

        mapKey.put(KeyEvent.VK_F1, "F1");
        mapKey.put(KeyEvent.VK_F2, "F2");
        mapKey.put(KeyEvent.VK_F3, "F3");
        mapKey.put(KeyEvent.VK_F4, "F4");
        mapKey.put(KeyEvent.VK_F5, "F5");
        mapKey.put(KeyEvent.VK_F6, "F6");
        mapKey.put(KeyEvent.VK_F7, "F7");
        mapKey.put(KeyEvent.VK_F8, "F8");
        mapKey.put(KeyEvent.VK_F9, "F9");
        mapKey.put(KeyEvent.VK_F10, "F10");
        mapKey.put(KeyEvent.VK_F11, "F11");
        mapKey.put(KeyEvent.VK_F12, "F12");
        mapKey.put(KeyEvent.VK_F13, "F13");
        mapKey.put(KeyEvent.VK_F14, "F14");
        mapKey.put(KeyEvent.VK_F15, "F15");
        mapKey.put(KeyEvent.VK_F16, "F16");
        mapKey.put(KeyEvent.VK_F17, "F17");
        mapKey.put(KeyEvent.VK_F18, "F18");
        mapKey.put(KeyEvent.VK_F19, "F19");
        mapKey.put(KeyEvent.VK_F20, "F20");
        mapKey.put(KeyEvent.VK_F21, "F21");
        mapKey.put(KeyEvent.VK_F22, "F22");
        mapKey.put(KeyEvent.VK_F23, "F23");
        mapKey.put(KeyEvent.VK_F24, "F24");

        mapKey.put(KeyEvent.VK_PRINTSCREEN, "Print Screen");
        mapKey.put(KeyEvent.VK_INSERT, "Insert");
        mapKey.put(KeyEvent.VK_HELP, "Help");
        mapKey.put(KeyEvent.VK_BACK_QUOTE, "Back Quote");
        mapKey.put(KeyEvent.VK_QUOTE, "Quote");

        mapKey.put(KeyEvent.VK_KP_UP, "Up");
        mapKey.put(KeyEvent.VK_KP_DOWN, "Down");
        mapKey.put(KeyEvent.VK_KP_LEFT, "Left");
        mapKey.put(KeyEvent.VK_KP_RIGHT, "Right");

        mapKey.put(KeyEvent.VK_DEAD_GRAVE, "Dead Grave");
        mapKey.put(KeyEvent.VK_DEAD_ACUTE, "Dead Acute");
        mapKey.put(KeyEvent.VK_DEAD_CIRCUMFLEX, "Dead Circumflex");
        mapKey.put(KeyEvent.VK_DEAD_TILDE, "Dead Tilde");
        mapKey.put(KeyEvent.VK_DEAD_MACRON, "Dead Macron");
        mapKey.put(KeyEvent.VK_DEAD_BREVE, "Dead Breve");
        mapKey.put(KeyEvent.VK_DEAD_ABOVEDOT, "Dead Above Dot");
        mapKey.put(KeyEvent.VK_DEAD_DIAERESIS, "Dead Diaeresis");
        mapKey.put(KeyEvent.VK_DEAD_ABOVERING, "Dead Above Ring");
        mapKey.put(KeyEvent.VK_DEAD_DOUBLEACUTE, "Dead Double Acute");
        mapKey.put(KeyEvent.VK_DEAD_CARON, "Dead Caron");
        mapKey.put(KeyEvent.VK_DEAD_CEDILLA, "Dead Cedilla");
        mapKey.put(KeyEvent.VK_DEAD_OGONEK, "Dead Ogonek");
        mapKey.put(KeyEvent.VK_DEAD_IOTA, "Dead Iota");
        mapKey.put(KeyEvent.VK_DEAD_VOICED_SOUND, "Dead Voiced Sound");
        mapKey.put(KeyEvent.VK_DEAD_SEMIVOICED_SOUND, "Dead Semivoiced Sound");

        mapKey.put(KeyEvent.VK_AMPERSAND, "Ampersand");
        mapKey.put(KeyEvent.VK_ASTERISK, "Asterisk");
        mapKey.put(KeyEvent.VK_QUOTEDBL, "Double Quote");
        mapKey.put(KeyEvent.VK_LESS, "Less");
        mapKey.put(KeyEvent.VK_GREATER, "Greater");
        mapKey.put(KeyEvent.VK_BRACELEFT, "Left Brace");
        mapKey.put(KeyEvent.VK_BRACERIGHT, "Right Brace");
        mapKey.put(KeyEvent.VK_AT, "At");
        mapKey.put(KeyEvent.VK_COLON, "Colon");
        mapKey.put(KeyEvent.VK_CIRCUMFLEX, "Circumflex");
        mapKey.put(KeyEvent.VK_DOLLAR, "Dollar");
        mapKey.put(KeyEvent.VK_EURO_SIGN, "Euro");
        mapKey.put(KeyEvent.VK_EXCLAMATION_MARK, "Exclamation Mark");
        mapKey.put(KeyEvent.VK_INVERTED_EXCLAMATION_MARK, "Inverted Exclamation Mark");
        mapKey.put(KeyEvent.VK_LEFT_PARENTHESIS, "Left Parenthesis");
        mapKey.put(KeyEvent.VK_NUMBER_SIGN, "Number Sign");
        mapKey.put(KeyEvent.VK_MINUS, "Minus");
        mapKey.put(KeyEvent.VK_PLUS, "Plus");
        mapKey.put(KeyEvent.VK_RIGHT_PARENTHESIS, "Right Parenthesis");
        mapKey.put(KeyEvent.VK_UNDERSCORE, "Underscore");

        mapKey.put(KeyEvent.VK_FINAL, "Final");
        mapKey.put(KeyEvent.VK_CONVERT, "Convert");
        mapKey.put(KeyEvent.VK_NONCONVERT, "No Convert");
        mapKey.put(KeyEvent.VK_ACCEPT, "Accept");
        mapKey.put(KeyEvent.VK_MODECHANGE, "Mode Change");
        mapKey.put(KeyEvent.VK_KANA, "Kana");
        mapKey.put(KeyEvent.VK_KANJI, "Kanji");
        mapKey.put(KeyEvent.VK_ALPHANUMERIC, "Alphanumeric");
        mapKey.put(KeyEvent.VK_KATAKANA, "Katakana");
        mapKey.put(KeyEvent.VK_HIRAGANA, "Hiragana");
        mapKey.put(KeyEvent.VK_FULL_WIDTH, "Full-Width");
        mapKey.put(KeyEvent.VK_HALF_WIDTH, "Half-Width");
        mapKey.put(KeyEvent.VK_ROMAN_CHARACTERS, "Roman Characters");
        mapKey.put(KeyEvent.VK_ALL_CANDIDATES, "All Candidates");
        mapKey.put(KeyEvent.VK_PREVIOUS_CANDIDATE, "Previous Candidate");
        mapKey.put(KeyEvent.VK_CODE_INPUT, "Code Input");
        mapKey.put(KeyEvent.VK_JAPANESE_KATAKANA, "Japanese Katakana");
        mapKey.put(KeyEvent.VK_JAPANESE_HIRAGANA, "Japanese Hiragana");
        mapKey.put(KeyEvent.VK_JAPANESE_ROMAN, "Japanese Roman");
        mapKey.put(KeyEvent.VK_KANA_LOCK, "Kana Lock");
        mapKey.put(KeyEvent.VK_INPUT_METHOD_ON_OFF, "Input Method On/Off");

        mapKey.put(KeyEvent.VK_AGAIN, "Again");
        mapKey.put(KeyEvent.VK_UNDO, "Undo");
        mapKey.put(KeyEvent.VK_COPY, "Copy");
        mapKey.put(KeyEvent.VK_PASTE, "Paste");
        mapKey.put(KeyEvent.VK_CUT, "Cut");
        mapKey.put(KeyEvent.VK_FIND, "Find");
        mapKey.put(KeyEvent.VK_PROPS, "Props");
        mapKey.put(KeyEvent.VK_STOP, "Stop");

        for (int keyCode = KeyEvent.VK_NUMPAD0; keyCode <= KeyEvent.VK_NUMPAD9; keyCode++) {
            String numpad = "NumPad";
            char c = (char) (keyCode - KeyEvent.VK_NUMPAD0 + '0');
            mapKey.put(keyCode, numpad + "-" + c);
        }

        for (Map.Entry<Integer, String> entry : mapKey.entrySet()) {
            mapRverseKey.put(entry.getValue(), entry.getKey());
        }

    }

    public static KeyStroke parseKeyText(String hotkey) {
        Map<String, Integer> modifierKeywords = Collections.synchronizedMap(uninitializedMap);
        int mask = 0;
        int keyCode = 0;
        StringTokenizer st = new StringTokenizer(hotkey, "+");
        int count = st.countTokens();
        for (int i = 0; i < count; i++) {
            String token = st.nextToken();
            Integer tokenMask = modifierKeywords.get(token);
            if (tokenMask != null) {
                mask |= tokenMask;
            }
            if (i == count - 1) {
                if (token.length() == 1) {
                    keyCode = token.charAt(0);
                } else {
                    keyCode = mapRverseKey.get(token);
                }
            }
        }
        return KeyStroke.getKeyStroke(keyCode, mask);
    }

    public static String getKeyText(int keyCode) {
        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9
                || keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
            return String.valueOf((char) keyCode);
        }

        String text = mapKey.get(keyCode);
        if (text != null) {
            return text;
        }

        if (keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) {
            String numpad = "NumPad";
            char c = (char) (keyCode - KeyEvent.VK_NUMPAD0 + '0');
            return numpad + "-" + c;
        }

        if ((keyCode & 0x01000000) != 0) {
            return String.valueOf((char) (keyCode ^ 0x01000000));
        }
        String unknown = "Unknown";
        return unknown + " keyCode: 0x" + Integer.toString(keyCode, 16);
    }

    public static String toKeyText(KeyStroke keyStroke) {
        StringBuilder buf = new StringBuilder();
        buf.append(KeyEvent.getModifiersExText(keyStroke.getModifiers()));
        buf.append("+");
        buf.append(getKeyText(keyStroke.getKeyCode()));
        return buf.toString();
    }

    final static Map<String, Integer> uninitializedMap = new HashMap<>(8, 1.0f);

    static {
        uninitializedMap.put("Shift", Integer.valueOf(InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK));
        uninitializedMap.put("Ctrl", Integer.valueOf(InputEvent.CTRL_DOWN_MASK | InputEvent.CTRL_MASK));
        uninitializedMap.put("Meta", Integer.valueOf(InputEvent.META_DOWN_MASK | InputEvent.META_MASK));
        uninitializedMap.put("Alt", Integer.valueOf(InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK));
        uninitializedMap.put("Alt Graph", Integer.valueOf(InputEvent.ALT_GRAPH_DOWN_MASK | InputEvent.ALT_GRAPH_MASK));
        uninitializedMap.put("Button1", Integer.valueOf(InputEvent.BUTTON1_DOWN_MASK));
        uninitializedMap.put("Button2", Integer.valueOf(InputEvent.BUTTON2_DOWN_MASK));
        uninitializedMap.put("Button3", Integer.valueOf(InputEvent.BUTTON3_DOWN_MASK));
    }

}
