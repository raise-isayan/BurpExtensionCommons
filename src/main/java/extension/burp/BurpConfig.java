package extension.burp;

import burp.api.montoya.MontoyaApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import extension.helpers.HttpUtil;
import extension.helpers.json.JsonUtil;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.UIManager;

/**
 *
 * @author isayan
 */
public class BurpConfig {

    public static String getUserHomePath() {
        return System.getProperties().getProperty("user.home");
    }

    public static File getUserHomeFile() {
        final File homePath = new File(getUserHomePath());
        return homePath;
    }

    public static String getUserDirPath() {
        return System.getProperties().getProperty("user.dir");
    }

    public static File getUserDirFile() {
        final File userDir = new File(getUserDirPath());
        return userDir;
    }

    public static File getUserConfig() {
        final File userDir = new File(getUserDirPath());
        return userDir;
    }

    private static final String CHARACTER_SETS_MODE_RECOGNIZE = "recognize_automatically";
    private static final String CHARACTER_SETS_MODE_DEFAULT = "use_the_platform_default";
    private static final String CHARACTER_SETS_MODE_RAW = "display_as_raw_bytes";
    private static final String CHARACTER_SETS_MODE_SET = "use_a_specific_character_set";

    public enum CharacterSetMode {
        RECOGNIZE_AUTO,
        PLATFORM_DEFAULT,
        RAW_BYTES,
        SPECIFIC_CHARACTER_SET;

        public String toIdent() {
            String result = CHARACTER_SETS_MODE_RAW;
            switch (this) {
                case RECOGNIZE_AUTO:
                    result = CHARACTER_SETS_MODE_RECOGNIZE;
                    break;
                case PLATFORM_DEFAULT:
                    result = CHARACTER_SETS_MODE_DEFAULT;
                    break;
                case RAW_BYTES:
                    result = CHARACTER_SETS_MODE_RAW;
                    break;
                case SPECIFIC_CHARACTER_SET:
                    result = CHARACTER_SETS_MODE_SET;
                    break;
            }
            return result;
        }

    }

    public static CharacterSets getCharacterSets(CharacterSetMode characterSet, String charCode) {
        return new CharacterSets(characterSet.toIdent(), charCode);
    }

    /* Burp built in PayloadStrings */
    private static final String BUILT_IN_PASSWORDS_SIGNATURE = "/resources/PayloadStrings/Passwords.pay";
    private static final String BUILT_IN_USERNAMES_SIGNATURE = "/resources/PayloadStrings/Usernames.pay";
    private static final String BUILT_IN_SHORT_WORDS_SIGNATURE = "/resources/PayloadStrings/Short words.pay";
    private static final String BUILT_IN_3_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/3 letter words.pay";
    private static final String BUILT_IN_4_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/4 letter words.pay";
    private static final String BUILT_IN_5_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/5 letter words.pay";
    private static final String BUILT_IN_6_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/6 letter words.pay";
    private static final String BUILT_IN_7_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/7 letter words.pay";
    private static final String BUILT_IN_8_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/8 letter words.pay";
    private static final String BUILT_IN_9_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/9 letter words.pay";
    private static final String BUILT_IN_10_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/10 letter words.pay";
    private static final String BUILT_IN_11_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/11 letter words.pay";
    private static final String BUILT_IN_12_LETTER_WORDS_SIGNATURE = "/resources/PayloadStrings/12 letter words.pay";

