package extension.burp;

import com.google.gson.annotations.Expose;
import extension.helpers.StringUtil;
import java.util.EnumSet;

/**
 *
 * @author isayan
 */
public class FilterProperty implements FilterHTTPProperty, FilterWebSocketProperty {

    public enum FilterCategory {
        HTTP,
        WEBSOCKET,
        SITE_MAP,
        LOGGER_CAPTURE,
        LOGGER_DISPLAY,
    }

    public enum FilterMode {
        SETTING, BAMBDA
    };

//    public FilterProperty() {
//        this(FilterCategory.HTTP);
//    }

    public FilterProperty() {
    }

    public FilterProperty(FilterCategory filterCategory) {
        this.filterCategory = filterCategory;
    }

    @Expose
    private boolean showOnlyScopeItems = false;

    @Override
    public boolean isShowOnlyScopeItems() {
        return this.showOnlyScopeItems;
    }

    @Override
    public void setShowOnlyScopeItems(boolean value) {
        this.showOnlyScopeItems = value;
    }

    @Expose
    private boolean hideItemsWithoutResponses = false;

    /**
     * @return the hideItemsWithoutResponses
     */
    @Override
    public boolean isHideItemsWithoutResponses() {
        return hideItemsWithoutResponses;
    }

    /**
     * @param hideItemsWithoutResponses the hideItemsWithoutResponses to set
     */
    @Override
    public void setHideItemsWithoutResponses(boolean hideItemsWithoutResponses) {
        this.hideItemsWithoutResponses = hideItemsWithoutResponses;
    }

    @Expose
    private boolean showOnlyParameterizedRequests = false;

    /**
     * @return the showOnlyParameterizedRequests
     */
    @Override
    public boolean isShowOnlyParameterizedRequests() {
        return showOnlyParameterizedRequests;
    }

    /**
     * @param showOnlyParameterizedRequests the showOnlyParameterizedRequests to
     * set
     */
    @Override
    public void setShowOnlyParameterizedRequests(boolean showOnlyParameterizedRequests) {
        this.showOnlyParameterizedRequests = showOnlyParameterizedRequests;
    }

    @Expose
    private boolean showOnlyEditedMessage = false;

    /**
     * @return the showOnlyEditedMessage
     */
    @Override
    public boolean isShowOnlyEditedMessage() {
        return showOnlyEditedMessage;
    }

    /**
     * @param showOnlyEditedMessage the showOnlyEditedMessage to set
     */
    @Override
    public void setShowOnlyEditedMessage(boolean showOnlyEditedMessage) {
        this.showOnlyEditedMessage = showOnlyEditedMessage;
    }

    @Expose
    private boolean showOnly = false;

    @Override
    public boolean getShowOnly() {
        return this.showOnly;
    }

    @Override
    public void setShowOnly(boolean value) {
        this.showOnly = value;
    }

    @Expose
    private boolean hide = false;

    @Override
    public boolean getHide() {
        return this.hide;
    }

    @Override
    public void setHide(boolean value) {
        this.hide = value;
    }

    @Expose
    private String showOnlyExtension = "asp,aspx,jsp,php";

    @Override
    public String getShowOnlyExtension() {
        return this.showOnlyExtension;
    }

    @Override
    public void setShowOnlyExtension(String value) {
        this.showOnlyExtension = value;
    }

    @Expose
    private String hideExtension = "js,gif,jpg,png,css";

    @Override
    public String getHideExtension() {
        return this.hideExtension;
    }

    @Override
    public void setHideExtension(String value) {
        this.hideExtension = value;
    }

    @Expose
    private boolean stat2xx = true;

    @Override
    public boolean getStat2xx() {
        return this.stat2xx;
    }

    @Override
    public void setStat2xx(boolean value) {
        this.stat2xx = value;
    }

    @Expose
    private boolean stat3xx = true;

    @Override
    public boolean getStat3xx() {
        return this.stat3xx;
    }

    @Override
    public void setStat3xx(boolean value) {
        this.stat3xx = value;
    }

