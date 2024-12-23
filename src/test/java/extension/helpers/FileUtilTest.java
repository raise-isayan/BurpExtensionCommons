package extension.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author isayan
 */
public class FileUtilTest {

    public FileUtilTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testFileSize() throws IOException {
        System.out.println("testFileSize");
        File tmpPath = File.createTempFile("prefix", ".suffix");
        System.out.println("testFileSize:" + FileUtil.totalFileSize(tmpPath, false));
        assertEquals(0, FileUtil.totalFileSize(tmpPath, false));
        tmpPath.deleteOnExit();
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        System.out.println("tmpDir:" + tmpDir);
        System.out.println("tmpFileSize:" + FileUtil.totalFileSize(tmpDir, false));
        System.out.println("tmpFileSize(r):" + FileUtil.totalFileSize(tmpDir, true));
    }

    @Test
    public void testCreateEmptyZip() {
        try {
            System.out.println("createEmptyZip");
            File zipFile = FileUtil.createEmptyZip(new File(System.getProperty("java.io.tmpdir"), StringUtil.randomIdent(12) + ".zip"));
            assertTrue(zipFile.exists());
            zipFile.deleteOnExit();
        } catch (IOException ex) {
            Logger.getLogger(FileUtilTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of extractFileExtension method, of class FileUtil.
     */
    @Test
    public void testExtractFileExtension() {
        System.out.println("extractFileExtension");
        {
            String value = "test.png";
            String expResult = ".png";
            String result = FileUtil.extractFileExtension(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test.png.gif";
            String expResult = ".gif";
            String result = FileUtil.extractFileExtension(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test.x";
            String expResult = ".x";
            String result = FileUtil.extractFileExtension(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test";
            String expResult = "";
            String result = FileUtil.extractFileExtension(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test.";
            String expResult = "";
            String result = FileUtil.extractFileExtension(value);
            assertEquals(expResult, result);
        }
        {
            String value = ".test";
            String expResult = "";
            String result = FileUtil.extractFileExtension(value);
            assertEquals(expResult, result);
        }
        {
            String value = "";
            String expResult = "";
            String result = FileUtil.extractFileExtension(value);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of extractFileBaseName method, of class FileUtil.
     */
    @Test
    public void testExtractFileBaseName() {
        System.out.println("extractFileBaseName");
        {
            String value = "test.png";
            String expResult = "test";
            String result = FileUtil.extractFileBaseName(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test.png.gif";
            String expResult = "test.png";
            String result = FileUtil.extractFileBaseName(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test.x";
            String expResult = "test";
            String result = FileUtil.extractFileBaseName(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test";
            String expResult = "test";
            String result = FileUtil.extractFileBaseName(value);
            assertEquals(expResult, result);
        }
        {
            String value = "test.";
            String expResult = "test.";
            String result = FileUtil.extractFileBaseName(value);
            assertEquals(expResult, result);
        }
        {
            String value = ".test";
            String expResult = ".test";
            String result = FileUtil.extractFileBaseName(value);
            assertEquals(expResult, result);
        }
        {
            String value = "";
            String expResult = "";
            String result = FileUtil.extractFileBaseName(value);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of existsStartsDir method, of class FileUtil.
     */
    @Test
    public void testExistsStartsDir() {
        System.out.println("existsStartsDir");
        String resourcesPath = FileUtilTest.class.getResource("/resources/").getPath();
        File dir = new File(resourcesPath);
        String value = "burpextender";
        boolean expResult = true;
        boolean result = FileUtil.existsStartsDir(dir, value);
        assertEquals(expResult, result);
    }

    /**
     * Test of rotateFile method, of class FileUtil.
     */
    @Test
    public void testRotateFile() {
        System.out.println("rotateFile");
        String resourcesPath = FileUtilTest.class.getResource("/resources/").getPath();
        File dir = new File(resourcesPath);
        String value = "burpextender";
        File expResult = new File(resourcesPath, "burpextender-1");
        File result = FileUtil.rotateFile(dir, value);
        assertEquals(expResult, result);
    }

    /**
     * Test of bytesFromFile method, of class FileUtil.
     */
    @Test
    public void testBytesFromFile() throws Exception {
        System.out.println("bytesFromFile");
        String responseUTF8 = FileUtilTest.class.getResource("/resources/response.UTF-8").getPath();
        String result = FileUtil.stringFromFile(new File(responseUTF8), StandardCharsets.UTF_8);
        assertTrue(result.contains("あいうえお"));
    }

    /**
     * Test of readAllBytes method, of class FileUtil.
     */
    @Test
    public void testReadAllBytes() throws Exception {
        System.out.println("readAllBytes");
        InputStream streamUTF8 = FileUtilTest.class.getResourceAsStream("/resources/response.UTF-8");
        String result = StringUtil.getStringUTF8(FileUtil.readAllBytes(streamUTF8));
        assertTrue(result.contains("あいうえお"));
    }

    /**
     * Test of appendFirstSeparator method, of class FileUtil.
     */
    @Test
    public void testAppendFirstSeparator() {
        System.out.println("appendFirstSeparator");
        {
            String path = "test";
            String separator = "/";
            String expResult = "/test";
            String result = FileUtil.appendFirstSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "/test";
            String separator = "/";
            String expResult = "/test";
            String result = FileUtil.appendFirstSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "test/";
            String separator = "/";
            String expResult = "/test/";
            String result = FileUtil.appendFirstSeparator(path, separator);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of removeFirstSeparator method, of class FileUtil.
     */
    @Test
    public void testRemoveFirstSeparator() {
        System.out.println("removeFirstSeparator");
        {
            String path = "test";
            String separator = "/";
            String expResult = "test";
            String result = FileUtil.removeFirstSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "/test";
            String separator = "/";
            String expResult = "test";
            String result = FileUtil.removeFirstSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "/test/";
            String separator = "/";
            String expResult = "test/";
            String result = FileUtil.removeFirstSeparator(path, separator);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of appendLastSeparator method, of class FileUtil.
     */
    @Test
    public void testAppendLastSeparator() {
        System.out.println("appendLastSeparator");
        {
            String path = "test";
            String separator = "/";
            String expResult = "test/";
            String result = FileUtil.appendLastSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "test/";
            String separator = "/";
            String expResult = "test/";
            String result = FileUtil.appendLastSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "/test/";
            String separator = "/";
            String expResult = "/test/";
            String result = FileUtil.appendLastSeparator(path, separator);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of removeLastSeparator method, of class FileUtil.
     */
    @Test
    public void testRemoveLastSeparator() {
        System.out.println("removeLastSeparator");
        {
            String path = "test";
            String separator = "/";
            String expResult = "test";
            String result = FileUtil.removeLastSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "test/";
            String separator = "/";
            String expResult = "test";
            String result = FileUtil.removeLastSeparator(path, separator);
            assertEquals(expResult, result);
        }
        {
            String path = "/test/";
            String separator = "/";
            String expResult = "/test";
            String result = FileUtil.removeLastSeparator(path, separator);
            assertEquals(expResult, result);
        }
    }

}
