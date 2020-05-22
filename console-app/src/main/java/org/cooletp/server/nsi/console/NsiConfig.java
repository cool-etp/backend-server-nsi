package org.cooletp.server.nsi.console;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Component
public class NsiConfig {
    private final String allNsi = "ALL";

    @Value("#{${cooletp.nsi.ftp.nsi-list:{:}}}")
    private Map<String, String> nsiMap;

    @Value("${cooletp.nsi.ftp.server:localhost}")
    private String ftpServer;

    @Value("${cooletp.nsi.ftp.port:21}")
    private int ftpPort;

    @Value("${cooletp.nsi.ftp.username:username}")
    private String ftpUsername;

    @Value("${cooletp.nsi.ftp.password:password}")
    private String ftpPassword;

    public NsiConfig() {
    }

    public Set<String> getAllowedNsiTypesNames() {
        Map<String, String> result = new HashMap<>(nsiMap);
        result.put(allNsi, "");

        return result.keySet();
    }

    public boolean isNsiTypeExists(String typeName) {
        return (nsiMap.keySet().stream().anyMatch(t -> t.equalsIgnoreCase(typeName)) || allNsi.equalsIgnoreCase(typeName));
    }

}
