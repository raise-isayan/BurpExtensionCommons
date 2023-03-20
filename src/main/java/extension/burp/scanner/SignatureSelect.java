package extension.burp.scanner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import extension.burp.Severity;

/**
 *
 * @author isayan
 */
public class SignatureSelect implements ISignatureItem {

    public SignatureSelect(String issueName, Severity serverity) {
        this.issueName = issueName;
        this.serverity = serverity;
    }

    @Expose
    @SerializedName("selected")
    private boolean selected = true;

    /**
     * @return the selected
     */
    @Override
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * @param selected the selected to set
     */
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Expose
    @SerializedName("issueName")
    private final String issueName;

    @Override
    public String getIssueName() {
        return issueName;
    }

    private final Severity serverity;

    @Override
    public Severity getServerity() {
        return serverity;
    }

}
