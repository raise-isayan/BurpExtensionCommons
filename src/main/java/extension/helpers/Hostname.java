package extension.helpers;

import java.io.File;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author isayan
 */
public class Hostname {

    public static HashMap<String, InetAddress> parseFile(File file) {
        HashMap<String, InetAddress> localResolv = new HashMap<>();
        try {
            Stream<String> lines = Files.lines(Path.of(file.toURI()));
            lines.forEach(l -> {
                String target = l;
                int commentStart = l.indexOf('#');
                if (commentStart > -1) {
                    target = l.substring(0, commentStart);
                }
                target = target.trim();
                if (!target.isEmpty()) {
                    try {
                        String[] hostnames = target.split("\\s");
                        InetAddress inetAdress  = InetAddress.getByName(hostnames[0]);
                        for (int i = 1; i < hostnames.length; i++) {
                            if (inetAdress instanceof Inet6Address inetv6) {
                                localResolv.put(hostnames[i], inetv6);
                            }
                            else {
                                localResolv.put(hostnames[i], inetAdress);
                            }
                        }

                    } catch (UnknownHostException ex) {
                    }
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Hostname.class.getName()).log(Level.SEVERE, null, ex);
        }
        return localResolv;
    }

}
