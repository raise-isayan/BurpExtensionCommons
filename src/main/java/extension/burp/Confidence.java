package extension.burp;

/**
 *
 * @author isayan
 */
public enum Confidence {
    CERTAIN, FIRM, TENTATIVE;

    public static Confidence parseEnum(String s) {
        String value = s.toUpperCase();
        return Enum.valueOf(Confidence.class, value);
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
