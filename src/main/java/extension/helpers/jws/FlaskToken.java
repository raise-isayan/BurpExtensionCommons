package extension.helpers.jws;

import com.google.gson.JsonSyntaxException;
import extension.helpers.BouncyUtil;
import extension.helpers.ConvertUtil;
import extension.helpers.MatchUtil;
import extension.helpers.StringUtil;
import extension.helpers.json.JsonUtil;
import extension.view.base.CaptureItem;
import java.nio.ByteOrder;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author isayan
 */
public class FlaskToken implements JsonToken {

    private final static Logger logger = Logger.getLogger(FlaskToken.class.getName());

    private final static FlaskToken flaskInstance = new FlaskToken();

    private FlaskToken.SessionData payload;
    private FlaskToken.Timestamp timestamp;
    private JsonToken.Signature signature;

    public FlaskToken() {
    }

    public FlaskToken(FlaskToken token) {
        this.payload = token.payload;
        this.timestamp = token.timestamp;
        this.signature = token.signature;
    }

    public FlaskToken(FlaskToken.SessionData payload, FlaskToken.Timestamp timestamp, FlaskToken.Signature signature) {
        this.payload = payload;
        this.timestamp = timestamp;
        this.signature = signature;
    }

    public FlaskToken(String payloadPart, String timestampPart, String signaturePart) {
        this.payload = new FlaskToken.SessionData(payloadPart);
        this.timestamp = new Timestamp(timestampPart);
        this.signature = new Signature(signaturePart);
    }

    public FlaskToken getInstance() {
        return flaskInstance;
    }

    public static class SessionData extends Payload {

        private boolean compress = false;

        public SessionData(String tokenPart) {
            this(false, tokenPart);
        }

        public SessionData(boolean compress, String tokenPart) {
            super(tokenPart);
            this.compress = compress;
        }

        public boolean isCompress() {
            return this.compress;
        }

        /**
         * @param pretty
         * @return the payload
         */
        @Override
        public String toJSON(boolean pretty) {
            if (this.compress) {
                return JsonUtil.prettyJson(JsonToken.decompressZlibBase64(this.getPart()), pretty);
            } else {
                return JsonUtil.prettyJson(JsonToken.decodeBase64UrlSafe(this.getPart()), pretty);
            }
        }

    }

    public static class Timestamp implements JsonSegment {

        private static final long EPOCH_TIME_MAX = 253402300799L * 1000L;

        private String tokenPart;

        public Timestamp(String tokenPart) {
            this.tokenPart = tokenPart;
        }

        public boolean isValid() {
            try {
                long epochTime = this.getTimestampAsEpoch();
                return (0 <= epochTime && epochTime < EPOCH_TIME_MAX);
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }

        @Override
        public String getPart() {
            return this.tokenPart;
        }

        public long getTimestampAsEpoch() {
            byte[] decode = Base64.getUrlDecoder().decode(this.tokenPart);
            return ConvertUtil.bytesToLong(decode, ByteOrder.BIG_ENDIAN) * 1000L;
        }

        public Date getTimestampAsDate() {
            return new Date(getTimestampAsEpoch());
        }

        public String getsDecodeBase64Url() {
            return JsonToken.decodeBase64UrlSafe(this.tokenPart);
        }

        public void setEncodeBase64Url(String value) {
            this.tokenPart = JsonToken.encodeBase64UrlSafe(value);
        }

    }

    private final static String FLASK_PATTEN = "(?:\\.|%2[eE])?(e(?:[0-9a-zA-Z_-]|%2[dD]|%5[fF]){5,})(?:\\.|%2[eE])((?:[0-9a-zA-Z_-]|%2[dD]|%5[fF]){4,8})(?:\\.|%2[eE])((?:[0-9a-zA-Z_-]|%2[dD]|%5[fF]){20,30})";
    private final static Pattern PTN_FLASK = Pattern.compile(FLASK_PATTEN);

    public final static Pattern FLASK_COOKIE = Pattern.compile("[\\w-]+=" + "(" + FLASK_PATTEN + ")");

    public boolean isValidToken(String value) {
        return parseToken(value, true) != null;
    }

    @Override
    public boolean isSignFormat() {
        return true;
    }

//    public static boolean containsTokenFormat(String value) {
//        Matcher m = PTN_FLASK.matcher(value);
//        if (m.find()) {
//            return isTokenFormat(m.group(0));
//        }
//        return false;
//    }
    public static boolean containsTokenFormat(String value) {
        CaptureItem[] tokens = JWSUtil.findTokenFormat(value, JWSUtil.INCLUDE_SIGNATURE | JWSUtil.FLASK_COMPRESS);
        return tokens.length > 0;
    }

