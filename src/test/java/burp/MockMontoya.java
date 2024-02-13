package burp;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.burpsuite.BurpSuite;
import burp.api.montoya.collaborator.Collaborator;
import burp.api.montoya.collaborator.CollaboratorClient;
import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.collaborator.CollaboratorServer;
import burp.api.montoya.collaborator.InteractionFilter;
import burp.api.montoya.collaborator.SecretKey;
import burp.api.montoya.comparer.Comparer;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.BurpSuiteEdition;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.core.Marker;
import burp.api.montoya.core.Range;
import burp.api.montoya.core.Registration;
import burp.api.montoya.core.Version;
import burp.api.montoya.decoder.Decoder;
import burp.api.montoya.extension.Extension;
import burp.api.montoya.http.Http;
import burp.api.montoya.http.HttpMode;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.RequestOptions;
import burp.api.montoya.http.handler.RequestToBeSentAction;
import burp.api.montoya.http.handler.ResponseReceivedAction;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.http.message.responses.analysis.ResponseKeywordsAnalyzer;
import burp.api.montoya.http.message.responses.analysis.ResponseVariationsAnalyzer;
import burp.api.montoya.http.sessions.ActionResult;
import burp.api.montoya.http.sessions.CookieJar;
import burp.api.montoya.internal.MontoyaObjectFactory;
import burp.api.montoya.intruder.GeneratedPayload;
import burp.api.montoya.intruder.HttpRequestTemplate;
import burp.api.montoya.intruder.HttpRequestTemplateGenerationOptions;
import burp.api.montoya.intruder.Intruder;
import burp.api.montoya.intruder.PayloadProcessingAction;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.organizer.Organizer;
import burp.api.montoya.persistence.PersistedList;
import burp.api.montoya.persistence.PersistedObject;
import burp.api.montoya.persistence.Persistence;
import burp.api.montoya.proxy.MessageReceivedAction;
import burp.api.montoya.proxy.MessageToBeSentAction;
import burp.api.montoya.proxy.Proxy;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;
import burp.api.montoya.proxy.http.ProxyResponseReceivedAction;
import burp.api.montoya.proxy.http.ProxyResponseToBeSentAction;
import burp.api.montoya.proxy.websocket.BinaryMessageReceivedAction;
import burp.api.montoya.proxy.websocket.BinaryMessageToBeSentAction;
import burp.api.montoya.proxy.websocket.TextMessageReceivedAction;
import burp.api.montoya.proxy.websocket.TextMessageToBeSentAction;
import burp.api.montoya.repeater.Repeater;
import burp.api.montoya.scanner.AuditConfiguration;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.BuiltInAuditConfiguration;
import burp.api.montoya.scanner.CrawlConfiguration;
import burp.api.montoya.scanner.Scanner;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import burp.api.montoya.scanner.audit.issues.AuditIssueConfidence;
import burp.api.montoya.scanner.audit.issues.AuditIssueDefinition;
import burp.api.montoya.scanner.audit.issues.AuditIssueSeverity;
import burp.api.montoya.scope.Scope;
import burp.api.montoya.sitemap.SiteMap;
import burp.api.montoya.sitemap.SiteMapFilter;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.menu.BasicMenuItem;
import burp.api.montoya.ui.menu.Menu;
import burp.api.montoya.utilities.Utilities;
import burp.api.montoya.websocket.BinaryMessageAction;
import burp.api.montoya.websocket.MessageAction;
import burp.api.montoya.websocket.TextMessageAction;
import burp.api.montoya.websocket.WebSockets;
import extension.burp.MessageHighlightColor;
import extension.helpers.ConvertUtil;
import extension.helpers.FileUtil;
import extension.helpers.StringUtil;
import extension.view.base.TableRowTransferHandler;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mockito.Mockito;

/**
 *
 * @author isayan
 * @see
 * https://github.com/PortSwigger/burp-extensions-montoya-api/blob/30b377428a332aed568c22af17cb08b891450027/api/src/test/java/burp/api/montoya/TestExtension.java
 */
public class MockMontoya {
    private final static Logger logger = Logger.getLogger(MockMontoya.class.getName());

    private Map<Class, Object> instanceMap = new HashMap<>();
    public final MockMontoyaObjectFactory mockFactory = Mockito.spy(new MockMontoyaObjectFactory());

    public final MontoyaApi mockApi = Mockito.mock(MontoyaApi.class);
    public final BurpSuite burpSuteApi = Mockito.mock(BurpSuite.class);
    public final Collaborator collaboratorApi = Mockito.mock(Collaborator.class);
    public final Comparer comparerApi = Mockito.mock(Comparer.class);
    public final Decoder decoderApi = Mockito.mock(Decoder.class);
    public final Extension extensionApi = Mockito.mock(Extension.class);
    public final Http httpApi = Mockito.mock(Http.class);
    public final Intruder intruderApi = Mockito.mock(Intruder.class);
    public final Logging loggingApi = Mockito.mock(Logging.class);
    public final Persistence persistenceApi = Mockito.mock(Persistence.class);
    public final Proxy proxyApi = Mockito.mock(Proxy.class);
    public final Repeater repeaterApi = Mockito.mock(Repeater.class);
    public final Scanner scannerApi = Mockito.mock(Scanner.class);
    public final Scope scopeApi = Mockito.mock(Scope.class);
    ;
    public final SiteMap siteMapApi = Mockito.mock(SiteMap.class);
    public final UserInterface userInterfaceApi = Mockito.mock(UserInterface.class);
    public final Utilities utilitiesApi = Mockito.mock(Utilities.class);
    public final WebSockets websocketsApi = Mockito.mock(WebSockets.class);
    public final Version versionApi = Mockito.mock(Version.class);
    public final CollaboratorClient collaboratorClientApi = Mockito.mock(CollaboratorClient.class);
    public final CollaboratorPayload collaboratorPayloadApi = Mockito.mock(CollaboratorPayload.class);
    public final CollaboratorServer collaboratorServerApi = Mockito.mock(CollaboratorServer.class);
    public final Registration registrationApi = Mockito.mock(Registration.class);
    public final ResponseKeywordsAnalyzer responseKeywordsAnalyzerApi = Mockito.mock(ResponseKeywordsAnalyzer.class);
    public final ResponseVariationsAnalyzer responseVariationsAnalyzer = Mockito.mock(ResponseVariationsAnalyzer.class);
    public final ByteArray byteArrayApi = Mockito.mock(ByteArray.class);
    public final CookieJar cookieJar = Mockito.mock(CookieJar.class);
    public final HttpRequest httpRequestApi = Mockito.mock(HttpRequest.class);
    public final HttpResponse httpResponseApi = Mockito.mock(HttpResponse.class);
    public final Organizer organizerApi = Mockito.mock(Organizer.class);

