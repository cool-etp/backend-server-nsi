package org.cooletp.server.nsi.console.configs;

import lombok.Getter;
import org.cooletp.common.ftp.IFtpProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Component
public class NsiFtpConfig implements IFtpProperties {
    private final String allNsi = "ALL";

    @Value("#{${cooletp.nsi.ftp.nsi-list:{:}}}")
    private Map<String, String> nsiMap;

    @Value("${cooletp.nsi.ftp.server:localhost}")
    private String server;

    @Value("${cooletp.nsi.ftp.port:21}")
    private int port;

    @Value("${cooletp.nsi.ftp.username:username}")
    private String username;

    @Value("${cooletp.nsi.ftp.password:password}")
    private String password;

    @Value("${cooletp.nsi.ftp.nsi-root-path:/out/nsi}")
    private String nsiRootPath;

    public NsiFtpConfig() {
    }

    public Set<String> getAllowedNsiTypesNames() {
        Map<String, String> result = new HashMap<>(nsiMap);
        result.put(allNsi, "");

        return result.keySet();
    }
}
