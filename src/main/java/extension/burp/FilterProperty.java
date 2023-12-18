package extension.burp;

import com.google.gson.annotations.Expose;
import extension.helpers.StringUtil;
import java.util.EnumSet;

/**
 *
 * @author isayan
 */
public class FilterProperty {

    public enum FilterMode { SETTING, BAMBDA };

    @Expose
    private int listenerPort = -1;

    /**
     * @return the listenerPort
     */
    public int getListenerPort() {
        return listenerPort;
    }

    /**
     * @param listenerPort the listenerPort to set
     */
    public void setListenerPort(int listenerPort) {
        this.listenerPort = listenerPort;
    }

    @Expose
    private boolean showOnlyScopeItems = false;

    public boolean isShowOnlyScopeItems() {
        return this.showOnlyScopeItems;
    }

    public void setShowOnlyScopeItems(boolean value) {
        this.showOnlyScopeItems = value;
    }

    @Expose
    private boolean hideItemsWithoutResponses = false;

    /**
     * @return the hideItemsWithoutResponses
     */
    public boolean isHideItemsWithoutResponses() {
        return hideItemsWithoutResponses;
    }

    /**
     * @param hideItemsWithoutResponses the hideItemsWithoutResponses to set
     */
    public void setHideItemsWithoutResponses(boolean hideItemsWithoutResponses) {
        this.hideItemsWithoutResponses = hideItemsWithoutResponses;
    }

    private boolean showOnlyParameterizedRequests = false;

    /**
     * @return the showOnlyParameterizedRequests
     */
    public boolean isShowOnlyParameterizedRequests() {
        return showOnlyParameterizedRequests;
    }

    /**
     * @param showOnlyParameterizedRequests the showOnlyParameterizedRequests to set
     */
    public void setShowOnlyParameterizedRequests(boolean showOnlyParameterizedRequests) {
        this.showOnlyParameterizedRequests = showOnlyParameterizedRequests;
    }

    private boolean showOnlyEditedMessage = false;

    /**
     * @return the showOnlyEditedMessage
     */
    public boolean isShowOnlyEditedMessage() {
        return showOnlyEditedMessage;
    }

    /**
     * @param showOnlyEditedMessage the showOnlyEditedMessage to set
     */
    public void setShowOnlyEditedMessage(boolean showOnlyEditedMessage) {
        this.showOnlyEditedMessage = showOnlyEditedMessage;
    }

    @Expose
    private boolean showOnly = false;

    public boolean getShowOnly() {
        return this.showOnly;
    }

    public void setShowOnly(boolean value) {
        this.showOnly = value;
    }

    @Expose
    private boolean hide = false;

    public boolean getHide() {
        return this.hide;
    }

    public void setHide(boolean value) {
        this.hide = value;
    }

    @Expose
    private String showOnlyExtension = "asp,aspx,jsp,php";

    public String getShowOnlyExtension() {
        return this.showOnlyExtension;
    }

    public void setShowOnlyExtension(String value) {
        this.showOnlyExtension = value;
    }

    @Expose
    private String hideExtension = "js,gif,jpg,png,css";

    public String getHideExtension() {
        return this.hideExtension;
    }

    public void setHideExtension(String value) {
        this.hideExtension = value;
    }

    @Expose
    private boolean stat2xx = true;

    public boolean getStat2xx() {
        return this.stat2xx;
    }

    public void setStat2xx(boolean value) {
        this.stat2xx = value;
    }

    @Expose
    private boolean stat3xx = true;

    public boolean getStat3xx() {
        return this.stat3xx;
    }

    public void setStat3xx(boolean value) {
        this.stat3xx = value;
    }

    @Expose
    private boolean stat4xx = true;

    public boolean getStat4xx() {
        return this.stat4xx;
    }

    public void setStat4xx(boolean value) {
        this.stat4xx = value;
    }

    @Expose
    private boolean stat5xx = true;

    public boolean getStat5xx() {
        return this.stat5xx;
    }

    public void setStat5xx(boolean value) {
        this.stat5xx = value;
    }

    @Expose
    private boolean showOnlyHighlightColors = false;

