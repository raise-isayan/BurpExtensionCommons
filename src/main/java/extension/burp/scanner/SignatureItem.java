package extension.burp.scanner;

import extension.burp.IBurpTab;
import extension.burp.IPropertyConfig;
import extension.burp.Severity;

/**
 *
 * @author isayan
 */
public class SignatureItem extends SignatureSelect {

    public SignatureItem(SignatureScanBase<? extends IssueItem> item, Severity serverity) {
        super(item.getIssueName(), serverity);
        this.item = item;
    }

    public char getSortOrder() {
        return 'z';
    }

    private final SignatureScanBase<? extends IssueItem> item;

    public SignatureScanBase<? extends IssueItem> getSignatureScan() {
        return this.item;
    }

    public IBurpTab getBurpTab() {
        if (this.item instanceof IBurpTab iBurpTab) {
            return iBurpTab;
        } else {
            return null;
        }
    }

    public IPropertyConfig getSignatureConfig() {
        if (item instanceof IPropertyConfig iPropertyConfig) {
            return iPropertyConfig;
        } else {
            return null;
        }
    }

}
