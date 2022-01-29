package extension.helpers;

import burp.IHttpService;
import burp.IRequestInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isayan
 */
public class HttpRequest extends HttpMessage implements HttpRequestLine {
    private final static String CONTENT_TYPE_URL_ENCODED_IDENT = "application/x-www-form-urlencoded";
    private final static String CONTENT_TYPE_MULTIPART_IDENT = "multipart/form-data";
    private final static String CONTENT_TYPE_JSON_IDENT = "application/json";
    private final static String CONTENT_TYPE_TEXT_JSON_IDENT = "text/json";
    private final static String CONTENT_TYPE_XML_IDENT = "application/xml";
    private final static String CONTENT_TYPE_TEXT_XML_IDENT = "text/xml";
    private final static String CONTENT_TYPE_AMF_IDENT = "application/x-amf";

    private Boolean useSSL = null;
    private final static Pattern REQ_URL = Pattern.compile("^([a-zA-Z]+?)\\s+(.*?)\\s+(.*?)$", Pattern.MULTILINE);
    private final static Pattern HOST_HEADER = Pattern.compile("(.*?)(:(\\d+))?", Pattern.MULTILINE);
    private HttpRequestLine requestLine;

    protected HttpRequest() {
        super();
    }

    public HttpRequest(HttpMessage message) throws ParseException {
        super(message);
        this.parseHeader();
    }

    public HttpRequest(HttpMessage message, boolean useSSL) throws ParseException {
        this(message);
        this.useSSL = useSSL;
    }

    protected void parseHeader() throws ParseException {
        this.requestLine = parseHttpRequestLine(this.getHeader());
    }

    protected void parseParameter() throws ParseException {
        

    }
    
    
    @Override
    public String getRequestLine() {
        return this.requestLine.getRequestLine();
    }

    @Override
    public String getMethod() {
        return this.requestLine.getMethod();
    }

    @Override
    public String getUri() {
        return this.requestLine.getUri();
    }

    @Override
    public String getProtocolVersion() {
        return this.requestLine.getProtocolVersion();
    }

    public String getUrl() {
        String url = String.format("%s://%s:%d%s", new Object[]{HttpUtil.getDefaultProtocol(this.isSSL()), this.getHost(), this.getPort(), this.getUri()});
        return HttpUtil.normalizeURL(url);
    }

    public String getUrl(boolean useSSL) {
        String url = String.format("%s://%s:%d%s", new Object[]{HttpUtil.getDefaultProtocol(useSSL), this.getHost(), this.getPort(), this.getUri()});
        return HttpUtil.normalizeURL(url);
    }

    public String getUrl(IHttpService httpService) {
        String url = String.format("%s://%s:%d%s", new Object[]{httpService.getProtocol(), httpService.getHost(), httpService.getPort(), this.getUri()});
        return HttpUtil.normalizeURL(url);
    }

    public static HttpRequestLine parseHttpRequestLine(byte[] message) throws ParseException {
        String request = StringUtil.getBytesRawString(message);
        return new HttpRequest(parseHttpMessage(request));
    }

    public static HttpRequestLine parseHttpRequestLine(String message) throws ParseException {
        final Matcher requestLine = REQ_URL.matcher(message);
        if (!requestLine.find()) {
            throw new ParseException("Illegal HttpRequest Format:" + message, 1);
        }
        return new HttpRequestLine() {

            @Override
            public String getRequestLine() {
                return requestLine.group(0);
            }

            @Override
            public String getMethod() {
                return requestLine.group(1);
            }

            @Override
            public String getUri() {
                String path = requestLine.group(2);
                if (HttpUtil.startsWithHttp(path)) {
                    URL url;
                    try {
                        url = new URL(path);
                        return url.getFile();
                    } catch (MalformedURLException ex) {
                        return path;
                    }
                } else {
                    return path;
                }
            }

            @Override
            public String getProtocolVersion() {
                return requestLine.group(3);
            }
        };
    }

    public static HttpRequest parseHttpRequest(byte[] message) throws ParseException {
        return new HttpRequest(parseHttpMessage(StringUtil.getBytesRawString(message)));
    }

    public static HttpRequest parseHttpRequest(byte[] message, boolean useSSL) throws ParseException {
        return new HttpRequest(parseHttpMessage(StringUtil.getBytesRawString(message)), useSSL);
    }

    public static HttpRequest parseHttpRequest(String message) throws ParseException {
        return new HttpRequest(parseHttpMessage(message));
    }

