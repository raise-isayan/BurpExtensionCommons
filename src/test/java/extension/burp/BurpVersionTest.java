package extension.burp;

import burp.IBurpCollaboratorClientContext;
import burp.IBurpExtenderCallbacks;
import burp.IContextMenuFactory;
import burp.ICookie;
import burp.IExtensionHelpers;
import burp.IExtensionStateListener;
import burp.IHttpHeader;
import burp.IHttpListener;
import burp.IHttpRequestResponse;
import burp.IHttpRequestResponsePersisted;
import burp.IHttpRequestResponseWithMarkers;
import burp.IHttpService;
import burp.IIntruderPayloadGeneratorFactory;
import burp.IIntruderPayloadProcessor;
import burp.IMenuItemHandler;
import burp.IMessageEditor;
import burp.IMessageEditorController;
import burp.IMessageEditorTabFactory;
import burp.IProxyListener;
import burp.IScanIssue;
import burp.IScanQueueItem;
import burp.IScannerCheck;
import burp.IScannerInsertionPointProvider;
import burp.IScannerListener;
import burp.IScopeChangeListener;
import burp.ISessionHandlingAction;
import burp.ITab;
import burp.ITempFile;
import burp.ITextEditor;
import java.awt.Component;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author isayan
 */
public class BurpVersionTest {

    public BurpVersionTest() {
    }

    private static final String[] BURP_VERSION_FREE = {
        "Burp Suite Community Edition",
        "2.1",
        "02"
    };

    private static final String[] BURP_VERSION_PRO = {
        "Burp Suite Professional Edition",
        "2.1",
        "02"
    };

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseFreeVersion method, of class BurpVersion.
     */
    @Test
    public void testParseFreeVersion() {
        System.out.println("parseFreeVersion");

        IBurpExtenderCallbacks cb = new CallbacksAdaptr() {

            @Override
            public String[] getBurpVersion() {
                return BURP_VERSION_FREE;
            }

        };
        BurpVersion instance = new BurpVersion(cb);
        assertEquals("2.1", instance.getMajor());
        assertEquals("02", instance.getMinor());
        assertEquals(21, instance.getMajorVersion());
        assertEquals(2, instance.getMinorVersion());
        assertFalse(instance.isProfessional());
        assertEquals("Burp Suite Community Edition", instance.getProductName());
        assertEquals("Burp Suite Community Edition 2.1.02", instance.getVersion());
        System.out.println(instance.getBurpConfigHome());
        System.out.println(instance.getBurpConfigFile());

    }


    /**
     * Test of parseProVersion method, of class BurpVersion.
     */
    @Test
    public void testParseProersion() {
        System.out.println("parseProVersion");

        IBurpExtenderCallbacks cb = new CallbacksAdaptr() {

            @Override
            public String[] getBurpVersion() {
                return BURP_VERSION_PRO;
            }

        };
        BurpVersion instance = new BurpVersion(cb);
        assertEquals("2.1", instance.getMajor());
        assertEquals("02", instance.getMinor());
        assertEquals(21, instance.getMajorVersion());
        assertEquals(2, instance.getMinorVersion());
        assertTrue(instance.isProfessional());
        assertEquals("Burp Suite Professional Edition", instance.getProductName());
        assertEquals("Burp Suite Professional Edition 2.1.02", instance.getVersion());

    }

    class CallbacksAdaptr implements IBurpExtenderCallbacks {

