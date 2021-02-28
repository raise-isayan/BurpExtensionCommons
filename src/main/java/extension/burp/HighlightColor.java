package extension.burp;

import extension.helpers.SwingUtil;
import java.awt.Color;
import java.util.EnumMap;
import java.util.EnumSet;
import javax.swing.ImageIcon;

/**
 *
 * @author isayan
 */
public enum HighlightColor {

    WHITE, RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PINK, MAGENTA, GRAY;

    private final static EnumMap<HighlightColor, Color> namedColor = new EnumMap<HighlightColor, Color>(HighlightColor.class);
    private final static EnumMap<HighlightColor, ImageIcon> namedIcon = new EnumMap<HighlightColor, ImageIcon>(HighlightColor.class);

    static {
        // WHITE == unselect
        namedColor.put(WHITE, Color.WHITE);
        namedColor.put(RED, Color.RED);
        namedColor.put(ORANGE, Color.ORANGE);
        namedColor.put(YELLOW, Color.YELLOW);
        namedColor.put(GREEN, Color.GREEN);
        namedColor.put(CYAN, Color.CYAN);
        namedColor.put(BLUE, Color.BLUE);
        namedColor.put(PINK, Color.PINK);
        namedColor.put(MAGENTA, Color.MAGENTA);
        namedColor.put(GRAY, Color.GRAY);

        namedIcon.put(WHITE, SwingUtil.createSquareIcon(Color.WHITE, 12, 12));
        namedIcon.put(RED, SwingUtil.createSquareIcon(Color.RED, 12, 12));
        namedIcon.put(ORANGE, SwingUtil.createSquareIcon(Color.ORANGE, 12, 12));
        namedIcon.put(YELLOW, SwingUtil.createSquareIcon(Color.YELLOW, 12, 12));
        namedIcon.put(GREEN, SwingUtil.createSquareIcon(Color.GREEN, 12, 12));
        namedIcon.put(CYAN, SwingUtil.createSquareIcon(Color.CYAN, 12, 12));
        namedIcon.put(BLUE, SwingUtil.createSquareIcon(Color.BLUE, 12, 12));
        namedIcon.put(PINK, SwingUtil.createSquareIcon(Color.PINK, 12, 12));
        namedIcon.put(MAGENTA, SwingUtil.createSquareIcon(Color.MAGENTA, 12, 12));
        namedIcon.put(GRAY, SwingUtil.createSquareIcon(Color.GRAY, 12, 12));

    }

    public Color toColor() {
        return namedColor.get(this);
    }

    public ImageIcon toIcon() {
        return namedIcon.get(this);
    }

    @Override
    public String toString() {
        String value = name().toLowerCase();
        return value.replace('_', ' ');
    }

    public static HighlightColor parseEnum(String value) {
        if (value == null) {
            // no select color
            return HighlightColor.WHITE;
        }
        else {
            value = value.toUpperCase();
            value = value.replace(' ', '_');
            return Enum.valueOf(HighlightColor.class, value);
        }
    }

    public static EnumSet<HighlightColor> parseEnumSet(String s) {
        EnumSet<HighlightColor> highlightColor = EnumSet.noneOf(HighlightColor.class);
        if (!s.startsWith("[") && s.endsWith("]")) {
            throw new IllegalArgumentException("No enum constant " + HighlightColor.class.getCanonicalName() + "." + s);
        }
        String content = s.substring(1, s.length() - 1).trim();
        if (content.isEmpty()) {
            return highlightColor;
        }
        for (String t : content.split(",")) {
            String v = t.trim();
            highlightColor.add(parseEnum(v.replaceAll("\"", "")));
        }
        return highlightColor;
    }

}
