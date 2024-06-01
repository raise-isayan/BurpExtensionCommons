package extension.burp;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.handler.HttpHandler;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.handler.RequestToBeSentAction;
import burp.api.montoya.http.handler.ResponseReceivedAction;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.proxy.http.InterceptedResponse;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;
import burp.api.montoya.proxy.http.ProxyResponseHandler;
import burp.api.montoya.proxy.http.ProxyResponseReceivedAction;
import burp.api.montoya.proxy.http.ProxyResponseToBeSentAction;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.InvocationType;
import burp.api.montoya.ui.contextmenu.MessageEditorHttpRequestResponse;
import burp.api.montoya.ui.editor.EditorOptions;
import extension.helpers.ConvertUtil;
import extension.helpers.StringUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;

/**
 *
 * @author isayan
 */
public class BurpUtil {

    private final static Logger logger = Logger.getLogger(BurpUtil.class.getName());

    public EditorOptions[] EDITOR_READ_ONLY = new EditorOptions[]{EditorOptions.READ_ONLY};

    public static boolean isLoadClass(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException ex) {
            return false;
        }
        return true;
    }

    public static Frame suiteFrame() {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame.isVisible() && frame.getTitle().startsWith("Burp Suite")) {
                return frame;
            }
        }
        return null;
    }

    public static JTabbedPane suiteTabbedPane() {
        Frame frame = suiteFrame();
        if (frame != null) {
            return findSuiteTabbedPane(frame);
        }
        return null;
    }

    protected static JTabbedPane findSuiteTabbedPane(Container container) {
        if (container instanceof JTabbedPane jTabbedPane) {
            if ("burpTabBar".equals(jTabbedPane.getName())) {
                return jTabbedPane;
            }
        }
        else {
            for (int i = 0; i < container.getComponentCount(); i++) {
                Component c = container.getComponent(i);
                if (c instanceof Container inner) {
                    JTabbedPane tabbed = findSuiteTabbedPane(inner);
                    if (tabbed != null) {
                        return tabbed;
                    }
                }
            }
        }
        return null;
    }

    public static BurpVersion suiteVersion() {
        Frame suiteFrame = suiteFrame();
        return new BurpVersion(suiteFrame.getTitle());
    }

    public static boolean isTemporaryProject() {
        Frame suiteFrame = BurpUtil.suiteFrame();
        String title = suiteFrame.getTitle();
        return (title.contains(" - Temporary Project"));
    }

    public static String[] splitFilterPattern(String pattern) {
        String[] extentions = pattern.split(",");
        return extentions;
    }

    public static String parseFilterPattern(String pattern) {
        String[] extentions = splitFilterPattern(pattern);
        StringBuilder buff = new StringBuilder();
        if (extentions.length == 1 && extentions[0].equals("")) {
            return buff.toString();
        }
        buff.append("\\.");
        buff.append("(");
        for (int i = 0; i < extentions.length; i++) {
            if (extentions[i].length() > 0) {
                if (i > 0) {
                    buff.append("|");
                }
                buff.append(extentions[i]);
            }
        }
        buff.append(")$");
        return buff.toString();
    }

    public static String copySelectionData(ContextMenuEvent contextMenu, boolean selectionTextOnly) {
        String text = null;
        InvocationType context = contextMenu.invocationType();
        if (contextMenu.messageEditorRequestResponse().isEmpty()) {
            return null;
        }
        MessageEditorHttpRequestResponse messageInfo = contextMenu.messageEditorRequestResponse().get();
        byte message[] = new byte[0];
        if (context == InvocationType.MESSAGE_EDITOR_REQUEST || context == InvocationType.MESSAGE_VIEWER_REQUEST) {
            message = messageInfo.requestResponse().request().toByteArray().getBytes();
        } else if (context == InvocationType.MESSAGE_EDITOR_RESPONSE || context == InvocationType.MESSAGE_EDITOR_RESPONSE) {
            message = messageInfo.requestResponse().response().toByteArray().getBytes();
        }
        if (message.length > 0) {
            if (messageInfo.selectionOffsets().isEmpty()) {
                if (!selectionTextOnly) {
                    text = StringUtil.getStringCharset(message, StandardCharsets.ISO_8859_1);
                }
            } else {
                Range range = messageInfo.selectionOffsets().get();
                text = StringUtil.getStringCharset(message, range.startIndexInclusive(), range.endIndexExclusive() - range.startIndexInclusive(), StandardCharsets.ISO_8859_1);
            }
        }
        return text;
    }

    public static void pasteSelectionData(ContextMenuEvent contextMenu, String text, boolean selectionTextOnly) {
        InvocationType context = contextMenu.invocationType();
        if (contextMenu.messageEditorRequestResponse().isEmpty()) {
            return;
        }
        MessageEditorHttpRequestResponse messageInfo = contextMenu.messageEditorRequestResponse().get();
        byte message[] = new byte[0];
        if (context == InvocationType.MESSAGE_EDITOR_REQUEST || context == InvocationType.MESSAGE_VIEWER_REQUEST) {
            message = messageInfo.requestResponse().request().toByteArray().getBytes();
        } else if (context == InvocationType.MESSAGE_EDITOR_RESPONSE || context == InvocationType.MESSAGE_VIEWER_RESPONSE) {
            message = messageInfo.requestResponse().response().toByteArray().getBytes();
        }
        if (message.length > 0) {
            Range range = Range.range(messageInfo.caretPosition(), messageInfo.caretPosition());
            if (messageInfo.selectionOffsets().isEmpty()) {
                if (selectionTextOnly) {
                    return;
                }
            } else {
                range = messageInfo.selectionOffsets().get();
            }
            String updateMessage = StringUtil.getStringRaw(ConvertUtil.replaceByte(message, range.startIndexInclusive(), range.endIndexExclusive(), StringUtil.getBytesRaw(text)));
            if (context == InvocationType.MESSAGE_EDITOR_REQUEST || context == InvocationType.MESSAGE_VIEWER_REQUEST) {
                messageInfo.setRequest(HttpRequest.httpRequest(messageInfo.requestResponse().httpService(), ByteArray.byteArray(updateMessage)));
            } else if (context == InvocationType.MESSAGE_EDITOR_RESPONSE || context == InvocationType.MESSAGE_VIEWER_RESPONSE) {
                messageInfo.setResponse(HttpResponse.httpResponse(ByteArray.byteArray(updateMessage)));
            }
        }
    }

    public static void flashTab(String caption) {
        JTabbedPane tab = BurpUtil.suiteTabbedPane();
        if (tab != null) {
            BurpUtil.flashTab(tab, caption);
        }
    }

    public static void flashTab(IBurpTab tab, String caption) {
        if (tab.getUiComponent() == null) {
            return;
        }
        Container container = tab.getUiComponent().getParent();
        if (container instanceof JTabbedPane jTabbedPane) {
            BurpUtil.flashTab(jTabbedPane, caption);
        }
    }

    public static void flashTab(JTabbedPane jTabbedPane, String caption) {
        final Color burpTextHighlightColor = BurpConfig.getTabFlashColor();
        final JTabbedPane tabbet = jTabbedPane;
        final int index = tabbet.indexOfTab(caption);
        if (index > -1) {
            tabbet.setBackgroundAt(index, burpTextHighlightColor);
            // 解除
            final Timer timer = new Timer(false);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    tabbet.setForegroundAt(index, null);
                    tabbet.setBackgroundAt(index, null);
                }
            };
            timer.schedule(task, 5000);
        }
    }

    /*
     *
     */
    public static String escapeRegex(String url) {
        StringBuilder builder = new StringBuilder();
        builder.append("^");
        for (int i = 0; i < url.length(); i = url.offsetByCodePoints(i, 1)) {
            int codePoint = url.codePointAt(i);
            if ('.' == (char) codePoint) {
                builder.append('\\');
            }
            builder.appendCodePoint(codePoint);
        }
        builder.append("$");
        return builder.toString();
    }

    public static String escapeRegexPath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append("^");
        for (int i = 0; i < path.length(); i = path.offsetByCodePoints(i, 1)) {
            int codePoint = path.codePointAt(i);
            if ('.' == (char) codePoint) {
                builder.append('\\');
            }
            builder.appendCodePoint(codePoint);
        }
        if (!path.endsWith("/")) {
            builder.append("/");
        }
        builder.append(".*");
        return builder.toString();
    }

    public static class HttpHandlerAdapter implements HttpHandler {

        @Override
        public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent httpRequestToBeSent) {
            return RequestToBeSentAction.continueWith(httpRequestToBeSent, httpRequestToBeSent.annotations());
        }

        @Override
        public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived httpResponseReceived) {
            return ResponseReceivedAction.continueWith(httpResponseReceived, httpResponseReceived.annotations());
        }
    }

    public static class ProxyHttpRequestHandlerAdapter implements ProxyRequestHandler {

        @Override
        public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
            return ProxyRequestReceivedAction.continueWith(interceptedRequest, interceptedRequest.annotations());
        }

        @Override
        public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
            return ProxyRequestToBeSentAction.continueWith(interceptedRequest, interceptedRequest.annotations());
        }

    }

    public static class ProxyHttpResponseHandlerAdapter implements ProxyResponseHandler {

        @Override
        public ProxyResponseReceivedAction handleResponseReceived(InterceptedResponse interceptedResponse) {
            return ProxyResponseReceivedAction.continueWith(interceptedResponse, interceptedResponse.annotations());
        }

        @Override
        public ProxyResponseToBeSentAction handleResponseToBeSent(InterceptedResponse interceptedResponse) {
            return ProxyResponseToBeSentAction.continueWith(interceptedResponse, interceptedResponse.annotations());
        }

    }

}
