package extension.burp;

/**
 *
 * @author isayan
 */
public interface FilterWebSocketProperty extends FilterAnnotationProperty {

    public FilterProperty.FilterMode getFilterMode();

    public void setFilterMode(FilterProperty.FilterMode filterMode);

    public boolean isShowOnlyScopeItems();

    public void setShowOnlyScopeItems(boolean value);

    public boolean isHideOutgoingMessage();

    public void setHideOutgoingMessage(boolean value);

    public boolean isHideIncomingMessage();

    public void setHideIncomingMessage(boolean value);

    public boolean isMessageRegex();

    public void setMessageRegex(boolean requestRegex);

    public boolean isMessageIgnoreCase();

    public void setMessageIgnoreCase(boolean requestIgnoreCase);

    public String getMessage();

    public void setMessage(String message);

    public String getBambdaQuery();

}
