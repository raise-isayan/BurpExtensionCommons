package extension.burp.scanner;

import burp.api.montoya.http.Http;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import burp.api.montoya.scanner.scancheck.ActiveScanCheck;

/**
 *
 * @author isayan
 */
public class ActiveScanCheckAdapter implements ActiveScanCheck {

    final String issueName;

    public ActiveScanCheckAdapter(String issueName) {
        this.issueName = issueName;
    }

    @Override
    public String checkName() {
        return this.issueName;
    }

    @Override
    public AuditResult doCheck(HttpRequestResponse baseRequestResponse, AuditInsertionPoint insertionPoint, Http http) {
        return null;
    }

    public ConsolidationAction consolidateIssues(AuditIssue existingIssue, AuditIssue newIssue) {
        if (existingIssue.name().equals(newIssue.name())) {
            // 同一とみなせる場合は報告をスキップ
            return ConsolidationAction.KEEP_EXISTING;
        }
        return ConsolidationAction.KEEP_BOTH;
    }

}
