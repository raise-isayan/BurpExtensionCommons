package extension.burp;

import burp.api.montoya.MontoyaApi;
import extension.helpers.ConvertUtil;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public final class BurpVersion implements Comparable<BurpVersion> {

    // Professional / Community 2024.2.1.3 build:28102 BuldNumber:20240201003028102

    private final static BurpVersion SUPPORT_MIN_VERSION = new BurpVersion("Burp Suite Support", "2023", "1.2", "");

    private String productName = "";
    private String majorVersion = "";
    private String minorVersion = "";
    private String build = "";
    private long buildNumber = -1;

    public BurpVersion(MontoyaApi api) {
        this(api.burpSuite().version());
    }

    public BurpVersion(burp.api.montoya.core.Version version) {
        this(version.name(), version.major(), version.minor(), version.build());
    }

    public BurpVersion(String title) {
        parseVersion(title);
    }

    @Deprecated(forRemoval = true)
    protected BurpVersion(String name, String major, String minor, String build) {
        this.productName = name;
        this.majorVersion = major;
        this.minorVersion = minor;
        this.build = build;
    }

    protected BurpVersion(long buildNumber) {
        this.buildNumber = buildNumber;
    }

    private final static Pattern SUITE_VERSION = Pattern.compile("(Burp Suite \\w+(?: Edition)?) v(\\d+)\\.([\\d\\.]+)(-(\\d+))?");

    private void parseVersion(String title) {
        Matcher m = SUITE_VERSION.matcher(title);
        if (m.lookingAt()) {
            this.productName = m.group(1);
            this.majorVersion = m.group(2);
            this.minorVersion = m.group(3);
            this.build = m.group(5);
        }
    }

    public String getProductName() {
        return this.productName;
    }

    public String getMajor() {
        return this.majorVersion;
    }

    public String getMinor() {
        return this.minorVersion;
    }

    public String getBuild() {
        return this.build;
    }

    public long getBuildNumber() {
        return this.buildNumber;
    }

    public int getMajorVersion() {
        String majorver = this.majorVersion.replaceAll("\\.", "");
        return ConvertUtil.parseIntDefault(majorver, 0);
    }

    public int getMinorVersion() {
        return (int) ConvertUtil.parseFloatDefault(this.minorVersion, 0);
    }

    public boolean isProfessional() {
        return this.productName.contains("Professional");
    }

    public static boolean isUnsupportVersion(BurpVersion version) {
        return (version.compareTo(SUPPORT_MIN_VERSION) < 0);
    }

    private static boolean showUnsupport = false;

    /**
     * バージョンが古い場合警告を表示
     *
     * @param version
     * @param productname
     * @return 警告が表示された場合はtrue
     */
    public synchronized static boolean showUnsupporttDlg(BurpVersion version, String productname) {
        if (!showUnsupport && isUnsupportVersion(version)) {
            JOptionPane.showMessageDialog(null, "Burp suite v2023.1.2 or higher version is required.", productname, JOptionPane.INFORMATION_MESSAGE);
            showUnsupport = true;
            return true;
        }
        return false;
    }

    /**
     * バージョン番号
     *
     * @return バージョン番号
     */
    public String getVersion() {
        return String.format("%s %s.%s", getProductName(), getMajor(), getMinor());
    }

    @Override
    public int compareTo(BurpVersion o) {
        int major = this.getMajorVersion() - o.getMajorVersion();
        if (major != 0) {
            return major;
        }
        return compareMinor(this.getMinor(), o.getMinor());
    }

    public static int compareMinor(String minora, String minorb) {
        String minoras[] = minora.split("\\.");
        String minorbs[] = minorb.split("\\.");
        for (int i = 0; i < minoras.length; i++) {
            if (i == minorbs.length) {
                return 1;
            }
            int a = ConvertUtil.parseIntDefault(minoras[i], 0);
            int b = ConvertUtil.parseIntDefault(minorbs[i], 0);
            int r = (a - b);
            if (r != 0) {
                return r;
            }
        }
        if (minoras.length < minorbs.length) {
            return -1;
        }
        return 0;
    }

    public enum OSType {
        WINDOWS,
        LINUX,
        MAC,
        UNKOWN;
    }

    public enum ArcType {
        AMD64,
        ARM64,
        UNKOWN;
    }

    public static OSType getOSType() {
        String os_name = System.getProperty("os.name").toLowerCase();
        if (os_name.contains("win")) {
            return OSType.WINDOWS;
        } else if (os_name.contains("linux")) {
            return OSType.LINUX;
        } else if (os_name.contains("mac") || os_name.contains("darwin") ) {
            return OSType.MAC;
        } else {
            return OSType.UNKOWN;
        }
    }

    public static ArcType getArchType() {
        String cpu_arc = System.getProperty("os.arch").toLowerCase();
        if (cpu_arc.startsWith("amd64") || cpu_arc.startsWith("x86_64")) {
            return ArcType.AMD64;
        } else if (cpu_arc.startsWith("arm64") || cpu_arc.startsWith("aarch64")) {
            return ArcType.ARM64;
        } else {
            return ArcType.UNKOWN;
        }
    }

    /*
     * Windows
     *   %APPDATA%\BurpSuite\UserConfigCommunity.json
     *   %APPDATA%\BurpSuite\UserConfigPro.json
     * Linux
     *   %HOME%/.BurpSuite/UserConfigCommunity.json
     *   %HOME%/.BurpSuite/UserConfigPro.json
     * Mac
     *   %HOME%/.BurpSuite/UserConfigCommunity.json
     *   %HOME%/.BurpSuite/UserConfigPro.json     *
     */
    private final String USER_CONFIG_COMMUNITY = "UserConfigCommunity.json";
    private final String USER_CONFIG_PRO = "UserConfigPro.json";

    public File getBurpConfigFile() {
        if (isProfessional()) {
            final File burpConfig = new File(getBurpConfigHome(), USER_CONFIG_PRO);
            return burpConfig;
        } else {
            final File burpConfig = new File(getBurpConfigHome(), USER_CONFIG_COMMUNITY);
            return burpConfig;
        }
    }

    public File getBurpConfigHome() {
        if (BurpVersion.getOSType() == BurpVersion.OSType.WINDOWS) {
            String home = System.getenv("APPDATA");
            if (home != null) {
                final File burpHome = new File(home, "BurpSuite");
                return burpHome;
            }
        } else {
            String home = System.getenv("HOME");
            if (home != null) {
                final File burpHome = new File(home, ".BurpSuite");
                return burpHome;
            }
        }
        return null;
    }

}
