package extension.burp;

/**
 *
 * @author isayan
 */
public interface FilterHTTPProperty extends FilterBambdaProperty, FilterRequestResponseProperty, FilterAnnotationProperty {

    public boolean isShowOnlyEditedMessage();

    public void setShowOnlyEditedMessage(boolean showOnlyEditedMessage);

    public int getListenerPort();

    public void setListenerPort(int listenerPort);

}
