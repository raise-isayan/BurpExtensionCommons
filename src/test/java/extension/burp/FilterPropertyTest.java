package extension.burp;

import extension.burp.FilterProperty.FilterCategory;
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
     * Test of HttpBuild method, of class FilterProperty.
     */
    @Test
    public void testHttpBuild() {
        System.out.println("HttpBuild");
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            String result = instance.build();
            assertEquals("return true;", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setListenerPort(8080);
            String result = instance.build();
            assertEquals("return requestResponse.listenerPort() == 8080;", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setShowOnlyScopeItems(true);
            String result = instance.build();
            assertEquals("return requestResponse.request().isInScope();", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setShowOnlyParameterizedRequests(true);
            String result = instance.build();
            assertEquals("return (requestResponse.request().hasParameters(HttpParameterType.URL) || requestResponse.request().hasParameters(HttpParameterType.BODY));", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setShowOnlyScopeItems(true);
            instance.setHideItemsWithoutResponses(true);
            String result = instance.build();
            assertEquals("return requestResponse.request().isInScope()\n" + " && requestResponse.hasResponse();", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setStat2xx(false);
            instance.setStat3xx(false);
            instance.setStat4xx(false);
            instance.setStat5xx(false);
            String result = instance.build();
            assertEquals("return !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_2XX_SUCCESS))\n"
                    + " && !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_3XX_REDIRECTION))\n"
                    + " && !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_4XX_CLIENT_ERRORS))\n"
                    + " && !(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_5XX_SERVER_ERRORS));", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setShowOnlyComment(true);
            String result = instance.build();
            assertEquals("return requestResponse.annotations().hasNotes();", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            EnumSet<MessageHighlightColor> colors = EnumSet.allOf(MessageHighlightColor.class);
            instance.setShowOnlyHighlightColors(true);
            instance.setHighlightColors(colors);
            String result = instance.build();
            assertEquals("return ((Predicate<HighlightColor>)((color)->{ return color.equals(HighlightColor.NONE)\n"
                    + " || color.equals(HighlightColor.RED)\n"
                    + " || color.equals(HighlightColor.ORANGE)\n"
                    + " || color.equals(HighlightColor.YELLOW)\n"
                    + " || color.equals(HighlightColor.GREEN)\n"
                    + " || color.equals(HighlightColor.CYAN)\n"
                    + " || color.equals(HighlightColor.BLUE)\n"
                    + " || color.equals(HighlightColor.PINK)\n"
                    + " || color.equals(HighlightColor.MAGENTA)\n"
                    + " || color.equals(HighlightColor.GRAY); })).test(requestResponse.annotations().highlightColor());", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            EnumSet<MessageHighlightColor> colors = EnumSet.allOf(MessageHighlightColor.class);
            instance.setShowOnlyHighlightColors(false);
            instance.setHighlightColors(colors);
            String result = instance.build();
            assertEquals("return true;", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setMethod("OPTIONS");
            String result = instance.build();
            assertEquals("return requestResponse.request().method().toUpperCase().equals(\"OPTIONS\");", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setPath("/path");
            String result = instance.build();
            assertEquals("return requestResponse.request().path().contains(\"/path\");", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setPath("/path");
            String result = instance.build();
            assertEquals("return requestResponse.request().path().contains(\"/path\");", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setShowOnly(true);
            String result = instance.build();
            assertEquals("return ((Predicate<String>)((path)->{ return path.endsWith(\".asp\")\n"
                    + " || path.endsWith(\".aspx\")\n"
                    + " || path.endsWith(\".jsp\")\n"
                    + " || path.endsWith(\".php\"); })).test(requestResponse.request().pathWithoutQuery().toLowerCase());", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setHide(true);
            String result = instance.build();
            assertEquals("return ((Predicate<String>)((path)->{ return !path.endsWith(\".js\")\n"
                    + " && !path.endsWith(\".gif\")\n"
                    + " && !path.endsWith(\".jpg\")\n"
                    + " && !path.endsWith(\".png\")\n"
                    + " && !path.endsWith(\".css\"); })).test(requestResponse.request().pathWithoutQuery().toLowerCase());", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setRequest("www");
            String result = instance.build();
            assertEquals("return requestResponse.request().contains(\"www\", false);", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setRequest("www");
            instance.setRequestRegex(true);
            instance.setRequestIgnoreCase(false);
            String result = instance.build();
            assertEquals("return requestResponse.request().contains(Pattern.compile(\"www\", Pattern.DOTALL | Pattern.CASE_INSENSITIVE));", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setResponse("www");
            String result = instance.build();
            assertEquals("return requestResponse.response().contains(\"www\", false);", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.HTTP);
            instance.setResponse("www");
            instance.setResponseRegex(true);
            instance.setResponseIgnoreCase(false);
            String result = instance.build();
            assertEquals("return requestResponse.response().contains(Pattern.compile(\"www\", Pattern.DOTALL | Pattern.CASE_INSENSITIVE));", result);
        }
    }

    /**
     * Test of WebSocketBuild method, of class FilterProperty.
     */
    @Test
    public void testWebSocketBuild() {
        System.out.println("WebSocketBuild");
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            String result = instance.build();
            assertEquals("return true;", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setListenerPort(8080);
            String result = instance.build();
            assertEquals("return message.listenerPort() == 8080;", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setShowOnlyScopeItems(true);
            String result = instance.build();
            assertEquals("return message.upgradeRequest().isInScope();", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setHideIncomingMessage(true);
            String result = instance.build();
            assertEquals("return message.direction() != Direction.SERVER_TO_CLIENT;", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setHideOutgoingMessage(true);
            String result = instance.build();
            assertEquals("return message.direction() != Direction.CLIENT_TO_SERVER;", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setMessage("wsmsg");
            String result = instance.build();
            assertEquals("return message.contains(\"wsmsg\", false);", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setMessage("wsmsg");
            instance.setMessageIgnoreCase(true);
            String result = instance.build();
            assertEquals("return message.contains(\"wsmsg\", true);", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setMessage("wsmsg");
            instance.setMessageRegex(true);
            String result = instance.build();
            assertEquals("return message.contains(Pattern.compile(\"wsmsg\", Pattern.DOTALL | Pattern.CASE_INSENSITIVE));", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setMessage("wsmsg");
            instance.setMessageRegex(true);
            instance.setMessageIgnoreCase(true);
            String result = instance.build();
            assertEquals("return message.contains(Pattern.compile(\"wsmsg\", Pattern.DOTALL));", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            instance.setShowOnlyComment(true);
            String result = instance.build();
            assertEquals("return message.annotations().hasNotes();", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            EnumSet<MessageHighlightColor> colors = EnumSet.allOf(MessageHighlightColor.class);
            instance.setShowOnlyHighlightColors(true);
            instance.setHighlightColors(colors);
            String result = instance.build();
            assertEquals("return ((Predicate<HighlightColor>)((color)->{ return color.equals(HighlightColor.NONE)\n"
                    + " || color.equals(HighlightColor.RED)\n"
                    + " || color.equals(HighlightColor.ORANGE)\n"
                    + " || color.equals(HighlightColor.YELLOW)\n"
                    + " || color.equals(HighlightColor.GREEN)\n"
                    + " || color.equals(HighlightColor.CYAN)\n"
                    + " || color.equals(HighlightColor.BLUE)\n"
                    + " || color.equals(HighlightColor.PINK)\n"
                    + " || color.equals(HighlightColor.MAGENTA)\n"
                    + " || color.equals(HighlightColor.GRAY); })).test(message.annotations().highlightColor());", result);
        }
        {
            FilterProperty instance = new FilterProperty(FilterCategory.WEBSOCKET);
            EnumSet<MessageHighlightColor> colors = EnumSet.allOf(MessageHighlightColor.class);
            instance.setShowOnlyHighlightColors(false);
            instance.setHighlightColors(colors);
            String result = instance.build();
            assertEquals("return true;", result);
        }

    }

}
