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
import java.util.regex.Pattern;

/**
 *
 * @author isayan
 */
public class HttpRequestWapper extends HttpMessageWapper implements HttpRequest {

    public final static Pattern FIRST_LINE = Pattern.compile("^([a-zA-Z]+?)\\s+(\\S+)\\s+(\\S+)$", Pattern.MULTILINE);

    private final HttpRequest request;

    public HttpRequestWapper(HttpRequest request) {
        super(request);
        this.request = request;
    }

    public boolean hasHttpRequest() {
        return this.request != null;
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
    public HttpRequest withAddedParameters(HttpParameter... parameters) {
        return request.withAddedParameters(parameters);
    }

    @Override
    public HttpRequest withRemovedParameters(HttpParameter... parameters) {
        return request.withRemovedParameters(parameters);
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

    public final static String METHOD_GET = "GET";
    public final static String METHOD_POST = "POST";
    public final static String METHOD_HEAD = "HEAD";
    public final static String METHOD_OPTIONS = "OPTIONS";
    public final static String METHOD_PUT = "PUT";
    public final static String METHOD_DELETE = "DELETE";
    public final static String METHOD_TRACE = "TRACE";

    public String getRequestLine() {
        StringBuilder firstLine = new StringBuilder();
        firstLine.append(this.request.method());
        firstLine.append(" ");
        firstLine.append(this.request.path());
        firstLine.append(" ");
        firstLine.append(this.request.httpVersion());
        return firstLine.toString();
    }

    public HttpRequest withRequestLine(String firstLine) {
        Matcher m = FIRST_LINE.matcher(firstLine);
        if (m.find()) {
            return request
                    .withMethod(m.group(1))
                    .withPath(m.group(2));
        }
        return this.request;
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

    public boolean hasUrlDecodeParameter() {
        List<ParsedHttpParameter> params = this.parameters();
        boolean isDecodeParam = false;
        for (ParsedHttpParameter p : params) {
            switch (p.type()) {
                case URL:
                case COOKIE:
                    isDecodeParam = true;
                    break;
                case BODY:
                    isDecodeParam = false;
                    break;
                case MULTIPART_ATTRIBUTE:
                    break;
            }
        }
        return (this.contentType() == ContentType.URL_ENCODED) || (this.contentType() == ContentType.NONE && isDecodeParam);
    }

    public boolean isGET() {
        return METHOD_GET.equals(this.request.method());
    }

    public boolean isPOST() {
        return METHOD_POST.equals(this.request.method());
    }

    public String getEnctype() {
        return this.getContentMimeType();
    }

    public boolean isSecure() {
        if (this.request.httpService() == null) {
            if (PROTOCOL_HTTP_2.equals(this.request.httpVersion())) {
                return true;
            } else {
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

    public String getGuessCharset(String defaultCharset) {
        String guessCharset = getGuessCharset(this.request);
        return guessCharset == null ? defaultCharset : guessCharset;
    }

    /**
     * httpRequest static method
     * @param url
     * @return
     */
    public static String getUrlPath(String url) {
        int i = url.indexOf('?');
        return i > 0 ? url.substring(0, i) : url;
    }

    /**
     *
     * @param parameters
     * @return
     */
    public static boolean hasQueryParameter(List<ParsedHttpParameter> parameters) {
        return parameters.stream().anyMatch(p -> p.type() == HttpParameterType.URL);
    }

    public static String getQueryParameter(String url) {
        int queryPos = url.indexOf('?');
        return queryPos > -1 && url.length() - 1 > queryPos ? url.substring(queryPos + 1) : null;
    }

    private static String getGuessCharset(HttpRequest httpRequest) {
        String charset = null;
        HttpHeader contentTypeHeader = getContentTypeHeader(httpRequest);
        if (contentTypeHeader != null) {
            Matcher m = CONTENT_CHARSET.matcher(contentTypeHeader.value());
            if (m.find()) {
                charset = m.group(2);
            }
        }
        if (charset == null) {
            ContentType contentType = httpRequest.contentType();
            if (ContentType.URL_ENCODED.equals(contentType)) {
                if (hasQueryParameter(httpRequest.parameters())) {
                    byte[] content = StringUtil.getBytesRaw(httpRequest.url());
                    charset = HttpUtil.getGuessCode(SmartCodec.toUrlDecode(content));
                }
                if (charset == null) {
                    byte[] content = httpRequest.body().getBytes();
                    charset = HttpUtil.getGuessCode(SmartCodec.toUrlDecode(content));
                }
            } else {
                charset = HttpUtil.getGuessCode(httpRequest.body().getBytes());
            }
        }
        return HttpUtil.normalizeCharset(charset);
    }

    public HttpRequest withAjustContentLength() {
        int contentLength = request.body().length();
        return request.withHeader(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    @Override
    public HttpRequest withHeader(HttpHeader header) {
        return request.withHeader(header);
    }

    @Override
    public HttpRequest withHeader(String name, String value) {
        return request.withHeader(name, value);
    }

    @Override
    public HttpRequest withParameter(HttpParameter parameter) {
        return request.withParameter(parameter);
    }

    @Override
    public boolean isInScope() {
        return request.isInScope();
    }

    @Override
    public List<ParsedHttpParameter> parameters(HttpParameterType parameters) {
        return request.parameters(parameters);
    }

    @Override
    public boolean hasParameters() {
        return request.hasParameters();
    }

    @Override
    public ParsedHttpParameter parameter(String name, HttpParameterType type) {
        return request.parameter(name, type);
    }

    @Override
    public String parameterValue(String name, HttpParameterType type) {
        return request.parameterValue(name, type);
    }

    @Override
    public boolean hasParameter(String name, HttpParameterType type) {
        return request.hasParameter(name, type);
    }

    @Override
    public boolean hasParameter(HttpParameter parameter) {
        return request.hasParameter(parameter);
    }

    @Override
    public HttpRequest withAddedParameters(List<? extends HttpParameter> parameters) {
        return request.withAddedParameters(parameters);
    }

    @Override
    public HttpRequest withRemovedParameters(List<? extends HttpParameter> parameters) {
        return request.withAddedParameters(parameters);
    }

    @Override
    public HttpRequest withUpdatedParameters(List<? extends HttpParameter> parameters) {
        return request.withAddedParameters(parameters);
    }

    @Override
    public String pathWithoutQuery() {
        return request.pathWithoutQuery();
    }

    @Override
    public boolean hasParameters(HttpParameterType type) {
        return request.hasParameters(type);
    }

    @Override
    public String query() {
        return request.query();
    }

    @Override
    public String fileExtension() {
        return request.fileExtension();
    }

    @Override
    public HttpRequest withAddedHeaders(List<? extends HttpHeader> list) {
        return request.withAddedHeaders(list);
    }

    @Override
    public HttpRequest withAddedHeaders(HttpHeader... httpHeader) {
        return request.withAddedHeaders(httpHeader);
    }

    @Override
    public HttpRequest withUpdatedHeaders(List<? extends HttpHeader> list) {
        return request.withUpdatedHeaders(list);
    }

    @Override
    public HttpRequest withUpdatedHeaders(HttpHeader... httpHeader) {
        return request.withUpdatedHeaders(httpHeader);
    }

    @Override
    public HttpRequest withRemovedHeaders(List<? extends HttpHeader> list) {
        return request.withRemovedHeaders(list);
    }

    @Override
    public HttpRequest withRemovedHeaders(HttpHeader... httpHeader) {
        return request.withRemovedHeaders(httpHeader);
    }

}
