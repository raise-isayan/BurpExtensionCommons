package burp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *
 * @author isayan
 */
public class BurpPreferences {

    private final static Logger logger = Logger.getLogger(BurpPreferences.class.getName());

    private static final String CA_PASSWORD = "/burp/media/ps.p12";

    public static String getCAPassword() {
        return CA_PASSWORD;
    }

    public static KeyStore loadCACeart() throws KeyStoreException {
        try {
            final KeyStore ks = KeyStore.getInstance("PKCS12");
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(burp.BurpPreferences.class);
            byte[] caCartByte = Base64.getDecoder().decode(prefs.get("caCert", ""));
            ks.load(new ByteArrayInputStream(caCartByte), CA_PASSWORD.toCharArray());
            return ks;
        } catch (IOException | NoSuchAlgorithmException | CertificateException ex) {
            throw new KeyStoreException(ex);
        }
    }

    public static KeyPair loadCAKeyPair() throws KeyStoreException {
        try {
            KeyStore caKeyStore = loadCACeart();
            Enumeration<String> emu = caKeyStore.aliases();
            while (emu.hasMoreElements()) {
                String alias = emu.nextElement();
                KeyPair keyPair = new KeyPair(caKeyStore.getCertificate(alias).getPublicKey(), (PrivateKey) caKeyStore.getKey(alias, CA_PASSWORD.toCharArray()));
                return keyPair;
            }
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public static File getWorkingDir() {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(burp.BurpPreferences.class);
        return new File(prefs.get("workingdirectory", ""));
    }

    public static burp.api.montoya.persistence.Preferences extensions(String extensionName) {
        java.util.prefs.Preferences exts = null;
        try {
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(burp.BurpPreferences.class);
            Preferences ext = prefs.node("extensions");
            if (ext.nodeExists("_" + extensionName)) {
                exts = ext.node("_" + extensionName);
            }
            return convertPreferences(exts, extensionName);
        } catch (BackingStoreException ex) {
            //
        }
        return null;
    }

    public static burp.api.montoya.persistence.Preferences convertPreferences(java.util.prefs.Preferences exts, String extensionName) {
        return new burp.api.montoya.persistence.Preferences() {

            private String getExtensionKey(String type, String key) {
                return type + "_" + key;
            }

            private Set<String> getKeys(String type) {
                try {
                    List<String> keys = List.of(exts.keys());
                    String prefix = type + "_";
                    return new HashSet<>(keys.stream().filter(k -> k.startsWith(prefix)).map(k -> k.substring((prefix).length())).toList());
                } catch (BackingStoreException ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
                return null;
            }

            @Override
            public String getString(String key) {
                return exts.get(getExtensionKey("string", key), null);
            }

            @Override
            public void setString(String key, String value) {
                exts.put(getExtensionKey("string", key), value);
            }

            @Override
            public void deleteString(String key) {
                exts.remove(getExtensionKey("string", key));
            }

            @Override
            public Set<String> stringKeys() {
                return getKeys("string");
            }

            @Override
            public Boolean getBoolean(String key) {
                String value = exts.get(getExtensionKey("boolean", key), null);
                return Boolean.valueOf(value);
            }

            @Override
            public void setBoolean(String key, boolean value) {
                exts.put(getExtensionKey("boolean", key), String.valueOf(value));
            }

            @Override
            public void deleteBoolean(String key) {
                exts.remove(getExtensionKey("boolean", key));
            }

            @Override
            public Set<String> booleanKeys() {
                return getKeys("boolean");
            }

            @Override
            public Byte getByte(String key) {
                String value = exts.get(getExtensionKey("byte", key), null);
                return Byte.valueOf(value);
            }

            @Override
            public void setByte(String key, byte value) {
                exts.put(getExtensionKey("byte", key), String.valueOf(value));
            }

            @Override
            public void deleteByte(String key) {
                exts.remove(getExtensionKey("byte", key));
            }

            @Override
            public Set<String> byteKeys() {
                return getKeys("byte");
            }

            @Override
            public Short getShort(String key) {
                String value = exts.get(getExtensionKey("short", key), null);
                return Short.valueOf(value);
            }

            @Override
            public void setShort(String key, short value) {
                exts.put(getExtensionKey("short", key), String.valueOf(value));
            }

            @Override
            public void deleteShort(String key) {
                exts.remove(getExtensionKey("short", key));
            }

            @Override
            public Set<String> shortKeys() {
                return getKeys("short");
            }

            @Override
            public Integer getInteger(String key) {
                String value = exts.get(getExtensionKey("int", key), null);
                return Integer.valueOf(value);
            }

            @Override
            public void setInteger(String key, int value) {
                exts.put(getExtensionKey("int", key), String.valueOf(value));
            }

            @Override
            public void deleteInteger(String key) {
                exts.remove(getExtensionKey("int", key));
            }

            @Override
            public Set<String> integerKeys() {
                return getKeys("int");
            }

            @Override
            public Long getLong(String key) {
                String value = exts.get(getExtensionKey("long", key), null);
                return Long.valueOf(value);
            }

            @Override
            public void setLong(String key, long value) {
                exts.put(getExtensionKey("long", key), String.valueOf(value));
            }

            @Override
            public void deleteLong(String key) {
                exts.remove(getExtensionKey("long", key));
            }

            @Override
            public Set<String> longKeys() {
                return getKeys("long");
            }

        };
    }

}
