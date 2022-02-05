package extension.helpers;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isayan
 */
public class HttpResponse extends HttpMessage implements HttpStatusLine {

    private final static Pattern RES_STATUS = Pattern.compile("^(.*?)\\s+(\\d+)\\s+(.*?)$", Pattern.MULTILINE);
    private HttpStatusLine statusLine;

    protected HttpResponse() {
        super();
    }

    public HttpResponse(HttpMessage message) throws ParseException {
        super(message);
        this.parseHeader();
    }

    protected void parseHeader() throws ParseException {
        this.statusLine = parseHttpStatusLine(this.getHeader());
    }

    @Override
    public String getStatusLine() {
        return this.statusLine.getStatusLine();
    }

    @Override
    public String getProtocolVersion() {
        return this.statusLine.getProtocolVersion();
    }

    @Override
    public short getStatusCode() {
        return this.statusLine.getStatusCode();
    }

    @Override
    public String getReasonPhrase() {
        return this.statusLine.getReasonPhrase();
    }
    private final Pattern RESMETA_SET = Pattern.compile("<meta (?:.*?)charset=[\"\']?([\\w_-]+)[\"\']?\\W+", Pattern.CASE_INSENSITIVE);
    //<META http-equiv="Content-Type" content="text/html; charset=Shift_JIS">

    public boolean isContentMimeType(String mime) {
        String mimeType = this.getContentTypeHeader();
        if (mimeType != null) {
            return mimeType.contains(mime);
        } else {
            return false;
        }
    }

//    public boolean isContentMimeType(ContentType contentType) {
//        String mimeType = this.getContentTypeHeader();
//        if (mimeType == null) {
//            return false;
//        }        
//        boolean result = false;
//        switch (contentType) {
//            case JAVA_SCRIPT:
//                result = mimeType.equalsIgnoreCase("text/javascript") || mimeType.equalsIgnoreCase("application/javascript") || mimeType.equalsIgnoreCase("application/x-javascript");
//                break;
//            case JSON:
//                result = mimeType.equalsIgnoreCase("application/json") || mimeType.equalsIgnoreCase("application/javascript");
//                break;
//            case XML:
//                result = mimeType.equalsIgnoreCase("application/xml") && mimeType.equalsIgnoreCase("application/javascript") || mimeType.equalsIgnoreCase("text/xml");
//                break;
//        }
//        return result;
//    }
        
    @Override
    public String getGuessCharset() {
        String charset = super.getGuessCharset();
        if (charset == null) {
            Matcher m2 = RESMETA_SET.matcher(this.getBody());
            if (m2.find()) {
                charset = m2.group(1);
            }
        }
        if (charset == null) {
            charset = HttpUtil.getGuessCode(this.getBodyBytes());
        }
        return normalizeCharset(charset);
    }

    public static HttpStatusLine parseHttpStatusLine(byte[] message) throws ParseException {
        String request = StringUtil.getBytesRawString(message);
        return parseHttpStatusLine(request);
    }

    public static HttpStatusLine parseHttpStatusLine(String message) throws ParseException {
        final Matcher statusLine = RES_STATUS.matcher(message);
        if (!statusLine.find()) {
            throw new ParseException("Illegal Response Format:" + message, 1);
        }
        return new HttpStatusLine() {

            @Override
            public String getStatusLine() {
                return statusLine.group(0);
            }

            @Override
            public String getProtocolVersion() {
                return statusLine.group(1);
            }

            @Override
            public short getStatusCode() {
                return (short) ConvertUtil.parseIntDefault(statusLine.group(2), 0);
            }

            @Override
            public String getReasonPhrase() {
                return statusLine.group(3);
            }

        };
    }

    public static HttpResponse parseHttpResponse(byte[] message) throws ParseException {
        return new HttpResponse(parseHttpMessage(message));
    }

    public static HttpResponse parseHttpResponse(String message) throws ParseException {
        return new HttpResponse(parseHttpMessage(message));
    }

}
