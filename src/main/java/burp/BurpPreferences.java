package burp;

import java.util.Base64;
import java.util.prefs.Preferences;

/**
 *
 * @author isayan
 */
public class BurpPreferences {

    public static byte [] getCaCert() {
        Preferences prefs = Preferences.userNodeForPackage(burp.BurpPreferences.class);
        byte[] caCartByte = Base64.getDecoder().decode(prefs.get("caCert", ""));
        return caCartByte;
    }

}
