package extension.helpers;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author t.isayama
 */
public class SmartCodec {

    // 条件一致時にEncode
    public final static Pattern ENCODE_PATTERN_ALL = Pattern.compile(".", Pattern.DOTALL);
    public final static Pattern ENCODE_PATTERN_ALPHANUM = Pattern.compile("[^a-zA-Z0-9]");
    public final static Pattern ENCODE_PATTERN_STANDARD = Pattern.compile("[^A-Za-z0-9!\"$&'()*+,:=@|~]");
    public final static Pattern ENCODE_PATTERN_LIGHT = Pattern.compile("[^A-Za-z0-9!\"'()*,/:<>@\\[\\\\\\]^`{|}~]");
    public final static Pattern ENCODE_PATTERN_BURP = Pattern.compile("[^A-Za-z0-9!\"$'()*,/:<>@\\[\\\\\\]^`{|},.~-]");
    public final static Pattern ENCODE_PATTERN_ASCII = Pattern.compile("[^\\x00-\\xff]");
    public final static Pattern ENCODE_PATTERN_JS = Pattern.compile("[^ !#$&=~/,@+*|0-9A-Za-z\\[\\]\\(\\)\\{\\}?-]");

    private final static HashMap<String, Character> ENTITY = new HashMap<>();

    static {
        // see https://www.w3.org/TR/REC-html40/sgml/entities.html
        ENTITY.put("nbsp", (char) 160); // no-break space = non-breaking space, U+00A0 ISOnum
        ENTITY.put("iexcl", (char) 161); // inverted exclamation mark, U+00A1 ISOnum
        ENTITY.put("cent", (char) 162); // cent sign, U+00A2 ISOnum
        ENTITY.put("pound", (char) 163); // pound sign, U+00A3 ISOnum
        ENTITY.put("curren", (char) 164); // currency sign, U+00A4 ISOnum
        ENTITY.put("yen", (char) 165); // yen sign = yuan sign, U+00A5 ISOnum
        ENTITY.put("brvbar", (char) 166); // broken bar = broken vertical bar, U+00A6 ISOnum
        ENTITY.put("sect", (char) 167); // section sign, U+00A7 ISOnum
        ENTITY.put("uml", (char) 168); // diaeresis = spacing diaeresis, U+00A8 ISOdia
        ENTITY.put("copy", (char) 169); // copyright sign, U+00A9 ISOnum
        ENTITY.put("ordf", (char) 170); // feminine ordinal indicator, U+00AA ISOnum
        ENTITY.put("laquo", (char) 171); // left-pointing double angle quotation mark = left pointing guillemet, U+00AB ISOnum
        ENTITY.put("not", (char) 172); // not sign, U+00AC ISOnum
        ENTITY.put("shy", (char) 173); // soft hyphen = discretionary hyphen, U+00AD ISOnum
        ENTITY.put("reg", (char) 174); // registered sign = registered trade mark sign, U+00AE ISOnum
        ENTITY.put("macr", (char) 175); // macron = spacing macron = overline = APL overbar, U+00AF ISOdia
        ENTITY.put("deg", (char) 176); // degree sign, U+00B0 ISOnum
        ENTITY.put("plusmn", (char) 177); // plus-minus sign = plus-or-minus sign, U+00B1 ISOnum
        ENTITY.put("sup2", (char) 178); // superscript two = superscript digit two = squared, U+00B2 ISOnum
        ENTITY.put("sup3", (char) 179); // superscript three = superscript digit three = cubed, U+00B3 ISOnum
        ENTITY.put("acute", (char) 180); // acute accent = spacing acute, U+00B4 ISOdia
        ENTITY.put("micro", (char) 181); // micro sign, U+00B5 ISOnum
        ENTITY.put("para", (char) 182); // pilcrow sign = paragraph sign, U+00B6 ISOnum
        ENTITY.put("middot", (char) 183); // middle dot = Georgian comma = Greek middle dot, U+00B7 ISOnum
        ENTITY.put("cedil", (char) 184); // cedilla = spacing cedilla, U+00B8 ISOdia
        ENTITY.put("sup1", (char) 185); // superscript one = superscript digit one, U+00B9 ISOnum
        ENTITY.put("ordm", (char) 186); // masculine ordinal indicator, U+00BA ISOnum
        ENTITY.put("raquo", (char) 187); // right-pointing double angle quotation mark = right pointing guillemet, U+00BB ISOnum
        ENTITY.put("frac14", (char) 188); // vulgar fraction one quarter = fraction one quarter, U+00BC ISOnum
        ENTITY.put("frac12", (char) 189); // vulgar fraction one half = fraction one half, U+00BD ISOnum
        ENTITY.put("frac34", (char) 190); // vulgar fraction three quarters = fraction three quarters, U+00BE ISOnum
        ENTITY.put("iquest", (char) 191); // inverted question mark = turned question mark, U+00BF ISOnum
        ENTITY.put("Agrave", (char) 192); // latin capital letter A with grave = latin capital letter A grave, U+00C0 ISOlat1
        ENTITY.put("Aacute", (char) 193); // latin capital letter A with acute, U+00C1 ISOlat1
        ENTITY.put("Acirc", (char) 194); // latin capital letter A with circumflex, U+00C2 ISOlat1
        ENTITY.put("Atilde", (char) 195); // latin capital letter A with tilde, U+00C3 ISOlat1
        ENTITY.put("Auml", (char) 196); // latin capital letter A with diaeresis, U+00C4 ISOlat1
        ENTITY.put("Aring", (char) 197); // latin capital letter A with ring above = latin capital letter A ring, U+00C5 ISOlat1
        ENTITY.put("AElig", (char) 198); // latin capital letter AE = latin capital ligature AE, U+00C6 ISOlat1
        ENTITY.put("Ccedil", (char) 199); // latin capital letter C with cedilla, U+00C7 ISOlat1
        ENTITY.put("Egrave", (char) 200); // latin capital letter E with grave, U+00C8 ISOlat1
        ENTITY.put("Eacute", (char) 201); // latin capital letter E with acute, U+00C9 ISOlat1
        ENTITY.put("Ecirc", (char) 202); // latin capital letter E with circumflex, U+00CA ISOlat1
        ENTITY.put("Euml", (char) 203); // latin capital letter E with diaeresis, U+00CB ISOlat1
        ENTITY.put("Igrave", (char) 204); // latin capital letter I with grave, U+00CC ISOlat1
        ENTITY.put("Iacute", (char) 205); // latin capital letter I with acute, U+00CD ISOlat1
        ENTITY.put("Icirc", (char) 206); // latin capital letter I with circumflex, U+00CE ISOlat1
        ENTITY.put("Iuml", (char) 207); // latin capital letter I with diaeresis, U+00CF ISOlat1
        ENTITY.put("ETH", (char) 208); // latin capital letter ETH, U+00D0 ISOlat1
        ENTITY.put("Ntilde", (char) 209); // latin capital letter N with tilde, U+00D1 ISOlat1
        ENTITY.put("Ograve", (char) 210); // latin capital letter O with grave, U+00D2 ISOlat1
        ENTITY.put("Oacute", (char) 211); // latin capital letter O with acute, U+00D3 ISOlat1
        ENTITY.put("Ocirc", (char) 212); // latin capital letter O with circumflex, U+00D4 ISOlat1
        ENTITY.put("Otilde", (char) 213); // latin capital letter O with tilde, U+00D5 ISOlat1
        ENTITY.put("Ouml", (char) 214); // latin capital letter O with diaeresis, U+00D6 ISOlat1
        ENTITY.put("times", (char) 215); // multiplication sign, U+00D7 ISOnum
        ENTITY.put("Oslash", (char) 216); // latin capital letter O with stroke = latin capital letter O slash, U+00D8 ISOlat1
        ENTITY.put("Ugrave", (char) 217); // latin capital letter U with grave, U+00D9 ISOlat1
        ENTITY.put("Uacute", (char) 218); // latin capital letter U with acute, U+00DA ISOlat1
        ENTITY.put("Ucirc", (char) 219); // latin capital letter U with circumflex, U+00DB ISOlat1
        ENTITY.put("Uuml2", (char) 220); // latin capital letter U with diaeresis, U+00DC ISOlat1
        ENTITY.put("Yacute", (char) 221); // latin capital letter Y with acute, U+00DD ISOlat1
        ENTITY.put("THORN", (char) 222); // latin capital letter THORN, U+00DE ISOlat1
        ENTITY.put("szlig", (char) 223); // latin small letter sharp s = ess-zed, U+00DF ISOlat1
        ENTITY.put("agrave", (char) 224); // latin small letter a with grave = latin small letter a grave, U+00E0 ISOlat1
        ENTITY.put("aacute", (char) 225); // latin small letter a with acute, U+00E1 ISOlat1
        ENTITY.put("acirc", (char) 226); // latin small letter a with circumflex, U+00E2 ISOlat1
        ENTITY.put("atilde", (char) 227); // latin small letter a with tilde, U+00E3 ISOlat1
        ENTITY.put("auml", (char) 228); // latin small letter a with diaeresis, U+00E4 ISOlat1
        ENTITY.put("aring", (char) 229); // latin small letter a with ring above = latin small letter a ring, U+00E5 ISOlat1
        ENTITY.put("aelig", (char) 230); // latin small letter ae = latin small ligature ae, U+00E6 ISOlat1
        ENTITY.put("ccedil", (char) 231); // latin small letter c with cedilla, U+00E7 ISOlat1
        ENTITY.put("egrave", (char) 232); // latin small letter e with grave, U+00E8 ISOlat1
        ENTITY.put("eacute", (char) 233); // latin small letter e with acute, U+00E9 ISOlat1
        ENTITY.put("ecirc", (char) 234); // latin small letter e with circumflex, U+00EA ISOlat1
        ENTITY.put("euml", (char) 235); // latin small letter e with diaeresis, U+00EB ISOlat1
        ENTITY.put("igrave", (char) 236); // latin small letter i with grave, U+00EC ISOlat1
        ENTITY.put("iacute", (char) 237); // latin small letter i with acute, U+00ED ISOlat1
        ENTITY.put("icirc", (char) 238); // latin small letter i with circumflex, U+00EE ISOlat1
        ENTITY.put("iuml", (char) 239); // latin small letter i with diaeresis, U+00EF ISOlat1
        ENTITY.put("eth", (char) 240); // latin small letter eth, U+00F0 ISOlat1
        ENTITY.put("ntilde", (char) 241); // latin small letter n with tilde, U+00F1 ISOlat1
        ENTITY.put("ograve", (char) 242); // latin small letter o with grave, U+00F2 ISOlat1
        ENTITY.put("oacute", (char) 243); // latin small letter o with acute, U+00F3 ISOlat1
        ENTITY.put("ocirc", (char) 244); // latin small letter o with circumflex, U+00F4 ISOlat1
        ENTITY.put("otilde", (char) 245); // latin small letter o with tilde, U+00F5 ISOlat1
        ENTITY.put("ouml", (char) 246); // latin small letter o with diaeresis, U+00F6 ISOlat1
        ENTITY.put("divide", (char) 247); // division sign, U+00F7 ISOnum
        ENTITY.put("oslash", (char) 248); // latin small letter o with stroke, = latin small letter o slash, U+00F8 ISOlat1
        ENTITY.put("ugrave", (char) 249); // latin small letter u with grave, U+00F9 ISOlat1
        ENTITY.put("uacute", (char) 250); // latin small letter u with acute, U+00FA ISOlat1
        ENTITY.put("ucirc", (char) 251); // latin small letter u with circumflex, U+00FB ISOlat1
        ENTITY.put("uuml", (char) 252); // latin small letter u with diaeresis, U+00FC ISOlat1
        ENTITY.put("yacute", (char) 253); // latin small letter y with acute, U+00FD ISOlat1
        ENTITY.put("thorn", (char) 254); // latin small letter thorn, U+00FE ISOlat1
        ENTITY.put("yuml", (char) 255); // latin small letter y with diaeresis, U+00FF ISOlat1s
    }
    /**
     * Encode
     */

