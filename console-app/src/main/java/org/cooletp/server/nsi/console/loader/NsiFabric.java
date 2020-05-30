package org.cooletp.server.nsi.console.loader;

import lombok.extern.slf4j.Slf4j;
import org.cooletp.common.ftp.EtpFtpClient;
import org.cooletp.server.nsi.console.configs.NsiFtpConfig;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NsiFabric {
    private final EtpFtpClient client;
    private final NsiFtpConfig config;

    @Autowired
    public NsiFabric(NsiFtpConfig config) {
        this.config = config;

        this.client = new EtpFtpClient(config);
    }

    public NsiLoader getLoader(String name, boolean loadAll) {
        String nsiPath = config.getNsiMap().get(name);

        if(nsiPath == null) {
            throw new CliParseException("Неверный тип NSI (".concat(name).concat(")"));
        }

        return new NsiLoader(client, nsiPath, config.getNsiRootPath(), loadAll);
    }
}