    private static final String BUILT_IN_0_9_SIGNATURE = "/resources/PayloadStrings/0-9.pay";
    private static final String BUILT_IN_A_Z_UPPERCASE_SIGNATURE = "/resources/PayloadStrings/A-Z .pay";
    private static final String BUILT_IN_A_Z_LOWERCASE_SIGNATURE = "/resources/PayloadStrings/a-z.pay";
    private static final String BUILT_IN_CGI_SCRIPTS_SIGNATURE = "/resources/PayloadStrings/CGI scripts.pay";
    private static final String BUILT_IN_DIRECTORIES_LONG_SIGNATURE = "/resources/PayloadStrings/Directories - long.pay";
    private static final String BUILT_IN_DIRECTORIES_SHORT_SIGNATURE = "/resources/PayloadStrings/Directories - short.pay";
    private static final String BUILT_IN_EXTENSIONS_LONG_SIGNATURE = "/resources/PayloadStrings/Extensions - long.pay";
    private static final String BUILT_IN_EXTENSIONS_SHORT_SIGNATURE = "/resources/PayloadStrings/Extensions - short.pay";
    private static final String BUILT_IN_FILENAMES_LONG_SIGNATURE = "/resources/PayloadStrings/Filenames - long.pay";
    private static final String BUILT_IN_FILENAMES_SHORT_SIGNATURE = "/resources/PayloadStrings/Filenames - short.pay";
    private static final String BUILT_IN_FORM_FIELD_NAMES_LONG_SIGNATURE = "/resources/PayloadStrings/Form field names - long.pay";
    private static final String BUILT_IN_FORM_FIELD_NAMES_SHORT_SIGNATURE = "/resources/PayloadStrings/Form field names - short.pay";
    private static final String BUILT_IN_FORM_FIELD_VALUES_SIGNATURE = "/resources/PayloadStrings/Form field values.pay";
    private static final String BUILT_IN_FORMAT_STRINGS_SIGNATURE = "/resources/PayloadStrings/Format strings.pay";
    private static final String BUILT_IN_FUZZING_FULL_SIGNATURE = "/resources/PayloadStrings/Fuzzing - full.pay";
    private static final String BUILT_IN_FUZZING_JSON_XML_INJECTION_SIGNATURE = "/resources/PayloadStrings/Fuzzing - JSON_XML injection.pay";
    private static final String BUILT_IN_FUZZING_OUT_OF_BAND_SIGNATURE = "/resources/PayloadStrings/Fuzzing - out-of-band.pay";
    private static final String BUILT_IN_FUZZING_PATH_TRAVERSAL_SINGLE_FILE_SIGNATURE = "/resources/PayloadStrings/Fuzzing - path traversal (single file).pay";
    private static final String BUILT_IN_FUZZING_PATH_TRAVERSAL_SIGNATURE = "/resources/PayloadStrings/Fuzzing - path traversal.pay";
    private static final String BUILT_IN_FUZZING_QUICK_SIGNATURE = "/resources/PayloadStrings/Fuzzing - quick.pay";
    private static final String BUILT_IN_FUZZING_SQL_INJECTION_SIGNATURE = "/resources/PayloadStrings/Fuzzing - SQL injection.pay";
    private static final String BUILT_IN_FUZZING_TEMPLATE_INJECTION_SIGNATURE = "/resources/PayloadStrings/Fuzzing - template injection.pay";
    private static final String BUILT_IN_FUZZING_XSS_SIGNATURE = "/resources/PayloadStrings/Fuzzing - XSS.pay";
    private static final String BUILT_IN_HTTP_HEADERS_SIGNATURE = "/resources/PayloadStrings/HTTP headers.pay";
    private static final String BUILT_IN_HTTP_VERBS_SIGNATURE = "/resources/PayloadStrings/HTTP verbs.pay";
    private static final String BUILT_IN_IIS_FILES_AND_DIRECTORIES_SIGNATURE = "/resources/PayloadStrings/IIS files and directories.pay";
    private static final String BUILT_IN_INTERESTING_FILES_AND_DIRECTORIES_SIGNATURE = "/resources/PayloadStrings/Interesting files and directories.pay";
    private static final String BUILT_IN_LOCAL_FILES_JAVA_SIGNATURE = "/resources/PayloadStrings/Local files - Java.pay";
    private static final String BUILT_IN_LOCAL_FILES_LINUX_SIGNATURE = "/resources/PayloadStrings/Local files - Linux.pay";
    private static final String BUILT_IN_LOCAL_FILES_WINDOWS_SIGNATURE = "/resources/PayloadStrings/Local files - Windows.pay";
    private static final String BUILT_IN_SERVER_SIDE_VARIABLE_NAMES_SIGNATURE = "/resources/PayloadStrings/Server-side variable names.pay";
    private static final String BUILT_IN_SSRF_TARGETS_SIGNATURE = "/resources/PayloadStrings/SSRF targets.pay";
    private static final String BUILT_IN_USER_AGENTS_LONG_SIGNATURE = "/resources/PayloadStrings/User agents - long.pay";
    private static final String BUILT_IN_USER_AGENTS_SHORT_SIGNATURE = "/resources/PayloadStrings/User agents - short.pay";

    public enum PayloadType {
        BUILT_IN_PASSWORDS, BUILT_IN_USERNAMES, BUILT_IN_SHORT_WORDS,
        BUILT_IN_LETTER_3_WORDS, BUILT_IN_LETTER_4_WORDS,
        BUILT_IN_LETTER_5_WORDS, BUILT_IN_LETTER_6_WORDS,
        BUILT_IN_LETTER_7_WORDS, BUILT_IN_LETTER_8_WORDS,
        BUILT_IN_LETTER_9_WORDS, BUILT_IN_LETTER_10_WORDS,
        BUILT_IN_LETTER_11_WORDS, BUILT_IN_LETTER_12_WORDS,
        BUILT_IN_0_9,
        BUILT_IN_A_Z_UPPERCASE,
        BUILT_IN_A_Z_LOWERCASE,
        BUILT_IN_CGI_SCRIPTS,
        BUILT_IN_DIRECTORIES_LONG,
        BUILT_IN_DIRECTORIES_SHORT,
        BUILT_IN_EXTENSIONS_LONG,
        BUILT_IN_EXTENSIONS_SHORT,
        BUILT_IN_FILENAMES_LONG,
        BUILT_IN_FILENAMES_SHORT,
        BUILT_IN_FORM_FIELD_NAMES_LONG,
        BUILT_IN_FORM_FIELD_NAMES_SHORT,
        BUILT_IN_FORM_FIELD_VALUES,
        BUILT_IN_FORMAT_STRINGS,
        BUILT_IN_FUZZING_FULL,
        BUILT_IN_FUZZING_JSON_XML_INJECTION,
        BUILT_IN_FUZZING_OUT_OF_BAND,
        BUILT_IN_FUZZING_PATH_TRAVERSAL_SINGLE_FILE,
        BUILT_IN_FUZZING_PATH_TRAVERSAL,
        BUILT_IN_FUZZING_QUICK,
        BUILT_IN_FUZZING_SQL_INJECTION,
        BUILT_IN_FUZZING_TEMPLATE_INJECTION,
        BUILT_IN_FUZZING_XSS,
        BUILT_IN_HTTP_HEADERS,
        BUILT_IN_HTTP_VERBS,
        BUILT_IN_IIS_FILES_AND_DIRECTORIES,
        BUILT_IN_INTERESTING_FILES_AND_DIRECTORIES,
        BUILT_IN_LOCAL_FILES_JAVA,
        BUILT_IN_LOCAL_FILES_LINUX,
        BUILT_IN_LOCAL_FILES_WINDOWS,
        BUILT_IN_SERVER_SIDE_VARIABLE_NAMES,
        BUILT_IN_SSRF_TARGETS,
        BUILT_IN_USER_AGENTS_LONG,
        BUILT_IN_USER_AGENTS_SHORT,
    };

    protected static List<String> loadFromFile(File file) throws IOException {
        return loadFromStream(new FileInputStream(file));
    }

