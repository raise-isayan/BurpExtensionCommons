package extension.view.base;

import java.util.HashSet;

/**
 *
 * @author isayan
 */
public class JSONDocument extends AbstractSyntaxDocument {

    private final static String KEYWORDS[] = {
        "true",
        "false",
        "null"
    };

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
        String operands = ":,[]{}";

        if (Character.isWhitespace(character.charAt(0))
                || operands.contains(character)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean isQuoteDelimiter(String character) {
        String quoteDelimiters = "\\\"";
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
