package org.cooletp.server.nsi.console.ftp;

import lombok.Getter;
import org.cooletp.server.nsi.console.exception.FtpClientException;
import org.cooletp.server.nsi.console.exception.NsiFileException;
import org.cooletp.server.nsi.console.util.IOHelper;

import java.nio.file.Paths;

@Getter
public class NsiFtpLoader {
    private final NsiFtpClient client;
    private final String nsiPrefix;
    private final String nsiRootPath;
    private final boolean loadAll;

    private final String nsiDailyPath = "daily";

    public NsiFtpLoader(NsiFtpClient client, String nsiPrefix, String nsiRootPath, boolean loadAll) {
        this.client = client;
        this.nsiPrefix = nsiPrefix;
        this.nsiRootPath = nsiRootPath;
        this.loadAll = loadAll;
    }

    public void open() throws FtpClientException {
        this.getClient().open();
    }

    public void close() throws FtpClientException {
        getClient().close();
    }

    public void startLoading() throws NsiFileException, FtpClientException {
        downloadFileFromFtpToTmp(!isLoadAll());
    }

    protected void downloadFileFromFtpToTmp(boolean isDaily) throws NsiFileException, FtpClientException {
        IOHelper.createTmpDir(getNsiPrefix());
        String fileDir = isDaily ? getNsiStringDailyDirPath() : getNsiStringDirPath();

        String fileName = getClient().getLatestFile(fileDir);

        String filePathSource = Paths.get(fileDir, fileName).toString();

        getClient().downloadFile(filePathSource, getTmpFilePath(fileName));
    }

    /*
     * Путь к папке на сервере где лежат "глобальные" файлы
     */
    protected String getNsiStringDirPath() {
        return Paths.get(getNsiRootPath(), getNsiPrefix()).toString();
    }

    /*
     * Путь к папке на сервере где лежат "ежедневные" файлы
     */
    protected String getNsiStringDailyDirPath() {
        return Paths.get(getNsiRootPath(), getNsiPrefix(), getNsiDailyPath()).toString();
    }

    private String getTmpFilePath(String fileName) throws NsiFileException {
        String tmpDirName = IOHelper.getTmpDir(getNsiPrefix()).toString();

        return Paths.get(tmpDirName, fileName).toString();
    }
}
