package extension.burp;

import burp.IExtensionHelpers;
import burp.IHttpHeader;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IParameter;
import burp.IRequestInfo;
import burp.IResponseInfo;
import burp.IResponseKeywords;
import burp.IResponseVariations;
import burp.IScannerInsertionPoint;
import java.net.URL;
import java.util.List;

/**
 *
 * @author isayan
 */
public class ExtensionHelpers implements IExtensionHelpers {

    private final IExtensionHelpers helpers;

    public ExtensionHelpers(IExtensionHelpers helpers) {
        this.helpers = helpers;
    }

    @Override
    public IRequestInfo analyzeRequest(IHttpRequestResponse request) {
        return this.helpers.analyzeRequest(request);
    }

    @Override
    public IRequestInfo analyzeRequest(IHttpService httpService, byte[] request) {
        return this.helpers.analyzeRequest(httpService, request);
    }

    @Override
    public IRequestInfo analyzeRequest(byte[] request) {
        return this.helpers.analyzeRequest(request);
    }

    @Override
    public IResponseInfo analyzeResponse(byte[] response) {
        return this.helpers.analyzeResponse(response);
    }

    @Override
    public IParameter getRequestParameter(byte[] request, String parameterName) {
        return this.helpers.getRequestParameter(request, parameterName);
    }

    @Override
    public String urlDecode(String data) {
        return this.helpers.urlDecode(data);
    }

    @Override
    public String urlEncode(String data) {
        return this.helpers.urlEncode(data);
    }

    @Override
    public byte[] urlDecode(byte[] data) {
        return this.helpers.urlDecode(data);
    }

    @Override
    public byte[] urlEncode(byte[] data) {
        return this.helpers.urlEncode(data);
    }

    @Override
    public byte[] base64Decode(String data) {
        return this.helpers.base64Decode(data);
    }

    @Override
    public byte[] base64Decode(byte[] data) {
        return this.helpers.base64Decode(data);
    }

    @Override
    public String base64Encode(String data) {
        return this.helpers.base64Encode(data);
    }

    @Override
    public String base64Encode(byte[] data) {
        return this.helpers.base64Encode(data);
    }

    @Override
    public byte[] stringToBytes(String data) {
        return this.helpers.stringToBytes(data);
    }

    @Override
    public String bytesToString(byte[] data) {
        return this.helpers.bytesToString(data);
    }

    @Override
    public int indexOf(byte[] data, byte[] pattern, boolean caseSensitive, int from, int to) {
        return this.helpers.indexOf(data, pattern, caseSensitive, from, to);
    }

    @Override
    public byte[] buildHttpMessage(List<String> headers, byte[] body) {
        return this.helpers.buildHttpMessage(headers, body);
    }

    @Override
    public byte[] buildHttpRequest(URL url) {
        return this.helpers.buildHttpRequest(url);
    }

    @Override
    public byte[] addParameter(byte[] request, IParameter parameter) {
        return this.helpers.addParameter(request, parameter);
    }

    @Override
    public byte[] removeParameter(byte[] request, IParameter parameter) {
        return this.helpers.removeParameter(request, parameter);
    }

    @Override
    public byte[] updateParameter(byte[] request, IParameter parameter) {
        return this.helpers.updateParameter(request, parameter);
    }

    @Override
    public byte[] toggleRequestMethod(byte[] request) {
        return this.helpers.toggleRequestMethod(request);
    }

    @Override
    public IHttpService buildHttpService(String host, int port, String protocol) {
        return this.helpers.buildHttpService(host, port, protocol);
    }

    @Override
    public IHttpService buildHttpService(String host, int port, boolean useHttps) {
        return this.helpers.buildHttpService(host, port, useHttps);
    }

    @Override
    public IParameter buildParameter(String name, String value, byte type) {
        return this.helpers.buildParameter(name, value, type);
    }

    @Override
    public IScannerInsertionPoint makeScannerInsertionPoint(String insertionPointName,
            byte[] baseRequest, int from, int to) {
        return this.helpers.makeScannerInsertionPoint(insertionPointName, baseRequest, from, to);
    }

    @Override
    public IResponseVariations analyzeResponseVariations(byte[]
        ... responses) {
        return this.helpers.analyzeResponseVariations(responses);
    }

    @Override
    public IResponseKeywords analyzeResponseKeywords(List<String> keywords, byte[]
        ... responses) {
        return this.helpers.analyzeResponseKeywords(keywords, responses);
    }

    public URL getURL(IHttpRequestResponse messageInfo) {
        IRequestInfo reqInfo = this.helpers.analyzeRequest(messageInfo.getHttpService(), messageInfo.getRequest());
        return reqInfo.getUrl();
    }

    public URL getURL(IHttpService httpService, byte[] request) {
        IRequestInfo reqInfo = this.helpers.analyzeRequest(httpService, request);
        return reqInfo.getUrl();
    }

    @Override
    public IHttpHeader buildHeader(String name, String value) {
        return this.helpers.buildHeader(name, value);
    }

}
