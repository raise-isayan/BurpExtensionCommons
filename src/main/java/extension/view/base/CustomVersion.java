package extension.view.base;

import extension.helpers.ConvertUtil;

/**
 *
 * @author isayan
 */
public class CustomVersion {

    private static int MAJOR_VERSION = 0;
    private static int MINOR_VERSION = 0;
    private static int REVISION_VERSION = 0;
    private static int RELEASE_NUMBER = 0;

    protected static void parseVersion(String version) {
        String[] splitversion = version.split("\\.");
        if (splitversion.length > 0) {
            MAJOR_VERSION = ConvertUtil.parseIntDefault(splitversion[0], -1);
        }
        if (splitversion.length > 1) {
            MINOR_VERSION = ConvertUtil.parseIntDefault(splitversion[1], -1);
        }
        if (splitversion.length > 2) {
            REVISION_VERSION = ConvertUtil.parseIntDefault(splitversion[2], -1);
        }
        if (splitversion.length > 3) {
            RELEASE_NUMBER = ConvertUtil.parseIntDefault(splitversion[3], -1);
        }
    }

    /**
     * メジャーバージョン
     *
     * @return メジャーバージョン番号
     */
    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    /**
     * マイナーバージョン
     *
     * @return マイナーバージョン番号
     */
    public int getMinorVersion() {
        return MINOR_VERSION;
    }

    /**
     * リビジョン番号
     *
     * @return リビジョン番号
     */
    public int getRevision() {
        return REVISION_VERSION;
    }

    /**
     * リリース番号
     *
     * @return リリース番号
     */
    public int getReleaseNumber() {
        return RELEASE_NUMBER;
    }

    /**
     * バージョン番号
     *
     * @return バージョン番号
     */
    public String getVersion() {
        return String.format("%d.%d.%d.%d", MAJOR_VERSION, MINOR_VERSION, REVISION_VERSION, RELEASE_NUMBER);
    }

}
