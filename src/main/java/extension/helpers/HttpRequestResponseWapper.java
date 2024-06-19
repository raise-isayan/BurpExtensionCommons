package extension.helpers;

import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.Marker;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.handler.TimingData;
import burp.api.montoya.http.message.ContentType;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.proxy.ProxyHttpRequestResponse;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 *
 * @author isayan
 */
public class HttpRequestResponseWapper implements HttpRequestResponse {

    private final HttpRequestResponse httpRequestResponse;

    public HttpRequestResponseWapper(HttpRequestResponse httpRequestResponse) {
        this.httpRequestResponse = httpRequestResponse;
    }

    @Override
    public HttpRequest request() {
        return this.httpRequestResponse.request();
    }

    @Override
    public HttpResponse response() {
        return this.httpRequestResponse.response();
    }

    @Override
    public HttpService httpService() {
        return this.httpRequestResponse.httpService();
    }

    @Override
    public Annotations annotations() {
        return this.httpRequestResponse.annotations();
    }

    @Override
    public Optional<TimingData> timingData() {
        return this.httpRequestResponse.timingData();
    }

    @Override
    @Deprecated(forRemoval = true)
    public String url() {
        return this.httpRequestResponse.request().url();
    }

    @Override
    public boolean hasResponse() {
        return this.httpRequestResponse.hasResponse();
    }

    @Override
    @Deprecated(forRemoval = true)
    public ContentType contentType() {
        return this.httpRequestResponse.request().contentType();
    }

    @Override
    @Deprecated(forRemoval = true)
    public short statusCode() {
        return this.httpRequestResponse.response().statusCode();
    }

    @Override
    public List<Marker> requestMarkers() {
        return this.httpRequestResponse.requestMarkers();
    }

    @Override
    public List<Marker> responseMarkers() {
        return this.httpRequestResponse.responseMarkers();
    }

    @Override
    public boolean contains(String searchTerm, boolean caseSensitive) {
        return this.httpRequestResponse.contains(searchTerm, caseSensitive);
    }

    @Override
    public boolean contains(Pattern pattern) {
        return this.httpRequestResponse.contains(pattern);
    }

    @Override
    public HttpRequestResponse copyToTempFile() {
        return this.httpRequestResponse.copyToTempFile();
    }

    @Override
    public HttpRequestResponse withAnnotations(Annotations annotations) {
        return this.httpRequestResponse.withAnnotations(annotations);
    }

    @Override
    public HttpRequestResponse withRequestMarkers(List<Marker> requestMarkers) {
        return this.httpRequestResponse.withRequestMarkers(requestMarkers);
    }

    @Override
    public HttpRequestResponse withRequestMarkers(Marker... requestMarkers) {
        return this.httpRequestResponse.withRequestMarkers(requestMarkers);
    }

    @Override
    public HttpRequestResponse withResponseMarkers(List<Marker> responseMarkers) {
        return this.httpRequestResponse.withResponseMarkers(responseMarkers);
    }

    @Override
    public HttpRequestResponse withResponseMarkers(Marker... responseMarkers) {
        return this.httpRequestResponse.withResponseMarkers(responseMarkers);
    }

    public HttpRequestResponse valueOf(ProxyHttpRequestResponse proxyHttpRequestResponse) {
        final HttpRequestResponse requestResponse = new HttpRequestResponse() {
            @Override
            public HttpRequest request() {
                return proxyHttpRequestResponse.request();
            }

            @Override
            public HttpResponse response() {
                return proxyHttpRequestResponse.response();
            }

            @Override
            public HttpService httpService() {
                return proxyHttpRequestResponse.httpService();
            }

            @Override
            public Annotations annotations() {
                return proxyHttpRequestResponse.annotations();
            }

            @Override
            public Optional<TimingData> timingData() {
                return Optional.ofNullable(proxyHttpRequestResponse.timingData());
            }

            @Override
            @Deprecated(forRemoval = true)
            public String url() {
                return proxyHttpRequestResponse.request().url();
            }

            @Override
            public boolean hasResponse() {
                return proxyHttpRequestResponse.hasResponse();
            }

            @Override
            @Deprecated(forRemoval = true)
            public ContentType contentType() {
                return proxyHttpRequestResponse.request().contentType();
            }

            @Override
            @Deprecated(forRemoval = true)
            public short statusCode() {
                return proxyHttpRequestResponse.response().statusCode();
            }

            @Override
            public List<Marker> requestMarkers() {
                return proxyHttpRequestResponse.request().markers();
            }

            @Override
            public List<Marker> responseMarkers() {
                return proxyHttpRequestResponse.response().markers();
            }

            @Override
            public boolean contains(String searchTerm, boolean caseSensitive) {
                return proxyHttpRequestResponse.contains(searchTerm, caseSensitive);
            }

            @Override
            public boolean contains(Pattern pattern) {
                return proxyHttpRequestResponse.contains(pattern);
            }

            @Override
            public HttpRequestResponse copyToTempFile() {
                final HttpRequest request = proxyHttpRequestResponse.request().copyToTempFile();
                final HttpResponse response = proxyHttpRequestResponse.hasResponse() ? null : proxyHttpRequestResponse.response();
                return HttpRequestResponse.httpRequestResponse(request, response);
            }

            @Override
            public HttpRequestResponse withAnnotations(Annotations annotations) {
                return HttpRequestResponse.httpRequestResponse(proxyHttpRequestResponse.request(), proxyHttpRequestResponse.response(), annotations);
            }

            @Override
            public HttpRequestResponse withRequestMarkers(List<Marker> requestMarkers) {
                final HttpRequest request = proxyHttpRequestResponse.request().withMarkers(requestMarkers);
                return HttpRequestResponse.httpRequestResponse(request, proxyHttpRequestResponse.response());
            }

            @Override
            public HttpRequestResponse withRequestMarkers(Marker... requestMarkers) {
                final HttpRequest request = proxyHttpRequestResponse.request().withMarkers(requestMarkers);
                return HttpRequestResponse.httpRequestResponse(request, proxyHttpRequestResponse.response());
            }

            @Override
            public HttpRequestResponse withResponseMarkers(List<Marker> responseMarkers) {
                final HttpResponse response = proxyHttpRequestResponse.response().withMarkers(responseMarkers);
                return HttpRequestResponse.httpRequestResponse(proxyHttpRequestResponse.request(), response);
            }

            @Override
            public HttpRequestResponse withResponseMarkers(Marker... responseMarkers) {
                final HttpResponse response = proxyHttpRequestResponse.response().withMarkers(responseMarkers);
                return HttpRequestResponse.httpRequestResponse(proxyHttpRequestResponse.request(), response);
            }

        };
        return requestResponse;
    }


}