    public boolean getShowOnlyHighlightColors() {
        return this.showOnlyHighlightColors;
    }

    public void setShowOnlyHighlightColors(boolean highlightColors) {
        this.showOnlyHighlightColors = highlightColors;
    }

    @Expose
    private EnumSet<MessageHighlightColor> colors = EnumSet.allOf(MessageHighlightColor.class);

    public EnumSet<MessageHighlightColor> getHighlightColors() {
        return this.colors;
    }

    public void setHighlightColors(EnumSet<MessageHighlightColor> colors) {
        this.colors = colors;
    }

    @Expose
    private boolean showOnlyComment = false;

    public boolean getShowOnlyComment() {
        return this.showOnlyComment;
    }

    public void setShowOnlyComment(boolean comments) {
        this.showOnlyComment = comments;
    }

    @Expose
    private String method = "";

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    @Expose
    private String path = "";

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    @Expose
    private boolean requestRegex = false;

    /**
     * @return the requestRegex
     */
    public boolean isRequestRegex() {
        return requestRegex;
    }

    /**
     * @param requestRegex the requestRegex to set
     */
    public void setRequestRegex(boolean requestRegex) {
        this.requestRegex = requestRegex;
    }

    @Expose
    private boolean requestIgnoreCase = false;

    /**
     * @return the requestIgnoreCase
     */
    public boolean isRequestIgnoreCase() {
        return requestIgnoreCase;
    }

    /**
     * @param requestIgnoreCase the requestIgnoreCase to set
     */
    public void setRequestIgnoreCase(boolean requestIgnoreCase) {
        this.requestIgnoreCase = requestIgnoreCase;
    }

    @Expose
    private boolean responseRegex = false;

    /**
     * @return the responseRegex
     */
    public boolean isResponseRegex() {
        return responseRegex;
    }

    @Expose
    private boolean responseIgnoreCase = false;

    /**
     * @param responseRegex the responseRegex to set
     */
    public void setResponseRegex(boolean responseRegex) {
        this.responseRegex = responseRegex;
    }

    /**
     * @return the responseIgnoreCase
     */
    public boolean isResponseIgnoreCase() {
        return responseIgnoreCase;
    }

    /**
     * @param responseIgnoreCase the responseIgnoreCase to set
     */
    public void setResponseIgnoreCase(boolean responseIgnoreCase) {
        this.responseIgnoreCase = responseIgnoreCase;
    }

    @Expose
    private String request = "";