    public MockMontoya() {
        try {
            this.instanceMap.put(MockMontoyaObjectFactory.class, this.mockFactory);
            String projectFile = MockMontoya.class.getResource("/resources/project_json.json").getPath();
            String project_json = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(projectFile)));
            String userFile = MockMontoya.class.getResource("/resources/user_json.json").getPath();
            String user_json = StringUtil.getStringRaw(FileUtil.bytesFromFile(new File(userFile)));

            Mockito.when(this.mockApi.burpSuite()).thenReturn(this.burpSuteApi);
            Mockito.when(this.burpSuteApi.version()).thenReturn(this.versionApi);
            Mockito.when(this.burpSuteApi.exportProjectOptionsAsJson(Mockito.anyString())).thenReturn(project_json);
            Mockito.when(this.burpSuteApi.exportUserOptionsAsJson(Mockito.anyString())).thenReturn(user_json);

            Mockito.doNothing().when(this.burpSuteApi).importProjectOptionsFromJson(Mockito.anyString());
            Mockito.doNothing().when(this.burpSuteApi).importUserOptionsFromJson(Mockito.anyString());

            Mockito.when(this.mockApi.collaborator()).thenReturn(this.collaboratorApi);
            Mockito.when(this.collaboratorApi.createClient()).thenReturn(this.collaboratorClientApi);
            Mockito.when(this.collaboratorClientApi.server()).thenReturn(this.collaboratorServerApi);
            Mockito.when(this.collaboratorClientApi.getSecretKey()).thenReturn(this.mockFactory.secretKeyApi);
            Mockito.when(this.mockApi.comparer()).thenReturn(this.comparerApi);
            Mockito.when(this.mockApi.decoder()).thenReturn(this.decoderApi);
            Mockito.when(this.mockApi.extension()).thenReturn(this.extensionApi);

            Mockito.when(this.mockApi.http()).thenReturn(this.httpApi);
            Mockito.when(this.httpApi.createResponseVariationsAnalyzer()).thenReturn(this.responseVariationsAnalyzer);
            Mockito.when(this.httpApi.cookieJar()).thenReturn(this.cookieJar);
            Mockito.when(this.httpApi.sendRequest(Mockito.any(HttpRequest.class))).thenReturn(mockFactory.httpRequestResponseApi);
            Mockito.when(this.httpApi.sendRequest(Mockito.any(HttpRequest.class), Mockito.any(HttpMode.class))).thenReturn(mockFactory.httpRequestResponseApi);
            Mockito.when(this.httpApi.sendRequest(Mockito.any(HttpRequest.class), Mockito.any(HttpMode.class), Mockito.anyString())).thenReturn(mockFactory.httpRequestResponseApi);

            Mockito.when(this.mockApi.http()).thenReturn(this.httpApi);
            Mockito.when(this.mockApi.logging()).thenReturn(this.loggingApi);
            Mockito.when(this.mockApi.persistence()).thenReturn(this.persistenceApi);
            Mockito.when(this.mockApi.proxy()).thenReturn(this.proxyApi);
            Mockito.when(this.mockApi.repeater()).thenReturn(this.repeaterApi);
            Mockito.when(this.mockApi.scanner()).thenReturn(this.scannerApi);
            Mockito.when(this.mockApi.scope()).thenReturn(this.scopeApi);
            Mockito.when(this.mockApi.siteMap()).thenReturn(this.siteMapApi);
            Mockito.when(this.mockApi.userInterface()).thenReturn(this.userInterfaceApi);
            Mockito.when(this.mockApi.utilities()).thenReturn(this.utilitiesApi);
            Mockito.when(this.mockApi.websockets()).thenReturn(this.websocketsApi);
            Mockito.when(this.mockApi.organizer()).thenReturn(this.organizerApi);

            // other
            burp.api.montoya.internal.ObjectFactoryLocator.FACTORY = this.mockFactory;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public <T extends Object> T instance(Class<T> type) {
        return (T) instanceMap.get(type);
    }

    public MontoyaApi api() {

        return new MontoyaApi() {
            @Override
            public BurpSuite burpSuite() {
                return burpSuteApi;
            }

            @Override
            public Collaborator collaborator() {
                return collaboratorApi;
            }

            @Override
            public Comparer comparer() {
                return comparerApi;
            }

            @Override
            public Decoder decoder() {
                return decoderApi;
            }

            @Override
            public Extension extension() {
                return extensionApi;
            }

            @Override
            public Http http() {
                return httpApi;
            }

            @Override
            public Intruder intruder() {
                return intruderApi;
            }

            @Override
            public Logging logging() {
                return loggingApi;
            }

            @Override
            public Persistence persistence() {
                return persistenceApi;
            }

            @Override
            public Proxy proxy() {
                return proxyApi;
            }

            @Override
            public Repeater repeater() {
                return repeaterApi;
            }

            @Override
            public Scanner scanner() {
                return scannerApi;
            }

            @Override
            public Scope scope() {
                return scopeApi;
            }

            @Override
            public SiteMap siteMap() {
                return siteMapApi;
            }

            @Override
            public UserInterface userInterface() {
                return userInterfaceApi;
            }

            @Override
            public Utilities utilities() {
                return utilitiesApi;
            }

            @Override
            public WebSockets websockets() {
                return websocketsApi;
            }

            @Override
            public Organizer organizer() {
                return organizerApi;
            }

        };
    }

    public static class MockMontoyaObjectFactory implements MontoyaObjectFactory {

