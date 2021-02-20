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
public class ConfidenceTest {
    
    public ConfidenceTest() {
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
        String s = "CERTAIN";
        Confidence expResult = Confidence.CERTAIN;
        Confidence result = Confidence.parseEnum(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseEnumSet method, of class Severity.
     */
    @Test
    public void testParseEnumSet() {
        System.out.println("parseEnumSet");
        {
            String s = "[\"CERTAIN\",\"FIRM\",\"TENTATIVE\"]";
            EnumSet<Confidence> expResult = EnumSet.of(Confidence.CERTAIN, Confidence.FIRM, Confidence.TENTATIVE);
            EnumSet<Confidence> result = Confidence.parseEnumSet(s);
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