    /**
     * @return the request
     */
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
    }

    @Expose
    private String response = "";

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        if (this.listenerPort > 0) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("requestResponse.listenerPort()").append(" == ").append(this.listenerPort);
        }
        if (this.showOnlyScopeItems) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("requestResponse.request().isInScope()");
        }
        if (this.hideItemsWithoutResponses) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("requestResponse.hasResponse()");
        }
        if (this.showOnlyParameterizedRequests) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("(requestResponse.request().hasParameters(HttpParameterType.URL) || requestResponse.request().hasParameters(HttpParameterType.BODY))");
        }
        if (this.showOnlyEditedMessage) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("requestResponse.edited()");
        }

        if (this.showOnly) {
            String [] extensions = BurpUtil.splitFilterPattern(this.showOnlyExtension);
            StringBuilder sub = new StringBuilder();
            for (String ext : extensions) {
                if (sub.length() > 0) sub.append( "\n || ");
                sub.append("path.endsWith(\"").append(".").append( StringUtil.literalEscape(ext)).append("\")");
            }
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("((Predicate<String>)((path)->{ return ").append(sub).append("; })).test(requestResponse.request().pathWithoutQuery().toLowerCase())");
        }
        if (this.hide) {
            String [] extensions = BurpUtil.splitFilterPattern(this.hideExtension);
            StringBuilder sub = new StringBuilder();
            for (String ext : extensions) {
                if (sub.length() > 0) sub.append( "\n && ");
                sub.append("!path.endsWith(\"").append(".").append( StringUtil.literalEscape(ext)).append("\")");
            }
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("((Predicate<String>)((path)->{ return ").append(sub).append("; })).test(requestResponse.request().pathWithoutQuery().toLowerCase())");
        }
        // HighlightColor
        if (this.showOnlyHighlightColors) {
            StringBuilder sub = new StringBuilder();
            for (MessageHighlightColor color : this.colors) {
                if (sub.length() > 0) sub.append( "\n || ");
                if (color == MessageHighlightColor.WHITE) {
                    sub.append( "color.equals(HighlightColor.").append("NONE").append(")");
                }
                else {
                    sub.append( "color.equals(HighlightColor.").append(color.name()).append(")");
                }
            }
            if (!sub.isEmpty()) {
                if (sb.length() > 0) sb.append( "\n && ");
                sb.append("((Predicate<HighlightColor>)((color)->{ return ").append(sub).append("; })).test(requestResponse.annotations().highlightColor())");
            }
        }
        // Comments
        if (this.showOnlyComment) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append( "requestResponse.annotations().hasNotes()");
        }
        // Status(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_4XX_CLIENT_ERRORS))
        if (!this.stat2xx) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append( "!(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_2XX_SUCCESS))");
        }
        if (!this.stat3xx) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append( "!(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_3XX_REDIRECTION))");
        }
        if (!this.stat4xx) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append( "!(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_4XX_CLIENT_ERRORS))");
        }
        if (!this.stat5xx) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append( "!(requestResponse.hasResponse() && requestResponse.response().isStatusCodeClass(StatusCodeClass.CLASS_5XX_SERVER_ERRORS))");
        }
        // Filter by Search Item
        if (!this.method.isEmpty()) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append( "requestResponse.request().method().toUpperCase().equals(\"").append(StringUtil.literalEscape(this.method)).append("\")");
        }
        if (!this.path.isEmpty()) {
            if (sb.length() > 0) sb.append( "\n && ");
            sb.append("requestResponse.request().path().contains(\"").append(StringUtil.literalEscape(this.path)).append("\")");
        }
        if (!this.request.isEmpty()) {
            if (sb.length() > 0) sb.append( "\n && ");
            if (this.requestRegex) {
                sb.append("requestResponse.request().contains(Pattern.compile(\"").append(StringUtil.literalEscape(this.request)).append("\", Pattern.DOTALL").append(this.requestIgnoreCase ? "" : " | Pattern.CASE_INSENSITIVE").append("))");
            }
            else {
                sb.append("requestResponse.request().contains(\"").append(StringUtil.literalEscape(this.request)).append("\", ").append(this.requestIgnoreCase).append(")");
            }
        }
        if (!this.response.isEmpty()) {
            if (sb.length() > 0) sb.append( "\n && ");
            if (this.responseRegex) {
                sb.append("requestResponse.response().contains(Pattern.compile(\"").append(StringUtil.literalEscape(this.response)).append("\", Pattern.DOTALL").append(this.requestIgnoreCase ? "" : " | Pattern.CASE_INSENSITIVE").append("))");
            }
            else {
                sb.append("requestResponse.response().contains(\"").append(StringUtil.literalEscape(this.response)).append("\", ").append(this.responseIgnoreCase).append(")");
            }
        }
        StringBuilder build = new StringBuilder();
        if (sb.isEmpty()) sb.append( "true");
        build.append( "return").append(" ").append(sb).append(";");
        return build.toString();
    }

    public void setProperty(FilterProperty property) {
        this.setFilterMode(property.getFilterMode());
        this.setListenerPort(property.getListenerPort());
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

        this.setShowOnlyComment(property.getShowOnlyComment());
        this.setShowOnlyHighlightColors(property.getShowOnlyHighlightColors());
        this.setHighlightColors(property.getHighlightColors());
        this.setMethod(property.getMethod());
        this.setPath(property.getPath());
        this.setRequest(property.getRequest());
        this.setRequestRegex(property.isRequestRegex());
        this.setRequestIgnoreCase(property.isRequestIgnoreCase());
        this.setResponse(property.getResponse());
        this.setResponseRegex(property.isResponseRegex());
        this.setResponseIgnoreCase(property.isResponseIgnoreCase());
        this.setBambda(property.getBambda());
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
        }
        else {
            return this.getBambda();
        }
    }

}
