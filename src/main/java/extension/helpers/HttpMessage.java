package extension.helpers;

import java.util.regex.Matcher;

/**
 *
 * @author isayan
 */
public class HttpMessage {

    private String header = "";
    private String body = "";

    private boolean modifiedHeader = false;
    private boolean modifiedBody = false;

    public boolean isModifiedHeader() {
        return this.modifiedHeader;
    }

    public boolean isModifiedBody() {
        return this.modifiedBody;
    }

    /**
     * @return the header
     */
    public String getHeader() {
        return this.header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        if (!this.header.equals(header)) {
            this.modifiedHeader = true;
        }
        this.header = header;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return this.body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        if (!this.body.equals(body)) {
            this.modifiedBody = true;
        }
        this.body = body;
    }

    public String getMessage() {
        StringBuilder message = new StringBuilder();
        Matcher m = HttpUtil.HTTP_LINESEP.matcher(this.header);
        String headerTrim = m.replaceFirst(HttpUtil.LINE_TERMINATE);
        message.append(headerTrim);
        message.append(HttpUtil.LINE_TERMINATE);
        message.append(this.body);
        return message.toString();
    }

    public static HttpMessage httpMessage(String header, String body) {
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.header = header;
        httpMessage.body = body;
        return httpMessage;
    }

    public static HttpMessage httpMessage(String message) {
        HttpMessage httpMessage = new HttpMessage();
        int bodyOffset = message.indexOf(HttpUtil.LINE_TERMINATE + HttpUtil.LINE_TERMINATE);
        if (bodyOffset > -1) {
            httpMessage.header = message.substring(0, bodyOffset + HttpUtil.LINE_TERMINATE.length());
            httpMessage.body = message.substring(bodyOffset + HttpUtil.LINE_TERMINATE.length() * 2);
        }
        return httpMessage;
    }
}
