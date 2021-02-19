package extension.burp;

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
