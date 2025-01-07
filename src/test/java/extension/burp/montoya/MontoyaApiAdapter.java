package extension.burp.montoya;

import burp.api.montoya.core.BurpSuiteEdition;
import burp.api.montoya.core.Version;

/**
 *
 * @author isayan
 */
public class MontoyaApiAdapter {

    public static class VersionAdapter implements Version {

        final String name;
        final String year;
        final String major;
        final String minor;
        final String revision;
        final String build;
        final long buildNumber;
        final BurpSuiteEdition edition;

        public VersionAdapter(String name, long buildNumber, BurpSuiteEdition edition) {
            this.buildNumber = buildNumber;
            this.name = name;
            this.year = Long.toString((this.buildNumber) / 10000000000000L);
            this.major = Long.toString((this.buildNumber % 10000000000000L) / 100000000000L);
            this.minor = Long.toString((this.buildNumber % 100000000000L) / 1000000000L);
            this.revision = Long.toString((this.buildNumber % 1000000000L) / 1000000L);
            this.build = Long.toString((this.buildNumber % 100000L));
            this.edition = edition;
        }

        @Override
        public String name() {
            return this.name;
        }

        public String year() {
            return this.year;
        }

        @Override
        public String major() {
            return this.major;
        }

        @Override
        public String minor() {
            return this.minor;
        }

        public String revision() {
            return this.revision;
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
            return this.buildNumber;
        }

    }

}
