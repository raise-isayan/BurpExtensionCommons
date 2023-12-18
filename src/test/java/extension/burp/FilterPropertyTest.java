/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package extension.burp;

import java.util.EnumSet;
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
public class FilterPropertyTest {

    public FilterPropertyTest() {
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
     * Test of build method, of class FilterProperty.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        {
            FilterProperty instance = new FilterProperty();
            String result = instance.build();
            assertEquals("return true;", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setListenerPort(8080);
            String result = instance.build();
            assertEquals("return requestResponse.listenerPort() == 8080;", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setShowOnlyScopeItems(true);
            String result = instance.build();
            assertEquals("return requestResponse.request().isInScope();", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setShowOnlyParameterizedRequests(true);
            String result = instance.build();
            assertEquals("return (requestResponse.request().hasParameters(HttpParameterType.URL) || requestResponse.request().hasParameters(HttpParameterType.BODY));", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setShowOnlyScopeItems(true);
            instance.setHideItemsWithoutResponses(true);
            String result = instance.build();
            assertEquals("return requestResponse.request().isInScope()\n" + " && requestResponse.hasResponse();", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setStat2xx(false);
            instance.setStat3xx(false);
            instance.setStat4xx(false);
            instance.setStat5xx(false);
            String result = instance.build();
            assertEquals("return !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_2XX_SUCCESS))\n" +
                " && !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_3XX_REDIRECTION))\n" +
                " && !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_4XX_CLIENT_ERRORS))\n" +
                " && !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_5XX_SERVER_ERRORS));", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            EnumSet<MessageHighlightColor> colors = EnumSet.allOf(MessageHighlightColor.class);
            instance.setShowOnlyHighlightColors(true);
            instance.setHighlightColors(colors);
            String result = instance.build();
            assertEquals("return requestResponse.annotations().highlightColor().equals(HighlightColor.NONE)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.RED)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.ORANGE)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.YELLOW)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.GREEN)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.CYAN)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.BLUE)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.PINK)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.MAGENTA)\n" +
                " && requestResponse.annotations().highlightColor().equals(HighlightColor.GRAY);", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setMethod("OPTIONS");
            String result = instance.build();
            assertEquals("return requestResponse.request().method().toUpperCase().equals(\"OPTIONS\");", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setPath("/path");
            String result = instance.build();
            assertEquals("return requestResponse.request().path().contains(\"/path\");", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setPath("/path");
            String result = instance.build();
            assertEquals("return requestResponse.request().path().contains(\"/path\");", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setShowOnly(true);
            String result = instance.build();
            assertEquals("return ((Predicate<String>)((path)->{ return path.endsWith(\".asp\")\n" +
                " || path.endsWith(\".aspx\")\n" +
                " || path.endsWith(\".jsp\")\n" +
                " || path.endsWith(\".php\"); })).test(requestResponse.request().pathWithoutQuery().toLowerCase());", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setHide(true);
            String result = instance.build();
            assertEquals("return ((Predicate<String>)((path)->{ return !path.endsWith(\".js\")\n" +
                " && !path.endsWith(\".gif\")\n" +
                " && !path.endsWith(\".jpg\")\n" +
                " && !path.endsWith(\".png\")\n" +
                " && !path.endsWith(\".css\"); })).test(requestResponse.request().pathWithoutQuery().toLowerCase());", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setShowOnly(true);
            instance.setHide(true);
            String result = instance.build();
            System.out.println(result);
//            assertEquals("return requestResponse.request().isInScope();", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setRequest("www");
            String result = instance.build();
            assertEquals("return requestResponse.request().contains(\"www\", false);", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setRequest("www");
            instance.setRequestRegex(true);
            instance.setRequestIgnoreCase(false);
            String result = instance.build();
            System.out.println(result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setResponse("www");
            String result = instance.build();
            assertEquals("return requestResponse.response().contains(\"www\", false);", result);
        }
        {
            FilterProperty instance = new FilterProperty();
            instance.setResponse("www");
            instance.setResponseRegex(true);
            instance.setResponseIgnoreCase(false);
            String result = instance.build();
            System.out.println(result);
        }
    }

}
