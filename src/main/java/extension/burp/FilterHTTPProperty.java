package extension.burp;

/**
 *
 * @author isayan
 */
public interface FilterHTTPProperty extends FilterAnnotationProperty {

    public FilterProperty.FilterMode getFilterMode();

    public void setFilterMode(FilterProperty.FilterMode filterMode);

    public boolean isShowOnlyScopeItems();

    public void setShowOnlyScopeItems(boolean value);

    public boolean isHideItemsWithoutResponses();

    public void setHideItemsWithoutResponses(boolean hideItemsWithoutResponses);

    public boolean isShowOnlyParameterizedRequests();

    public void setShowOnlyParameterizedRequests(boolean showOnlyParameterizedRequests);

    public boolean isShowOnlyEditedMessage();

    public void setShowOnlyEditedMessage(boolean showOnlyEditedMessage);

    public boolean getShowOnly();

    public void setShowOnly(boolean value);

    public boolean getHide();

    public void setHide(boolean value);

    public String getShowOnlyExtension();

    public void setShowOnlyExtension(String value);

    public String getHideExtension();

    public void setHideExtension(String value);

    public boolean getStat2xx();

    public void setStat2xx(boolean value);

    public boolean getStat3xx();

    public void setStat3xx(boolean value);

    public boolean getStat4xx();

    public void setStat4xx(boolean value);

    public boolean getStat5xx();

    public void setStat5xx(boolean value);

    /**
     * @return the method
     */
    public String getMethod();

    public void setMethod(String method);

    public String getPath();

    public void setPath(String path);

    public boolean isRequestRegex();

    public void setRequestRegex(boolean requestRegex);

    public boolean isRequestIgnoreCase();

    public void setRequestIgnoreCase(boolean requestIgnoreCase);

    public boolean isResponseRegex();

    public void setResponseRegex(boolean responseRegex);

    public boolean isResponseIgnoreCase();

    public void setResponseIgnoreCase(boolean responseIgnoreCase);

    public String getRequest();

    public void setRequest(String request);

    public String getResponse();

    public void setResponse(String response);

    public String getBambdaQuery();

}