    public static boolean containsValidToken(String value) {
        CaptureItem[] tokens = JWSUtil.findTokenFormat(value, JWSUtil.INCLUDE_SIGNATURE | JWSUtil.FLASK_COMPRESS);
        final FlaskToken flask = new FlaskToken();
        for (int i = 0; i < tokens.length; i++) {
            if (flask.parseToken(tokens[i].getCaptureValue(), true) != null) {
                return true;
            }
        }
        return false;
    }

//    public static CaptureItem[] findToken(String value) {
//        List<CaptureItem> tokens = new ArrayList<>();
//        Matcher m = PTN_FLASK.matcher(value);
//        while (m.find()) {
//            String capture = m.group(0);
//            if (isTokenFormat(capture)) {
//                CaptureItem item = new CaptureItem();
//                item.setCaptureValue(capture);
//                item.setStart(m.start());
//                item.setEnd(m.end());
//                tokens.add(item);
//            }
//        }
//        return tokens.toArray(CaptureItem[]::new);
//    }
//    protected static boolean isTokenFormat(String value) {
//        Matcher m = PTN_FLASK.matcher(value);
//        if (m.matches()) {
//            if (flaskInstance.parseToken(value, true) != null) {
//                return true;
//            }
//        }
//        return false;
//    }
    @Override
    public FlaskToken parseToken(String value, boolean matches) {
        FlaskToken token = null;
        if (MatchUtil.isUrlencoded(value)) {
            value = JsonToken.decodeUrl(value);
        }
        CaptureItem[] items = JWSUtil.findTokenFormat(value, JWSUtil.INCLUDE_SIGNATURE | JWSUtil.FLASK_COMPRESS);
        boolean find = items.length > 0;
        if (find) {
            for (int i = 0; i < items.length; i++) {
                String jws_token = items[i].getCaptureValue();
                if (matches && !(items[i].start() == 0 && items[i].end() == jws_token.length())) {
                    break;
                }
                boolean isCompress = jws_token.startsWith(".");
                FlaskToken fkask = new FlaskToken();
                String[] segment = JWSUtil.splitSegment(isCompress ? jws_token.substring(1) : jws_token);
                fkask.payload = new FlaskToken.SessionData(isCompress, segment[0]);
                fkask.timestamp = new FlaskToken.Timestamp(segment[1]);
                fkask.signature = new JsonToken.Signature(segment[2]);
                if (fkask.isValid()) {
                    token = fkask;
                    break;
                }
            }
        }
        return token;
    }

    /**
     * @return the token
     */
    @Override
    public String getToken() {
        StringBuilder buff = new StringBuilder();
        if (this.payload.compress) {
            buff.append(".");
        }
        buff.append(payload.getPart());
        buff.append(".");
        buff.append(timestamp.getPart());
        buff.append(".");
        buff.append(signature.getPart());
        return buff.toString();
    }

    @Override
    public String getData() {
        StringBuilder buff = new StringBuilder();
        if (this.payload.compress) {
            buff.append(".");
        }
        buff.append(payload.getPart());
        buff.append(".");
        buff.append(timestamp.getPart());
        return buff.toString();
    }

    public static String getData(boolean compress, String payloadPart, String timestampPart) {
        StringBuilder buff = new StringBuilder();
        if (compress) {
            buff.append(".");
        }
        buff.append(payloadPart);
        buff.append(".");
        buff.append(timestampPart);
        return buff.toString();
    }

    public static String getData(Payload payload, FlaskToken.Timestamp timestamp) {
        StringBuilder buff = new StringBuilder();
        buff.append(payload.getPart());
        buff.append(".");
        buff.append(timestamp.getPart());
        return buff.toString();
    }

    public FlaskToken.SessionData getSessionData() {
        return this.payload;
    }

    @Override
    public String getPayloadPart() {
        return this.payload.getPart();
    }

    @Override
    public JsonToken.Payload getPayload() {
        return this.payload;
    }

    @Override
    public String getSignaturePart() {
        return this.signature.getPart();
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public String getTimestampPart() {
        return this.timestamp.getPart();
    }

    @Override
    public JsonToken.Signature getSignature() {
        return this.signature;
    }

    @Override
    public boolean isValid() {
        try {
            this.payload.toJSON(false);
            return this.timestamp.isValid();
        } catch (JsonSyntaxException | IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public boolean signatureEqual(final String secret) {
        return signatureEqual(StringUtil.getBytesRaw(this.getData()), this.signature.getsDecodeBase64Url(), StringUtil.getBytesRaw(secret), StringUtil.getBytesRaw("cookie-session"));
    }

    public static byte[] sign(String payload, String secret) throws NoSuchAlgorithmException {
        return sign(payload, System.currentTimeMillis() / 1000L, StringUtil.getBytesRaw(secret));
    }

    public static byte[] sign(final String payload, long timestamp, final byte[] secret) throws NoSuchAlgorithmException {
        try {
            SecretKeySpec sk = new SecretKeySpec(BouncyUtil.hmacSHA1(secret, StringUtil.getBytesRaw("cookie-session")), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(sk);
            String data = payload + "." + JsonToken.encodeBase64UrlSafe(ConvertUtil.longToBytes(timestamp, ByteOrder.BIG_ENDIAN));
            final byte[] mac_bytes = mac.doFinal(StringUtil.getBytesRaw(data));
            return mac_bytes;
        } catch (InvalidKeyException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (GeneralSecurityException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null; // ここにはこないはず
    }

    public static boolean signatureEqual(String encrypt, final byte[] signature, String secret, String salt) {
        return signatureEqual(StringUtil.getBytesRaw(encrypt), signature, StringUtil.getBytesRaw(secret), StringUtil.getBytesRaw(salt));
    }

    protected static boolean signatureEqual(byte[] encrypt, final byte[] signature, final byte[] secret, byte[] salt) {
        try {
            SecretKeySpec sk = new SecretKeySpec(BouncyUtil.hmacSHA1(secret, salt), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(sk);
            byte[] mac_bytes = mac.doFinal(encrypt);
            return Arrays.equals(mac_bytes, signature);
        } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

}
