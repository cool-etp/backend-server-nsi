package org.cooletp.server.nsi.console.loader;

import lombok.extern.slf4j.Slf4j;
import org.cooletp.server.nsi.console.NsiConfig;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.cooletp.server.nsi.console.ftp.NsiFtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NsiFabric {
    private final NsiFtpClient client;
    private final NsiConfig config;

    @Autowired
    public NsiFabric(NsiFtpClient client, NsiConfig config) {
        this.client = client;
        this.config = config;
    }

    public NsiLoader getLoader(String name, boolean loadAll) {
        String nsiPath = config.getNsiMap().get(name);

        if(nsiPath == null) {
            throw new CliParseException("Неверный тип NSI (".concat(name).concat(")"));
        }

        return new NsiLoader(client, nsiPath, config.getNsiRootPath(), loadAll);
    }
}
