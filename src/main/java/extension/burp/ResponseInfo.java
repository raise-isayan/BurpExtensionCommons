package extension.burp;

import burp.ICookie;
import burp.IResponseInfo;
import extension.helpers.StringUtil;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author isayan
 */
public class ResponseInfo implements IResponseInfo {

    private final IResponseInfo responseInfo;
    private final byte [] content;
    
    public ResponseInfo(IResponseInfo responseInfo, byte [] content) {
        this.responseInfo = responseInfo;
        this.content = content;
    }

    public byte [] getRawMessage() { 
        return this.content;
    }
    
    @Override
    public List<String> getHeaders() {
        return this.responseInfo.getHeaders();
    }

    @Override
    public int getBodyOffset() {
        return this.responseInfo.getBodyOffset();
    }

    @Override
    public short getStatusCode() {
        return this.responseInfo.getStatusCode();
    }

    @Override
    public List<ICookie> getCookies() {
        return this.responseInfo.getCookies();
    }

    @Override
    public String getStatedMimeType() {
        return this.responseInfo.getStatedMimeType();
    }

    @Override
    public String getInferredMimeType() {
        return this.responseInfo.getInferredMimeType();
    }
    
    public String getHeader() { 
        return StringUtil.getStringCharset(Arrays.copyOfRange(this.content, 0, this.responseInfo.getBodyOffset()), StandardCharsets.ISO_8859_1);
    }
    
    public String getBody(String charsetName) throws UnsupportedEncodingException { 
        return StringUtil.getStringCharset(this.getBodyBytes(), charsetName);
    }

    public String getBody(Charset charset) { 
        return StringUtil.getStringCharset(this.getBodyBytes(), charset);
    }
    
    public final byte [] getBodyBytes() { 
        return Arrays.copyOfRange(this.content, this.responseInfo.getBodyOffset(), content.length);
    }

    public static byte[] getBodyBytes(IResponseInfo resInfo, byte[] content) {
        return Arrays.copyOfRange(content, resInfo.getBodyOffset(), content.length);
    }

    
}