    @Expose
    private boolean stat4xx = true;

    @Override
    public boolean getStat4xx() {
        return this.stat4xx;
    }

    @Override
    public void setStat4xx(boolean value) {
        this.stat4xx = value;
    }

    @Expose
    private boolean stat5xx = true;

    @Override
    public boolean getStat5xx() {
        return this.stat5xx;
    }

    @Override
    public void setStat5xx(boolean value) {
        this.stat5xx = value;
    }

    @Expose
    private boolean showOnlyComment = false;

    @Override
    public boolean getShowOnlyComment() {
        return this.showOnlyComment;
    }

    @Override
    public void setShowOnlyComment(boolean comments) {
        this.showOnlyComment = comments;
    }

    @Expose
    private boolean showOnlyHighlightColors = false;

    @Override
    public boolean getShowOnlyHighlightColors() {
        return this.showOnlyHighlightColors;
    }

    @Override
    public void setShowOnlyHighlightColors(boolean highlightColors) {
        this.showOnlyHighlightColors = highlightColors;
    }

    @Expose
    private EnumSet<MessageHighlightColor> colors = EnumSet.allOf(MessageHighlightColor.class);

    @Override
    public EnumSet<MessageHighlightColor> getHighlightColors() {
        return this.colors;
    }

    @Override
    public void setHighlightColors(EnumSet<MessageHighlightColor> colors) {
        this.colors = colors;
    }

    @Expose
    private int listenerPort = -1;

    /**
     * @return the listenerPort
     */
    @Override
    public int getListenerPort() {
        return listenerPort;
    }

    /**
     * @param listenerPort the listenerPort to set
     */
    @Override
    public void setListenerPort(int listenerPort) {
        this.listenerPort = listenerPort;
    }

    @Expose
    private String method = "";

    /**
     * @return the method
     */
    @Override
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Expose
    private String path = "";

    /**
     * @return the path
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Expose
    private boolean requestRegex = false;

    /**
     * @return the requestRegex
     */
    @Override
    public boolean isRequestRegex() {
        return requestRegex;
    }

    /**
     * @param requestRegex the requestRegex to set
     */
    @Override
    public void setRequestRegex(boolean requestRegex) {
        this.requestRegex = requestRegex;
    }

    @Expose
    private boolean requestIgnoreCase = false;

    /**
     * @return the requestIgnoreCase
     */
    @Override
    public boolean isRequestIgnoreCase() {
        return requestIgnoreCase;
    }

    /**
     * @param requestIgnoreCase the requestIgnoreCase to set
     */
    @Override
    public void setRequestIgnoreCase(boolean requestIgnoreCase) {
        this.requestIgnoreCase = requestIgnoreCase;
    }

    @Expose
    private boolean responseRegex = false;

    /**
     * @return the responseRegex
     */
    @Override
    public boolean isResponseRegex() {
        return responseRegex;
    }

    @Expose
    private boolean responseIgnoreCase = false;

    /**
     * @param responseRegex the responseRegex to set
     */
    @Override
    public void setResponseRegex(boolean responseRegex) {
        this.responseRegex = responseRegex;
    }

    /**
     * @return the responseIgnoreCase
     */
    @Override
    public boolean isResponseIgnoreCase() {
        return responseIgnoreCase;
    }

    /**
     * @param responseIgnoreCase the responseIgnoreCase to set
     */
    @Override
    public void setResponseIgnoreCase(boolean responseIgnoreCase) {
        this.responseIgnoreCase = responseIgnoreCase;
    }

    @Expose
    private String request = "";

    /**
     * @return the request
     */
    @Override
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    @Override
    public void setRequest(String request) {
        this.request = request;
    }

    @Expose
    private String response = "";

    /**
     * @return the response
     */
    @Override
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    @Override
    public void setResponse(String response) {
        this.response = response;
    }

    //
    // WebSockets
    //

    @Expose
    private boolean hideOutgoingMessage = false;

    @Override
    public boolean isHideOutgoingMessage() {
        return this.hideOutgoingMessage;
    }

