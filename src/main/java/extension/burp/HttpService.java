package extension.burp;

import burp.IHttpRequestResponse;
import burp.IHttpService;
import java.net.URL;

/**
 *
 * @author isayan
 */
public class HttpService implements IHttpService {
    public static final String PROTOCOL_ANY = "Any";
    public static final String PROTOCOL_HTTP = "http";
    public static final String PROTOCOL_HTTPS = "https";
    public static final String [] PROTOCOLS = {PROTOCOL_ANY, PROTOCOL_HTTP, PROTOCOL_HTTPS};

    private String host;
    private int port;
    private String protocol;

    public HttpService(URL url) {
        this(url.getHost(), (url.getPort() > 0) ? url.getPort() : url.getDefaultPort(), url.getProtocol());
    }

    public HttpService(IHttpService service) {
        this(service.getHost(), service.getPort(), service.getProtocol());
    }

    public HttpService(String host, int port, String protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    public boolean isHttps() {
        return PROTOCOL_HTTPS.equalsIgnoreCase(protocol);
    }

    public static IHttpService getHttpService(final IHttpRequestResponse messageInfo) {
        try {
            return messageInfo.getHttpService();
        } catch (AbstractMethodError ex) {
        } catch (IllegalArgumentException ex) {
        } catch (LinkageError ex) {
        } catch (Exception ex) {
        }
        return null;
    }

    public static IHttpService getHttpService(final String host, final int port, final boolean useHttps) {
        return new HttpService(host, port, (useHttps ? PROTOCOL_HTTPS : PROTOCOL_HTTP));
    }
    
}
