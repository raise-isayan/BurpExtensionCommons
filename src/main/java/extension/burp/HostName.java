package extension.burp;

import burp.api.montoya.http.HttpService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author isayan
 */
public class HostName {

    private final static Logger logger = Logger.getLogger(HostName.class.getName());

    private final List<HostNameEntry> hostentrys;

    private HostName(List<HostNameEntry> hostentrys) {
        this.hostentrys = hostentrys;
    }

    public static HostName parseHosts(final Stream<String> lines) throws IOException {
        final List<HostNameEntry> hosts = new ArrayList<>();
        lines.forEach(new Consumer<String>() {
            @Override
            public void accept(String l) {
                String target = l;
                int commentStart = l.indexOf('#');
                if (commentStart > -1) {
                    target = l.substring(0, commentStart);
                }
                target = target.trim();
                if (!target.isEmpty()) {
                    String[] hostnames = target.split("\\s+");
                    if (hostnames.length >= 2) {
                        String inetAdress = hostnames[0];
                        for (int i = 1; i < hostnames.length; i++) {
                            if (hostnames[i].startsWith("#")) {
                                break;
                            }
                            String hostName = hostnames[i];
                            HostNameEntry entry = new HostNameEntry(inetAdress, hostName);
                            hosts.add(entry);
                        }
                    }
                }
            }
        });
        HostName hostname = new HostName(hosts);
        return hostname;
    }

    public static Stream<String> parseLines(final String hosts) {
        BufferedReader br = new BufferedReader(new StringReader(hosts));
        return br.lines();
    }

    public static Stream<String> parseLines(final Path host_path) throws IOException {
        List<String> lines = Files.readAllLines(host_path, StandardCharsets.UTF_8);
        return lines.stream();
    }

    public static Stream<String> parseLines(byte[] buff, String charsetName) throws UnsupportedEncodingException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buff), charsetName));
        return br.lines();
    }

    public List<HostNameEntry> getHostNameList() {
        return this.hostentrys;
    }

    public HostNameEntry resolvInetAddress(String ipAddress) {
//        try {
//            byte[] inetAddress = IpUtil.parseIPAddressByte(ipAddress);
            Optional<HostNameEntry> entry = this.hostentrys.stream().filter(e -> e.equalsInetAddress(ipAddress)).findFirst();
            return entry.isEmpty() ? null : entry.get();
//        } catch (ParseException ex) {
//        }
    }

    public List<HostNameEntry> resolvInetAddresses(String ipAddress) {
        List<HostNameEntry> entry = this.hostentrys.stream().filter(e -> e.equalsInetAddress(ipAddress)).collect(Collectors.toList());
        return entry;
    }

    public HostNameEntry resolvHostName(String hostName) {
        Optional<HostNameEntry> entry = this.hostentrys.stream().filter(e -> hostName.equalsIgnoreCase(e.getHostName())).findFirst();
        return entry.isEmpty() ? null : entry.get();
    }

    public List<HostNameEntry> resolvHostNames(String hostName) {
        List<HostNameEntry> entry = this.hostentrys.stream().filter(e -> hostName.equalsIgnoreCase(e.getHostName())).collect(Collectors.toList());
        return entry;
    }

    /**
     * 同じホスト名に異なるIPアドレスがある場合に検出
     *
     */
    private static Map<String, Set<String>> detectWarningHost(Map<String, Set<String>> hostsMap) {
        Map<String, Set<String>> filterMap = hostsMap.entrySet().stream().filter(entry -> entry.getValue().size() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return filterMap;
    }

    public boolean ignoreMatch(HttpService destHttpService) {
        List<HostNameEntry> hostEntrys = resolvHostNames(destHttpService.host());
        for (HostNameEntry entry : hostEntrys) {
            if (!destHttpService.ipAddress().equalsIgnoreCase(entry.getIPAddress())) {
                return true;
            }
        }
        return false;
    }

    public static HostName getInstance(List<HostNameEntry> hostentrys) {
        return new HostName(hostentrys);
    }

    public static File getSystemHostFile() {
        File hosts = null;
        switch (BurpVersion.getOSType()) {
            case WINDOWS:
                hosts = Paths.get(System.getenv("windir"), "System32", "drivers", "etc", "hosts").toFile();
                break;
            case LINUX:
                hosts = Paths.get("etc", "hosts").toFile();
                break;
            case MAC:
                hosts = Paths.get("etc", "hosts").toFile();
                break;
            default:
                break;
        }
        return hosts;
    }


}
