package extension.helpers;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.Marker;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpMessage;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isayan
 */
public class HttpMessageWapper implements HttpMessage {

    public final static String PROTOCOL_HTTP_1_0 = "HTTP/1.0";
    public final static String PROTOCOL_HTTP_1_1 = "HTTP/1.1";
    public final static String PROTOCOL_HTTP_2 = "HTTP/2";

    public final static String CONTENT_TYPE = "Content-Type";
    public final static String CONTENT_LENGTH = "Content-Length";

    public enum ContentMimeType {
        JAVA_SCRIPT, JSON, XML, HTML
    };

    private final HttpMessage message;

    public HttpMessageWapper(HttpMessage message) {
        this.message = message;
    }

    public boolean hasHttpMessage() {
        return this.message != null;
    }

    @Override
    public String httpVersion() {
        return message.httpVersion();
    }

    @Override
    public List<HttpHeader> headers() {
        return message.headers();
    }

    public ByteArray header() {
        return ByteArray.byteArray(Arrays.copyOfRange(this.toByteArray().getBytes(), 0, this.bodyOffset()));
    }

    public String headerToString() {
        return ByteArray.byteArray(Arrays.copyOfRange(this.toByteArray().getBytes(), 0, this.bodyOffset())).toString();
    }

    @Override
    public ByteArray body() {
        return message.body();
    }

    @Override
    public String bodyToString() {
        return message.bodyToString();
    }

    @Override
    public int bodyOffset() {
        return message.bodyOffset();
    }

    @Override
    public List<Marker> markers() {
        return message.markers();
    }

    @Override
    public ByteArray toByteArray() {
        return message.toByteArray();
    }

    /**
     *
     *
     */
    public final static String LINE_TERMINATE = HttpUtil.LINE_TERMINATE;
    public final static Pattern HTTP_LINESEP = HttpUtil.HTTP_LINESEP;
    private final static Pattern CONTENT_TYPE_PATTERN = Pattern.compile("^Content-Type:\\s*([^;]+?)(?:;\\s*charset=[\"\']?([\\w_-]+)[\"\']?)?\\s*$", Pattern.MULTILINE);
    private final static Pattern CONTENT_TYPE_MIME = Pattern.compile("\\s*([^\\s;]+);?", Pattern.MULTILINE);
    protected final static Pattern CONTENT_CHARSET = Pattern.compile("(\\s*charset=[\"\']?([\\w_-]+)[\"\']?)", Pattern.MULTILINE);

    public String getHeader() {
        return getHeader(this.message);
    }

    public byte[] getHeaderBytes() {
        return getHeaderBytes(this.message);
    }

    public HttpHeader findHeader(String name) {
        return findHeader(headers(), name);
    }

    public HttpHeader getContentTypeHeader() {
        return getContentTypeHeader(this.message);
    }

    /**
     * httpMessage instance
     */
    /**
     * ヘッダの取得
     *
     * @param message
     * @return
     */
    public static String getHeader(HttpMessage message) {
        byte[] messateByte = message.toByteArray().getBytes();
        byte messateHeader[] = Arrays.copyOfRange(messateByte, 0, message.bodyOffset());
        return StringUtil.getStringRaw(messateHeader);
    }

    /**
     * ヘッダの取得
     *
     * @param message
     * @return
     */
    public static byte[] getHeaderBytes(HttpMessage message) {
        byte[] messateByte = message.toByteArray().getBytes();
        return Arrays.copyOfRange(messateByte, 0, message.bodyOffset());
    }

    /**
     * @param headers
     * @param name
     * @return
     */
    public static HttpHeader findHeader(List<HttpHeader> headers, String name) {
        Optional<HttpHeader> header = headers.stream().filter(h -> h.name().equalsIgnoreCase(name)).findFirst();
        if (header.isPresent()) {
            return header.get();
        } else {
            return null;
        }
    }

    public String getContentMimeType() {
        HttpHeader contentType = getContentTypeHeader(this.message);
        String mimeType = null;
        if (contentType != null) {
            Matcher m = CONTENT_TYPE_MIME.matcher(contentType.value());
            if (m.find()) {
                mimeType = m.group(1);
            }
        }
        return mimeType;
    }

    public static String getEncodeType(HttpRequest httpRequest) {
        HttpHeader contentType = getContentTypeHeader(httpRequest);
        Matcher m = CONTENT_TYPE_MIME.matcher(contentType.value());
        String encodeType = null;
        if (m.find()) {
            encodeType = m.group(1);
        }
        return encodeType;
    }