        public final HttpService httpServiceApi = Mockito.mock(HttpService.class);
        public final HttpHeader httpHeaderApi = Mockito.mock(HttpHeader.class);
        public final HttpParameter httpParameterApi = Mockito.mock(HttpParameter.class);
        public final HttpRequest httpRequestApi = Mockito.mock(HttpRequest.class);
        public final HttpResponse httpResponseApi = Mockito.mock(HttpResponse.class);
        public final HttpRequestResponse httpRequestResponseApi = Mockito.mock(HttpRequestResponse.class);
        public final Annotations annotationsApi = Mockito.mock(Annotations.class);
        public final AuditInsertionPoint auditInsertionPointApi = Mockito.mock(AuditInsertionPoint.class);
        public final AuditIssueDefinition auditIssueDefinitionApi = Mockito.mock(AuditIssueDefinition.class);
        public final AuditIssue auditIssueApi = Mockito.mock(AuditIssue.class);
        public final Selection selectionApi = Mockito.mock(Selection.class);
        public final SecretKey secretKeyApi = Mockito.mock(SecretKey.class);
        public final ProxyRequestReceivedAction proxyRequestReceivedActionApi = Mockito.mock(ProxyRequestReceivedAction.class);
        public final ProxyRequestToBeSentAction proxyRequestToBeSentActionApi = Mockito.mock(ProxyRequestToBeSentAction.class);
        public final ProxyResponseToBeSentAction proxyResponseToBeSentActionApi = Mockito.mock(ProxyResponseToBeSentAction.class);
        public final ProxyResponseReceivedAction proxyResponseReceivedActionApi = Mockito.mock(ProxyResponseReceivedAction.class);
        public final ResponseReceivedAction responseReceivedActionApi = Mockito.mock(ResponseReceivedAction.class);
        public final RequestToBeSentAction requestToBeSentActionApi = Mockito.mock(RequestToBeSentAction.class);
        public final HttpRequestTemplate httpRequestTemplateApi = Mockito.mock(HttpRequestTemplate.class);
        public final PayloadProcessingResult payloadProcessingResultApi = Mockito.mock(PayloadProcessingResult.class);
        public final InteractionFilter interactionFilterApi = Mockito.mock(InteractionFilter.class);
        public final SiteMapFilter siteMapFilterApi = Mockito.mock(SiteMapFilter.class);
        public final ByteArray byteArrayApi = Mockito.mock(ByteArray.class);
        public final TextMessageAction textMessageActionApi = Mockito.mock(TextMessageAction.class);
        public final BinaryMessageAction binaryMessageActionApi = Mockito.mock(BinaryMessageAction.class);
        public final BinaryMessageReceivedAction binaryMessageReceivedActionApi = Mockito.mock(BinaryMessageReceivedAction.class);
        public final TextMessageReceivedAction textMessageReceivedActionApi = Mockito.mock(TextMessageReceivedAction.class);
        public final BinaryMessageToBeSentAction binaryMessageToBeSentActionApi = Mockito.mock(BinaryMessageToBeSentAction.class);
        public final TextMessageToBeSentAction textMessageToBeSentActionApi = Mockito.mock(TextMessageToBeSentAction.class);
        public final PersistedObject persistedObjectApi = Mockito.mock(PersistedObject.class);
        public final PersistedList persistedListApi = Mockito.mock(PersistedList.class);
        public final AuditResult auditResultApi = Mockito.mock(AuditResult.class);
        public final AuditConfiguration auditConfigurationApi = Mockito.mock(AuditConfiguration.class);
        public final CrawlConfiguration crawlConfigurationApi = Mockito.mock(CrawlConfiguration.class);
        public final GeneratedPayload generatedPayloadApi = Mockito.mock(GeneratedPayload.class);
        public final HighlightColor highlightColorApi = Mockito.mock(HighlightColor.class);
        public final ActionResult actionResultApi = Mockito.mock(ActionResult.class);
        public final Menu menuApi = Mockito.mock(Menu.class);
        public final BasicMenuItem basicMenuItemApi = Mockito.mock(BasicMenuItem.class);
        public final HttpRequestTemplate httpRequestTemplate = Mockito.mock(HttpRequestTemplate.class);
        public final RequestOptions requestOptions = Mockito.mock(RequestOptions.class);