        @Override
        public void setExtensionName(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IExtensionHelpers getHelpers() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public OutputStream getStdout() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public OutputStream getStderr() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void printOutput(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void printError(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerExtensionStateListener(IExtensionStateListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IExtensionStateListener> getExtensionStateListeners() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeExtensionStateListener(IExtensionStateListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerHttpListener(IHttpListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IHttpListener> getHttpListeners() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeHttpListener(IHttpListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerProxyListener(IProxyListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IProxyListener> getProxyListeners() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeProxyListener(IProxyListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerScannerListener(IScannerListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IScannerListener> getScannerListeners() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeScannerListener(IScannerListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerScopeChangeListener(IScopeChangeListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IScopeChangeListener> getScopeChangeListeners() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeScopeChangeListener(IScopeChangeListener il) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerContextMenuFactory(IContextMenuFactory icmf) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IContextMenuFactory> getContextMenuFactories() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeContextMenuFactory(IContextMenuFactory icmf) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerMessageEditorTabFactory(IMessageEditorTabFactory imetf) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IMessageEditorTabFactory> getMessageEditorTabFactories() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeMessageEditorTabFactory(IMessageEditorTabFactory imetf) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerScannerInsertionPointProvider(IScannerInsertionPointProvider isipp) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IScannerInsertionPointProvider> getScannerInsertionPointProviders() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeScannerInsertionPointProvider(IScannerInsertionPointProvider isipp) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerScannerCheck(IScannerCheck isc) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IScannerCheck> getScannerChecks() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeScannerCheck(IScannerCheck isc) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerIntruderPayloadGeneratorFactory(IIntruderPayloadGeneratorFactory iipgf) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IIntruderPayloadGeneratorFactory> getIntruderPayloadGeneratorFactories() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeIntruderPayloadGeneratorFactory(IIntruderPayloadGeneratorFactory iipgf) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerIntruderPayloadProcessor(IIntruderPayloadProcessor iipp) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<IIntruderPayloadProcessor> getIntruderPayloadProcessors() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeIntruderPayloadProcessor(IIntruderPayloadProcessor iipp) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void registerSessionHandlingAction(ISessionHandlingAction action) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<ISessionHandlingAction> getSessionHandlingActions() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeSessionHandlingAction(ISessionHandlingAction action) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void unloadExtension() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addSuiteTab(ITab itab) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeSuiteTab(ITab itab) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void customizeUiComponent(Component cmpnt) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IMessageEditor createMessageEditor(IMessageEditorController imec, boolean bln) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String[] getCommandLineArguments() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void saveExtensionSetting(String string, String string1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String loadExtensionSetting(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ITextEditor createTextEditor() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void sendToRepeater(String string, int i, boolean bln, byte[] bytes, String string1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void sendToIntruder(String string, int i, boolean bln, byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void sendToIntruder(String string, int i, boolean bln, byte[] bytes, List<int[]> lists) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void sendToComparer(byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void sendToSpider(URL url) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IScanQueueItem doActiveScan(String string, int i, boolean bln, byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IScanQueueItem doActiveScan(String string, int i, boolean bln, byte[] bytes, List<int[]> lists) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void doPassiveScan(String string, int i, boolean bln, byte[] bytes, byte[] bytes1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IHttpRequestResponse makeHttpRequest(IHttpService ihs, byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public byte[] makeHttpRequest(String string, int i, boolean bln, byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isInScope(URL url) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void includeInScope(URL url) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void excludeFromScope(URL url) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void issueAlert(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IHttpRequestResponse[] getProxyHistory() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IHttpRequestResponse[] getSiteMap(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IScanIssue[] getScanIssues(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void generateScanReport(String string, IScanIssue[] isis, File file) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<ICookie> getCookieJarContents() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void updateCookieJar(ICookie ic) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addToSiteMap(IHttpRequestResponse ihrr) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void restoreState(File file) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void saveState(File file) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Map<String, String> saveConfig() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void loadConfig(Map<String, String> map) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String saveConfigAsJson(String... strings) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void loadConfigFromJson(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setProxyInterceptionEnabled(boolean bln) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String[] getBurpVersion() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getExtensionFilename() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isExtensionBapp() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void exitSuite(boolean bln) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public ITempFile saveToTempFile(byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IHttpRequestResponsePersisted saveBuffersToTempFiles(IHttpRequestResponse ihrr) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IHttpRequestResponseWithMarkers applyMarkers(IHttpRequestResponse ihrr, List<int[]> lists, List<int[]> lists1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getToolName(int i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addScanIssue(IScanIssue isi) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IBurpCollaboratorClientContext createBurpCollaboratorClientContext() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String[][] getParameters(byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String[] getHeaders(byte[] bytes) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        @SuppressWarnings("deprecation")
        public void registerMenuItem(String string, IMenuItemHandler imih) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IHttpRequestResponse makeHttpRequest(IHttpService arg0, byte[] arg1, boolean arg2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public byte[] makeHttpRequest(String arg0, int arg1, boolean arg2, byte[] arg3, boolean arg4) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public byte[] makeHttp2Request(IHttpService arg0, List<IHttpHeader> arg1, byte[] arg2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public byte[] makeHttp2Request(IHttpService arg0, List<IHttpHeader> arg1, byte[] arg2, boolean arg3) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public byte[] makeHttp2Request(IHttpService arg0, List<IHttpHeader> arg1, byte[] arg2, boolean arg3, String arg4) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }

}
