package extension.view.base;

import java.awt.Color;

/**
 *
 * @author isayan
 */
public class NamedColor extends Color implements Comparable<NamedColor> {
    private final Color color;
    private final String name;

    public NamedColor(Color color, String name) {
        super(color.getRGB());
        this.color = color;
        if (name == null) {
            new NullPointerException("name is null");
        }
        this.name = name;
    }

    public Color getTextColor() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        if (r > 240 || g > 240) {
            return Color.black;
        } else {
            return Color.white;
        }
    }

    public String getText() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(NamedColor o) {
        try {
            int parseIntA = Integer.valueOf(name);
            int parseIntB = Integer.valueOf(o.name);
            return parseIntA - parseIntB;
        } catch (NumberFormatException e) {
            return name.compareTo(o.name);
        }
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isDefaultColor() {
        return Color.white.equals(this.color);
    }
    
}
