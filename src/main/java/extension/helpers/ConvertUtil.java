package extension.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.IDN;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

/**
 *
 * @author isayan
 */
public class ConvertUtil {

    private final static Logger logger = Logger.getLogger(ConvertUtil.class.getName());

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
     * @param entityName
     * @return
     */
    static String fromHTMLEntity(String entityName) {
        Character ch = ENTITY.get(entityName);
        if (ch == null) {
            return null;
        }
        return Character.toString(ch);
    }


    public static boolean isAlphaNum(int codePoint) {
        return (0x30 <= codePoint && codePoint <= 0x39) || (0x41 <= codePoint && codePoint <= 0x5a) || (0x61 <= codePoint && codePoint <= 0x7a);
    }

    /**
     * 指定した文字数にマッチする場合に改行を追加する
     * @param separator
     * @param value
     * @param length
     * @return
     */
    public static String newLine(String separator, String value, int length) {
        Pattern p = Pattern.compile(String.format("(.{%d})", length));
        StringBuffer sb = new StringBuffer();
        Matcher m = p.matcher(value);
        while (m.find()) {
            m.appendReplacement(sb, m.group(1) + separator);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 文字列をintに変換
     *
     * @param value 対象文字列
     * @param defvalue 変換できなかった場合のデフォルト値
     * @return 変換後の数字
     */
    public static int parseIntDefault(String value, int defvalue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return defvalue;
        }
    }

    /**
     * 文字列をlongに変換
     *
     * @param value 対象文字列
     * @param defvalue 変換できなかった場合のデフォルト値
     * @return 変換後のFloat
     */
    public static long parseLongDefault(String value, long defvalue) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return defvalue;
        }
    }

    /**
     * 文字列をFloatに変換
     *
     * @param value 対象文字列
     * @param defvalue 変換できなかった場合のデフォルト値
     * @return 変換後のFloat
     */
    public static float parseFloatDefault(String value, float defvalue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException ex) {
            return defvalue;
        }
    }

    /**
     * 文字列をDoubleに変換
     *
     * @param value 対象文字列
     * @param defvalue 変換できなかった場合のデフォルト値
     * @return 変換後のFloat
     */
    public static double parseDoubleDefault(String value, double defvalue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return defvalue;
        }
    }

    /**
     * 文字列をBoolean型に変換
     *
     * @param value 対象文字列
     * @param defvalue 変換できなかった場合のデフォルト値
     * @return 変換後のBoolean
     */
    public static boolean parseBooleanDefault(String value, boolean defvalue) {
        if (value == null) {
            return defvalue;
        } else if (value.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (value.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        } else {
            return defvalue;
        }
    }

    /**
     * 文字列を対応するEnum型に変換
     *
     * @param <T>
     * @param enumType
     * @param name
     * @param defvalue 変換できなかった場合のデフォルト値
     * @return 変換後のEnum
     */
    public static <T extends Enum<T>> T parseEnumDefault(Class<T> enumType, String name, T defvalue) {
        try {
            return Enum.valueOf(enumType, name);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defvalue;
        }
    }

    public static String enumSetToString(EnumSet<?> enumset) {
        Iterator<?> it = enumset.iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            Enum<?> e = (Enum<?>) it.next();
            sb.append(e.name());
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }

    @SuppressWarnings("unchecked")
    public static Enum parseEnumValue(Class enumType, String value) {
        if (value != null) {
            value = value.toUpperCase();
            value = value.replace(' ', '_');
            return Enum.valueOf(enumType, value);
        }
        return null;
    }

    public static <T> List<T> toList(Iterator<T> e) {
        List<T> l = new ArrayList<>();
        while (e.hasNext()) {
            l.add(e.next());
        }
        return l;
    }

    public static <T> List<T> toUniqList(List<T> list) {
        Map<T, Boolean> mapUniq = new LinkedHashMap<>(16, (float) 0.75, true);
        for (T k : list) {
            mapUniq.put(k, true);
        }
        return toList(mapUniq.keySet().iterator());
    }

    public static List<String> toUniqList(String regex, List<String> list) {
        Pattern pattern = Pattern.compile(regex);
        Map<String, Boolean> mapUniq = new LinkedHashMap<>(16, (float) 0.75, true);
        for (String k : list) {
            Matcher m = pattern.matcher(String.valueOf(k));
            if (m.matches()) {
                String g = (m.groupCount() > 0) ? (m.group(1)) : (m.group(0));
                mapUniq.put(g, true);
            }
        }
        return toList(mapUniq.keySet().iterator());
    }

    public static int toInteger(byte[] input) {
        int value = 0;
        for (int i = 0; i < input.length; i++) {
            value = (value << 8) | (input[i] & 0xff);
        }
        return value;
    }

    public static String toHexString(byte input) {
        return toHexString(new byte[]{input});
    }

    public static String toHexString(int input) {
        byte [] hex = BigInteger.valueOf(input).toByteArray();
        int i = 0; while (hex[i] == 0) { i++; }
        return toHexString(Arrays.copyOfRange(hex, i, hex.length));
    }

    public static String toHexString(byte[] data) {
        return String.valueOf(encodeHex(data));
    }

    public static byte[] fromHexString(String data) {
        return decodeHex(data.toCharArray());
    }

    public static String escapeXml(String target) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Text text = document.createTextNode(target);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(text);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(source, result);
        return writer.toString();
    }

    public static String escapeJson(String value) {
        return value.replaceAll("([\"\\\\/])", "\\\\$1");
    }

    private static final char[] HEX_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static char[] encodeHex(final byte[] data) {
        char[] out = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            out[i * 2 + 0] = HEX_UPPER[(0xF0 & data[i]) >>> 4];
            out[i * 2 + 1] = HEX_UPPER[0x0F & data[i]];
        }
        return out;
    }

    private static byte[] decodeHex(final char[] data) {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        byte[] out = new byte[data.length / 2];
        for (int i = 0; i < out.length; i++) {
            final int digitH = Character.digit(data[i * 2 + 0], 16);
            final int digitL = Character.digit(data[i * 2 + 1], 16);
            int hex = digitH << 4;
            hex |= digitL;
            out[i] = (byte) (hex & 0xFF);
        }
        return out;
    }

    public static File tempFile(byte[] buff, String prefix) {
        File file = null;
        FileOutputStream fostm = null;
        try {
            file = File.createTempFile(prefix, ".tmp");
            file.deleteOnExit();
            fostm = new FileOutputStream(file, true);
            fostm.write(buff);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (fostm != null) {
                    fostm.close();
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return file;
    }

    public static byte[] appandByte(byte[] byteArray1, byte[] byteArray2) {
        ByteBuffer buf = ByteBuffer.allocate(byteArray1.length + byteArray2.length);
        buf.put(byteArray1);
        buf.put(byteArray2);
        buf.flip();
        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes);
        return bytes;
    }

    public static byte[] replaceByte(byte[] base, int startPos, int endPos, byte[] replace) {
        ByteBuffer buf = ByteBuffer.allocate(startPos + replace.length + base.length - endPos);
        buf.put(base, 0, startPos);
        buf.put(replace);
        buf.put(base, endPos, base.length - endPos);
        buf.flip();
        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes);
        return bytes;
    }

    public static byte[] bytesFromFile(File file) throws IOException {
        ByteArrayOutputStream bostm = new ByteArrayOutputStream();
        try (FileInputStream fstm = new FileInputStream(file)) {
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fstm.read(buff)) > 0) {
                bostm.write(buff, 0, len);
            }
        }
        return bostm.toByteArray();
    }

    public static File bytesToFile(byte[] bytes, File file) throws IOException {
        ByteArrayInputStream bostm = new ByteArrayInputStream(bytes);
        try (FileOutputStream fstm = new FileOutputStream(file)) {
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bostm.read(buff)) > 0) {
                fstm.write(buff, 0, len);
            }
        }
        return file;
    }

    public static String toBase64Encode(String src, Charset charset) {
        return toBase64Encode(src, charset, true);
    }

    public static String toBase64Encode(String src, Charset charset, boolean padding) {
        if (padding) {
            byte bytes[] = Base64.getEncoder().encode(StringUtil.getBytesCharset(src, charset));
            return StringUtil.getStringRaw(bytes);
        } else {
            byte bytes[] = Base64.getEncoder().withoutPadding().encode(StringUtil.getBytesCharset(src, charset));
            return StringUtil.getStringRaw(bytes);
        }
    }

    public static String toBase64Encode(String src, String charset)
            throws UnsupportedEncodingException {
        return toBase64Encode(src, charset, true);
    }

    public static String toBase64Encode(String src, String charset, boolean padding)
            throws UnsupportedEncodingException {
        if (padding) {
            byte bytes[] = Base64.getEncoder().encode(StringUtil.getBytesCharset(src, charset));
            return StringUtil.getStringRaw(bytes);
        } else {
            byte bytes[] = Base64.getEncoder().withoutPadding().encode(StringUtil.getBytesCharset(src, charset));
            return StringUtil.getStringRaw(bytes);
        }
    }

    public static String toBase64Encode(byte[] src, String charset)
            throws UnsupportedEncodingException {
        return toBase64Encode(src, true);
    }

    public static String toBase64Encode(byte[] src, boolean padding) {
        if (padding) {
            byte bytes[] = Base64.getEncoder().encode(src);
            return StringUtil.getStringRaw(bytes);
        } else {
            byte bytes[] = Base64.getEncoder().withoutPadding().encode(src);
            return StringUtil.getStringRaw(bytes);
        }
    }

    public static String toBase64Decode(String str, Charset charset) {
        byte bytes[] = Base64.getDecoder().decode(str);
        return new String(bytes, charset);
    }

    public static String toBase64Decode(String str, String charset)
            throws UnsupportedEncodingException {
        byte bytes[] = Base64.getDecoder().decode(str);
        return StringUtil.getStringCharset(bytes, charset);
    }

    public static byte[] toBase64Decode(String str) {
        return Base64.getDecoder().decode(str);
    }

    public static String toBase64URLSafeEncode(String src, Charset charset) {
        byte bytes[] = Base64.getUrlEncoder().withoutPadding().encode(src.getBytes(charset));
        return StringUtil.getStringRaw(bytes);
    }

    public static String toBase64URLSafeEncode(String src, String charset)
            throws UnsupportedEncodingException {
        byte bytes[] = Base64.getUrlEncoder().withoutPadding().encode(src.getBytes(charset));
        return StringUtil.getStringRaw(bytes);
    }

    public static String toBase64URLSafeEncode(byte[] src) {
        byte bytes[] = Base64.getUrlEncoder().withoutPadding().encode(src);
        return StringUtil.getStringRaw(bytes);
    }

    public static String toBase64URLSafeDecode(String str, Charset charset) {
        byte bytes[] = Base64.getUrlDecoder().decode(str);
        return StringUtil.getStringCharset(bytes, charset);
    }

    public static String toBase64URLSafeDecode(String str, String charset)
            throws UnsupportedEncodingException {
        byte bytes[] = Base64.getUrlDecoder().decode(str);
        return StringUtil.getStringCharset(bytes, charset);
    }

    public static byte[] toBase64URLSafeDecode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }

    public static byte[] compressGzip(byte[] content) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(content))) {
            byte[] buf = new byte[1024];
            int size;
            try (OutputStream gos = new GZIPOutputStream(baos)) {
                while ((size = in.read(buf, 0, buf.length)) != -1) {
                    gos.write(buf, 0, size);
                }
                gos.flush();
            }
        }
        return baos.toByteArray();
    }

    public static byte[] compressZlib(byte[] content) {
        return compressZlib(content, false);
    }

    public static byte[] compressZlib(byte[] content, boolean nowrap) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION, nowrap);
        try {
            compresser.setInput(content);
            compresser.finish();
            byte[] buf = new byte[1024];
            int count = 0;
            while (!compresser.finished()) {
                count = compresser.deflate(buf);
                bout.write(buf, 0, count);
            }
        } finally {
            compresser.end();
        }
        return bout.toByteArray();
    }

    public static String compressZlibBase64(String content, Charset charset) {
        return toBase64Encode(compressZlib(StringUtil.getBytesCharset(content, charset)), true);
    }

    public static String compressZlibBase64(String content) {
        return compressZlibBase64(content, StandardCharsets.ISO_8859_1);
    }

    public static byte[] decompressGzip(byte[] content) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content))) {
            try (BufferedOutputStream out = new BufferedOutputStream(baos)) {
                byte[] buf = new byte[1024];
                int size;
                while ((size = gis.read(buf, 0, buf.length)) != -1) {
                    out.write(buf, 0, size);
                }
                out.flush();
            }
        }
        return baos.toByteArray();
    }

    public static byte[] decompressZlib(byte[] content) {
        return decompressZlib(content, false);
    }

    public static byte[] decompressZlib(byte[] content, boolean nowrap) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Inflater decompresser = new Inflater(nowrap);
        try {
            decompresser.setInput(content);
            byte[] buf = new byte[1024];
            int count = 0;
            try {
                while (!decompresser.finished()) {
                    count = decompresser.inflate(buf);
                    if (count <= 0) {
                        break;
                    }
                    bout.write(buf, 0, count);
                }
            } catch (DataFormatException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        } finally {
            decompresser.end();
        }
        return bout.toByteArray();
    }

    public static String decompressZlibBase64(String content, Charset charset) {
        return StringUtil.getStringCharset(decompressZlib(toBase64Decode(content)), charset);
    }

    public static String decompressZlibBase64(String content) {
        return decompressZlibBase64(content, StandardCharsets.ISO_8859_1);
    }

    public static Process executeFormat(String target, String args[]) throws IOException {
        Process process = null;
        String command = "";
        MessageFormat msgfmt = new MessageFormat(target);
        if (msgfmt.getFormats().length > 0) {
            command = msgfmt.format(target, (Object[]) args);
            process = Runtime.getRuntime().exec(command);
        } else {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(args));
            list.add(0, target);
            process = Runtime.getRuntime().exec((String[]) list.toArray(String[]::new));
        }
        //Runtime.getRuntime().exec(args);
        return process;
    }

    /**
     * 正規表現のエンコード(エスケープ)
     *
     * @param value
     * @return エンコードされた値
     */
    public static String regexQuote(String value) {
        return value.replaceAll("([\\.\\\\\\+\\*\\?\\[\\^\\]\\$\\(\\)\\{\\}\\=\\!\\<\\>\\|\\:\\-])", "\\\\$1");
    }

    public static int bytesToInt(final byte[] bytes, ByteOrder byteOrder) {
        int result = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int i = 0; i < bytes.length; i++) {
                result <<= Byte.SIZE;
                result |= (bytes[i] & 0xFF);
            }
        } else {
            for (int i = bytes.length; i > 0; i--) {
                result <<= Byte.SIZE;
                result |= (bytes[i - 1] & 0xFF);
            }
        }
        return result;
    }

    public static byte[] intToBytes(final int value, ByteOrder byteOrder) {
        int mag = Integer.SIZE - Integer.numberOfLeadingZeros(value);
        int bsize = Math.max(((mag + (Byte.SIZE - 1)) / Byte.SIZE), 1);
        byte[] bytes = new byte[bsize];
        long val = value;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int i = bytes.length; i > 0; i--) {
                bytes[i - 1] = (byte) (val & 0xFF);
                val >>= Byte.SIZE;
            }
        } else {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (val & 0xFF);
                val >>= Byte.SIZE;
            }
        }
        return bytes;
    }

    public static long bytesToLong(final byte[] bytes, ByteOrder byteOrder) {
        long result = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int i = 0; i < bytes.length; i++) {
                result <<= Byte.SIZE;
                result |= (bytes[i] & 0xFF);
            }
        } else {
            for (int i = bytes.length; i > 0; i--) {
                result <<= Byte.SIZE;
                result |= (bytes[i - 1] & 0xFF);
            }
        }
        return result;
    }

    public static byte[] longToBytes(final long value, ByteOrder byteOrder) {
        int mag = Long.SIZE - Long.numberOfLeadingZeros(value);
        int bsize = Math.max(((mag + (Byte.SIZE - 1)) / Byte.SIZE), 1);
        byte[] bytes = new byte[bsize];
        long val = value;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int i = bytes.length; i > 0; i--) {
                bytes[i - 1] = (byte) (val & 0xFF);
                val >>= Byte.SIZE;
            }
        } else {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (val & 0xFF);
                val >>= Byte.SIZE;
            }
        }
        return bytes;
    }

    public static double calcStlength(int base, int exponent) {
        return Math.log(Math.pow(base, exponent)) / Math.log(2.0);
    }

    public static int calcCharacterKind(String base) {
        HashSet<Character> map = new HashSet<>();
        for (int j = 0; j < base.length(); j++) {
            char c = base.charAt(j);
            map.add(c);
        }
        return map.toArray().length;
    }

    public static String toEmpty(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    public static String toPunycodeEncode(String value) {
        return IDN.toASCII(value);
    }

    public static String toPunycodeDecode(String value) {
        return IDN.toUnicode(value);
    }

}