    /**
     * @param entityName
     * @return
     */
    protected static String fromHTMLEntity(String entityName) {
        Character ch = ENTITY.get(entityName);
        if (ch == null) {
            return null;
        }
        return Character.toString(ch);
    }

    public static String toHtmlDecEncode(String input) {
        return toHtmlDecEncode(input, ENCODE_PATTERN_ALPHANUM);
    }

    public static String toHtmlDecEncode(String input, Pattern pattern) {
        StringBuilder buff = new StringBuilder();
        int length = input.length();
        for (int i = 0; i < length; i = input.offsetByCodePoints(i, 1)) {
            int codePoint = input.codePointAt(i);
            Matcher m = pattern.matcher(Character.toString(codePoint));
            if (m.matches()) {
                buff.append(String.format("&#%d;", codePoint));
            } else {
                buff.appendCodePoint(codePoint);
            }
        }
        return buff.toString();
    }

    public static String toHtmlHexEncode(String input, boolean upperCase) {
        return toHtmlHexEncode(input, ENCODE_PATTERN_ALPHANUM, upperCase);
    }

    public static String toHtmlHexEncode(String input, Pattern pattern, boolean upperCase) {
        StringBuilder buff = new StringBuilder();
        int length = input.length();
        for (int i = 0; i < length; i = input.offsetByCodePoints(i, 1)) {
            int codePoint = input.codePointAt(i);
            Matcher m = pattern.matcher(Character.toString(codePoint));
            if (m.matches()) {
                if (upperCase) {
                    buff.append(String.format("&#X%X;", codePoint));
                } else {
                    buff.append(String.format("&#x%x;", codePoint));
                }
            } else {
                buff.appendCodePoint(codePoint);
            }
        }
        return buff.toString();
    }

