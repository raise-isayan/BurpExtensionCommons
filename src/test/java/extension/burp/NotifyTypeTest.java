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
public class NotifyTypeTest {
    
    public NotifyTypeTest() {
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
        String s = "ALERTS_TAB";
        NotifyType expResult = NotifyType.ALERTS_TAB;
        NotifyType result = NotifyType.parseEnum(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseEnumSet method, of class NotifyType.
     */
    @Test
    public void testParseEnumSet() {
        System.out.println("parseEnumSet");
        String s = "[\"ALERTS_TAB\",\"ITEM_HIGHLIGHT\",\"COMMENT\"]";
        EnumSet<NotifyType> expResult = EnumSet.of(NotifyType.ALERTS_TAB, NotifyType.ITEM_HIGHLIGHT, NotifyType.COMMENT);
        EnumSet<NotifyType> result = NotifyType.parseEnumSet(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Severity.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        EnumSet<NotifyType> instance = EnumSet.allOf(NotifyType.class);
        for (NotifyType e : instance) {
            System.out.println("name:" + e.name());
            assertEquals(e, NotifyType.parseEnum(e.name()));
        }
        
        for (NotifyType e : NotifyType.values()) {
            System.out.println("value:" + e.toString());            
            assertEquals(e, NotifyType.parseEnum(e.toString()));
        }
        System.out.println(instance.toString());
    }
    
}
