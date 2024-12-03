package extension.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author isayan
 */
public class FileUtil {

    private final static Logger logger = Logger.getLogger(FileUtil.class.getName());

    public static long totalFileSize(File filedir, boolean recursive) throws IOException {
        long filesize = 0;
        if (filedir.isDirectory()) {
            File[] list = filedir.listFiles();
            if (list != null) {
                for (File file : list) {
                    if (file.isDirectory() && recursive) {
                        filesize += totalFileSize(file, recursive);
                    } else {
                        filesize += Files.size(file.toPath());
                    }
                }
            }
        } else {
            filesize += Files.size(filedir.toPath());
        }
        return filesize;
    }

    public static String extractFileExtension(String filename) {
        int ext = filename.lastIndexOf('.');
        if (0 < ext && ext < filename.length() - 1) {
            return filename.substring(ext);
        }
        return "";
    }

    public static String extractFileBaseName(String filename) {
        int ext = filename.lastIndexOf('.');
        if (0 < ext && ext < filename.length() - 1) {
            return filename.substring(0, ext);
        }
        return filename;
    }

    /* 空のZIPファイルを作成する */
    public static File createEmptyZip(File zipFile) throws IOException {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {

        }
        return zipFile;
    }

    /**
     * ローテーション可能なファイル名を探す
     *
     * @param dir ディレクトリ
     * @param value
     * @return ローテーションファイル名
     */
    public static boolean existsStartsDir(File dir, final String value) {
        String[] list = dir.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(value);
            }
        });
        return (list.length > 0);
    }

    /**
     * ローテーション可能なファイル名を探す
     *
     * @param dir ディレクトリ
     * @param filename パターン
     * @return ローテーションファイル名
     */
    public static File rotateFile(File dir, String filename) {
        int count = 1;
        String baseName = extractFileBaseName(filename);
        String ext = extractFileExtension(filename);

        String pattern = baseName.replace("%", "%%");

        pattern += "-%d";
        // 存在しないファイルを探す
        File file = new File(dir, String.format(pattern, count));
        while (file.exists()) {
            count++;
            file = new File(dir, String.format(pattern + ext, count));
        }
        return file;
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
        } catch (Exception ex) {
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

    /* InputStream.readAllBytes は JDK 9 からサポート */
    public static byte[] readAllBytes(InputStream stream) throws IOException {
        ByteArrayOutputStream bostm = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = stream.read(buff)) >= 0) {
            bostm.write(buff, 0, len);
        }
        return bostm.toByteArray();
    }

    public static String stringFromFile(File file, Charset charset) throws IOException {
        StringWriter writer = new StringWriter();
        try (InputStreamReader fstm = new InputStreamReader(new FileInputStream(file), charset)) {
            char[] buff = new char[1024];
            int len = 0;
            while ((len = fstm.read(buff)) > 0) {
                writer.write(buff, 0, len);
            }
        }
        return writer.toString();
    }

    public static File stringToFile(String string, File file, Charset charset) throws IOException {
        StringReader reader = new StringReader(string);
        try (OutputStreamWriter fstm = new OutputStreamWriter(new FileOutputStream(file), charset)) {
            char[] buff = new char[1024];
            int len = 0;
            while ((len = reader.read(buff)) > 0) {
                fstm.write(buff, 0, len);
            }
        }
        return file;
    }

    public static String readAllString(Reader reader) throws IOException {
        StringWriter writer = new StringWriter();
        char[] buff = new char[1024];
        int len = 0;
        while ((len = reader.read(buff)) >= 0) {
            writer.write(buff, 0, len);
        }
        return writer.toString();
    }

    public static String appendFirstSeparator(String path, String separator) {
        if (path.startsWith(separator)) {
            return path;
        } else {
            return separator + path;
        }
    }

    public static String removeFirstSeparator(String path, String separator) {
        if (path.startsWith(separator)) {
            return path.substring(path.indexOf(separator) + separator.length());
        } else {
            return path;
        }
    }

    public static String appendLastSeparator(String path, String separator) {
        if (path.endsWith(separator)) {
            return path;
        } else {
            return path + separator;
        }
    }

    public static String removeLastSeparator(String path, String separator) {
        if (path.endsWith(separator)) {
            return path.substring(0, path.lastIndexOf(separator));
        } else {
            return path;
        }
    }

}