    public static HttpRequest parseHttpRequest(String message, boolean useSSL) throws ParseException {
        return new HttpRequest(parseHttpMessage(message), useSSL);
    }

    public static String makeGetRequest(String host, int port, String uri) {
        return makeGetRequest(host, port, uri, new String[0]);
    }

    public static String makeGetRequest(String host, int port, String uri, String[] headers) {
        StringBuilder buff = new StringBuilder();
        buff.append(String.format("%s %s %s", "GET", uri, "HTTP/1.1"));
        buff.append(HttpMessage.LINE_TERMINATE);
        if (port == 80 || port == 443) {
            buff.append(String.format("%s: %s", "Host", host));
        } else {
            buff.append(String.format("%s: %s:%s", "Host", host, port));
        }
        for (String header : headers) {
            buff.append(String.format("%s", header));
        }
        buff.append(HttpMessage.LINE_TERMINATE);
        buff.append(HttpMessage.LINE_TERMINATE);
        return buff.toString();
    }

    public static byte[] replaceProxyRequest(byte[] request, String host, int port) {
        String message = StringUtil.getBytesRawString(request);
        StringBuffer buff = new StringBuffer();
        Matcher m = REQ_URL.matcher(message);
        if (m.find()) {
            m.appendReplacement(buff, String.format("$1 http://%s:%s$2 $3", host, port));
        }
        m.appendTail(buff);
        return StringUtil.getBytesRaw(buff.toString());
    }

    public String getHostHeader() {
        return this.getHeader("Host");
    }

    public String getConnectionHeader() {
        return this.getHeader("Connection");
    }

    public String getCookieHeader() {
        return this.getHeader("Cookie");
    }

    public String getHost() {
        String value = getHostHeader();
        if (value != null) {
            Matcher m = HOST_HEADER.matcher(value);
            if (m.matches()) {
                return m.group(1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public byte getContentType() {
        String encType = this.getContentTypeHeader();
        byte contentType = IRequestInfo.CONTENT_TYPE_NONE;
        if (encType == null) {
            contentType = IRequestInfo.CONTENT_TYPE_NONE;
        }
        else if (CONTENT_TYPE_URL_ENCODED_IDENT.equals(encType)) {
            contentType = IRequestInfo.CONTENT_TYPE_URL_ENCODED;
        }
        else if (CONTENT_TYPE_MULTIPART_IDENT.equals(encType)){
            contentType = IRequestInfo.CONTENT_TYPE_MULTIPART;        
        }
        else if (CONTENT_TYPE_JSON_IDENT.equals(encType) || CONTENT_TYPE_TEXT_JSON_IDENT.equals(encType)){
            contentType = IRequestInfo.CONTENT_TYPE_JSON;        
        }
        else if (CONTENT_TYPE_XML_IDENT.equals(encType) || CONTENT_TYPE_TEXT_XML_IDENT.equals(encType)){
            contentType = IRequestInfo.CONTENT_TYPE_XML;        
        }
        else if (CONTENT_TYPE_AMF_IDENT.equals(encType)){
            contentType = IRequestInfo.CONTENT_TYPE_AMF;        
        }
        return contentType;
    }
    
    public String getEnctype() {
        return this.getContentTypeHeader();
    }

    public boolean isGET() {
        return this.getMethod().equals("GET");
    }

    public boolean isPOST() {
        return this.getMethod().equals("POST");
    }

    public boolean isSSL() {
        if (this.useSSL == null) {
            String value = HttpUtil.getHeader("Referer", this.getHeaders());
            if (value != null) {
                return value.startsWith("https://");
            } else {
                return false;
            }
        } else {
            return this.useSSL;
        }
    }

    public int getPort() {
        String value = getHostHeader();
        if (value == null) return -1;
        Matcher m = HOST_HEADER.matcher(value);
        if (m.matches()) {
            return ConvertUtil.parseIntDefault(m.group(3), HttpUtil.getDefaultPort(isSSL()));
        } else {
            return HttpUtil.getDefaultPort(isSSL());
        }
    }

//    private String [] parseBody() {
//        
//        
//        
//    }
    
    
    
    @Override
    public String getGuessCharset() {
        String charset = super.getGuessCharset();
        if (charset == null) {
            charset = HttpUtil.getGuessCode(this.getBodyBytes());
        }
        return normalizeCharset(charset);
    }

}
