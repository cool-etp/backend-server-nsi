package org.cooletp.server.nsi.console.ftp;

import org.cooletp.server.nsi.console.NsiConfig;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NsiLoaderFabric {
    private final NsiFtpClient client;
    private final NsiConfig config;

    @Autowired
    public NsiLoaderFabric(NsiFtpClient client, NsiConfig config) {
        this.client = client;
        this.config = config;
    }

    public NsiFtpLoader getLoader(String name, boolean loadAll) {
        String nsiPath = config.getNsiMap().get(name);

        if(nsiPath == null) {
            throw new CliParseException("Неверный тип NSI (".concat(name).concat(")"));
        }

        return new NsiFtpLoader(client, nsiPath, config.getNsiRootPath(), loadAll);
    }
}
