package extension.burp;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedEditor;

/**
 *
 * @author isayan
 */
public interface IBurpMessageTab extends ExtensionProvidedEditor {

    public HttpRequestResponse getHttpRequestResponse();

}
