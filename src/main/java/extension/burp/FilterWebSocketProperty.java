package extension.burp;

/**
 *
 * @author isayan
 */
public interface FilterWebSocketProperty extends FilterBambdaProperty, FilterAnnotationProperty {

    public boolean isShowOnlyScopeItems();

    public void setShowOnlyScopeItems(boolean value);

    public boolean isHideOutgoingMessage();

    public void setHideOutgoingMessage(boolean value);

    public boolean isHideIncomingMessage();

    public void setHideIncomingMessage(boolean value);

    public boolean isShowOnlyEditedMessage();

    public void setShowOnlyEditedMessage(boolean showOnlyEditedMessage);

    public boolean isMessageRegex();

    public void setMessageRegex(boolean requestRegex);

    public boolean isMessageIgnoreCase();

    public void setMessageIgnoreCase(boolean requestIgnoreCase);

    public String getMessage();

    public void setMessage(String message);

    public int getListenerPort();

    public void setListenerPort(int listenerPort);

}
