package extension.burp.scanner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import burp.api.montoya.core.Marker;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.ScanCheck;

/**
 *
 * @author raise.isayan
 * @param <M>
 */
public class SignatureScanBase<M extends IssueItem> {

    public SignatureScanBase(String issueName) {
        this.issueName = issueName;
    }

    private final String issueName;

    public String getIssueName() {
        return issueName;
    }

    public ScanCheck passiveScanCheck() {
        return new ScannerCheckAdapter();
    }

    public HttpRequestResponse applyMarkers(HttpRequestResponse baseRequestResponse, List<M> issueList) {
        List<Marker> requestMarkers = new ArrayList<>();
        List<Marker> responseMarkers = new ArrayList<>();
        for (IssueItem issue : issueList) {
            if (issue.isCapture()) {
                if (issue.isMessageIsRequest()) {
                    requestMarkers.add(Marker.marker(Range.range(issue.start(), issue.end())));
                } else {
                    responseMarkers.add(Marker.marker(Range.range(issue.start(), issue.end())));
                }
            }
        }
        if (!(requestMarkers.isEmpty() || responseMarkers.isEmpty())) {
            List<Marker> markers = new ArrayList<>();
            markers.addAll(requestMarkers);
            baseRequestResponse = baseRequestResponse.withRequestMarkers(markers);
            markers.addAll(responseMarkers);
            baseRequestResponse = baseRequestResponse.withResponseMarkers(markers);
        } else if (!requestMarkers.isEmpty()) {
            baseRequestResponse = baseRequestResponse.withRequestMarkers(requestMarkers);
        } else if (!responseMarkers.isEmpty()) {
            baseRequestResponse = baseRequestResponse.withResponseMarkers(responseMarkers);
        }
        return baseRequestResponse;
    }

    private final EventListenerList propertyChangeList = new EventListenerList();

    public final void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        Object[] listeners = this.propertyChangeList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == PropertyChangeListener.class) {
                ((PropertyChangeListener) listeners[i + 1]).propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
            }
        }
    }

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeList.add(PropertyChangeListener.class, listener);
    }

    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeList.remove(PropertyChangeListener.class, listener);
    }

}
