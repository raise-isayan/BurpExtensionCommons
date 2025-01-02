package extension.helpers;

import extension.burp.BurpVersion;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.EnumSet;
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

    /**
     * 指定した文字数にマッチする場合に改行を追加する
     *
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

    public static String toHexString(byte data, boolean upperCase) {
        return String.valueOf(encodeHex(data, upperCase));
    }

    public static String toHexString(int input, boolean upperCase) {
        byte[] hex = BigInteger.valueOf(input).toByteArray();
        int i = 0;
        while (i < hex.length - 1 && hex[i] == 0) {
            i++;
        }
        return toHexString(Arrays.copyOfRange(hex, i, hex.length), upperCase);
    }

    public static String toHexString(byte[] data, boolean upperCase) {
        return String.valueOf(encodeHex(data, upperCase));
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

    private static final char[] HEX_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static char[] encodeHex(final byte data, boolean upperCase) {
        char[] out = new char[2];
        final char[] HEX_CONVERT = (upperCase) ? HEX_UPPER : HEX_LOWER;
        out[0] = HEX_CONVERT[(0xF0 & data) >>> 4];
        out[1] = HEX_CONVERT[0x0F & data];
        return out;
    }

    private static char[] encodeHex(final byte[] data, boolean upperCase) {
        char[] out = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            char hex[] = encodeHex(data[i], upperCase);
            out[i * 2 + 0] = hex[0];
            out[i * 2 + 1] = hex[1];
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

    public static Process executeProcess(String target, String args[]) throws IOException {
        return executeProcess(target, Arrays.asList(args));
    }

    public static Process executeProcess(String target, List argsList) throws IOException {
        final List<String> commandList = new ArrayList<>();
        commandList.add(target);
        commandList.addAll(argsList);
        ProcessBuilder processBulder = new ProcessBuilder(commandList);
        return processBulder.start();
    }

    private final static String MAC_OPEN = "open";
    private final static String MAC_OPEN_N = "-n";
    private final static String MAC_ARGS = "--args";

    public static Process executeProcess(BurpVersion.OSType osType, String target, List argsList) throws IOException {
        final List<String> commandList = new ArrayList<>();
        if (BurpVersion.OSType.MAC == osType) {
            commandList.add(MAC_OPEN);
        }
        commandList.add(target);
        if (BurpVersion.OSType.MAC == osType) {
            commandList.add(MAC_OPEN_N);
            commandList.add(MAC_ARGS);
        }
        commandList.addAll(argsList);
        ProcessBuilder processBulder = new ProcessBuilder(commandList);
        return processBulder.start();
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

    private final static Pattern PTN_JS_META = Pattern.compile("(\\\\[rnbftv\\\\])|(\\\\x[0-9a-fA-F]{2})|((\\\\u[dD][89abAB][0-9a-fA-F]{2}\\\\u[dD][c-fC-F][0-9a-fA-F]{2})|(\\\\u[0-9a-fA-F]{4}))");

    /**
     * JavaScript言語形式のメタ文字デコード(エスケープされたものを戻す)
     *
     * @param input
     * @return デコードされた値
     */
    public static String decodeJsLangMeta(String input) {
        StringBuffer buff = new StringBuffer();
        Matcher m = PTN_JS_META.matcher(input);
        while (m.find()) {
            String p1 = m.group(1);
            String p2 = m.group(2);
            String p3 = m.group(3);
            if (p1 != null) {
                char code = p1.charAt(1);
                switch (code) {
                    case 'r':
                        m.appendReplacement(buff, "\r");
                        break;
                    case 'n':
                        m.appendReplacement(buff, "\n");
                        break;
                    case 'b':
                        m.appendReplacement(buff, Character.toString((char) (0x08)));
                        break;
                    case 'f':
                        m.appendReplacement(buff, Character.toString((char) (0x0c)));
                        break;
                    case 't':
                        m.appendReplacement(buff, "\t");
                        break;
                    case 'v':
                        m.appendReplacement(buff, Character.toString((char) (0x0b)));
                        break;
                    case '\\':
                        m.appendReplacement(buff, "\\\\");
                        break;
                    default:
                        break;
                }
            } else if (p2 != null) {
                String bytecode = p2;
                int ch = Integer.parseInt(bytecode.substring(2), 16);
                m.appendReplacement(buff, Matcher.quoteReplacement(String.valueOf(new char[]{(char) ch})));
            } else if (p3 != null) {
                String unicode = m.group(4);
                if (unicode != null) {
                    int chHigh = Integer.parseInt(unicode.substring(2, 6), 16);
                    int chLow = Integer.parseInt(unicode.substring(8, 12), 16);
                    m.appendReplacement(buff, Matcher.quoteReplacement(String.valueOf(new char[]{(char) chHigh, (char) chLow})));
                } else {
                    unicode = m.group(5);
                    int ch = Integer.parseInt(unicode.substring(2), 16);
                    m.appendReplacement(buff, Matcher.quoteReplacement(String.valueOf(new char[]{(char) ch})));
                }
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    private final static Pattern PTN_STANDARD_DECODE_META = Pattern.compile("\\\\([rnt])");

    /**
     * 標準言語形式のメタ文字デコード(エスケープされたものを戻す)
     *
     * @param input
     * @return デコードされた値
     */
    public static String decodeStandardLangMeta(String input) {
        StringBuffer buff = new StringBuffer();
        Matcher m = PTN_STANDARD_DECODE_META.matcher(input);
        while (m.find()) {
            String p1 = m.group(1);
            int code = p1.codePointAt(0);
            switch (code) {
                case 'r':
                    m.appendReplacement(buff, "\r");
                    break;
                case 'n':
                    m.appendReplacement(buff, "\n");
                    break;
                case 't':
                    m.appendReplacement(buff, "\t");
                    break;
                default:
                    break;
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    private final static Pattern PTN_STANDARD_ENCODE_META = Pattern.compile("([\r\n\t])");

    /**
     * 標準言語形式のメタ文字エンコード(エスケープする)
     *
     * @param input
     * @return エンコードされた値
     */
    public static String encodeStandardLangMeta(String input) {
        StringBuffer buff = new StringBuffer();
        Matcher m = PTN_STANDARD_ENCODE_META.matcher(input);
        while (m.find()) {
            String p1 = m.group(1);
            int code = p1.codePointAt(0);
            switch (code) {
                case '\r':
                    m.appendReplacement(buff, "\\\\r");
                    break;
                case '\n':
                    m.appendReplacement(buff, "\\\\n");
                    break;
                case '\t':
                    m.appendReplacement(buff, "\\\\t");
                    break;
                default:
                    break;
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    private final static Pattern PTN_JSON_ENCODE_META = Pattern.compile("([\r\n\t\b\f])");

    /**
     * 標準言語形式のメタ文字エンコード(エスケープする)
     *
     * @param input
     * @return エンコードされた値
     */
    public static String encodeJsonMeta(String input) {
        StringBuffer buff = new StringBuffer();
        Matcher m = PTN_JSON_ENCODE_META.matcher(input);
        while (m.find()) {
            String p1 = m.group(1);
            int code = p1.codePointAt(0);
            switch (code) {
                case '\r':
                    m.appendReplacement(buff, "\\\\r");
                    break;
                case '\n':
                    m.appendReplacement(buff, "\\\\n");
                    break;
                case '\t':
                    m.appendReplacement(buff, "\\\\t");
                    break;
                case '\b':
                    m.appendReplacement(buff, "\\\\b");
                    break;
                case '\f':
                    m.appendReplacement(buff, "\\\\f");
                    break;
                default:
                    break;
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    private final static Pattern PTN_JSON_DECODE_META = Pattern.compile("\\\\([rntbf])");

    /**
     * JSON形式のメタ文字デコード(エスケープされたものを戻す)
     *
     * @param input
     * @return デコードされた値
     */
    public static String decodeJsonMeta(String input) {
        StringBuffer buff = new StringBuffer();
        Matcher m = PTN_JSON_DECODE_META.matcher(input);
        while (m.find()) {
            String p1 = m.group(1);
            int code = p1.codePointAt(0);
            switch (code) {
                case 'r':
                    m.appendReplacement(buff, "\r");
                    break;
                case 'n':
                    m.appendReplacement(buff, "\n");
                    break;
                case 't':
                    m.appendReplacement(buff, "\t");
                    break;
                case 'b':
                    m.appendReplacement(buff, "\b");
                    break;
                case 'f':
                    m.appendReplacement(buff, "\f");
                    break;
                default:
                    break;
            }
        }
        m.appendTail(buff);
        return buff.toString();
    }

    public static String toRegexEncode(String value) {
        return toRegexEncode(value, false);
    }

    /**
     * 正規表現のリテラルエンコード(エスケープ)
     *
     * @param value
     * @param metachar
     * @return エンコードされた値
     */
    public static String toRegexEncode(String value, boolean metachar) {
        String encode = value;
        if (metachar) {
            encode = encodeStandardLangMeta(encode);
        }
        StringBuilder buff = new StringBuilder();
        int length = encode.length();
        for (int i = 0; i < length; i++) {
            char c = encode.charAt(i);
            buff.append(MatchUtil.toRegexEscape(c));
        }
        return buff.toString();
    }

    public static String toRegexDecode(String value) {
        return toRegexDecode(value, false);
    }

    public static String toRegexDecode(String value, boolean metachar) {
        String decode = value.replaceAll("\\\\([\\\\\\.\\+\\*\\?\\[\\^\\]\\$\\(\\)\\{\\}\\=\\!\\<\\>\\|\\:\\-])", "$1");
        if (metachar) {
            decode = decodeStandardLangMeta(decode);
        }
        return decode;
    }

    public static String encodeJsLangQuote(String value) {
        return encodeJsLangQuote(value, false);
    }

    /**
     * JavaScript言語形式のリテラルエンコード(エスケープ)
     *
     * @param value
     * @param metachar
     * @return エンコードされた値
     */
    public static String encodeJsLangQuote(String value, boolean metachar) {
        String encode = value.replaceAll("([\\\\\"'])", "\\\\$1");
        if (metachar) {
            encode = encodeStandardLangMeta(encode);
        }
        return encode;
    }

    public static String decodeJsLangQuote(String value) {
        return decodeJsLangQuote(value, false);
    }

    /**
     * JavaScript言語形式のリテラルデコード(エスケープされたものを戻す)
     *
     * @param value
     * @param metachar
     * @return デコードされた値
     */
    public static String decodeJsLangQuote(String value, boolean metachar) {
        String decode = value;
        if (metachar) {
            decode = decodeStandardLangMeta(decode);
        }
        return decode.replaceAll("\\\\([\\\\\"'])", "$1");
    }

    public static String encodeCLangQuote(String value) {
        return encodeCLangQuote(value, false);
    }

    /**
     * C言語形式のエンコード(エスケープ)
     *
     * @param value
     * @param metachar
     * @return エンコードされた値
     */
    public static String encodeCLangQuote(String value, boolean metachar) {
        String encode = value.replaceAll("([\\\\\"])", "\\\\$1");
        if (metachar) {
            encode = encodeStandardLangMeta(encode);
        }
        return encode;
    }

    public static String decodeCLangQuote(String value) {
        return decodeCLangQuote(value, false);
    }

    /**
     * C言語形式のデコード(エスケープされたものを戻す)
     *
     * @param value
     * @param metachar
     * @return デコードされた値
     */
    public static String decodeCLangQuote(String value, boolean metachar) {
        String decode = value;
        if (metachar) {
            decode = decodeStandardLangMeta(decode);
        }
        return decode.replaceAll("\\\\([\\\\\"])", "$1");
    }

    public static String encodeJsonLiteral(String value) {
        return encodeJsonLiteral(value, false);
    }

    /**
     * JSON形式のリテラルエンコード(エスケープ)
     *
     * @param value
     * @param metachar
     * @return エンコードされた値
     */
    public static String encodeJsonLiteral(String value, boolean metachar) {
        String encode = value.replaceAll("([\\\\\"/])", "\\\\$1");
        if (metachar) {
            encode = encodeJsonMeta(encode);
        }
        return encode;
    }

    public static String decodeJsonLiteral(String value) {
        return decodeJsonLiteral(value, false);
    }

    /**
     * JSON形式のリテラルデコード(エスケープされたものを戻す)
     *
     * @param value
     * @param metachar
     * @return デコードされた値
     */
    public static String decodeJsonLiteral(String value, boolean metachar) {
        String decode = value;
        if (metachar) {
            decode = decodeJsonMeta(decode);
        }
        return decode.replaceAll("\\\\([\\\\\"/])", "$1");
    }

    public static String encodeSQLLangQuote(String value) {
        return encodeSQLLangQuote(value, false);
    }

    /**
     * PL/SQL言語形式のエンコード(エスケープ)
     *
     * @param value
     * @param metachar
     * @return エンコードされた値
     */
    public static String encodeSQLLangQuote(String value, boolean metachar) {
        String encode = value.replaceAll("'", "''");
        if (metachar) {
            encode = encodeStandardLangMeta(encode);
        }
        return encode;
    }

    public static String decodeSQLangQuote(String value) {
        return decodeSQLangQuote(value, false);
    }

    /**
     * PL/SQL言語形式のデコード(エスケープされたものを戻す)
     *
     * @param value
     * @param metachar
     * @return デコードされた値
     */
    public static String decodeSQLangQuote(String value, boolean metachar) {
        String decode = value;
        if (metachar) {
            decode = decodeStandardLangMeta(decode);
        }
        return decode.replaceAll("''", "'");
    }

}
