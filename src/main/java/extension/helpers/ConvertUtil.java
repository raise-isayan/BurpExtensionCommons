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
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
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

/**
 *
 * @author isayan
 */
public class ConvertUtil {

    private final static Logger logger = Logger.getLogger(ConvertUtil.class.getName());

    public static String repeat(String str, int n) {
      return String.join("", Collections.nCopies(n, str));
    }    
    
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
        } catch (NumberFormatException e) {
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
     * @param enumType
     * @param name
     * @param defvalue 変換できなかった場合のデフォルト値
     * @return 変換後のEnum
     */
    public static <T extends Enum<T>> T parseEnumDefault(Class<T> enumType, String name, T defvalue) {
        try {
            return Enum.valueOf(enumType, name);
        } catch (IllegalArgumentException e) {
            return defvalue;
        } catch (NullPointerException e) {
            return defvalue;
        }
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

    public static String toHexString(byte [] data) {
        return String.valueOf(encodeHex(data));
    }

    public static byte [] fromHexString(String data) {
        return decodeHex(data.toCharArray());
    }
    
    private static final char[] HEX_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    
    private static char [] encodeHex(final byte [] data) {
        char [] out = new char[data.length * 2];        
        for (int i = 0; i < data.length; i++) {
            out[i*2+0] = HEX_UPPER[(0xF0 & data[i]) >>> 4];
            out[i*2+1] = HEX_UPPER[0x0F & data[i]];
        }
        return out;
    }

    private static byte [] decodeHex(final char [] data) {
        if (data.length % 2 != 0) new IllegalArgumentException();
        byte [] out = new byte[data.length / 2];        
        for (int i = 0; i < out.length; i++) {
            final int digitH = Character.digit(data[i*2+0], 16);
            final int digitL = Character.digit(data[i*2+1], 16);
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
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fostm != null) {
                    fostm.close();
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return file;
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
            byte bytes[] = Base64.getEncoder().encode(src.getBytes(charset));
            return StringUtil.getStringRaw(bytes);
        } else {
            byte bytes[] = Base64.getEncoder().withoutPadding().encode(src.getBytes(charset));
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
            byte bytes[] = Base64.getEncoder().encode(src.getBytes(charset));
            return StringUtil.getStringRaw(bytes);
        } else {
            byte bytes[] = Base64.getEncoder().withoutPadding().encode(src.getBytes(charset));
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
        return new String(bytes, charset);
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
        return new String(bytes, charset);
    }

    public static String toBase64URLSafeDecode(String str, String charset)
            throws UnsupportedEncodingException {
        byte bytes[] = Base64.getUrlDecoder().decode(str);
        return new String(bytes, charset);
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
    
}
