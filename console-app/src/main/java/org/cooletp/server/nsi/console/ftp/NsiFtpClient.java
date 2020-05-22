package org.cooletp.server.nsi.console.ftp;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.cooletp.server.nsi.console.NsiConfig;
import org.cooletp.server.nsi.console.exception.FtpClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class NsiFtpClient {
    private FTPClient ftpClient;
    private final NsiConfig config;

    @Autowired
    public NsiFtpClient(NsiConfig config) {
        this.config = config;
    }

    public void open() throws FtpClientException {
        try {
            ftpClient = new FTPClient();

            ftpClient.connect(config.getFtpServer(), config.getFtpPort());

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("Ошибка подключения к FTP серверу (код " + reply + ")");
            }

            if(!ftpClient.login(config.getFtpUsername(), config.getFtpPassword())) {
                reply = ftpClient.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    throw new IOException("Ошибка подключения к FTP серверу. Ошибка авторизации. (код " + reply + ")");
                }
            }
        } catch (IOException ex) {
            throw new FtpClientException(ex.getMessage());
        }
    }

    public void close() throws FtpClientException {
        try {
            ftpClient.disconnect();
        } catch (IOException ex) {
            throw new FtpClientException(ex.getMessage());
        }

    }

    public Collection<String> listFiles(String path) throws FtpClientException {
        try {
            FTPFile[] files = ftpClient.listFiles(path);

            return Arrays.stream(files)
                    .map(FTPFile::getName)
                    .collect(Collectors.toList());
        } catch(IOException ex) {
            throw new FtpClientException(ex.getMessage());
        }
    }
}
