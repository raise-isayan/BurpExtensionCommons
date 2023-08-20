package extension.helpers;

import extension.view.base.RegexItem;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isayan
 */
public class MatchUtil {

    private final static Logger logger = Logger.getLogger(MatchUtil.class.getName());

    public static String toSmartMatch(String value) {
        try {
            return toSmartMatch(value, null);
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public static String toSmartMatch(String value, String charset) throws UnsupportedEncodingException {
        StringBuilder buff = new StringBuilder();
        boolean escape = false;
        int length = value.length();
        for (int i = 0; i < length; i = value.offsetByCodePoints(i, 1)) {
            char ch = value.charAt(i);
            int code = value.codePointAt(i);
            buff.append('(');
            switch (ch) {
                case '<':
                case '>':
                case '&':
                case '"':
                    buff.append(toRegexEscape(ch));
                    buff.append('|');
                    buff.append(HttpUtil.toHtmlEncode(ch));
                    break;
                case '\\': // escape
                    if (i == length - 1) {
                        buff.append(toRegexEscape(ch));
                    } else {
                        escape = true;
                    }
                    break;
                case '.':
                case '+':
                case '[':
                case '^':
                case ']':
                case '$':
                case '(':
                case ')':
                case '{':
                case '}':
                case '=':
                case '!':
//                case '<':
//                case '>':
                case '|':
                case ':':
                case '-':
                    if (escape) {
                        buff.append(toRegexEscape('\\'));
                    }
                    buff.append(toRegexEscape(ch));
                    escape = false;
                    break;
                case '*': // wild card
                    if (escape) {
                        buff.append(toRegexEscape(ch));
                    } else {
                        buff.append("(?:.*?)");
                    }
                    escape = false;
                    break;
                case '?': // wild card
                    if (escape) {
                        buff.append(toRegexEscape(ch));
                    } else {
                        buff.append('.');
                    }
                    escape = false;
                    break;
                default:
                    if (escape) {
                        buff.append(toRegexEscape('\\'));
                    }
                    buff.appendCodePoint(code);
                    escape = false;
                    break;
            }
            buff.append('|');
            buff.append(String.format("([\\\\%%]u)%04x", code)); // unicode hex
            buff.append('|');
            buff.append(String.format("&#(x%04x|0{0,3}%d);", code, code)); // unicode hex,decimal
            if (charset != null) {
                buff.append('|');
                String str = value.substring(i, value.offsetByCodePoints(i, 1));
                byte decode[] = StringUtil.getBytesCharset(str, charset);
                for (int k = 0; k < decode.length; k++) {
                    buff.append(String.format("((\\\\x|%%)0{0,3}%x)", 0xff & decode[k])); // byte hex
                }
            } else {
                if (ch <= 0xff) {
                    buff.append('|');
                    buff.append(String.format("((\\\\x0{0,3}%x|%%%02x))", 0xff & ch, 0xff & ch)); // byte hex
                }
            }
            buff.append(')');
        }
        return buff.toString();
    }

    public static Pattern compileRegex(String text, boolean smartMatch, boolean regexp, boolean ignoreCase, int flags) {
        if (ignoreCase) {
            flags |= Pattern.CASE_INSENSITIVE;
        }
        Pattern p = RegexItem.compileRegex(text, flags, !regexp);
        if (smartMatch) {
            String smartRegex = toSmartMatch(text);
            p = RegexItem.compileRegex(smartRegex, flags, false);
        }
        return p;
    }

    public static Pattern compileRegex(String text, boolean smartMatch, boolean regexp, boolean ignoreCase) {
        return compileRegex(text, smartMatch, regexp, ignoreCase, 0);
    }

    /*  . \ + * ? [ ^ ] $ ( ) { } = ! < > | : - */
    public static String toRegexEscape(char ch) {
        StringBuilder buff = new StringBuilder();
        switch (ch) {
            case '\\':
            case '.':
            case '+':
            case '*':
            case '?':
            case '[':
            case '^':
            case ']':
            case '$':
            case '(':
            case ')':
            case '{':
            case '}':
            case '=':
            case '!':
            case '<':
            case '>':
            case '|':
            case ':':
            case '-':
                buff.append('\\');
                buff.append(ch);
                break;
            default:
                buff.append(ch);
                break;
        }
        return buff.toString();
    }

    public static boolean isUrlencoded(String value) {
        boolean result = true;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '%') {
                if (i + 2 < value.length()) {
                    char cl = value.charAt(i + 1);
                    char ch = value.charAt(i + 2);
                    if (!('0' <= cl && cl <= '9' || 'a' <= cl && cl <= 'z' || 'A' <= cl && cl <= 'Z' && '0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z')) {
                        result = false;
                        break;
                    }
                } else {
                    result = false;
                    break;
                }
            } else if (!('0' <= c && c <= '9' || 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || c == '*' || c == '_' || c == '+' || c == '.' || c == '-')) {
                result = false;
                break;
            }
        }
        return result;
    }

