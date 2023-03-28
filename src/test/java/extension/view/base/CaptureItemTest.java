package extension.view.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isayan
 */
public class CaptureItemTest {

    public CaptureItemTest() {
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
     * Test of isCapture method, of class CaptureItem.
     */
    @Test
    public void testIsCapture() {
        System.out.println("isCapture");
        CaptureItem instance = new CaptureItem();
        {
            instance.setStart(10);
            instance.setEnd(9);
            boolean expResult = false;
            boolean result = instance.isCapture();
            assertEquals(expResult, result);
        }
        {
            instance.setStart(10);
            instance.setEnd(10);
            boolean expResult = false;
            boolean result = instance.isCapture();
            assertEquals(expResult, result);
        }
        {
            instance.setStart(10);
            instance.setEnd(11);
            boolean expResult = true;
            boolean result = instance.isCapture();
            assertEquals(expResult, result);
        }
    }


}
