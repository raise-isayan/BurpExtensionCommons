package extension.view.base;

import java.util.HashSet;

/**
 *
 * @author isayan
 */
public class JavaSyntaxDocument extends AbstractSyntaxDocument {

    private final static String KEYWORDS[] = {
        "true",
        "false",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "final",
        "finally",
        "float",
        "for",
        "if",
        "goto",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "try",
        "void",
        "volatile",
        "while"};

    @Override
    public HashSet<String> getKeywords() {
        final HashSet<String> keywords = new HashSet<>();
        for (String kw : KEYWORDS) {
            keywords.add(kw);
        }
        return keywords;
    }

    @Override
    protected boolean isDelimiter(String character) {
        String operands = ";:{}()[]+-/%<=>!&|^~*";

        if (Character.isWhitespace(character.charAt(0))
                || operands.contains(character)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean isQuoteDelimiter(String character) {
        String quoteDelimiters = "\\\"'";
        return quoteDelimiters.contains(character);
    }

    @Override
    protected String getStartDelimiter() {
        return "/*";
    }

    @Override
    protected String getEndDelimiter() {
        return "*/";
    }

    @Override
    protected String getSingleLineDelimiter() {
        return "//";
    }

    @Override
    protected String getEscapeString(String quoteDelimiter) {
        return "\\" + quoteDelimiter;
    }

}
