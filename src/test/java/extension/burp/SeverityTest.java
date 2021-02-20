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
public class SeverityTest {
    
    public SeverityTest() {
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
     * Test of parseEnum method, of class Severity.
     */
    @Test
    public void testParseEnum() {
        System.out.println("parseEnum");
        String s = "LOW";
        Severity expResult = Severity.LOW;
        Severity result = Severity.parseEnum(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseEnumSet method, of class Severity.
     */
    @Test
    public void testParseEnumSet() {
        System.out.println("parseEnumSet");
        {
            String s = "[\"HIGH\",\"MEDIUM\",\"LOW\"]";
            EnumSet<Severity> expResult = EnumSet.of(Severity.HIGH, Severity.MEDIUM, Severity.LOW);
            EnumSet<Severity> result = Severity.parseEnumSet(s);
            assertEquals(expResult, result);        
        }
    }

    /**
     * Test of toString method, of class Severity.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        EnumSet<Severity> instance = EnumSet.allOf(Severity.class);
        for (Severity e : instance) {
            System.out.println("name:" + e.name());
            assertEquals(e, Severity.parseEnum(e.name()));
        }
        for (Severity e : Severity.values()) {
            System.out.println("value:" + e.toString());            
            assertEquals(e, Severity.parseEnum(e.toString()));
        }
        System.out.println(instance.toString());
    }
    
}
