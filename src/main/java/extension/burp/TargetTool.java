package extension.burp;

import java.util.EnumSet;

/**
 *
 * @author isayan
 */
public enum TargetTool {

    PROXY, SPIDER, SCANNER, INTRUDER, REPEATER, SEQUENCER, EXTENDER;
    
    public static TargetTool parseEnum(String s) {
        String value = s.toUpperCase().replace(' ', '_');
        return Enum.valueOf(TargetTool.class, value);
    }

    public static EnumSet<TargetTool> parseEnumSet(String s) {
        EnumSet<TargetTool> targetTool = EnumSet.noneOf(TargetTool.class);
        if (!s.startsWith("[") && s.endsWith("]")) {
            throw new IllegalArgumentException("No enum constant " + TargetTool.class.getCanonicalName() + "." + s);
        }
        String content = s.substring(1, s.length() - 1).trim();
        if (content.isEmpty()) {
            return targetTool;
        }
        for (String v : content.split(",")) {
            targetTool.add(parseEnum(v.trim()));
        }
        return targetTool;
    }
    
    @Override
    public String toString() {
        String value = name().toLowerCase();
        return value.replace('_', ' ');
    }
}
