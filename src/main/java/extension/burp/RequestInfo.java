package extension.burp;

import burp.IParameter;
import burp.IRequestInfo;
import extension.helpers.StringUtil;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author isayan
 */
public class RequestInfo implements IRequestInfo {

    private final IRequestInfo requestInfo;
    private final byte [] content;
    
    public RequestInfo(IRequestInfo requestInfo, byte [] content) {
        this.requestInfo = requestInfo;
        this.content = content;
    }
    
    @Override
    public String getMethod() {
        return this.requestInfo.getMethod();
    }

    @Override
    public URL getUrl() {
        return this.requestInfo.getUrl();
    }

    @Override
    public List<String> getHeaders() {
        return this.requestInfo.getHeaders();
    }

    @Override
    public List<IParameter> getParameters() {
        return this.requestInfo.getParameters();
    }

    @Override
    public int getBodyOffset() {
        return this.requestInfo.getBodyOffset();
    }

    @Override
    public byte getContentType() {
        return this.requestInfo.getContentType();
    }

    public byte [] getRawMessage() { 
        return this.content;
    }
        
    public String getHeader() { 
        return StringUtil.getStringCharset(Arrays.copyOfRange(this.content, 0, this.requestInfo.getBodyOffset()), StandardCharsets.ISO_8859_1);
    }
    
    public String getBody(String charsetName) throws UnsupportedEncodingException { 
        return StringUtil.getStringCharset(this.getBodyBytes(), charsetName);
    }

    public String getBody(Charset charset) { 
        return StringUtil.getStringCharset(this.getBodyBytes(), charset);
    }
    
    public final byte [] getBodyBytes() { 
        return Arrays.copyOfRange(this.content, this.requestInfo.getBodyOffset(), content.length);
    }

    public static byte[] getBodyBytes(IRequestInfo reqInfo, byte[] content) {
        return Arrays.copyOfRange(content, reqInfo.getBodyOffset(), content.length);
    }
    
}