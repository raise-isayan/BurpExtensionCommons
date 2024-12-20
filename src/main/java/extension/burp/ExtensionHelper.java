package extension.burp;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.ToolSource;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.EditorMode;
import extension.helpers.HttpUtil;
import extension.helpers.StringUtil;
import extension.helpers.SwingUtil;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author isayan
 */
public class ExtensionHelper {

    private final static Logger logger = Logger.getLogger(ExtensionHelper.class.getName());
    private final MontoyaApi api;

    public ExtensionHelper(MontoyaApi api) {
        this.api = api;
    }

    public void applyThemeToComponent(Component component) {
        api.userInterface().applyThemeToComponent(component);
    }

    public boolean isInScope(URL url) {
        return isInScope(url.toString());
    }

    public boolean isInScope(String url) {
        if (api != null) {
            return api.scope().isInScope(url);
        } else {
            throw new NullPointerException();
        }
    }

    public void outPrintln(String message) {
        outPrint(message + "\n");
    }

    public void outPrint(String message) {
        try {
            if (api != null) {
                api.logging().logToOutput(message);
            } else {
                System.out.print(message);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void errPrintln(String message) {
        errPrint(message + "\n");
    }

    public void errPrint(String message) {
        try {
            if (api != null) {
                api.logging().logToError(message);
            } else {
                System.err.print(message);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * burp alert 通知
     *
     * @param caption キャプション
     * @param text テキスト
     * @param messageType メッセージタイプ
     */
    public void issueAlert(String caption, String text, MessageType messageType) {
        String msg = String.format("%s:%s", caption, text);
        switch (messageType) {
            case CRITICAL:
                this.api.logging().raiseCriticalEvent(msg);
                break;
            case ERROR:
                this.api.logging().raiseErrorEvent(msg);
                break;
            case DEBUG:
                this.api.logging().raiseDebugEvent(msg);
                break;
            case INFO:
                this.api.logging().raiseInfoEvent(msg);
                break;
        }
    }

    /**
     * Add Host To Scope
     *
     * @param contextMenuEvent
     */
    public void addHostIncludeScope(ContextMenuEvent contextMenuEvent) {
        final List<HttpRequestResponse> messageList = contextMenuEvent.selectedRequestResponses();
        for (HttpRequestResponse messageInfo : messageList) {
            this.api.scope().includeInScope(HttpTarget.toURLString(messageInfo.request().httpService()));
        }
    }

    /**
     * Add Host To Exclude Scope
     *
     * @param contextMenuEvent
     */
    public void addHostExcludeScope(ContextMenuEvent contextMenuEvent) {
        final List<HttpRequestResponse> messageList = contextMenuEvent.selectedRequestResponses();
        for (HttpRequestResponse messageInfo : messageList) {
            this.api.scope().excludeFromScope(HttpTarget.toURLString(messageInfo.request().httpService()));
        }
    }

    /**
     * Add Url To Include Scope
     *
     * @param contextMenuEvent
     */
    public void addIncludeScope(ContextMenuEvent contextMenuEvent) {
        final List<HttpRequestResponse> messageList = contextMenuEvent.selectedRequestResponses();
        for (HttpRequestResponse messageInfo : messageList) {
            this.api.scope().includeInScope(messageInfo.request().url());
        }
    }

    /**
     * Add Url To Exclude Scope
     *
     * @param contextMenuEvent
     */
    public void addExcludeScope(ContextMenuEvent contextMenuEvent) {
        final List<HttpRequestResponse> messageList = contextMenuEvent.selectedRequestResponses();
        for (HttpRequestResponse messageInfo : messageList) {
            this.api.scope().excludeFromScope(messageInfo.request().url());
        }
    }

    /**
     * Add Url To Include Scope
     *
     * @param multilineURL
     */
    public void addIncludeScope(String multilineURL) {
        String[] urls = HttpUtil.parseMultiLineURL(multilineURL, false);
        for (String u : urls) {
            this.api.scope().includeInScope(u);
        }
    }

    /**
     * Add Url To Include Scope
     *
     * @param multilineURL
     */
    public void addExcludeScope(String multilineURL) {
        String[] urls = HttpUtil.parseMultiLineURL(multilineURL, false);
        for (String u : urls) {
            this.api.scope().excludeFromScope(u);
        }
    }

    /**
     * Add Url To Include Scope
     *
     * @param multilineURL
     */
    public void addNetlocIncludeScope(String multilineURL) {
        String[] urls = HttpUtil.parseMultiLineTopURL(multilineURL, false);
        for (String u : urls) {
            this.api.scope().includeInScope(u);
        }
    }

    /**
     * Add Url To Include Scope
     *
     * @param multilineURL
     */
    public void addHostExcludeScope(String multilineURL) {
        String[] urls = HttpUtil.parseMultiLineTopURL(multilineURL, false);
        for (String u : urls) {
            this.api.scope().excludeFromScope(u);
        }
    }

    /**
     * Add Url To Include Scope
     *
     * @param multilineURL
     * @param ignoreExists
     */
    public void addIncludeTargetScope(String multilineURL, boolean ignoreExists) {
        String[] urls = HttpUtil.parseMultiLineURL(multilineURL, true);
        addIncludeTargetScope(urls, ignoreExists);
    }

    /**
     * Add Url To Include Scope
     *
     * @param multilineURL
     * @param ignoreExists
     */
    public void addIncludeTopURLTargetScope(String multilineURL, boolean ignoreExists) {
        String[] urls = HttpUtil.parseMultiLineTopURL(multilineURL, true);
        addIncludeTargetScope(urls, ignoreExists);
    }

    /**
     * Add Url To Include Scope
     *
     * @param urls
     * @param ignoreExists
     */
    public void addIncludeTargetScope(String[] urls, boolean ignoreExists) {
        BurpConfig.TargetScope targetScope = BurpConfig.getTargetScope(this.api);
        List<BurpConfig.TargetScopeURL> targetURL = targetScope.getIncludeURL();
        List<BurpConfig.TargetScopeAdvance> targetAdvance = targetScope.getIncludeAdvance();
        for (String u : urls) {
            try {
                if (targetScope.isAdvancedMode()) {
                    BurpConfig.TargetScopeAdvance target = BurpConfig.TargetScopeAdvance.parseTargetURL(u);
                    if ((ignoreExists && targetAdvance.contains(target)) || !targetAdvance.contains(target)) {
                        targetAdvance.add(target);
                    }
                } else {
                    BurpConfig.TargetScopeURL target = BurpConfig.TargetScopeURL.parseTargetURL(u);
                    if ((ignoreExists && targetURL.contains(target)) || !targetURL.contains(target)) {
                        targetURL.add(target);
                    }
                }
            } catch (MalformedURLException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        BurpConfig.configTargetScope(this.api, targetScope);
    }

    /**
     * Add Url To Exclude Scope
     *
     * @param multilineURL
     * @param ignoreExists
     */
    public void addExcludeTargetScope(String multilineURL, boolean ignoreExists) {
        String[] urls = HttpUtil.parseMultiLineURL(multilineURL, true);
        addExcludeTargetScope(urls, ignoreExists);
    }

    /**
     * Add Url To Exclude Scope
     *
     * @param urls
     * @param ignoreExists
     */
    protected void addExcludeTargetScope(String[] urls, boolean ignoreExists) {
        BurpConfig.TargetScope targetScope = BurpConfig.getTargetScope(this.api);
        List<BurpConfig.TargetScopeURL> targetURL = targetScope.getExcludeURL();
        List<BurpConfig.TargetScopeAdvance> targetAdvance = targetScope.getExcludeAdvance();
        for (String u : urls) {
            try {
                if (targetScope.isAdvancedMode()) {
                    BurpConfig.TargetScopeAdvance target = BurpConfig.TargetScopeAdvance.parseTargetURL(u);
                    if ((ignoreExists && targetAdvance.contains(target)) || !targetAdvance.contains(target)) {
                        targetAdvance.add(target);
                    }
                } else {
                    BurpConfig.TargetScopeURL target = BurpConfig.TargetScopeURL.parseTargetURL(u);
                    if ((ignoreExists && targetURL.contains(target)) || !targetURL.contains(target)) {
                        targetURL.add(target);
                    }
                }
            } catch (MalformedURLException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        BurpConfig.configTargetScope(this.api, targetScope);
    }

    /**
     * Message Info Copy
     *
     * @param contextMenuEvent
     */
    public void sendToMessageInfoCopy(ContextMenuEvent contextMenuEvent) {
        final List<HttpRequestResponse> messageList = contextMenuEvent.selectedRequestResponses();
        StringBuilder buff = new StringBuilder();
        buff.append("url\tquery\tmethod\tstatus\tlength").append(System.lineSeparator());
        for (HttpRequestResponse messageInfo : messageList) {
//                IRequestInfo reqInfo = api.getHelpers().analyzeRequest(messageInfo);
            URI uri = URI.create(messageInfo.request().url());
            buff.append(HttpUtil.toURL(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getPath()));
            buff.append("\t");
            buff.append(uri.getQuery());
            buff.append("\t");
            buff.append(messageInfo.request().method());
            if (messageInfo.response() != null) {
                buff.append("\t");
                buff.append(messageInfo.response().statusCode());
                buff.append("\t");
                buff.append(messageInfo.response().body().length());
            }
            buff.append(System.lineSeparator());
        }
        SwingUtil.systemClipboardCopy(buff.toString());
    }

    public void sendToTableInfoCopy(ContextMenuEvent contextMenuEvent) {
        StringBuilder buff = new StringBuilder();
        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
        if (c instanceof JTable table) {
            buff.append(copyJTable(table));
        }
        SwingUtil.systemClipboardCopy(buff.toString());
    }

    public String copyJTable(JTable table) {
        StringBuilder export = new StringBuilder();
        TableModel model = table.getModel();
        int colcount = table.getColumnCount();
        boolean[] cols = new boolean[colcount];
        for (int i = 0; i < cols.length; i++) {
            cols[i] = false;
            if (String.class.isAssignableFrom(table.getColumnClass(i))) {
                table.getColumnClass(i).asSubclass(String.class);
                cols[i] = true;
            } else if (Integer.class.isAssignableFrom(table.getColumnClass(i))) {
                table.getColumnClass(i).asSubclass(Integer.class);
                cols[i] = true;
            } else if (Long.class.isAssignableFrom(table.getColumnClass(i))) {
                table.getColumnClass(i).asSubclass(Long.class);
                cols[i] = true;
            }
            if (cols[i]) {
                export.append(table.getColumnName(i));
                export.append("\t");
            }
        }
        export.append(System.lineSeparator());
        int[] rows = table.getSelectedRows();
        for (int k = 0; k < rows.length; k++) {
            for (int i = 0; i < colcount; i++) {
                if (cols[i]) {
                    int rawRow = table.convertRowIndexToModel(rows[k]);
                    Object data = model.getValueAt(rawRow, i);
                    export.append(StringUtil.toString(data));
                    export.append("\t");
                }
            }
            export.append(System.lineSeparator());
        }
        return export.toString();
    }

    public static ToolSource newToolSource(ToolType toolType) {
        return new ToolSource() {
            @Override
            public ToolType toolType() {
                return toolType;
            }

            @Override
            public boolean isFromTool(ToolType... tts) {
                for (ToolType t : tts) {
                    if (t.equals(toolType)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static EditorCreationContext newEditorCreationContext(final ToolType toolType, final EditorMode editorMode) {
        return new EditorCreationContext() {
            @Override
            public ToolSource toolSource() {
                return newToolSource(toolType);
            }

            @Override
            public EditorMode editorMode() {
                return editorMode;
            }
        };
    }

    private final static Pattern HTTP2_VERSION_PATTERN = Pattern.compile("(\\S+) +(\\S+) +HTTP/2\r\n");

    // HTTP/1.x HTTP/2 に対応したラッパー
    public static HttpRequest httpRequest(HttpService httpService, ByteArray request) {
        HttpRequest warapRequest = HttpRequest.httpRequest(httpService, request);
        Matcher m = HTTP2_VERSION_PATTERN.matcher(StringUtil.getStringRaw(request.getBytes()));
        if (m.lookingAt()) {
            HttpRequest warap2Request = HttpRequest.http2Request(warapRequest.httpService(), warapRequest.headers(), warapRequest.body());
            warap2Request = warap2Request.withMethod(warapRequest.method()).withPath(warapRequest.path());
            return warap2Request;
        }
        return warapRequest;
    }

}
