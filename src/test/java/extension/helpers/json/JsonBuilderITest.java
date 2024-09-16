package extension.helpers.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author isayan
 */
public class JsonBuilderITest {

    public JsonBuilderITest() {
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
     * Test of createObjectBuilder method, of class JsonBuilder.
     */
    @Test
    public void testCreateObjectBuilder() {
        System.out.println("createObjectBuilder");
        JsonObjectBuilder result = JsonBuilder.createObjectBuilder();
        result.add("aaa", "zzz").add("bbb", "yyy").add("ccc", "zzz").add("ddd", 111).add("eee", false);
        System.out.println(result.build());
    }

    /**
     * Test of createArrayBuilder method, of class JsonBuilder.
     */
    @Test
    public void testCreateArrayBuilder() {
        System.out.println("createArrayBuilder");
        {
            JsonArrayBuilder result = JsonBuilder.createArrayBuilder();
            result.add("1").add("2").add("3");
            System.out.println(result.build());
        }
        {
            JsonArrayBuilder result = JsonBuilder.createArrayBuilder();
            result.add(7).add(8).add(9);
            System.out.println(result.build());
        }
    }

    /**
     * Test of JsonBuilder method, of class JsonBuilder.
     */
    @Test
    public void testBuilder() {
        System.out.println("testBuilder");
        {
            JsonObjectBuilder result = JsonBuilder.createObjectBuilder();
            result.add("owner", JsonBuilder.createObjectBuilder().add("abc", "edf").build());
            System.out.println(result.build());
        }
        {
            JsonObjectBuilder result = JsonBuilder.createObjectBuilder();
            result.add("owner", JsonBuilder.createArrayBuilder().add("abc").add("def").build());
            System.out.println(result.build());
        }
    }

}
