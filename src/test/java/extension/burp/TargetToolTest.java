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
public class TargetToolTest {
    
    public TargetToolTest() {
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
     * Test of parseEnum method, of class TargetTool.
     */
    @Test
    public void testParseEnum() {
        System.out.println("parseEnum");
        String s = "PROXY";
        TargetTool expResult = TargetTool.PROXY;
        TargetTool result = TargetTool.parseEnum(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseEnumSet method, of class Severity.
     */
    @Test
    public void testParseEnumSet() {
        System.out.println("parseEnumSet");
        {
            String s = "[\"PROXY\",\"REPEATER\",\"SCANNER\"]";
            EnumSet<TargetTool> expResult = EnumSet.of(TargetTool.PROXY, TargetTool.REPEATER, TargetTool.SCANNER);
            EnumSet<TargetTool> result = TargetTool.parseEnumSet(s);
            assertEquals(expResult, result);        
        }
    }

    /**
     * Test of toString method, of class Severity.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        EnumSet<TargetTool> instance = EnumSet.allOf(TargetTool.class);
        for (TargetTool e : instance) {
            System.out.println("name:" + e.name());
            assertEquals(e, TargetTool.parseEnum(e.name()));
        }
        for (TargetTool e : TargetTool.values()) {
            System.out.println("value:" + e.toString());            
            assertEquals(e, TargetTool.parseEnum(e.toString()));
        }
        System.out.println(instance.toString());
    }
    
}
