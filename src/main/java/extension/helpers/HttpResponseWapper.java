package extension.helpers;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Marker;
import burp.api.montoya.http.message.Cookie;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.MimeType;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.http.message.responses.analysis.Attribute;
import burp.api.montoya.http.message.responses.analysis.AttributeType;
import burp.api.montoya.http.message.responses.analysis.KeywordCount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isayan
 */
public class HttpResponseWapper extends HttpMessageWapper implements HttpResponse {

    private final HttpResponse response;

    public HttpResponseWapper(HttpResponse response) {
        super(response);
        this.response = response;
    }

    @Override
    public short statusCode() {
        return response.statusCode();
    }

    @Override
    public String reasonPhrase() {
        return response.reasonPhrase();
    }

    @Override
    public String httpVersion() {
        return response.httpVersion();
    }

    @Override
    public List<Cookie> cookies() {
        return response.cookies();
    }

    @Override
    public MimeType statedMimeType() {
        return response.statedMimeType();
    }

    @Override
    public MimeType inferredMimeType() {
        return response.inferredMimeType();
    }

    @Override
    public List<KeywordCount> keywordCounts(String... keywords) {
        return response.keywordCounts(keywords);
    }

    @Override
    public List<Attribute> attributes(AttributeType... types) {
        return response.attributes(types);
    }

    @Override
    public HttpResponse copyToTempFile() {
        return response.copyToTempFile();
    }

    @Override
    public HttpResponse withStatusCode(short statusCode) {
        return response.withStatusCode(statusCode);
    }

    @Override
    public HttpResponse withReasonPhrase(String reasonPhrase) {
        return response.withReasonPhrase(reasonPhrase);
    }

    @Override
    public HttpResponse withHttpVersion(String httpVersion) {
        return response.withHttpVersion(httpVersion);
    }

    @Override
    public HttpResponse withBody(String body) {
        return response.withBody(body);
    }

    @Override
    public HttpResponse withBody(ByteArray body) {
        return response.withBody(body);
    }

    @Override
    public HttpResponse withAddedHeader(HttpHeader header) {
        return response.withAddedHeader(header);
    }

    @Override
    public HttpResponse withAddedHeader(String name, String value) {
        return response.withAddedHeader(name, value);
    }

    @Override
    public HttpResponse withUpdatedHeader(HttpHeader header) {
        return response.withUpdatedHeader(header);
    }

    @Override
    public HttpResponse withUpdatedHeader(String name, String value) {
        return response.withUpdatedHeader(name, value);
    }

    @Override
    public HttpResponse withRemovedHeader(HttpHeader header) {
        return response.withRemovedHeader(header);
    }

    @Override
    public HttpResponse withRemovedHeader(String name) {
        return response.withRemovedHeader(name);
    }

    @Override
    public HttpResponse withMarkers(List<Marker> markers) {
        return response.withMarkers(markers);
    }

    @Override
    public HttpResponse withMarkers(Marker... markers) {
        return response.withMarkers(markers);
    }

    public ZonedDateTime getDateHeaderAsZoneDate() {
        return getDateHeader(this.response);
    }

    public String getGuessCharset() {
        return getGuessCharset(this.response);
    }

    private final static Pattern RESPONSE_META_SET = Pattern.compile("<meta (?:.*?)charset=[\"\']?([\\w_-]+)[\"\']?\\W+", Pattern.CASE_INSENSITIVE);

    public static String getGuessCharset(HttpResponse httpResponse) {
        String charset = null;
        HttpHeader contentType = getContentTypeHeader(httpResponse);
        if (contentType != null) {
            Matcher m = CONTENT_CHARSET.matcher(contentType.value());
            if (m.find()) {
                charset = m.group(2);
            }
        }
        if (charset == null) {
            Matcher m2 = RESPONSE_META_SET.matcher(StringUtil.getStringRaw(httpResponse.body().getBytes()));
            if (m2.find()) {
                charset = m2.group(1);
            }
        }
        if (charset == null) {
            charset = HttpUtil.getGuessCode(httpResponse.body().getBytes());
        }
        return HttpUtil.normalizeCharset(charset);
    }

    public static ZonedDateTime getDateHeader(HttpResponse httpResponse) {
        ZonedDateTime zdtm = null;
        HttpHeader headerDate = findHeader(httpResponse.headers(), "Date");
        if (headerDate != null) {
            zdtm = DateUtil.parseHttpDate(headerDate.value());
        }
        return zdtm;
    }

}