    private final static Pattern PTN_B64 = Pattern.compile("([0-9a-zA-Z+/\r\n])+={0,2}");
    private final static Pattern PTN_B64_URLSAFE = Pattern.compile("([0-9a-zA-Z_\\-])");

    public static boolean isBase64(String value) {
        // base64
        Matcher m64 = PTN_B64.matcher(value);
        return m64.matches();
    }

    public static boolean isBase64URLSafe(String value) {
        // base64 UrlSafe
        Matcher m64_URLSafe = PTN_B64_URLSAFE.matcher(value);
        return m64_URLSafe.matches();
    }

    private final static Pattern PTN_URL = Pattern.compile("%([0-9a-fA-F]{2})");

    public static boolean containsUrlencoded(String value) {
        Matcher m = PTN_URL.matcher(value);
        return m.find();
    }

    /**
     * email
     * https://html.spec.whatwg.org/multipage/input.html#e-mail-state-(type%3Demail)
     */
    public static final Pattern MAIL_ADDRESS = Pattern.compile("[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*", Pattern.CASE_INSENSITIVE);

    public static boolean containsMailAddress(String word) {
        return MAIL_ADDRESS.matcher(word).find();
    }

    /*
    * https://www.regular-expressions.info/creditcard.html
    ^(?:4[0-9]{12}(?:[0-9]{3})?         # Visa
    |   (?:5[1-5][0-9]{2}                # MasterCard
    |   222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}
    |   3[47][0-9]{13}                   # American Express
    |   3(?:0[0-5]|[68][0-9])[0-9]{11}   # Diners Club
    |   6(?:011|5[0-9]{2})[0-9]{12}      # Discover
    |   (?:2131|1800|35\d{3})\d{11}      # JCB
   )$
     */
    public static final Pattern CREDIT_CARD = Pattern.compile("(4[0-9]{12}(?:[0-9]{3})?|(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})");

    private static final String[] TEST_CREDIT_CARD = new String[]{
        // Visa
        "4111111111111111",
        "4242424242424242",
        "4012888888881881",
        "4222222222222",
        // Master Card
        "5555555555554444",
        "5105105105105100",
        "5431111111111111",
        "5111111111111118",
        // JCB
        "3530111333300000",
        "3566002020360505",
        // American Express
        "378282246310005",
        "371449635398431",
        "341111111111111",
        "378734493671000",
        // Diners Club
        "30569309025904",
        "38520000023237",
        // Discover Card
        "6011111111111117",
        "6011000990139424",
        "6011601160116611"
    };

    public static boolean isTestCreditCard(String value) {
        for (String test : TEST_CREDIT_CARD) {
            if (test.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String findCreditCard(String value) {
        Matcher m = CREDIT_CARD.matcher(value);
        if (m.find()) {
            String pan = m.group(1);
            return pan;
        }
        return null;
    }

    public static boolean containsCreditCard(String value) {
        return CREDIT_CARD.matcher(value).find();
    }

    public static boolean containsCreditCard(String value, boolean checkDigit) {
        String pan = findCreditCard(value);
        if (pan != null) {
            if (checkDigit) {
                return isLuhnChecksum(pan);
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean isLuhnChecksum(String pan) {
        char[] charArray = pan.toCharArray();
        int[] number = new int[charArray.length];
        int sum = 0;

        for (int i = 0; i < charArray.length; i++) {
            number[i] = Character.getNumericValue(charArray[i]);
        }

        for (int i = number.length - 2; i > -1; i -= 2) {
            number[i] *= 2;
            if (number[i] > 9) {
                number[i] -= 9;
            }
        }

        for (int i = 0; i < number.length; i++) {
            sum += number[i];
        }

        return (sum % 10 == 0);
    }

}
