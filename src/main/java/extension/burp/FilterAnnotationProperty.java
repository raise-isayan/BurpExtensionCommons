package extension.burp;

import java.util.EnumSet;

/**
 *
 * @author isayan
 */
public interface FilterAnnotationProperty {

    public boolean getShowOnlyComment();

    public void setShowOnlyComment(boolean comments);

    public boolean getShowOnlyHighlightColors();

    public void setShowOnlyHighlightColors(boolean highlightColors);

    public EnumSet<MessageHighlightColor> getHighlightColors();

    public void setHighlightColors(EnumSet<MessageHighlightColor> colors);

    public int getListenerPort();

    public void setListenerPort(int listenerPort);

}