    public static HttpHeader getContentTypeHeader(HttpRequest httpRequest) {
        return findHeader(httpRequest.headers(), CONTENT_TYPE);
    }

    public static boolean isSecue(HttpRequest httpRequest) {
        HttpHeader header = findHeader(httpRequest.headers(), "Referer");
        if (header != null) {
            return header.value().startsWith("https://");
        } else {
            return false;
        }
    }

    /**
     * httpMessate
     *
     * @param httpMessae
     * @return
     *
     */
    public static HttpHeader getContentTypeHeader(HttpMessage httpMessae) {
        return findHeader(httpMessae.headers(), "Content-Type");
    }

    public static String getContentMimeType(HttpResponse httpResponse) {
        String mimeType = null;
        HttpHeader contentType = getContentTypeHeader(httpResponse);
        if (contentType != null) {
            Matcher m = CONTENT_TYPE_MIME.matcher(contentType.value());
            if (m.find()) {
                mimeType = m.group(2);
            }
        }
        return mimeType;
    }

    public boolean isContentMimeType(String mime) {
        HttpHeader mimeType = this.getContentTypeHeader();
        if (mimeType != null) {
            return mimeType.value().contains(mime);
        } else {
            return false;
        }
    }

    public boolean isContentMimeType(ContentMimeType contentMimeType) {
        String mimeType = this.getContentMimeType();
        if (mimeType == null) {
            return false;
        }
        boolean result = false;
        switch (contentMimeType) {
            case JAVA_SCRIPT:
                result = mimeType.equalsIgnoreCase("application/javascript") || mimeType.equalsIgnoreCase("text/javascript") || mimeType.equalsIgnoreCase("text/x-javascript") || mimeType.equalsIgnoreCase("application/x-javascript");
                break;
            case JSON:
                result = mimeType.equalsIgnoreCase("application/json") || mimeType.equalsIgnoreCase("text/json");
                break;
            case XML:
                result = mimeType.equalsIgnoreCase("application/xml") || mimeType.equalsIgnoreCase("text/xml");
                break;
            case HTML:
                result = mimeType.equalsIgnoreCase("text/html") || mimeType.equalsIgnoreCase("application/xhtml+xml");
                break;
        }
        return result;
    }

    public String getMessageString(String charset) throws UnsupportedEncodingException {
        String messageString = StringUtil.getStringCharset(this.toByteArray().getBytes(), charset);
        return messageString;
    }

    public String getMessageString(Charset charset) {
        String messageString = StringUtil.getStringCharset(this.toByteArray().getBytes(), charset);
        return messageString;
    }

    public byte[] getMessageByte() {
        byte[] messageByte = this.toByteArray().getBytes();
        return messageByte;
    }

    public String getBodyString(String charset, boolean smartDecode) throws UnsupportedEncodingException {
        String body = StringUtil.getStringCharset(this.body().getBytes(), charset);
        if (smartDecode) {
            body = SmartCodec.toUnicodeDecode(body, SmartCodec.ENCODE_PATTERN_LIGHT);
            body = SmartCodec.toHtmlDecode(body, SmartCodec.ENCODE_PATTERN_ASCII);
        }
        return body;
    }

    public String getBodyString(Charset charset, boolean smartDecode) {
        String body = StringUtil.getStringCharset(this.body().getBytes(), charset);
        if (smartDecode) {
            body = SmartCodec.toUnicodeDecode(body, SmartCodec.ENCODE_PATTERN_LIGHT);
            body = SmartCodec.toHtmlDecode(body, SmartCodec.ENCODE_PATTERN_ASCII);
        }
        return body;
    }

    public byte[] getBodyByte() {
        return this.body().getBytes();
    }

    @Override
    public boolean hasHeader(HttpHeader header) {
        return message.hasHeader(header);
    }

    @Override
    public boolean hasHeader(String name) {
        return message.hasHeader(name);
    }

    @Override
    public boolean hasHeader(String name, String value) {
        return message.hasHeader(name, value);
    }

    @Override
    public HttpHeader header(String name) {
        return message.header(name);
    }

    @Override
    public String headerValue(String name) {
        return message.headerValue(name);
    }

    @Override
    public boolean contains(String searchTerm, boolean caseSensitive) {
        return message.contains(searchTerm, caseSensitive);
    }

    @Override
    public boolean contains(Pattern pattern) {
        return message.contains(pattern);
    }

}
