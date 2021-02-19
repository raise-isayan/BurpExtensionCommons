package extension.burp;

import java.awt.Color;
import java.util.EnumSet;
import javax.swing.ImageIcon;
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
     * Test of valueOf method, of class HighlightColor.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        {
            String arg0 = EnumSet.noneOf(HighlightColor.class).toString();
            EnumSet<HighlightColor> e = HighlightColor.parseEnumSet(arg0);
            assertEquals(EnumSet.noneOf(HighlightColor.class), e);        
        }
        {
            String arg0 = "[ ]";
            EnumSet<HighlightColor> e = HighlightColor.parseEnumSet(arg0);
            assertEquals(EnumSet.noneOf(HighlightColor.class), e);        
        }
        {
            String arg0 = EnumSet.of(HighlightColor.RED).toString();
            EnumSet<HighlightColor> e = HighlightColor.parseEnumSet(arg0);
            assertEquals(EnumSet.of(HighlightColor.RED), e);        
        }
        {
            String arg0 = EnumSet.of(HighlightColor.RED, HighlightColor.GREEN).toString();
            EnumSet<HighlightColor> e = HighlightColor.parseEnumSet(arg0);
            assertEquals(EnumSet.of(HighlightColor.RED, HighlightColor.GREEN), e);        
        }
        {
            String arg0 = EnumSet.allOf(HighlightColor.class).toString();
            EnumSet<HighlightColor> e = HighlightColor.parseEnumSet(arg0);
            assertEquals(EnumSet.allOf(HighlightColor.class), e);        
        }
        
    }    
}
