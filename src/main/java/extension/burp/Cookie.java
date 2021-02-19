package extension.burp;

import burp.ICookie;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * RFC 6255 https://tools.ietf.org/html/rfc6265
 *
 * @author isayan
 */
public class Cookie implements ICookie {

    private String domain;
    private String path;
    private ZonedDateTime expiration;
    private String name;
    private String value;
    private long maxage = -1;
    private boolean secure = false;
    private boolean httpOnly = false;

    // ex. Expires=Wed, 09 Jun 2021 10:18:14 GMT
    private final static DateTimeFormatter EXPIRES__FORMAT = DateTimeFormatter.ofPattern("EEE, d MMM uuuu HH:mm:ss zzz").withLocale(Locale.ENGLISH).withResolverStyle(ResolverStyle.SMART);

    public Cookie(ICookie cookie) {
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.domain = cookie.getDomain();
        this.path = cookie.getPath();
        this.expiration = ZonedDateTime.from(cookie.getExpiration().toInstant());
    }

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getDomain() {
        return this.domain;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Date getExpiration() {
        return (this.expiration == null) ? null : Date.from(this.expiration.toInstant());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public long getMaxage() {
        return this.maxage;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    /**
     * Set-Cookie
     *
     * @param cookieString
     * @return
     * @throws ParseException
     */
    public static Cookie parseResponse(String cookieString) throws ParseException {
        Cookie cookie = null;
        Scanner s = new Scanner(cookieString).useDelimiter(";");
        if (s.hasNext()) {
            String target = s.next();
            String[] nameValue = target.split("=");
            String cookieName = nameValue[0].trim();
            if (!cookieName.isEmpty() && target.indexOf('=') > 0) {
                String cookieValue = nameValue.length >= 2 ? nameValue[1].trim() : "";
                cookie = new Cookie(cookieName, cookieValue);
            }
        }
        while (s.hasNext() && cookie != null) {
            String target = s.next();
            String[] attributeNameValue = target.split("=");
            String attributeName = attributeNameValue[0].trim();
            if (attributeNameValue.length < 2) {
                if ("secure".equalsIgnoreCase(attributeName)) {
                    cookie.secure = true;
                } else if ("HttpOnly".equalsIgnoreCase(attributeName)) {
                    cookie.httpOnly = true;
                }
            } else {
                String attributeValue = attributeNameValue[1].trim();
                if ("Expires".equalsIgnoreCase(attributeName)) {
                    try {
                        cookie.expiration = ZonedDateTime.from(EXPIRES__FORMAT.parse(attributeValue));
                    } catch (DateTimeParseException e) {
                        // 
                    }
                } else if ("Max-Age".equalsIgnoreCase(attributeName)) {
                    long maxAge = Long.parseLong(attributeValue);
                    cookie.maxage = maxAge;

                } else if ("Domain".equalsIgnoreCase(attributeName)) {
                    cookie.domain = attributeValue;

                } else if ("Path".equalsIgnoreCase(attributeName)) {
                    cookie.path = attributeValue;
                }

            }

        }
        if (cookie == null) {
            throw new ParseException("missing Cookie", 0);
        }
        return cookie;
    }

    /**
     * Cookie
     *
     * @param cookieString
     * @return
     * @throws ParseException
     */
    public static Cookie[] parseResuest(String cookieString) throws ParseException {
        List<Cookie> cookieList = new ArrayList<>();
        Scanner s = new Scanner(cookieString).useDelimiter(";");
        while (s.hasNext()) {
            String target = s.next();
            String[] nameValue = target.split("=");
            String cookieName = nameValue[0].trim();
            if (!cookieName.isEmpty() && target.indexOf('=') > 0) {
                String cookieValue = nameValue.length >= 2 ? nameValue[1].trim() : "";
                Cookie cookie = new Cookie(cookieName, cookieValue);
                cookieList.add(cookie);
            }
        }
        return cookieList.toArray(new Cookie[]{});
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append("=");
        builder.append(value);
        builder.append(";");
        if (this.path != null) {
            builder.append(" ");
            builder.append("Path=");
            builder.append(this.path);
            builder.append(";");
        }
        if (this.domain != null) {
            builder.append(" ");
            builder.append("Domain=");
            builder.append(this.domain);
        }
        if (this.expiration != null) {
            builder.append(" ");
            builder.append("Expires=");
            builder.append(EXPIRES__FORMAT.format(this.expiration));
            builder.append(";");
        }
        if (this.maxage >= 0) {
            builder.append(" ");
            builder.append("Max-Age=");
            builder.append(this.maxage);
            builder.append(";");
        }
        if (this.secure) {
            builder.append(" ");
            builder.append("Secure");
            builder.append(";");
        }
        if (this.httpOnly) {
            builder.append(" ");
            builder.append("HttpOnly");
            builder.append(";");
        }
        return builder.toString();
    }

}
