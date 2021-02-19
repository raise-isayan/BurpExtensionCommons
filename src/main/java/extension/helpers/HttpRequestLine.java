package extension.helpers;

/**
 *
 * @author isayan
 */
public interface HttpRequestLine {

    public String getRequestLine();

    public String getMethod();

    public String getUri();

    public String getProtocolVersion();

}
