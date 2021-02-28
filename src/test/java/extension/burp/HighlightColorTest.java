package extension.burp;

import java.util.EnumSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author isayan
 */
public class HighlightColorTest {

    public HighlightColorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseEnum method, of class NotifyType.
     */
    @Test
    public void testParseEnum() {
        System.out.println("parseEnum");
        {
            String s = null;
            HighlightColor expResult = HighlightColor.WHITE;
            HighlightColor result = HighlightColor.parseEnum(s);
            assertEquals(expResult, result);
        }
        {
            String s = "RED";
            HighlightColor expResult = HighlightColor.RED;
            HighlightColor result = HighlightColor.parseEnum(s);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of parseEnumSet method, of class NotifyType.
     */
    @Test
    public void testParseEnumSet() {
        System.out.println("parseEnumSet");
        String s = "[\"WHITE\",\"BLUE\",\"YELLOW\"]";
        EnumSet<HighlightColor> expResult = EnumSet.of(HighlightColor.WHITE, HighlightColor.BLUE, HighlightColor.YELLOW);
        EnumSet<HighlightColor> result = HighlightColor.parseEnumSet(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Severity.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        EnumSet<HighlightColor> instance = EnumSet.allOf(HighlightColor.class);
        for (HighlightColor e : instance) {
            System.out.println("name:" + e.name());
            assertEquals(e, HighlightColor.parseEnum(e.name()));
        }

        for (HighlightColor e : HighlightColor.values()) {
            System.out.println("value:" + e.toString());
            assertEquals(e, HighlightColor.parseEnum(e.toString()));
        }
        System.out.println(instance.toString());
    }

}