    protected static List<String> loadFromResource(String name) throws IOException {
        return loadFromStream(BurpConfig.class.getResourceAsStream(name));
    }

    protected static List<String> loadFromStream(InputStream stream) throws IOException {
        List<String> signatures = new ArrayList<>();
        try (Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().length() == 0) {
                    continue;
                }
                signatures.add(line);
            }
        }
        return signatures;
    }

    public static List<String> loadFromSignatureTypes(PayloadType payloadType) throws IOException {
        List<String> signatures = new ArrayList<>();
        switch (payloadType) {
            case BUILT_IN_PASSWORDS:
                signatures.addAll(loadFromResource(BUILT_IN_PASSWORDS_SIGNATURE));
                break;
            case BUILT_IN_USERNAMES:
                signatures.addAll(loadFromResource(BUILT_IN_USERNAMES_SIGNATURE));
                break;
            case BUILT_IN_SHORT_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_SHORT_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_3_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_3_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_4_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_4_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_5_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_5_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_6_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_6_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_7_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_7_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_8_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_8_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_9_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_9_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_10_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_10_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_11_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_11_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_LETTER_12_WORDS:
                signatures.addAll(loadFromResource(BUILT_IN_12_LETTER_WORDS_SIGNATURE));
                break;
            case BUILT_IN_0_9:
                signatures.addAll(loadFromResource(BUILT_IN_0_9_SIGNATURE));
                break;
            case BUILT_IN_A_Z_UPPERCASE:
                signatures.addAll(loadFromResource(BUILT_IN_A_Z_UPPERCASE_SIGNATURE));
                break;
            case BUILT_IN_A_Z_LOWERCASE:
                signatures.addAll(loadFromResource(BUILT_IN_A_Z_LOWERCASE_SIGNATURE));
                break;
            case BUILT_IN_CGI_SCRIPTS:
                signatures.addAll(loadFromResource(BUILT_IN_CGI_SCRIPTS_SIGNATURE));
                break;
            case BUILT_IN_DIRECTORIES_LONG:
                signatures.addAll(loadFromResource(BUILT_IN_DIRECTORIES_LONG_SIGNATURE));
                break;
            case BUILT_IN_DIRECTORIES_SHORT:
                signatures.addAll(loadFromResource(BUILT_IN_DIRECTORIES_SHORT_SIGNATURE));
                break;
            case BUILT_IN_EXTENSIONS_LONG:
                signatures.addAll(loadFromResource(BUILT_IN_EXTENSIONS_LONG_SIGNATURE));
                break;
            case BUILT_IN_EXTENSIONS_SHORT:
                signatures.addAll(loadFromResource(BUILT_IN_EXTENSIONS_SHORT_SIGNATURE));
                break;
            case BUILT_IN_FILENAMES_LONG:
                signatures.addAll(loadFromResource(BUILT_IN_FILENAMES_LONG_SIGNATURE));
                break;
            case BUILT_IN_FILENAMES_SHORT:
                signatures.addAll(loadFromResource(BUILT_IN_FILENAMES_SHORT_SIGNATURE));
                break;
            case BUILT_IN_FORM_FIELD_NAMES_LONG:
                signatures.addAll(loadFromResource(BUILT_IN_FORM_FIELD_NAMES_LONG_SIGNATURE));
                break;
            case BUILT_IN_FORM_FIELD_NAMES_SHORT:
                signatures.addAll(loadFromResource(BUILT_IN_FORM_FIELD_NAMES_SHORT_SIGNATURE));
                break;
            case BUILT_IN_FORM_FIELD_VALUES:
                signatures.addAll(loadFromResource(BUILT_IN_FORM_FIELD_VALUES_SIGNATURE));
                break;
            case BUILT_IN_FORMAT_STRINGS:
                signatures.addAll(loadFromResource(BUILT_IN_FORMAT_STRINGS_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_FULL:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_FULL_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_JSON_XML_INJECTION:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_JSON_XML_INJECTION_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_OUT_OF_BAND:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_OUT_OF_BAND_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_PATH_TRAVERSAL_SINGLE_FILE:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_PATH_TRAVERSAL_SINGLE_FILE_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_PATH_TRAVERSAL:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_PATH_TRAVERSAL_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_QUICK:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_QUICK_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_SQL_INJECTION:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_SQL_INJECTION_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_TEMPLATE_INJECTION:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_TEMPLATE_INJECTION_SIGNATURE));
                break;
            case BUILT_IN_FUZZING_XSS:
                signatures.addAll(loadFromResource(BUILT_IN_FUZZING_XSS_SIGNATURE));
                break;
            case BUILT_IN_HTTP_HEADERS:
                signatures.addAll(loadFromResource(BUILT_IN_HTTP_HEADERS_SIGNATURE));
                break;
            case BUILT_IN_HTTP_VERBS:
                signatures.addAll(loadFromResource(BUILT_IN_HTTP_VERBS_SIGNATURE));
                break;
            case BUILT_IN_IIS_FILES_AND_DIRECTORIES:
                signatures.addAll(loadFromResource(BUILT_IN_IIS_FILES_AND_DIRECTORIES_SIGNATURE));
                break;
            case BUILT_IN_INTERESTING_FILES_AND_DIRECTORIES:
                signatures.addAll(loadFromResource(BUILT_IN_INTERESTING_FILES_AND_DIRECTORIES_SIGNATURE));
                break;
            case BUILT_IN_LOCAL_FILES_JAVA:
                signatures.addAll(loadFromResource(BUILT_IN_LOCAL_FILES_JAVA_SIGNATURE));
                break;
            case BUILT_IN_LOCAL_FILES_LINUX:
                signatures.addAll(loadFromResource(BUILT_IN_LOCAL_FILES_LINUX_SIGNATURE));
                break;
            case BUILT_IN_LOCAL_FILES_WINDOWS:
                signatures.addAll(loadFromResource(BUILT_IN_LOCAL_FILES_WINDOWS_SIGNATURE));
                break;
            case BUILT_IN_SERVER_SIDE_VARIABLE_NAMES:
                signatures.addAll(loadFromResource(BUILT_IN_SERVER_SIDE_VARIABLE_NAMES_SIGNATURE));
                break;
            case BUILT_IN_SSRF_TARGETS:
                signatures.addAll(loadFromResource(BUILT_IN_SSRF_TARGETS_SIGNATURE));
                break;
            case BUILT_IN_USER_AGENTS_LONG:
                signatures.addAll(loadFromResource(BUILT_IN_USER_AGENTS_LONG_SIGNATURE));
                break;
            case BUILT_IN_USER_AGENTS_SHORT:
                signatures.addAll(loadFromResource(BUILT_IN_USER_AGENTS_SHORT_SIGNATURE));
                break;
            default:
                break;
        }
        return signatures;
    }

    public static Color getTabFlashColor() {
        try {
            return UIManager.getColor("Burp.tabFlashColour");
        } catch (NullPointerException ex) {
            return new Color(0xff, 0x66, 0x33);
        }
    }

    private final static BurpVersion SUPPORT_BAMBDA = new BurpVersion("Burp Suite Support", "2023", "10.3", "");

    public enum SupportApi {
        BURPSUITE_USEROPTION, BURPSUITE_BAMBDA
    }

    public static boolean isSupportApi(MontoyaApi api, SupportApi type) {
        try {
            switch (type) {
                case BURPSUITE_USEROPTION:
                    api.burpSuite().exportUserOptionsAsJson("user_options");
                    break;
                case BURPSUITE_BAMBDA:
                    BurpVersion burp_version = BurpUtil.suiteVersion();
                    return burp_version.compareTo(SUPPORT_BAMBDA) >= 0;
            }
            return true;
        } catch (java.lang.NoSuchMethodError ex) {
            return false;
        }
    }

    /**
     * "display":{ "character_sets":{ "mode":"use_a_specific_character_set",
     * "specific_character_set":"UTF-8" } }
     */
    /**
     *
     * @param api
     * @return
     */
    public static CharacterSets getCharacterSets(MontoyaApi api) {
        String config = api.burpSuite().exportUserOptionsAsJson("user_options.display.character_sets");
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject character_sets = root_json.getAsJsonObject("user_options").getAsJsonObject("display").getAsJsonObject("character_sets");
        return JsonUtil.jsonFromString(JsonUtil.jsonToString(character_sets, true), BurpConfig.CharacterSets.class, true);
    }

    /**
     *
     * @param api
     * @param charsets
     */
    public static void configCharacterSets(MontoyaApi api, CharacterSets charsets) {
        String config = api.burpSuite().exportUserOptionsAsJson("user_options.display.character_sets");
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject display_json = root_json.getAsJsonObject("user_options").getAsJsonObject("display");
        JsonElement updateJsonElemet = JsonUtil.jsonToJsonElement(charsets, true);
        display_json.add("character_sets", updateJsonElemet);
        String updateConfig = JsonUtil.prettyJson(root_json, true);
        api.burpSuite().importUserOptionsFromJson(updateConfig);
    }

    public static class CharacterSets {

        public CharacterSets(String mode, String specific_character_set) {
            this.mode = mode;
            this.specific_character_set = specific_character_set;
        }

        @Expose
        private String mode;

        @Expose
        private String specific_character_set;

        /**
         * @return the mode
         */
        public String getMode() {
            return this.mode;
        }

        /**
         * @param mode the mode to set
         */
        public void setMode(String mode) {
            this.mode = mode;
        }

        /**
         * @return the specific_character_set
         */
        public String getCharacterSet() {
            return this.specific_character_set;
        }

        /**
         * @param specific_character_set the specific_character_set to set
         */
        public void setCharacterSet(String specific_character_set) {
            this.specific_character_set = specific_character_set;
        }

    }

    /**
     * config: { "project_options":{ "connections":{ "hostname_resolution":[ {
     * "enabled":true, "hostname":"test", "ip_address":"127.0.0.1" }, {
     * "enabled":true, "hostname":"hoge", "ip_address":"192.168.0.2" } ] } } }
     */
    /**
     *
     * @param api
     * @param hosts
     */
    public static synchronized void configHostnameResolution(MontoyaApi api, List<HostnameResolution> hosts) {
        configHostnameResolution(api, hosts, false);
    }

    /**
     * *
     *
     * @param api
     * @param hosts
     * @param remove
     */
    public static void configHostnameResolution(MontoyaApi api, List<HostnameResolution> hosts, boolean remove) {
        String config = api.burpSuite().exportProjectOptionsAsJson("project_options.connections.hostname_resolution");
        String updateConfig = updateHostnameResolution(config, hosts, remove);
        api.burpSuite().importProjectOptionsFromJson(updateConfig);
    }

    static String updateHostnameResolution(String config, List<HostnameResolution> hosts) {
        return updateHostnameResolution(config, hosts, false);
    }

    static String updateHostnameResolution(String config, List<HostnameResolution> hosts, boolean remove) {
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject connections = root_json.getAsJsonObject("project_options").getAsJsonObject("connections");
        Type listType = new TypeToken<ArrayList<HostnameResolution>>() {
        }.getType();
        JsonArray jsonArray = connections.getAsJsonArray("hostname_resolution");
        List<HostnameResolution> hostnameResolution = JsonUtil.jsonFromJsonElement(jsonArray, listType, true);
        List<HostnameResolution> resolvHost = new ArrayList<>();
        for (HostnameResolution h : hosts) {
            if (remove) {
                hostnameResolution.removeAll(hostnameResolution.stream().filter(m -> m.hostname.equalsIgnoreCase(h.hostname)).toList());
            } else {
                if (hostnameResolution.stream().noneMatch(m -> m.hostname.equalsIgnoreCase(h.hostname))) {
                    resolvHost.add(h);
                }
            }
        }
        if (!resolvHost.isEmpty()) {
            hostnameResolution.addAll(resolvHost);
        }
        JsonElement updateJsonElemet = JsonUtil.jsonToJsonElement(hostnameResolution, true);
        connections.add("hostname_resolution", updateJsonElemet);
        String updateConfig = JsonUtil.prettyJson(root_json, true);
        return updateConfig;
    }

    public static class HostnameResolution {

        public HostnameResolution(boolean enabled, String hostname, String ip_address) {
            this.enabled = enabled;
            this.hostname = hostname;
            this.ip_address = ip_address;
        }

        @Expose
        private boolean enabled = true;
        @Expose
        private String hostname = "";
        @Expose
        private String ip_address = "";

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return this.enabled;
        }

        /**
         * @param enabled the enabled to set
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * @return the hostname
         */
        public String getHostname() {
            return this.hostname;
        }

        /**
         * @param hostname the hostname to set
         */
        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        /**
         * @return the ip_address
         */
        public String getIPAddress() {
            return this.ip_address;
        }

        /**
         * @param ip_address the ip_address to set
         */
        public void setIPAddress(String ip_address) {
            this.ip_address = ip_address;
        }
    }

    /**
     *
     * @param api
     * @param rules
     */
    public static void configSSLPassThroughRules(MontoyaApi api, List<SSLPassThroughRule> rules) {
        configSSLPassThroughRules(api, rules, false);
    }

    /**
     * "proxy":{ "ssl_pass_through":{ "rules":[ { "enabled":true,
     * "host":"www,example.com", "port":"443", "protocol":"any" }, {
     * "enabled":true, "host":"test.com", "port":"443", "protocol":"any" } ] } }
     */
    /**
     *
     * @param api
     * @param rules
     * @param remove
     */
    public static void configSSLPassThroughRules(MontoyaApi api, List<SSLPassThroughRule> rules, boolean remove) {
        String config = api.burpSuite().exportProjectOptionsAsJson("proxy.ssl_pass_through.rules");
        String updateConfig = updateSSLPassThroughRules(config, rules, remove);
        api.burpSuite().importProjectOptionsFromJson(updateConfig);
    }

    static String updateSSLPassThroughRules(String config, List<SSLPassThroughRule> rules) {
        return updateSSLPassThroughRules(config, rules, false);
    }

    static String updateSSLPassThroughRules(String config, List<SSLPassThroughRule> rules, boolean remove) {
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject ssl_pass_through = root_json.getAsJsonObject("proxy").getAsJsonObject("ssl_pass_through");
        Type listType = new TypeToken<ArrayList<BurpConfig.SSLPassThroughRule>>() {
        }.getType();
        JsonArray jsonArray = ssl_pass_through.getAsJsonArray("rules");
        List<BurpConfig.SSLPassThroughRule> passsThrougRules = JsonUtil.jsonFromJsonElement(jsonArray, listType, true);
        List<BurpConfig.SSLPassThroughRule> resolvRules = new ArrayList<>();
        for (SSLPassThroughRule h : rules) {
            if (remove) {
                passsThrougRules.removeAll(passsThrougRules.stream().filter(m -> m.host.equalsIgnoreCase(h.host)).toList());
            } else {
                if (passsThrougRules.stream().noneMatch(m -> m.host.equalsIgnoreCase(h.host))) {
                    resolvRules.add(h);
                }
            }
        }
        if (!resolvRules.isEmpty()) {
            passsThrougRules.addAll(resolvRules);
        }
        JsonElement updateJsonElemet = JsonUtil.jsonToJsonElement(passsThrougRules, true);
        ssl_pass_through.add("rules", updateJsonElemet);
        String updateConfig = JsonUtil.prettyJson(root_json, true);
        return updateConfig;
    }

    public static class SSLPassThroughRule {

        public SSLPassThroughRule(boolean enabled, String host, int port) {
            this.enabled = enabled;
            this.host = host;
            this.port = port;
        }

        @Expose
        private boolean enabled = true;
        @Expose
        private String host = "";
        @Expose
        private int port;
        @Expose
        private String protocol = "any";

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return this.enabled;
        }

        /**
         * @param enabled the enabled to set
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * @return the host
         */
        public String getHost() {
            return this.host;
        }

        /**
         * @param host the host to set
         */
        public void setHost(String host) {
            this.host = host;
        }

        /**
         * @return the port
         */
        public int getPort() {
            return this.port;
        }

        /**
         * @param port the port to set
         */
        public void setPort(int port) {
            this.port = port;
        }

        /**
         * @return the protocol
         */
        public String getProtocol() {
            return this.protocol;
        }

        /**
         * @param protocol the protocol to set
         */
        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

    }

    public static List<MatchReplaceRule> getMatchReplaceRules(MontoyaApi api) {
        String config = api.burpSuite().exportProjectOptionsAsJson("proxy.match_replace_rules");
        return getMatchReplaceRules(config);
    }

    static List<MatchReplaceRule> getMatchReplaceRules(String config) {
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonArray jsonArray = root_json.getAsJsonObject("proxy").getAsJsonArray("match_replace_rules");
        Type listType = new TypeToken<ArrayList<BurpConfig.MatchReplaceRule>>() {
        }.getType();
        List<MatchReplaceRule> matchReplaceRule = JsonUtil.jsonFromJsonElement(jsonArray, listType, true);
        return matchReplaceRule;
    }

    public static class MatchReplaceRule {

        @Expose
        private String comment = "";
        @Expose
        private boolean enabled = false;
        @Expose
        private boolean is_simple_match = false;
        @Expose
        private String rule_type = "";
        @Expose
        private String string_match = "";
        @Expose
        private String string_replace = "";

        /**
         * @return the comment
         */
        public String getComment() {
            return this.comment;
        }

        /**
         * @param comment the comment to set
         */
        public void setComment(String comment) {
            this.comment = comment;
        }

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return this.enabled;
        }

        /**
         * @param enabled the enabled to set
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * @return the is_simple_match
         */
        public boolean isSimpleMatch() {
            return this.is_simple_match;
        }

        /**
         * @param is_simple_match the is_simple_match to set
         */
        public void setSimpleMatch(boolean is_simple_match) {
            this.is_simple_match = is_simple_match;
        }

        /**
         * @return the rule_type
         */
        public String getRuleType() {
            return this.rule_type;
        }

        /**
         * @param rule_type the rule_type to set
         */
        public void setRuleType(String rule_type) {
            this.rule_type = rule_type;
        }

        public String getRuleTypeName() {
            return this.rule_type.replace('_', ' ');
        }

        /**
         * @return the string_match
         */
        public String getStringMatch() {
            return this.string_match;
        }

        /**
         * @param string_match the string_match to set
         */
        public void setStringMatch(String string_match) {
            this.string_match = string_match;
        }

        /**
         * @return the string_replace
         */
        public String getStringReplace() {
            return this.string_replace;
        }

        /**
         * @param string_replace the string_replace to set
         */
        public void setStringReplace(String string_replace) {
            this.string_replace = string_replace;
        }
    }

    /**
     *
     * @param api
     * @param override_project
     * @return
     */
    public static SocksProxy getSocksProxy(MontoyaApi api, boolean override_project) {
        String config_key = override_project ? "project_options" : "user_options";
        String config = override_project ? api.burpSuite().exportProjectOptionsAsJson(config_key + ".connections.socks_proxy") : api.burpSuite().exportUserOptionsAsJson(config_key + ".connections.socks_proxy");
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject socks_proxy = root_json.getAsJsonObject(config_key).getAsJsonObject("connections").getAsJsonObject("socks_proxy");
        return JsonUtil.jsonFromString(JsonUtil.jsonToString(socks_proxy, true), SocksProxy.class, true);
    }

    /**
     *
     * @param api
     * @param socksProxy
     * @param override_project
     */
    public static void configSocksProxy(MontoyaApi api, SocksProxy socksProxy, boolean override_project) {
        String config_key = override_project ? "project_options" : "user_options";
        String config = override_project ? api.burpSuite().exportProjectOptionsAsJson(config_key + ".connections.socks_proxy") : api.burpSuite().exportUserOptionsAsJson(config_key + ".connections.socks_proxy");
        String updateConfig = updateSocksProxy(config, socksProxy, override_project);
        api.burpSuite().importProjectOptionsFromJson(updateConfig);
    }

    static String updateSocksProxy(String config, SocksProxy socksProxy, boolean override_project) {
        String config_key = override_project ? "project_options" : "user_options";
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject socks_proxy = root_json.getAsJsonObject(config_key).getAsJsonObject("connections").getAsJsonObject("socks_proxy");
        JsonElement updateJsonElemet = JsonUtil.jsonToJsonElement(socksProxy, true);
        socks_proxy.add("socks_proxy", updateJsonElemet);
        String updateConfig = JsonUtil.prettyJson(root_json, true);
        return updateConfig;
    }

    public static class SocksProxy {

        public SocksProxy(boolean use_proxy, String host, int port, String username, String password, boolean dns_over_socks) {
            this.use_proxy = use_proxy;
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
            this.dns_over_socks = dns_over_socks;
        }

        @Expose
        private boolean dns_over_socks = false;
        @Expose
        private String host = "";
        @Expose
        private String password = "";
        @Expose
        private int port = -1;
        @Expose
        private boolean use_proxy = false;
        @Expose
        private String username = "";

        /**
         * @return the dns_over_socks
         */
        public boolean isDnsOverSocks() {
            return this.dns_over_socks;
        }

        /**
         * @param dns_over_socks the dns_over_socks to set
         */
        public void setDnsOverSocks(boolean dns_over_socks) {
            this.dns_over_socks = dns_over_socks;
        }

        /**
         * @return the host
         */
        public String getHost() {
            return this.host;
        }

        /**
         * @param host the host to set
         */
        public void setHost(String host) {
            this.host = host;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return this.password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * @return the port
         */
        public int getPort() {
            return this.port;
        }

        /**
         * @param port the port to set
         */
        public void setPort(int port) {
            this.port = port;
        }

        /**
         * @return the use_proxy
         */
        public boolean isUseProxy() {
            return this.use_proxy;
        }

        /**
         * @param use_proxy the use_proxy to set
         */
        public void setUseProxy(boolean use_proxy) {
            this.use_proxy = use_proxy;
        }

        /**
         * @return the username
         */
        public String getUsername() {
            return this.username;
        }

        /**
         * @param username the username to set
         */
        public void setUsername(String username) {
            this.username = username;
        }
    }

    /**
     *
     * @param api
     * @return
     */
    public static String getBambda(MontoyaApi api) {
        String config = api.burpSuite().exportProjectOptionsAsJson("bambda.http_history_display_filter.bambda");
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject bambda = root_json.getAsJsonObject("bambda").getAsJsonObject("http_history_display_filter");
        return bambda.getAsJsonPrimitive("bambda").getAsString();
    }

    /**
     *
     * @param api
     * @param filter
     * @param changeFilterMode
     */
    public static void configBambda(MontoyaApi api, FilterProperty filter, boolean changeFilterMode) {
        String config = api.burpSuite().exportProjectOptionsAsJson("bambda.http_history_display_filter.bambda");
        String updateConfig = updateBambda(config, filter, changeFilterMode);
        api.burpSuite().importProjectOptionsFromJson(updateConfig);
    }

    static String updateBambda(String config, FilterProperty filter, boolean changeFilterMode) {
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject history_filter = root_json.getAsJsonObject("bambda").getAsJsonObject("http_history_display_filter");
        history_filter.addProperty("bambda", filter.getBambdaQuery());
        if (changeFilterMode) {
            JsonObject filter_mode = new JsonObject();
            filter_mode.addProperty("filter_mode", "BAMBDA");
            JsonObject http_history_display_filter = new JsonObject();
            http_history_display_filter.add("http_history_display_filter", filter_mode);
            root_json.add("proxy", http_history_display_filter);
            //root_json.getAsJsonObject("proxy").getAsJsonObject("http_history_display_filter").addProperty("filter_mode", "BAMBDA");
        }
        String updateConfig = JsonUtil.prettyJson(root_json, true);
        return updateConfig;
    }

    /**
     *
     * @param api
     * @return
     */
    public static TargetScope getTargetScope(MontoyaApi api) {
        String config = api.burpSuite().exportProjectOptionsAsJson("target.scope");
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject targetScopeJson = root_json.getAsJsonObject("target").getAsJsonObject("scope");
        TargetScope targetScope = JsonUtil.jsonFromJsonElement(targetScopeJson, TargetScope.class, true);
        targetScope.setJson(config);
        return targetScope;
    }

    static String updateTargetScope(String config, TargetScope targetScope) {
        JsonObject root_json = JsonUtil.parseJsonObject(config);
        JsonObject target = root_json.getAsJsonObject("target");
        target.add("scope", targetScope.getJson());
        String updateConfig = JsonUtil.prettyJson(root_json, true);
        return updateConfig;
    }

    /**
     *
     * @param api
     * @param targetScope
     */
    public static void configTargetScope(MontoyaApi api, TargetScope targetScope) {
        String config = api.burpSuite().exportProjectOptionsAsJson("target.scope");
        String updateConfig = updateTargetScope(config, targetScope);
        api.burpSuite().importProjectOptionsFromJson(updateConfig);
    }

    public static class TargetScope {
        @Expose
        private boolean advanced_mode = false;

        /**
         * @return the advanced_mode
         */
        public boolean isAdvancedMode() {
            return this.advanced_mode;
        }

        /**
         * @param advanced_mode the advanced_mode to set
         */
        public void setAdvancedMode(boolean advanced_mode) {
            this.advanced_mode = advanced_mode;
        }

        private final List<TargetScopeURL> include_URL = new ArrayList<>();
        private final List<TargetScopeURL> exclude_URL = new ArrayList<>();

        private final List<TargetScopeAdvance> include_Advance = new ArrayList<>();
        private final List<TargetScopeAdvance> exclude_Advance = new ArrayList<>();

        /**
         * @return the include_URL
         */
        public List<TargetScopeURL> getIncludeURL() {
            return this.include_URL;
        }

        /**
         * @param include_URL the include_URL to set
         */
        public void setIncludeURL(List<TargetScopeURL> include_URL) {
            this.include_URL.clear();
            this.include_URL.addAll(include_URL);
        }

        /**
         * @return the exclude_URL
         */
        public List<TargetScopeURL> getExcludeURL() {
            return this.exclude_URL;
        }

        /**
         * @param exclude_URL the exclude_URL to set
         */
        public void setExcludeURL(List<TargetScopeURL> exclude_URL) {
            this.exclude_URL.clear();
            this.exclude_URL.addAll(exclude_URL);
        }

        /**
         * @return the include_Advance
         */
        public List<TargetScopeAdvance> getIncludeAdvance() {
            return this.include_Advance;
        }

        /**
         * @param include_Advance the include_Advance to set
         */
        public void setIncludeAdvance(List<TargetScopeAdvance> include_Advance) {
            this.include_Advance.clear();
            this.include_Advance.addAll(include_Advance);
        }

        /**
         * @return the exclude_Advance
         */
        public List<TargetScopeAdvance> getExcludeAdvance() {
            return exclude_Advance;
        }

        /**
         * @param exclude_Advance the exclude_Advance to set
         */
        public void setExcludeAdvance(List<TargetScopeAdvance> exclude_Advance) {
            this.exclude_Advance.clear();
            this.exclude_Advance.addAll(exclude_Advance);
        }

        public void setJson(String config) {
            JsonObject root_json = JsonUtil.parseJsonObject(config);
            JsonObject targetScope_json = root_json.getAsJsonObject("target").getAsJsonObject("scope");
            BurpConfig.TargetScope targetScope = JsonUtil.jsonFromString(JsonUtil.jsonToString(targetScope_json, true), BurpConfig.TargetScope.class, true);
            this.advanced_mode = targetScope.advanced_mode;
            if (this.advanced_mode) {
                Type listType = new TypeToken<ArrayList<TargetScopeAdvance>>() {
                }.getType();
                List<TargetScopeAdvance> includeAdvance = JsonUtil.jsonFromJsonElement(targetScope_json.get("include").getAsJsonArray(), listType, true);
                List<TargetScopeAdvance> excludeAdvance = JsonUtil.jsonFromJsonElement(targetScope_json.get("exclude").getAsJsonArray(), listType, true);
                this.setIncludeAdvance(includeAdvance);
                this.setExcludeAdvance(excludeAdvance);
            }
            else {
                Type listType = new TypeToken<ArrayList<TargetScopeURL>>() {
                }.getType();
                List<TargetScopeURL> includeURL = JsonUtil.jsonFromJsonElement(targetScope_json.get("include").getAsJsonArray(), listType, true);
                List<TargetScopeURL> excludeURL = JsonUtil.jsonFromJsonElement(targetScope_json.get("exclude").getAsJsonArray(), listType, true);
                this.setIncludeURL(includeURL);
                this.setExcludeURL(excludeURL);
            }
        }

        public JsonElement getJson() {
            JsonElement root_json = JsonUtil.jsonToJsonElement(this, true);
            if (root_json.isJsonObject()) {
                JsonObject json_object = root_json.getAsJsonObject();
                if (this.advanced_mode) {
                    json_object.add("include", JsonUtil.jsonToJsonElement(this.include_Advance, true));
                    json_object.add("exclude", JsonUtil.jsonToJsonElement(this.exclude_Advance, true));
                }
                else {
                    json_object.add("include", JsonUtil.jsonToJsonElement(this.include_URL, true));
                    json_object.add("exclude", JsonUtil.jsonToJsonElement(this.exclude_URL, true));
                }
            }
            return root_json;
        }

    }

    public static class TargetScopeURL {
        @Expose
        private boolean enabled = true;
        @Expose
        private boolean include_subdomains = false;
        @Expose
        private String prefix = "";

        public static TargetScopeURL parseTargetURL(String url_string) throws MalformedURLException {
            URL url = new URL(url_string);
            return new TargetScopeURL(true, false, HttpUtil.toURL(url.getProtocol(), url.getHost(), url.getPort(), url.getPath()));
        }

        public TargetScopeURL(boolean enabled, boolean include_subdomains, String prefix) {
            this.enabled = enabled;
            this.include_subdomains = include_subdomains;
            this.prefix = prefix;
        }

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return this.enabled;
        }

        /**
         * @param enabled the enabled to set
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * @return the include_subdomains
         */
        public boolean getIncludeSubdomains() {
            return this.include_subdomains;
        }

        /**
         * @param include_subdomains the include_subdomains to set
         */
        public void setIncludeSubdomains(boolean include_subdomains) {
            this.include_subdomains = include_subdomains;
        }

        /**
         * @return the prefix
         */
        public String getPrefix() {
            return this.prefix;
        }

        /**
         * @param prefix the prefix to set
         */
        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public boolean equals(Object value) {
            if (value instanceof TargetScopeURL url) {
                return this.enabled == url.enabled
                        && this.include_subdomains == url.include_subdomains
                        && (this.prefix == null ? url.prefix == null : this.prefix.equals(url.prefix));
            }
            else {
                return false;
            }
        }

    }

    public static class TargetScopeAdvance {
        @Expose
        private boolean enabled = true;
        @Expose
        private String file = "";
        @Expose
        private String host = "";
        @Expose
        private String port = "";
        @Expose
        private String protocol = "";

        public static TargetScopeAdvance parseTargetURL(String url_string) throws MalformedURLException {
            URL url = HttpUtil.toURL(new URL(url_string));
            return new TargetScopeAdvance(true, url.getProtocol(), escapeURL(url.getHost()), escapeURL(Integer.toString(url.getPort())), escapePath(url.getPath()));
        }

        public TargetScopeAdvance(boolean enabled, String protocol, String host, String port, String file) {
            this.enabled = enabled;
            this.file = file;
            this.host = host;
            this.port = port;
            this.protocol = protocol;
        }

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return this.enabled;
        }

        /**
         * @param enabled the enabled to set
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * @return the file
         */
        public String getFile() {
            return this.file;
        }

        /**
         * @param file the file to set
         */
        public void setFile(String file) {
            this.file = file;
        }

        /**
         * @return the host
         */
        public String getHost() {
            return this.host;
        }

        /**
         * @param host the host to set
         */
        public void setHost(String host) {
            this.host = host;
        }

        /**
         * @return the port
         */
        public String getPort() {
            return this.port;
        }

        /**
         * @param port the port to set
         */
        public void setPort(String port) {
            this.port = port;
        }

        /**
         * @return the protocol
         */
        public String getProtocol() {
            return this.protocol;
        }

        /**
         * @param protocol the protocol to set
         */
        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        /*
         *
         */
        public static String escapeURL(String url) {
            StringBuilder builder = new StringBuilder();
            builder.append("^");
            for (int i = 0; i < url.length(); i = url.offsetByCodePoints(i, 1)) {
                int codePoint = url.codePointAt(i);
                if ('.' == (char)codePoint) {
                    builder.append('\\');
                }
                builder.appendCodePoint(codePoint);
            }
            builder.append("$");
            return builder.toString();
        }

        public static String escapePath(String path) {
            StringBuilder builder = new StringBuilder();
            builder.append("^");
            for (int i = 0; i < path.length(); i = path.offsetByCodePoints(i, 1)) {
                int codePoint = path.codePointAt(i);
                if ('.' == (char)codePoint) {
                    builder.append('\\');
                }
                builder.appendCodePoint(codePoint);
            }
            if (!path.endsWith("/")) {
                builder.append("/");
            }
            builder.append(".*");
            return builder.toString();
        }

        @Override
        public boolean equals(Object value) {
            if (value instanceof TargetScopeAdvance url) {
                return this.enabled == url.enabled
                        && (this.file == null ? url.file == null : this.file.equals(url.file))
                        && (this.host == null ? url.host == null : this.host.equals(url.host))
                        && (this.port == null ? url.port == null : this.port.equals(url.port))
                        && (this.protocol == null ? url.protocol == null : this.protocol.equals(url.protocol));
            }
            else {
                return false;
            }
        }

    }

}
