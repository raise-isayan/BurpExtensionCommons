package extension.burp;

import extension.helpers.StringUtil;
import java.util.EnumSet;

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

    public static EnumSet<Confidence> parseEnumSet(String s) {
        EnumSet<Confidence> confidence = EnumSet.noneOf(Confidence.class);
        if (!s.startsWith("[") && s.endsWith("]")) {
            throw new IllegalArgumentException("No enum constant " + Confidence.class.getCanonicalName() + "." + s);
        }
        String content = s.substring(1, s.length() - 1).trim();
        if (content.isEmpty()) {
            return confidence;
        }
        for (String t : content.split(",")) {
            String v = t.trim();
            confidence.add(parseEnum(v.replaceAll("\"", "")));
        }
        return confidence;
    }
        
    @Override
    public String toString() {
        String value = StringUtil.toPascalCase(name());
        return value.replace('_', ' ');
    }

}