    @Override
    public void setHideOutgoingMessage(boolean value) {
        this.hideOutgoingMessage = value;
    }

    @Expose
    private boolean hideIncomingMessage = false;

    @Override
    public boolean isHideIncomingMessage() {
        return this.hideIncomingMessage;
    }

    @Override
    public void setHideIncomingMessage(boolean value) {
        this.hideIncomingMessage = value;
    }

    @Expose
    private boolean messageRegex = false;

    @Override
    public boolean isMessageRegex() {
        return this.messageRegex;
    }

    @Override
    public void setMessageRegex(boolean messageRegex) {
        this.messageRegex = messageRegex;
    }

    @Expose
    private boolean messageIgnoreCase = false;

    @Override
    public boolean isMessageIgnoreCase() {
        return this.messageIgnoreCase;
    }

    @Override
    public void setMessageIgnoreCase(boolean messageIgnoreCase) {
        this.messageIgnoreCase = messageIgnoreCase;
    }

    @Expose
    private String message = "";

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    private boolean isHttpProtocol() {
        return !this.filterCategory.equals(FilterCategory.WEBSOCKET);
    }

    public String build() {
        String variable = isHttpProtocol() ? "requestResponse" : "message";
        StringBuilder sb = new StringBuilder();

        //
        // Filter by request
        //
        if (this.showOnlyScopeItems) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            if (isHttpProtocol()) {
                sb.append(variable).append(".").append("request().isInScope()");
            }
            else {
                sb.append(variable).append(".").append("upgradeRequest().isInScope()");
            }
        }
        if (this.hideItemsWithoutResponses) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append(variable).append(".").append("hasResponse()");
        }
        if (this.showOnlyParameterizedRequests) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("(").append(variable).append(".").append("request().hasParameters(HttpParameterType.URL) || ").append(variable).append(".").append("request().hasParameters(HttpParameterType.BODY))");
        }

        if (this.showOnlyEditedMessage) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            if (isHttpProtocol())
                sb.append(variable).append(".").append("edited()");
            else
                sb.append(variable).append(".").append("editedPayload() != null");
        }

        //
        // Filter by file extension
        //
        if (this.showOnly) {
            String[] extensions = BurpUtil.splitFilterPattern(this.showOnlyExtension);
            StringBuilder sub = new StringBuilder();
            for (String ext : extensions) {
                if (sub.length() > 0) {
                    sub.append("\n || ");
                }
                sub.append("path.endsWith(\"").append(".").append(StringUtil.literalEscape(ext)).append("\")");
            }
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("((Predicate<String>)((path)->{ return ").append(sub).append("; })).test(").append(variable).append(".").append("request().pathWithoutQuery().toLowerCase())");
        }
        if (this.hide) {
            String[] extensions = BurpUtil.splitFilterPattern(this.hideExtension);
            StringBuilder sub = new StringBuilder();
            for (String ext : extensions) {
                if (sub.length() > 0) {
                    sub.append("\n && ");
                }
                sub.append("!path.endsWith(\"").append(".").append(StringUtil.literalEscape(ext)).append("\")");
            }
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("((Predicate<String>)((path)->{ return ").append(sub).append("; })).test(").append(variable).append(".").append("request().pathWithoutQuery().toLowerCase())");
        }

        //
        // Annotation
        //

        // HighlightColor
        if (this.showOnlyHighlightColors) {
            StringBuilder sub = new StringBuilder();
            for (MessageHighlightColor color : this.colors) {
                if (sub.length() > 0) {
                    sub.append("\n || ");
                }
                if (color == MessageHighlightColor.WHITE) {
                    sub.append("color.equals(HighlightColor.").append("NONE").append(")");
                } else {
                    sub.append("color.equals(HighlightColor.").append(color.name()).append(")");
                }
            }
            if (!sub.isEmpty()) {
                if (sb.length() > 0) {
                    sb.append("\n && ");
                }
                sb.append("((Predicate<HighlightColor>)((color)->{ return ").append(sub).append("; })).test(").append(variable).append(".").append("annotations().highlightColor())");
            }
        }
        // Listener
        if (this.listenerPort > 0) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append(variable).append(".").append("listenerPort()").append(" == ").append(this.listenerPort);
        }

        // Comments
        if (this.showOnlyComment) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append(variable).append(".").append("annotations().hasNotes()");
        }

        //
        // Filter by status code
        //

        // Status
        if (!this.stat2xx) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("!").append("(").append(variable).append(".").append("hasResponse() && ").append(variable).append(".").append("response().isStatusCodeClass(StatusCodeClass.CLASS_2XX_SUCCESS))");
        }
        if (!this.stat3xx) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("!").append("(").append(variable).append(".").append("hasResponse() && ").append(variable).append(".").append("response().isStatusCodeClass(StatusCodeClass.CLASS_3XX_REDIRECTION))");
        }
        if (!this.stat4xx) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("!").append("(").append(variable).append(".").append("hasResponse() && ").append(variable).append(".").append("response().isStatusCodeClass(StatusCodeClass.CLASS_4XX_CLIENT_ERRORS))");
        }
        if (!this.stat5xx) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("!").append("(").append(variable).append(".").append("hasResponse() && ").append(variable).append(".").append("response().isStatusCodeClass(StatusCodeClass.CLASS_5XX_SERVER_ERRORS))");
        }

        // Filter by Search Item
        if (!this.method.isEmpty()) {
//            if (sb.length() > 0) {
//                sb.append("\n && ");
//            }
//            sb.append(variable).append(".").append("request().method().toUpperCase().equals(\"").append(StringUtil.literalEscape(this.method)).append("\")");
            String[] methods = BurpUtil.splitFilterPattern(this.method);
            StringBuilder sub = new StringBuilder();
            for (String m : methods) {
                if (sub.length() > 0) {
                    sub.append("\n || ");
                }
                sub.append("method.equals(\"").append(StringUtil.literalEscape(m)).append("\")");
            }
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append("((Predicate<String>)((method)->{ return ").append(sub).append("; })).test(").append(variable).append(".").append("request().method().toUpperCase())");

        }
        if (!this.path.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append(variable).append(".").append("request().path().contains(\"").append(StringUtil.literalEscape(this.path)).append("\")");
        }
        if (!this.request.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            if (this.requestRegex) {
                sb.append(variable).append(".").append("request().contains(Pattern.compile(\"").append(StringUtil.literalEscape(this.request)).append("\", Pattern.DOTALL").append(this.requestIgnoreCase ? "" : " | Pattern.CASE_INSENSITIVE").append("))");
            } else {
                sb.append(variable).append(".").append("request().contains(\"").append(StringUtil.literalEscape(this.request)).append("\", ").append(this.requestIgnoreCase).append(")");
            }
        }
        if (!this.response.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            if (this.responseRegex) {
                sb.append(variable).append(".").append("hasResponse() && ").append(variable).append(".").append("response().contains(Pattern.compile(\"").append(StringUtil.literalEscape(this.response)).append("\", Pattern.DOTALL").append(this.requestIgnoreCase ? "" : " | Pattern.CASE_INSENSITIVE").append("))");
            } else {
                sb.append(variable).append(".").append("hasResponse() && ").append(variable).append(".").append("response().contains(\"").append(StringUtil.literalEscape(this.response)).append("\", ").append(this.responseIgnoreCase).append(")");
            }
        }

        if (this.hideOutgoingMessage) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append(variable).append(".").append("direction() != Direction.CLIENT_TO_SERVER");
        }

        if (this.hideIncomingMessage) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            sb.append(variable).append(".").append("direction() != Direction.SERVER_TO_CLIENT");
        }

        if (!this.message.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n && ");
            }
            if (this.messageRegex) {
                sb.append(variable).append(".").append("contains(Pattern.compile(\"").append(StringUtil.literalEscape(this.message)).append("\", Pattern.DOTALL").append(this.messageIgnoreCase ? "" : " | Pattern.CASE_INSENSITIVE").append("))");
            } else {
                sb.append(variable).append(".").append("contains(\"").append(StringUtil.literalEscape(this.message)).append("\", ").append(this.messageIgnoreCase).append(")");
            }
        }

        StringBuilder build = new StringBuilder();
        if (sb.isEmpty()) {
            sb.append("true");
        }
        build.append("return").append(" ").append(sb).append(";");
        return build.toString();
    }

    public void setProperty(FilterProperty property) {
        this.setFilterCategory(property.getFilterCategory());
        this.setFilterMode(property.getFilterMode());

        // HTTP

        this.setShowOnlyScopeItems(property.isShowOnlyScopeItems());
        this.setHideItemsWithoutResponses(property.isHideItemsWithoutResponses());
        this.setShowOnlyParameterizedRequests(property.isShowOnlyParameterizedRequests());
        this.setShowOnlyEditedMessage(property.isShowOnlyEditedMessage());

        this.setShowOnly(property.getShowOnly());
        this.setShowOnlyExtension(property.getShowOnlyExtension());
        this.setHide(property.getHide());
        this.setHideExtension(property.getHideExtension());

        this.setStat2xx(property.getStat2xx());
        this.setStat3xx(property.getStat3xx());
        this.setStat4xx(property.getStat4xx());
        this.setStat5xx(property.getStat5xx());

        this.setAnnotationProperty(property);

        this.setMethod(property.getMethod());
        this.setPath(property.getPath());
        this.setRequest(property.getRequest());
        this.setRequestRegex(property.isRequestRegex());
        this.setRequestIgnoreCase(property.isRequestIgnoreCase());
        this.setResponse(property.getResponse());
        this.setResponseRegex(property.isResponseRegex());
        this.setResponseIgnoreCase(property.isResponseIgnoreCase());

        this.setBambda(property.getBambda());

        // WebSocket

        this.setShowOnlyScopeItems(property.isShowOnlyScopeItems());
        this.setHideOutgoingMessage(property.isHideOutgoingMessage());
        this.setHideIncomingMessage(property.isHideIncomingMessage());

        this.setMessageRegex(property.isMessageRegex());
        this.setRequestIgnoreCase(property.isRequestIgnoreCase());
        this.setResponseRegex(property.isResponseRegex());
        this.setResponseIgnoreCase(property.isResponseIgnoreCase());

        this.setMessage(property.getMessage());

    }

    public void setAnnotationProperty(FilterAnnotationProperty property) {
        this.setShowOnlyComment(property.getShowOnlyComment());
        this.setShowOnlyHighlightColors(property.getShowOnlyHighlightColors());
        this.setHighlightColors(property.getHighlightColors());
        this.setListenerPort(property.getListenerPort());
    }

    @Expose
    private FilterCategory filterCategory = FilterCategory.HTTP;

    /**
     * @return the filterCategory
     */
    public FilterCategory getFilterCategory() {
        return filterCategory;
    }

    /**
     * @param filterCategory the filterCategory to set
     */
    public void setFilterCategory(FilterCategory filterCategory) {
        this.filterCategory = filterCategory;
    }

    @Expose
    private FilterMode filterMode = FilterMode.SETTING;

    /**
     * @return the filterMode
     */
    public FilterMode getFilterMode() {
        return this.filterMode;
    }

    /**
     * @param filterMode the filterMode to set
     */
    public void setFilterMode(FilterMode filterMode) {
        this.filterMode = filterMode;
    }

    @Expose
    private String bambda = "";

    /**
     * @return the bambdaQuery
     */
    public String getBambda() {
        return bambda;
    }

    /**
     * @param bambdaQuery the bambdaQuery to set
     */
    public void setBambda(String bambdaQuery) {
        this.bambda = bambdaQuery;
    }

    public String getBambdaQuery() {
        if (this.filterMode == FilterMode.SETTING) {
            return this.build();
        } else {
            return this.getBambda();
        }
    }

}
