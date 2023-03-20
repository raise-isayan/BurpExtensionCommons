package extension.burp.scanner;

import extension.burp.scanner.IssueItem;
import extension.burp.IBurpTab;
import extension.burp.IPropertyConfig;
import extension.burp.Severity;
import extension.burp.scanner.SignatureScanBase;
import extension.burp.scanner.SignatureSelect;

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
        return item;
    }

    public IBurpTab getBurpTab() {
        if (item instanceof IBurpTab iBurpTab) {
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