        private MockMontoyaObjectFactory() {

            Mockito.when(this.httpRequestResponseApi.request()).thenReturn(this.httpRequestApi);
            Mockito.when(this.httpRequestResponseApi.request().body()).thenReturn(this.byteArrayApi);
            Mockito.when(this.httpRequestResponseApi.response()).thenReturn(this.httpResponseApi);
            Mockito.when(this.httpRequestResponseApi.response().body()).thenReturn(this.byteArrayApi);

            MockMontoyaObjectFactory montoyaObjectFactoryApi = Mockito.spy(this);

            Mockito.when(montoyaObjectFactoryApi.httpService(Mockito.anyString())).thenReturn(httpServiceApi);
            Mockito.when(montoyaObjectFactoryApi.httpService(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(httpServiceApi);
            Mockito.when(montoyaObjectFactoryApi.httpService(Mockito.anyString(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(httpServiceApi);
            Mockito.when(montoyaObjectFactoryApi.httpHeader(Mockito.anyString(), Mockito.anyString())).thenReturn(httpHeaderApi);
            Mockito.when(montoyaObjectFactoryApi.httpHeader(Mockito.anyString())).thenReturn(httpHeaderApi);
            Mockito.when(montoyaObjectFactoryApi.parameter(Mockito.anyString(), Mockito.anyString(), Mockito.any(HttpParameterType.class))).thenReturn(httpParameterApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequest()).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequest(Mockito.any(ByteArray.class))).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequest(Mockito.anyString())).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequest(Mockito.any(HttpService.class), Mockito.any(ByteArray.class))).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequest(Mockito.any(HttpService.class), Mockito.anyString())).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.http2Request(Mockito.any(HttpService.class), Mockito.any(List.class), Mockito.anyString())).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.http2Request(Mockito.any(HttpService.class), Mockito.any(List.class), Mockito.any(ByteArray.class))).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequestFromUrl(Mockito.anyString())).thenReturn(httpRequestApi);
            Mockito.when(montoyaObjectFactoryApi.httpResponse()).thenReturn(httpResponseApi);
            Mockito.when(montoyaObjectFactoryApi.httpResponse(Mockito.anyString())).thenReturn(httpResponseApi);
            Mockito.when(montoyaObjectFactoryApi.httpResponse(Mockito.any(ByteArray.class))).thenReturn(httpResponseApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequestResponse(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.class), Mockito.any(Annotations.class))).thenReturn(httpRequestResponseApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequestResponse(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.class))).thenReturn(httpRequestResponseApi);
            //Mockito.when(montoyaObjectFactoryApi.range(Mockito.anyInt(), Mockito.anyInt())).thenReturn(rangeApi);
            Mockito.when(montoyaObjectFactoryApi.annotations()).thenReturn(annotationsApi);
            Mockito.when(montoyaObjectFactoryApi.annotations(Mockito.anyString())).thenReturn(annotationsApi);
            Mockito.when(montoyaObjectFactoryApi.annotations(Mockito.any(HighlightColor.class))).thenReturn(annotationsApi);
            Mockito.when(montoyaObjectFactoryApi.annotations(Mockito.anyString(), Mockito.any(HighlightColor.class))).thenReturn(annotationsApi);
            Mockito.when(montoyaObjectFactoryApi.auditInsertionPoint(Mockito.anyString(), Mockito.any(HttpRequest.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(auditInsertionPointApi);
            Mockito.when(montoyaObjectFactoryApi.auditIssueDefinition(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(AuditIssueSeverity.class))).thenReturn(auditIssueDefinitionApi);
            Mockito.when(montoyaObjectFactoryApi.auditIssue(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(AuditIssueSeverity.class), Mockito.any(AuditIssueConfidence.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(AuditIssueSeverity.class), Mockito.any(List.class))).thenReturn(auditIssueApi);
            Mockito.when(montoyaObjectFactoryApi.auditIssue(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(AuditIssueSeverity.class), Mockito.any(AuditIssueConfidence.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(AuditIssueSeverity.class), Mockito.any(HttpRequestResponse.class))).thenReturn(auditIssueApi);
            Mockito.when(montoyaObjectFactoryApi.selection(Mockito.any(ByteArray.class))).thenReturn(selectionApi);
            Mockito.when(montoyaObjectFactoryApi.selection(Mockito.anyInt(), Mockito.anyInt())).thenReturn(selectionApi);
            Mockito.when(montoyaObjectFactoryApi.selection(Mockito.any(ByteArray.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(selectionApi);
            Mockito.when(montoyaObjectFactoryApi.secretKey(Mockito.anyString())).thenReturn(secretKeyApi);
            Mockito.when(montoyaObjectFactoryApi.proxyRequestReceivedAction(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class), Mockito.any(MessageReceivedAction.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.proxyRequestToBeSentAction(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class), Mockito.any(MessageToBeSentAction.class))).thenReturn(proxyRequestToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.proxyResponseToReturnAction(Mockito.any(HttpResponse.class), Mockito.any(Annotations.class), Mockito.any(MessageToBeSentAction.class))).thenReturn(proxyResponseToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.proxyResponseReceivedAction(Mockito.any(HttpResponse.class), Mockito.any(Annotations.class), Mockito.any(MessageReceivedAction.class))).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestResult(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class))).thenReturn(requestToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseResult(Mockito.any(HttpResponse.class), Mockito.any(Annotations.class))).thenReturn(responseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequestTemplate(Mockito.any(ByteArray.class), Mockito.any(List.class))).thenReturn(httpRequestTemplateApi);
            Mockito.when(montoyaObjectFactoryApi.httpRequestTemplate(Mockito.any(HttpRequest.class), Mockito.any(List.class))).thenReturn(httpRequestTemplateApi);
            Mockito.when(montoyaObjectFactoryApi.payloadProcessingResult(Mockito.any(ByteArray.class), Mockito.any(PayloadProcessingAction.class))).thenReturn(payloadProcessingResultApi);
            Mockito.when(montoyaObjectFactoryApi.interactionIdFilter(Mockito.anyString())).thenReturn(interactionFilterApi);
            Mockito.when(montoyaObjectFactoryApi.interactionPayloadFilter(Mockito.anyString())).thenReturn(interactionFilterApi);
            Mockito.when(montoyaObjectFactoryApi.prefixFilter(Mockito.anyString())).thenReturn(siteMapFilterApi);
            //Mockito.when(montoyaObjectFactoryApi.marker(Mockito.any(Range.class))).thenReturn(markerApi);
            //Mockito.when(montoyaObjectFactoryApi.marker(Mockito.anyInt(), Mockito.anyInt())).thenReturn(markerApi);
            Mockito.when(montoyaObjectFactoryApi.byteArrayOfLength(Mockito.anyInt())).thenReturn(byteArrayApi);
            Mockito.when(montoyaObjectFactoryApi.byteArray(Mockito.any(byte[].class))).thenReturn(byteArrayApi);
            Mockito.when(montoyaObjectFactoryApi.byteArray(Mockito.any(int[].class))).thenReturn(byteArrayApi);
            Mockito.when(montoyaObjectFactoryApi.byteArray(Mockito.anyString())).thenReturn(byteArrayApi);
            Mockito.when(montoyaObjectFactoryApi.continueWithTextMessage(Mockito.anyString())).thenReturn(textMessageActionApi);
            Mockito.when(montoyaObjectFactoryApi.dropTextMessage()).thenReturn(textMessageActionApi);
            Mockito.when(montoyaObjectFactoryApi.textMessageAction(Mockito.anyString(), Mockito.any(MessageAction.class))).thenReturn(textMessageActionApi);
            Mockito.when(montoyaObjectFactoryApi.continueWithBinaryMessage(Mockito.any(ByteArray.class))).thenReturn(binaryMessageActionApi);
            Mockito.when(montoyaObjectFactoryApi.dropBinaryMessage()).thenReturn(binaryMessageActionApi);
            Mockito.when(montoyaObjectFactoryApi.binaryMessageAction(Mockito.any(ByteArray.class), Mockito.any(MessageAction.class))).thenReturn(binaryMessageActionApi);
            Mockito.when(montoyaObjectFactoryApi.binaryMessageAction(Mockito.any(ByteArray.class), Mockito.any(MessageAction.class))).thenReturn(binaryMessageActionApi);
            Mockito.when(montoyaObjectFactoryApi.followUserRulesInitialProxyBinaryMessage(Mockito.any(ByteArray.class))).thenReturn(binaryMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.followUserRulesInitialProxyTextMessage(Mockito.anyString())).thenReturn(textMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.interceptInitialProxyBinaryMessage(Mockito.any(ByteArray.class))).thenReturn(binaryMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.interceptInitialProxyTextMessage(Mockito.anyString())).thenReturn(textMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.dropInitialProxyBinaryMessage()).thenReturn(binaryMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.dropInitialProxyTextMessage()).thenReturn(textMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.doNotInterceptInitialProxyBinaryMessage(Mockito.any(ByteArray.class))).thenReturn(binaryMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.doNotInterceptInitialProxyTextMessage(Mockito.anyString())).thenReturn(textMessageReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.continueWithFinalProxyBinaryMessage(Mockito.any(ByteArray.class))).thenReturn(binaryMessageToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.continueWithFinalProxyTextMessage(Mockito.anyString())).thenReturn(textMessageToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.dropFinalProxyBinaryMessage()).thenReturn(binaryMessageToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.dropFinalProxyTextMessage()).thenReturn(textMessageToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.persistedObject()).thenReturn(persistedObjectApi);
            Mockito.when(montoyaObjectFactoryApi.persistedBooleanList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedShortList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedIntegerList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedLongList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedStringList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedByteArrayList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedHttpRequestList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedHttpResponseList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.persistedHttpRequestResponseList()).thenReturn(persistedListApi);
            Mockito.when(montoyaObjectFactoryApi.auditResult(Mockito.any(List.class))).thenReturn(auditResultApi);
            Mockito.when(montoyaObjectFactoryApi.auditResult(Mockito.any(AuditIssue.class))).thenReturn(auditResultApi);
            Mockito.when(montoyaObjectFactoryApi.auditResult(Mockito.any(AuditIssue.class))).thenReturn(auditResultApi);
            Mockito.when(montoyaObjectFactoryApi.auditConfiguration(Mockito.any(BuiltInAuditConfiguration.class))).thenReturn(auditConfigurationApi);
            Mockito.when(montoyaObjectFactoryApi.crawlConfiguration(Mockito.anyString())).thenReturn(crawlConfigurationApi);
            Mockito.when(montoyaObjectFactoryApi.urlParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(httpParameterApi);
            Mockito.when(montoyaObjectFactoryApi.bodyParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(httpParameterApi);
            Mockito.when(montoyaObjectFactoryApi.cookieParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(httpParameterApi);
            Mockito.when(montoyaObjectFactoryApi.payload(Mockito.anyString())).thenReturn(generatedPayloadApi);
            Mockito.when(montoyaObjectFactoryApi.payload(Mockito.any(ByteArray.class))).thenReturn(generatedPayloadApi);
            Mockito.when(montoyaObjectFactoryApi.payloadEnd()).thenReturn(generatedPayloadApi);
            Mockito.when(montoyaObjectFactoryApi.usePayload(Mockito.any(ByteArray.class))).thenReturn(payloadProcessingResultApi);
            Mockito.when(montoyaObjectFactoryApi.skipPayload()).thenReturn(payloadProcessingResultApi);
            Mockito.when(montoyaObjectFactoryApi.requestFinalInterceptResultContinueWith(Mockito.any(HttpRequest.class))).thenReturn(proxyRequestToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestFinalInterceptResultContinueWith(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class))).thenReturn(proxyRequestToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestFinalInterceptResultDrop()).thenReturn(proxyRequestToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseFinalInterceptResultDrop()).thenReturn(proxyResponseToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseFinalInterceptResultContinueWith(Mockito.any(HttpResponse.class), Mockito.any(Annotations.class))).thenReturn(proxyResponseToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseFinalInterceptResultContinueWith(Mockito.any(HttpResponse.class))).thenReturn(proxyResponseToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseInitialInterceptResultIntercept(Mockito.any(HttpResponse.class))).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseInitialInterceptResultIntercept(Mockito.any(HttpResponse.class), Mockito.any(Annotations.class))).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseInitialInterceptResultDoNotIntercept(Mockito.any(HttpResponse.class))).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseInitialInterceptResultDoNotIntercept(Mockito.any(HttpResponse.class), Mockito.any(Annotations.class))).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseInitialInterceptResultFollowUserRules(Mockito.any(HttpResponse.class))).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseInitialInterceptResultFollowUserRules(Mockito.any(HttpResponse.class), Mockito.any(Annotations.class))).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseInitialInterceptResultDrop()).thenReturn(proxyResponseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultIntercept(Mockito.any(HttpRequest.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultIntercept(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultDoNotIntercept(Mockito.any(HttpRequest.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultDoNotIntercept(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultFollowUserRules(Mockito.any(HttpRequest.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultFollowUserRules(Mockito.any(HttpRequest.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultFollowUserRules(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class))).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestInitialInterceptResultDrop()).thenReturn(proxyRequestReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.responseResult(Mockito.any(HttpResponse.class))).thenReturn(responseReceivedActionApi);
            Mockito.when(montoyaObjectFactoryApi.requestResult(Mockito.any(HttpRequest.class))).thenReturn(requestToBeSentActionApi);
            Mockito.when(montoyaObjectFactoryApi.actionResult(Mockito.any(HttpRequest.class))).thenReturn(actionResultApi);
            Mockito.when(montoyaObjectFactoryApi.actionResult(Mockito.any(HttpRequest.class), Mockito.any(Annotations.class))).thenReturn(actionResultApi);
//            Mockito.when(montoyaObjectFactoryApi.highlightColor(Mockito.anyString())).thenReturn(highlightColorApi);
            Mockito.when(montoyaObjectFactoryApi.menu(Mockito.anyString())).thenReturn(menuApi);
            Mockito.when(montoyaObjectFactoryApi.basicMenuItem(Mockito.anyString())).thenReturn(basicMenuItemApi);
        }

        @Override
        public HttpService httpService(String baseUrl) {
            return this.httpServiceApi;
        }

        @Override
        public HttpService httpService(String host, boolean secure) {
            return this.httpServiceApi;
        }

        @Override
        public HttpService httpService(String host, int port, boolean secure) {
            return this.httpServiceApi;
        }

        @Override
        public HttpHeader httpHeader(String name, String value) {
            return this.httpHeaderApi;
        }

        @Override
        public HttpHeader httpHeader(String header) {
            return this.httpHeaderApi;
        }

        @Override
        public HttpParameter parameter(String name, String value, HttpParameterType type) {
            return this.httpParameterApi;
        }

        @Override
        public HttpRequest httpRequest() {
            return this.httpRequestApi;
        }

        @Override
        public HttpRequest httpRequest(ByteArray request) {
            return this.httpRequestApi;
        }

        @Override
        public HttpRequest httpRequest(String request) {
            return this.httpRequestApi;
        }

        @Override
        public HttpRequest httpRequest(HttpService service, ByteArray request) {
            return this.httpRequestApi;
        }

        @Override
        public HttpRequest httpRequest(HttpService service, String request) {
            return this.httpRequestApi;
        }

        @Override
        public HttpRequest http2Request(HttpService service, List<HttpHeader> headers, String body) {
            return this.httpRequestApi;
        }

        @Override
        public HttpRequest http2Request(HttpService service, List<HttpHeader> headers, ByteArray body) {
            return this.httpRequestApi;
        }

        @Override
        public HttpRequest httpRequestFromUrl(String url) {
            return this.httpRequestApi;
        }

        @Override
        public HttpResponse httpResponse() {
            return this.httpResponseApi;
        }

        @Override
        public HttpResponse httpResponse(String response) {
            return this.httpResponseApi;
        }

        @Override
        public HttpResponse httpResponse(ByteArray response) {
            return this.httpResponseApi;
        }

        @Override
        public HttpRequestResponse httpRequestResponse(HttpRequest request, HttpResponse response, Annotations annotations) {
            return this.httpRequestResponseApi;
        }

        @Override
        public HttpRequestResponse httpRequestResponse(HttpRequest request, HttpResponse response) {
            return this.httpRequestResponseApi;
        }

        @Override
        public Range range(int startIndexInclusive, int endIndexExclusive) {
            return new Range() {
                @Override
                public int startIndexInclusive() {
                    return startIndexInclusive;
                }

                @Override
                public int endIndexExclusive() {
                    return endIndexExclusive;
                }

                @Override
                public boolean contains(int index){
                    return startIndexInclusive <= index && index <= endIndexExclusive;
                }

            };
        }

        @Override
        public Annotations annotations() {
            return this.annotationsApi;
        }

        @Override
        public Annotations annotations(String notes) {
            return this.annotationsApi;
        }

        @Override
        public Annotations annotations(HighlightColor highlightColor) {
            return this.annotationsApi;
        }

        @Override
        public Annotations annotations(String notes, HighlightColor highlightColor) {
            return this.annotationsApi;
        }

        @Override
        public AuditInsertionPoint auditInsertionPoint(String name, HttpRequest baseRequest, int startIndexInclusive, int endIndexExclusive) {
            return this.auditInsertionPointApi;
        }

        @Override
        public AuditIssueDefinition auditIssueDefinition(String name, String background, String remediation, AuditIssueSeverity typicalSeverity) {
            return this.auditIssueDefinitionApi;
        }

        @Override
        public AuditIssue auditIssue(
                String name,
                String detail,
                String remediation,
                String baseUrl,
                AuditIssueSeverity severity,
                AuditIssueConfidence confidence,
                String background,
                String remediationBackground,
                AuditIssueSeverity typicalSeverity,
                List<HttpRequestResponse> requestResponses) {
            return this.auditIssueApi;
        }

        @Override
        public AuditIssue auditIssue(
                String name,
                String detail,
                String remediation,
                String baseUrl,
                AuditIssueSeverity severity,
                AuditIssueConfidence confidence,
                String background,
                String remediationBackground,
                AuditIssueSeverity typicalSeverity,
                HttpRequestResponse... requestResponses) {
            return this.auditIssueApi;
        }

        @Override
        public Selection selection(ByteArray selectionContents) {
            return this.selectionApi;
        }

        @Override
        public Selection selection(int startIndexInclusive, int endIndexExclusive) {
            return this.selectionApi;
        }

        @Override
        public Selection selection(ByteArray selectionContents, int startIndexInclusive, int endIndexExclusive) {
            return this.selectionApi;
        }

        @Override
        public SecretKey secretKey(String encodedKey) {
            return this.secretKeyApi;
        }

        @Override
        public ProxyRequestReceivedAction proxyRequestReceivedAction(HttpRequest request, Annotations annotations, MessageReceivedAction action) {
            return this.proxyRequestReceivedActionApi;
        }

        @Override
        public ProxyRequestToBeSentAction proxyRequestToBeSentAction(HttpRequest request, Annotations annotations, MessageToBeSentAction action) {
            return this.proxyRequestToBeSentActionApi;
        }

        @Override
        public ProxyResponseToBeSentAction proxyResponseToReturnAction(HttpResponse response, Annotations annotations, MessageToBeSentAction action) {
            return this.proxyResponseToBeSentActionApi;
        }

        @Override
        public ProxyResponseReceivedAction proxyResponseReceivedAction(HttpResponse response, Annotations annotations, MessageReceivedAction action) {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public RequestToBeSentAction requestResult(HttpRequest request, Annotations annotations) {
            return this.requestToBeSentActionApi;
        }

        @Override
        public ResponseReceivedAction responseResult(HttpResponse response, Annotations annotations) {
            return this.responseReceivedActionApi;
        }

        @Override
        public HttpRequestTemplate httpRequestTemplate(ByteArray content, List<Range> insertionPointOffsets) {
            return this.httpRequestTemplateApi;
        }

        @Override
        public HttpRequestTemplate httpRequestTemplate(HttpRequest request, List<Range> insertionPointOffsets) {
            return this.httpRequestTemplateApi;
        }

        @Override
        public PayloadProcessingResult payloadProcessingResult(ByteArray processedPayload, PayloadProcessingAction action) {
            return this.payloadProcessingResultApi;
        }

        @Override
        public InteractionFilter interactionIdFilter(String id) {
            return this.interactionFilterApi;
        }

        @Override
        public InteractionFilter interactionPayloadFilter(String payload) {
            return this.interactionFilterApi;
        }

        @Override
        public SiteMapFilter prefixFilter(String prefix) {
            return this.siteMapFilterApi;
        }

        @Override
        public Marker marker(Range range) {
            return new Marker() {
                @Override
                public Range range() {
                    return range;
                }
            };
        }

        @Override
        public Marker marker(int startIndexInclusive, int endIndexExclusive) {
            Marker marker = new Marker() {
                @Override
                public Range range() {
                    return new Range() {
                        @Override
                        public int startIndexInclusive() {
                            return startIndexInclusive;
                        }

                        @Override
                        public int endIndexExclusive() {
                            return endIndexExclusive;
                        }

                        @Override
                        public boolean contains(int index) {
                            return startIndexInclusive <= index && index <= endIndexExclusive;
                        }

                    };
                }
            };
            return marker;
        }

        @Override
        public ByteArray byteArrayOfLength(int length) {
            return this.byteArrayApi;
        }

        @Override
        public ByteArray byteArray(byte[] bytes) {
            return this.byteArrayApi;
        }

        @Override
        public ByteArray byteArray(int[] ints) {
            return this.byteArrayApi;
        }

        @Override
        public ByteArray byteArray(String text) {
            return this.byteArrayApi;
        }

        @Override
        public TextMessageAction continueWithTextMessage(String payload) {
            return this.textMessageActionApi;
        }

        @Override
        public TextMessageAction dropTextMessage() {
            return this.textMessageActionApi;
        }

        @Override
        public TextMessageAction textMessageAction(String payload, MessageAction action) {
            return this.textMessageActionApi;
        }

        @Override
        public BinaryMessageAction continueWithBinaryMessage(ByteArray payload) {
            return this.binaryMessageActionApi;
        }

        @Override
        public BinaryMessageAction dropBinaryMessage() {
            return this.binaryMessageActionApi;
        }

        @Override
        public BinaryMessageAction binaryMessageAction(ByteArray payload, MessageAction action) {
            return this.binaryMessageActionApi;
        }

        @Override
        public BinaryMessageReceivedAction followUserRulesInitialProxyBinaryMessage(ByteArray payload) {
            return this.binaryMessageReceivedActionApi;
        }

        @Override
        public TextMessageReceivedAction followUserRulesInitialProxyTextMessage(String payload) {
            return this.textMessageReceivedActionApi;
        }

        @Override
        public BinaryMessageReceivedAction interceptInitialProxyBinaryMessage(ByteArray payload) {
            return this.binaryMessageReceivedActionApi;
        }

        @Override
        public TextMessageReceivedAction interceptInitialProxyTextMessage(String payload) {
            return this.textMessageReceivedActionApi;
        }

        @Override
        public BinaryMessageReceivedAction dropInitialProxyBinaryMessage() {
            return this.binaryMessageReceivedActionApi;
        }

        @Override
        public TextMessageReceivedAction dropInitialProxyTextMessage() {
            return this.textMessageReceivedActionApi;
        }

        @Override
        public BinaryMessageReceivedAction doNotInterceptInitialProxyBinaryMessage(ByteArray payload) {
            return this.binaryMessageReceivedActionApi;
        }

        @Override
        public TextMessageReceivedAction doNotInterceptInitialProxyTextMessage(String payload) {
            return this.textMessageReceivedActionApi;
        }

        @Override
        public BinaryMessageToBeSentAction continueWithFinalProxyBinaryMessage(ByteArray payload) {
            return this.binaryMessageToBeSentActionApi;
        }

        @Override
        public TextMessageToBeSentAction continueWithFinalProxyTextMessage(String payload) {
            return this.textMessageToBeSentActionApi;
        }

        @Override
        public BinaryMessageToBeSentAction dropFinalProxyBinaryMessage() {
            return this.binaryMessageToBeSentActionApi;
        }

        @Override
        public TextMessageToBeSentAction dropFinalProxyTextMessage() {
            return this.textMessageToBeSentActionApi;
        }

        @Override
        public PersistedObject persistedObject() {
            return this.persistedObjectApi;

        }

        @Override
        public PersistedList<Boolean> persistedBooleanList() {
            return this.persistedListApi;

        }

        @Override
        public PersistedList<Short> persistedShortList() {
            return this.persistedListApi;
        }

        @Override
        public PersistedList<Integer> persistedIntegerList() {
            return this.persistedListApi;
        }

        @Override
        public PersistedList<Long> persistedLongList() {
            return this.persistedListApi;
        }

        @Override
        public PersistedList<String> persistedStringList() {
            return this.persistedListApi;
        }

        @Override
        public PersistedList<ByteArray> persistedByteArrayList() {
            return this.persistedListApi;
        }

        @Override
        public PersistedList<HttpRequest> persistedHttpRequestList() {
            return this.persistedListApi;
        }

        @Override
        public PersistedList<HttpResponse> persistedHttpResponseList() {
            return this.persistedListApi;
        }

        @Override
        public PersistedList<HttpRequestResponse> persistedHttpRequestResponseList() {
            return this.persistedListApi;
        }

        @Override
        public AuditResult auditResult(List<AuditIssue> auditIssues) {
            return this.auditResultApi;
        }

        @Override
        public AuditResult auditResult(AuditIssue... auditIssues) {
            return this.auditResultApi;
        }

        @Override
        public AuditConfiguration auditConfiguration(BuiltInAuditConfiguration builtInAuditConfiguration) {
            return this.auditConfigurationApi;
        }

        @Override
        public CrawlConfiguration crawlConfiguration(String... seedUrls) {
            return this.crawlConfigurationApi;
        }

        @Override
        public HttpParameter urlParameter(String name, String value) {
            return this.httpParameterApi;
        }

        @Override
        public HttpParameter bodyParameter(String name, String value) {
            return this.httpParameterApi;
        }

        @Override
        public HttpParameter cookieParameter(String name, String value) {
            return this.httpParameterApi;
        }

        @Override
        public GeneratedPayload payload(String payload) {
            return this.generatedPayloadApi;
        }

        @Override
        public GeneratedPayload payload(ByteArray payload) {
            return this.generatedPayloadApi;
        }

        @Override
        public GeneratedPayload payloadEnd() {
            return this.generatedPayloadApi;
        }

        @Override
        public PayloadProcessingResult usePayload(ByteArray processedPayload) {
            return this.payloadProcessingResultApi;
        }

        @Override
        public PayloadProcessingResult skipPayload() {
            return this.payloadProcessingResultApi;
        }

        @Override
        public ProxyRequestToBeSentAction requestFinalInterceptResultContinueWith(HttpRequest request) {
            return this.proxyRequestToBeSentActionApi;
        }

        @Override
        public ProxyRequestToBeSentAction requestFinalInterceptResultContinueWith(HttpRequest request, Annotations annotations) {
            return this.proxyRequestToBeSentActionApi;
        }

        @Override
        public ProxyRequestToBeSentAction requestFinalInterceptResultDrop() {
            return this.proxyRequestToBeSentActionApi;
        }

        @Override
        public ProxyResponseToBeSentAction responseFinalInterceptResultDrop() {
            return this.proxyResponseToBeSentActionApi;
        }

        @Override
        public ProxyResponseToBeSentAction responseFinalInterceptResultContinueWith(HttpResponse response, Annotations annotations) {
            return this.proxyResponseToBeSentActionApi;
        }

        @Override
        public ProxyResponseToBeSentAction responseFinalInterceptResultContinueWith(HttpResponse response) {
            return this.proxyResponseToBeSentActionApi;

        }

        @Override
        public ProxyResponseReceivedAction responseInitialInterceptResultIntercept(HttpResponse response) {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public ProxyResponseReceivedAction responseInitialInterceptResultIntercept(HttpResponse response, Annotations annotations) {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public ProxyResponseReceivedAction responseInitialInterceptResultDoNotIntercept(HttpResponse response) {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public ProxyResponseReceivedAction responseInitialInterceptResultDoNotIntercept(HttpResponse response, Annotations annotations) {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public ProxyResponseReceivedAction responseInitialInterceptResultFollowUserRules(HttpResponse response) {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public ProxyResponseReceivedAction responseInitialInterceptResultFollowUserRules(HttpResponse response, Annotations annotations) {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public ProxyResponseReceivedAction responseInitialInterceptResultDrop() {
            return this.proxyResponseReceivedActionApi;
        }

        @Override
        public ProxyRequestReceivedAction requestInitialInterceptResultIntercept(HttpRequest request) {
            return this.proxyRequestReceivedActionApi;
        }

        @Override
        public ProxyRequestReceivedAction requestInitialInterceptResultIntercept(HttpRequest request, Annotations annotations) {
            return this.proxyRequestReceivedActionApi;

        }

        @Override
        public ProxyRequestReceivedAction requestInitialInterceptResultDoNotIntercept(HttpRequest request) {
            return this.proxyRequestReceivedActionApi;
        }

        @Override
        public ProxyRequestReceivedAction requestInitialInterceptResultDoNotIntercept(HttpRequest request, Annotations annotations) {
            return this.proxyRequestReceivedActionApi;
        }

        @Override
        public ProxyRequestReceivedAction requestInitialInterceptResultFollowUserRules(HttpRequest request) {
            return this.proxyRequestReceivedActionApi;
        }

        @Override
        public ProxyRequestReceivedAction requestInitialInterceptResultFollowUserRules(HttpRequest request, Annotations annotations) {
            return this.proxyRequestReceivedActionApi;
        }

        @Override
        public ProxyRequestReceivedAction requestInitialInterceptResultDrop() {
            return this.proxyRequestReceivedActionApi;
        }

        @Override
        public ResponseReceivedAction responseResult(HttpResponse response) {
            return this.responseReceivedActionApi;
        }

        @Override
        public RequestToBeSentAction requestResult(HttpRequest request) {
            return this.requestToBeSentActionApi;
        }

        @Override
        public HighlightColor highlightColor(String color) {
            return MessageHighlightColor.parseEnum(color).toHighlightColor();
        }

        @Override
        public ActionResult actionResult(HttpRequest request) {
            return this.actionResultApi;
        }

        @Override
        public ActionResult actionResult(HttpRequest request, Annotations annotations) {
            return this.actionResultApi;
        }

        @Override
        public Menu menu(String caption) {
            return this.menuApi;
        }

        @Override
        public BasicMenuItem basicMenuItem(String caption) {
            return this.basicMenuItemApi;
        }

        @Override
        public HttpRequestTemplate httpRequestTemplate(ByteArray ba, HttpRequestTemplateGenerationOptions hrtgo) {
            return this.httpRequestTemplate;
        }

        @Override
        public HttpRequestTemplate httpRequestTemplate(HttpRequest hr, HttpRequestTemplateGenerationOptions hrtgo) {
            return this.httpRequestTemplate;
        }

        @Override
        public RequestOptions requestOptions() {
            return this.requestOptions;
        }
    }

    public ByteArray wrapByte(byte[] message) {
        Mockito.when(this.byteArrayApi.getBytes()).thenReturn(message);
        Mockito.when(byteArrayApi.length()).thenReturn(message.length);
        return byteArrayApi;
    }

    public static class VersionAdapter implements Version {

        public final String name;
        public final String major;
        public final String minor;
        public final String build;
        public final BurpSuiteEdition edition;

        public VersionAdapter(String name, String major, String minor, String build, BurpSuiteEdition edition) {
            this.name = name;
            this.major = major;
            this.minor = minor;
            this.build = build;
            this.edition = edition;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public String major() {
            return this.major;
        }

        @Override
        public String minor() {
            return this.minor;
        }

        @Override
        public String build() {
            return this.build;
        }

        @Override
        public BurpSuiteEdition edition() {
            return this.edition;
        }

        @Override
        public long buildNumber() {
            return ConvertUtil.parseLongDefault(this.build, 0);
        }

    }

}
