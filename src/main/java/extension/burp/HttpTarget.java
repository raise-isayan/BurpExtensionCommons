package extension.burp;

import extension.helpers.HttpUtil;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;

/**
 *
 * @author isayan
 */
public class HttpTarget implements burp.api.montoya.http.HttpService {

    public static final String PROTOCOL_ANY = "Any";
    public static final String PROTOCOL_HTTP = "http";
    public static final String PROTOCOL_HTTPS = "https";
    public static final String[] PROTOCOLS = {PROTOCOL_HTTP, PROTOCOL_HTTPS};

    private String host;
    private int port;
    private boolean secure;
    private String ipAddress;

    public HttpTarget(URL url) {
        this(url.getHost(), (url.getPort() > 0) ? url.getPort() : url.getDefaultPort(), isSecure(url.getProtocol()));
    }

    public HttpTarget(String host, int port, boolean secure) {
        this(host, port, secure, null);
    }

    public HttpTarget(String host, int port, boolean secure, String ipAddress) {
        this.host = host;
        this.port = port;
        this.secure = secure;
        this.ipAddress = ipAddress;
    }

    public static String toURLString(burp.api.montoya.http.HttpService httpService) {
        if (HttpUtil.getDefaultPort(getProtocol(httpService.secure())) == httpService.port()) {
            return String.format("%s://%s/", getProtocol(httpService.secure()), httpService.host());
        } else {
            return String.format("%s://%s:%d/", getProtocol(httpService.secure()), httpService.host(), httpService.port());
        }
    }

    public static HttpTarget getHttpTarget(burp.api.montoya.http.HttpService service) {
        return new HttpTarget(service.host(), service.port(), service.secure());
    }

    public static HttpTarget getHttpTarget(final String host, final int port, final boolean secure) {
        return new HttpTarget(host, port, secure);
    }

    public static HttpTarget getHttpTarget(final String host, final int port, final String protocol) {
        return new HttpTarget(host, port, isSecure(protocol));
    }

    public static HttpTarget getHttpTarget(final String target) throws MalformedURLException {
        return new HttpTarget(URI.create(target).toURL());
    }

    public static String getProtocol(boolean secure) {
        return secure ? PROTOCOL_HTTPS : PROTOCOL_HTTP;
    }

    public static boolean isSecure(String protocol) {
        return protocol.equals(PROTOCOL_HTTPS);
    }

    @Override
    public String host() {
        return this.host;
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public boolean secure() {
        return this.secure;
    }

    @Override
    public String ipAddress() {
        if (this.ipAddress != null) {
            return this.ipAddress;
        } else {
            try {
                return InetAddress.getByName(this.host).getHostAddress();
            } catch (UnknownHostException ex) {
                return null;
            }
        }
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isSecure() {
        return this.secure;
    }

}
