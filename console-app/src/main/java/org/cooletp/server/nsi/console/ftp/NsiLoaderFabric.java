package org.cooletp.server.nsi.console.ftp;

import org.cooletp.server.nsi.console.NsiConfig;
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

    public NsiFtpLoader getLoader(String name) {
        NsiFtpLoader loader = new NsiFtpLoader(client, "nsiOkato", "/out/nsi");
        return loader;
    }
}
