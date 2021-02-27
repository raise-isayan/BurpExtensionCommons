package extension.burp;

import java.util.EnumSet;

/**
 *
 * @author isayan
 */
public enum Severity {
    HIGH, MEDIUM, LOW, INFORMATION, FALSE_POSITIVE;

    public static Severity parseEnum(String s) {
        String value = s.toUpperCase().replace(' ', '_');
        return Enum.valueOf(Severity.class, value);
    }

    public static EnumSet<Severity> parseEnumSet(String s) {
        EnumSet<Severity> severity = EnumSet.noneOf(Severity.class);
        if (!s.startsWith("[") && s.endsWith("]")) {
            throw new IllegalArgumentException("No enum constant " + Severity.class.getCanonicalName() + "." + s);
        }
        String content = s.substring(1, s.length() - 1).trim();
        if (content.isEmpty()) {
            return severity;
        }
        for (String t : content.split(",")) {
            String v = t.trim();
            severity.add(parseEnum(v.replaceAll("\"", "")));
        }
        return severity;
    }

    @Override
    public String toString() {
        char ch[] = name().toLowerCase().toCharArray();
        if (ch.length > 0) {
            ch[0] = Character.toUpperCase(ch[0]);
        }
        String value = new String(ch);
        return value.replace('_', ' ');
    }

}
