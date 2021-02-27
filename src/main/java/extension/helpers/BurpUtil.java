package extension.helpers;

import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;
import burp.ITab;
import java.awt.Color;
import java.awt.Container;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTabbedPane;

/**
 *
 * @author isayan
 */
public class BurpUtil {

    public static String parseFilterPattern(String pattern) {
        String[] extentions = pattern.split(",");
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

    public static String copySelectionData(IContextMenuInvocation contextMenu, boolean selectionTextOnly) {
        String text = null;
        byte context = contextMenu.getInvocationContext();
        int se[] = contextMenu.getSelectionBounds();
        if (se == null && selectionTextOnly) {
            return null;
        }
        IHttpRequestResponse[] messageInfo = contextMenu.getSelectedMessages();
        byte message[] = new byte[0];
        if (context == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_REQUEST || context == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_REQUEST) {
            message = messageInfo[0].getRequest();
        } else if (context == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_RESPONSE || context == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_RESPONSE) {
            message = messageInfo[0].getResponse();
        }
        if (message != null) {
            if (se == null) {
                text = StringUtil.getStringRaw(message);
            } else {
                text = StringUtil.getStringCharset(message, se[0], se[1] - se[0], StandardCharsets.ISO_8859_1);
            }
        }
        return text;
    }

    public static void pasteSelectionData(IContextMenuInvocation contextMenu, String text, boolean selectionTextOnly) {
        int se[] = contextMenu.getSelectionBounds();
        byte context = contextMenu.getInvocationContext();
        if (se == null && selectionTextOnly) {
            return;
        }
        IHttpRequestResponse[] messageInfo = contextMenu.getSelectedMessages();
        byte message[] = new byte[0];
        if (context == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_REQUEST || context == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_REQUEST) {
            message = messageInfo[0].getRequest();
        } else if (context == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_RESPONSE || context == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_RESPONSE) {
            message = messageInfo[0].getResponse();
        }
        if (message != null) {
            if (se == null) {
                // nothing
            } else {
                text = StringUtil.getStringRaw(ConvertUtil.replaceByte(message, se[0], se[1], StringUtil.getBytesRaw(text)));
            }
        }
        if (context == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_REQUEST || context == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_REQUEST) {
            messageInfo[0].setRequest(StringUtil.getBytesRaw(text));
        } else if (context == IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_RESPONSE || context == IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_RESPONSE) {
            messageInfo[0].setResponse(StringUtil.getBytesRaw(text));
        }
    }

    public static void sendToTextHighlight(ITab tab) {
        final Color burpTextHighlightColor = new Color(255, 102, 52);
        if (tab.getUiComponent() == null) return;
        Container container = tab.getUiComponent().getParent();
        if (container instanceof JTabbedPane) {
            JTabbedPane tabbet = (JTabbedPane) container;
            int index = tabbet.indexOfTab(tab.getTabCaption());
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
    }

}
