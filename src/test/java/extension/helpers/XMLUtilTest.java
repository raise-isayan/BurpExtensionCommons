package extension.helpers;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author isayan
 */
public class XMLUtilTest {

    public XMLUtilTest() {
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

    /**
     * Test of toXPath method, of class XMLUtil.
     */
    @Test
    public void testToXPath() throws SAXException {
        try {
            System.out.println("toXPath");
            String xml = "<root><a><b>value1</b><b>value2</b></a></root>";
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            String expResult = "/root/a/b[2]";
            String result = XMLUtil.toXPath(doc.getElementsByTagName("b").item(1));
            System.out.println("toXPath:" + result);
            assertEquals(expResult, result);
        } catch (ParserConfigurationException ex) {
            fail(ex);
        } catch (IOException ex) {
            fail(ex);
        }
    }

}
