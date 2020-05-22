package org.cooletp.server.nsi.console.ftp;

import lombok.Getter;
import lombok.Setter;
import org.cooletp.server.nsi.console.exception.FtpClientException;

import java.util.Collection;

@Getter
@Setter
public class NsiFtpLoader {
    private NsiFtpClient client;
    private final String nsiPrefix;
    private final String rootPath;

    public NsiFtpLoader(NsiFtpClient client, String nsiPrefix, String rootPath) {
        this.client = client;
        this.nsiPrefix = nsiPrefix;
        this.rootPath = rootPath;
    }

    public void open() throws FtpClientException {
        this.getClient().open();
    }

    public Collection<String> listFiles(String path) throws FtpClientException {
        String fullPath = rootPath + "/" + nsiPrefix;

        return getClient().listFiles(fullPath);
    }

    public void close() throws FtpClientException {
        getClient().close();
    }
}
