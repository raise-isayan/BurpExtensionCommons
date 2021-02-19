package extension.burp;

import burp.IBurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import extension.helpers.StringUtil;
import java.awt.TrayIcon;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isayan
 */
public class BurpExtenderImpl implements IBurpExtender {

    private static BurpExtenderImpl extenderImpl;
    private static IBurpExtenderCallbacks callbacks;
    private BurpVersion burp_version = null;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks cb) {
        extenderImpl = this;
        callbacks = cb;
        burp_version = new BurpVersion(cb);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BurpExtenderImpl> T getInstance() {
        return (T) extenderImpl;
    }

    public static IBurpExtenderCallbacks getCallbacks() {
        return callbacks;
    }

    public static IExtensionHelpers getHelpers() {
        if (callbacks != null) {
            try {
                return callbacks.getHelpers();
            } catch (AbstractMethodError ex) {
            } catch (IllegalArgumentException ex) {
            } catch (LinkageError ex) {
            } catch (Exception ex) {
            }
            return null;
        } else {
            return null;
        }
    }

    public BurpVersion getBurpVersion() {
        return burp_version;
    }

    public static void outPrintln(String message) {
        outPrint(message + "\n");
    }

    public static void outPrint(String message) {
        try {
            if (callbacks != null) {
                OutputStream ostm = callbacks.getStdout();
                byte b[] = StringUtil.getBytesRaw(message);
                ostm.write(b, 0, b.length);
            }
            else {
                System.out.print(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(BurpExtenderImpl.class.getName()).log(Level.SEVERE, null, ex);        
        }
    }

    public static void errPrintln(String message) {
        errPrint(message + "\n");
    }

    public static void errPrint(String message) {
        try {
            if (callbacks != null) {
                OutputStream ostm = callbacks.getStderr();
                byte b[] = StringUtil.getBytesRaw(message);
                ostm.write(b, 0, b.length);
            }
            else {
                System.err.print(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(BurpExtenderImpl.class.getName()).log(Level.SEVERE, null, ex);                
        }
    }
    
    /**
     * burp alert 通知
     *
     * @param caption キャプション
     * @param text テキスト
     * @param messageType メッセージタイプ
     */
    public static void issueAlert(String caption, String text, TrayIcon.MessageType messageType) {
        if (callbacks != null) {
            callbacks.issueAlert(String.format("[%s] %s:%s", messageType, caption, text));
        }
    }

}
