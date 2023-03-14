package extension.helpers;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Marker;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.ContentType;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.requests.HttpTransformation;
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 * @author isayan
 */
public class HttpRequestWapper extends HttpMessageWapper implements HttpRequest {

    private final HttpRequest request;

    public HttpRequestWapper(HttpRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public HttpService httpService() {
        return request.httpService();
    }

    @Override
    public String url() {
        return request.url();
    }

    @Override
    public String method() {
        return request.method();
    }

    @Override
    public String path() {
        return request.path();
    }

    @Override
    public String httpVersion() {
        return request.httpVersion();
    }

    @Override
    public ContentType contentType() {
        return request.contentType();
    }

    @Override
    public List<ParsedHttpParameter> parameters() {
        return request.parameters();
    }

    @Override
    public HttpRequest copyToTempFile() {
        return request.copyToTempFile();
    }

    @Override
    public HttpRequest withService(HttpService service) {
        return request.withService(service);
    }

    @Override
    public HttpRequest withPath(String path) {
        return request.withPath(path);
    }

    @Override
    public HttpRequest withMethod(String method) {
        return request.withPath(method);
    }

    @Override
    public HttpRequest withAddedParameters(List<HttpParameter> parameters) {
        return request.withAddedParameters(parameters);
    }

    @Override
    public HttpRequest withAddedParameters(HttpParameter... parameters) {
        return request.withAddedParameters(parameters);
    }

    @Override
    public HttpRequest withRemovedParameters(List<HttpParameter> parameters) {
        return request.withRemovedParameters(parameters);
    }

    @Override
    public HttpRequest withRemovedParameters(HttpParameter... parameters) {
        return request.withRemovedParameters(parameters);
    }

    @Override
    public HttpRequest withUpdatedParameters(List<HttpParameter> parameters) {
        return request.withUpdatedParameters(parameters);
    }

    @Override
    public HttpRequest withUpdatedParameters(HttpParameter... parameters) {
        return request.withUpdatedParameters(parameters);
    }

    @Override
    public HttpRequest withTransformationApplied(HttpTransformation transformation) {
        return request.withTransformationApplied(transformation);
    }

    @Override
    public HttpRequest withBody(String body) {
        return request.withBody(body);
    }

    @Override
    public HttpRequest withBody(ByteArray body) {
        return request.withBody(body);
    }

    @Override
    public HttpRequest withAddedHeader(String name, String value) {
        return request.withAddedHeader(name, value);
    }

    @Override
    public HttpRequest withAddedHeader(HttpHeader header) {
        return request.withAddedHeader(header);
    }

    @Override
    public HttpRequest withUpdatedHeader(String name, String value) {
        return request.withUpdatedHeader(name, value);
    }

    @Override
    public HttpRequest withUpdatedHeader(HttpHeader header) {
        return request.withUpdatedHeader(header);
    }

    @Override
    public HttpRequest withRemovedHeader(String name) {
        return request.withRemovedHeader(name);
    }

    @Override
    public HttpRequest withRemovedHeader(HttpHeader header) {
        return request.withRemovedHeader(header);
    }

    @Override
    public HttpRequest withMarkers(List<Marker> markers) {
        return request.withMarkers(markers);
    }

    @Override
    public HttpRequest withMarkers(Marker... markers) {
        return request.withMarkers(markers);
    }

    @Override
    public HttpRequest withDefaultHeaders() {
        return request.withDefaultHeaders();
    }

    public String getHost() {
        HttpHeader header = getHostHeader();
        return header.value();
    }

    public HttpHeader getHostHeader() {
        return findHeader(this.request.headers(), "Host");
    }

    public boolean hasQueryParameter() {
        return hasQueryParameter(this.request.parameters());
    }

    public boolean isGET() {
        return "GET".equals(method());
    }

    public boolean isPOST() {
        return "POST".equals(method());
    }

    public String getEnctype() {
        return this.getContentMimeType();
    }

    public boolean isSecure() {
        if (this.request.httpService() == null) {
            if ("HTTTP/2".equals(this.request.httpVersion())) {
                return true;
            }
            else {
                HttpHeader header = findHeader("Referer");
                if (header != null) {
                    return header.value().startsWith("https://");
                } else {
                    return false;
                }
            }

        } else {
            return this.request.httpService().secure();
        }
    }

    public String getGuessCharset() {
        return getGuessCharset(this.request);
    }

    /**
     * httpRequest static method
     */

    /**
     *
     * @param parameters
     * @return
     */
    public static boolean hasQueryParameter(List<ParsedHttpParameter> parameters) {
        return parameters.stream().anyMatch(p -> p.type() == HttpParameterType.URL);
    }

    public static String getGuessCharset(HttpRequest httpRequest) {
        String charset = null;
        HttpHeader contentType = getContentTypeHeader(httpRequest);
        if (contentType != null) {
            Matcher m = CONTENT_CHARSET.matcher(contentType.value());
            if (m.find()) {
                charset = m.group(2);
            }
        }
        if (charset == null) {
            charset = HttpUtil.getGuessCode(httpRequest.body().getBytes());
        }
        return HttpUtil.normalizeCharset(charset);
    }

}