    public static String toHtmlByteHexEncode(String input, String charset, Pattern pattern, boolean upperCase) throws UnsupportedEncodingException {
        return toHtmlByteHexEncode(StringUtil.getBytesCharset(input, charset), pattern, upperCase);
    }

    public static String toHtmlByteHexEncode(byte[] bytes, Pattern pattern, boolean upperCase) {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i] & 0xff;
            Matcher m = pattern.matcher(Character.toString((char) b));
            if (m.matches()) {
                if (upperCase) {
                    buff.append(String.format("&#X%X;", b));
                } else {
                    buff.append(String.format("&#x%x;", b));
                }
            } else {
                buff.append(b);
            }
        }
        return buff.toString();
    }

    public static String toUrlEncode(String pString, Charset charset, boolean upperCase) {
        return StringUtil.getStringCharset(toUrlEncode(StringUtil.getBytesCharset(pString, charset), ENCODE_PATTERN_ALPHANUM, upperCase), StandardCharsets.US_ASCII);
    }

    public static String toUrlEncode(String pString, String charset, boolean upperCase) throws UnsupportedEncodingException {
        return StringUtil.getStringCharset(toUrlEncode(StringUtil.getBytesCharset(pString, charset), ENCODE_PATTERN_ALPHANUM, upperCase), StandardCharsets.US_ASCII);
    }

    public static String toUrlEncode(String pString, Charset charset, Pattern pattern, boolean upperCase) {
        return StringUtil.getStringCharset(toUrlEncode(StringUtil.getBytesCharset(pString, charset), pattern, upperCase), StandardCharsets.US_ASCII);
    }

    public static String toUrlEncode(String pString, String charset, Pattern pattern, boolean upperCase) throws UnsupportedEncodingException {
        return StringUtil.getStringCharset(toUrlEncode(StringUtil.getBytesCharset(pString, charset), pattern, upperCase), StandardCharsets.US_ASCII);
    }

    private static byte[] toUrlEncode(byte[] bytes, Pattern pattern, boolean upperCase) {
        if (bytes == null) {
            throw new NullPointerException();
        }
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length * 3);

        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i] & 0xff;
            Matcher m = pattern.matcher(String.valueOf(new char[]{(char) b}));
            if (b == ' ') {
                b = '+';
                buffer.put((byte) b);
            } else if (m.matches()) {
                buffer.put((byte) '%');
                char hex1 = Character.toLowerCase(Character.forDigit((b >> 4) & 0xf, 16));
                char hex2 = Character.toLowerCase(Character.forDigit(b & 0xf, 16));
                if (upperCase) {
                    hex1 = Character.toUpperCase(hex1);
                    hex2 = Character.toUpperCase(hex2);
                }
                buffer.put((byte) hex1);
                buffer.put((byte) hex2);
            } else {
                buffer.put((byte) b);
            }
        }
        buffer.flip();
        byte[] value = new byte[buffer.limit()];
        buffer.get(value);
        return value;
    }

    public static String toUnocodeUrlEncode(String input, boolean upperCase) {
        return toUnocodeUrlEncode(input, ENCODE_PATTERN_ALPHANUM, upperCase);
    }

    public static String toUnocodeUrlEncode(String input, Pattern pattern, boolean upperCase) {
        StringBuilder buff = new StringBuilder();
        int length = input.length();
//        for (int i = 0; i < length; i = input.offsetByCodePoints(i, 1)) {
//            int c = input.codePointAt(i);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            Matcher m = pattern.matcher(Character.toString(c));
            if (m.matches()) {
                if (upperCase) {
                    buff.append(String.format("%%U%04X", (int) c));
                } else {
                    buff.append(String.format("%%u%04x", (int) c));
                }
            } else {
                buff.appendCodePoint((int) c);
            }
        }
        return buff.toString();
    }

    public static String toUnocodeEncode(String input, String prefix, Pattern pattern, boolean upperCase) {
        StringBuilder buff = new StringBuilder();
        int length = input.length();
//        for (int i = 0; i < length; i = input.offsetByCodePoints(i, 1)) {
//            int c = input.codePointAt(i);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            Matcher m = pattern.matcher(Character​.toString(c));
            if (m.matches()) {
                if (upperCase) {
                    buff.append(String.format("%s%04X", prefix, (int) c));
                } else {
                    buff.append(String.format("%s%04x", prefix, (int) c));
                }
            } else {
                buff.appendCodePoint(c);
            }
        }
        return buff.toString();
    }

    public static String toUnocodeEncode(String input, boolean upperCase) {
        return toUnocodeEncode(input, ENCODE_PATTERN_ALPHANUM, upperCase);
    }

    public static String toUnocodeEncode(String input, Pattern pattern, boolean upperCase) {
        StringBuilder buff = new StringBuilder();
        int length = input.length();
//        for (int i = 0; i < length; i = input.offsetByCodePoints(i, 1)) {
//            int c = input.codePointAt(i);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            Matcher m = pattern.matcher(Character​.toString(c));
            if (m.matches()) {
                if (upperCase) {
                    buff.append(String.format("\\U%04X", (int) c));
                } else {
                    buff.append(String.format("\\u%04x", (int) c));
                }
            } else {
                buff.appendCodePoint(c);
            }
        }
        return buff.toString();
    }

    /**
     * Decode
     */

    private final static Pattern PTN_HTML = Pattern.compile("(&#(\\d+);)|(&#[xX]([0-9a-fA-F]+);)+");
    private final static Pattern PTN_URL_UNICODE = Pattern.compile("%[uU]([0-9a-fA-F]{4})");
    private final static Pattern PTN_UNICODE = Pattern.compile("\\\\[uU]([0-9a-fA-F]{4})");

    public static String toHtmlDecode(String input) {
        return toHtmlDecode(input, ENCODE_PATTERN_ALL);
    }

    public static String toHtmlDecode(String input, Pattern pattern) {
        StringBuffer buff = new StringBuffer();
        Pattern p = Pattern.compile("(&(?:(#\\d+)|(#[xX][0-9a-fA-F]+)|(\\w+));)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            String html = m.group(1);
            if (html != null) {
                if (html.startsWith("&#x") || html.startsWith("&#X")) {
                    String htmlhex = m.group(3);
                    int ch = Integer.parseInt(htmlhex.substring(2), 16);
                    String cpStr = Character.toString(ch);
                    Matcher m2 = pattern.matcher(cpStr);
                    if (m2.matches()) {
                        m.appendReplacement(buff, Matcher.quoteReplacement(cpStr));
                    }
                } else if (html.startsWith("&#")) {
                    String htmldec = m.group(2);
                    int ch = Integer.parseInt(htmldec.substring(1), 10);
                    String cpStr = Character.toString(ch);
                    Matcher m2 = pattern.matcher(cpStr);
                    if (m2.matches()) {
                        m.appendReplacement(buff, Matcher.quoteReplacement(cpStr));
                    }
                } else if (html.startsWith("&")) {
                    String htmlwd = m.group(4);
                    if (htmlwd == null) {
                        continue;
                    }
                    String htmlch = "";
                    if (htmlwd.equals("lt")) {
                        htmlch = "<";
                    } else if (htmlwd.equals("gt")) {
                        htmlch = ">";
                    } else if (htmlwd.equals("amp")) {
                        htmlch = "&";
                    } else if (htmlwd.equals("quot")) {
                        htmlch = "\"";
                    } else {
                        htmlch = fromHTMLEntity(htmlwd);
                        if (htmlch == null) {
                            htmlch = m.group(0);    // 変換しない
                        }
                    }
                    Matcher m2 = pattern.matcher(htmlch);
                    if (m2.matches()) {
                        m.appendReplacement(buff, htmlch);
                    }
                }
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    /**
     * @param input
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String toHtmlDecode(String input, String charset) throws UnsupportedEncodingException {
        String decode = toHtmlDecode(input, ENCODE_PATTERN_ALL);
        if (charset == null) {
            return decode;
        } else {
            return StringUtil.getStringCharset(StringUtil.getBytesRaw(decode), charset);
        }
    }

    public static String toUrlDecode(String pString, Charset charset) {
        return StringUtil.getStringCharset(toUrlDecode(StringUtil.getBytesCharset(pString, StandardCharsets.US_ASCII)), charset);
    }

    public static String toUrlDecode(String pString, String charset) throws UnsupportedEncodingException {
        return StringUtil.getStringCharset(toUrlDecode(StringUtil.getBytesCharset(pString, StandardCharsets.US_ASCII)), charset);
    }

    public static byte[] toUrlDecode(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException();
        }
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            if (b == '+') {
                buffer.put((byte) ' ');
            } else if (b == '%') {
                try {
                    int chHigh = Character.digit((char) bytes[++i], 16);
                    int chLow = Character.digit((char) bytes[++i], 16);
                    buffer.put((byte) ((chHigh << 4) + chLow));
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            } else {
                buffer.put((byte) b);
            }
        }
        buffer.flip();
        byte[] value = new byte[buffer.limit()];
        buffer.get(value);
        return value;
    }

    private final static Pattern PTN_UNICODE_STR_SURROGATE = Pattern.compile("(\\\\[uU][dD][89abAB][0-9a-fA-F]{2}\\\\[uU][dD][c-fC-F][0-9a-fA-F]{2})|(\\\\[uU][0-9a-fA-F]{4})");

    public static String toUnicodeDecode(String input) {
        return toUnicodeDecode(input, ENCODE_PATTERN_ALL);
    }

    public static String toUnicodeDecode(String input, Pattern pattern) {
        StringBuffer buff = new StringBuffer();
        // 上位サロゲート(\uD800-\uDBFF)
        // 下位サロゲート(\uDC00-\uDFFF)
        Matcher m = PTN_UNICODE_STR_SURROGATE.matcher(input);
        while (m.find()) {
            String unicode = m.group(1);
            if (unicode != null) {
                int chHigh = Integer.parseInt(unicode.substring(2, 6), 16);
                int chLow = Integer.parseInt(unicode.substring(8, 12), 16);
                m.appendReplacement(buff, Matcher.quoteReplacement(String.valueOf(new char[]{(char) chHigh, (char) chLow})));
            } else {
                unicode = m.group(2);
                int ch = Integer.parseInt(unicode.substring(2), 16);
                String cpStr = Character.toString(ch);
                Matcher m2 = pattern.matcher(cpStr);
                if (m2.matches()) {
                    m.appendReplacement(buff, Matcher.quoteReplacement(cpStr));
                }
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    private final static Pattern PTN_UNICODE_URL_SURROGATE = Pattern.compile("(%[uU][dD][89abAB][0-9a-fA-F]{2}%[uU][dD][c-fC-F][0-9a-fA-F]{2})|(%[uU][0-9a-fA-F]{4})");

    public static String toUnicodeUrlDecode(String input) {
        return toUnicodeUrlDecode(input, ENCODE_PATTERN_ALL);
    }

    public static String toUnicodeUrlDecode(String input, Pattern pattern) {
        StringBuffer buff = new StringBuffer();
        // 上位サロゲート(\uD800-\uDBFF)
        // 下位サロゲート(\uDC00-\uDFFF)
        Matcher m = PTN_UNICODE_URL_SURROGATE.matcher(input);
        while (m.find()) {
            String unicode = m.group(1);
            if (unicode != null) {
                int chHigh = Integer.parseInt(unicode.substring(2, 6), 16);
                int chLow = Integer.parseInt(unicode.substring(8, 12), 16);
                m.appendReplacement(buff, Matcher.quoteReplacement(String.valueOf(new char[]{(char) chHigh, (char) chLow})));
            } else {
                unicode = m.group(2);
                int ch = Integer.parseInt(unicode.substring(2), 16);
                String cpStr = Character.toString(ch);
                Matcher m2 = pattern.matcher(cpStr);
                if (m2.matches()) {
                    m.appendReplacement(buff, Matcher.quoteReplacement(cpStr));
                }
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    public static String toUnocodeDecode(String input) {
        return toUnocodeDecode(input, "\\\\[uU]");
    }

    public static String toUnocodeDecode(String input, String prefix) {
        final Pattern PTN_UNICODE2_STR_SURROGATE = Pattern.compile(String.format("(%s([dD][89abAB][0-9a-fA-F]{2})%s([dD][c-fC-F][0-9a-fA-F]{2}))|%s([0-9a-fA-F]{4})", Pattern.quote(prefix), Pattern.quote(prefix), prefix));
        StringBuffer buff = new StringBuffer();
        // 上位サロゲート(\uD800-\uDBFF)
        // 下位サロゲート(\uDC00-\uDFFF)
        Matcher m = PTN_UNICODE2_STR_SURROGATE.matcher(input);
        while (m.find()) {
            String unicode = m.group(1);
            if (unicode != null) {
                String gpHigh = m.group(2);
                String gpLow = m.group(3);
                int chHigh = Integer.parseInt(gpHigh, 16);
                int chLow = Integer.parseInt(gpLow, 16);
                m.appendReplacement(buff, Matcher.quoteReplacement(String.valueOf(new char[]{(char) chHigh, (char) chLow})));
            } else {
                unicode = m.group(4);
                int ch = Integer.parseInt(unicode, 16);
                m.appendReplacement(buff, Matcher.quoteReplacement(String.valueOf(new char[]{(char) ch})));
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

}
