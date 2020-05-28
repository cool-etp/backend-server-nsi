package org.cooletp.server.nsi.console.loader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.cooletp.server.nsi.console.exception.FtpClientException;
import org.cooletp.server.nsi.console.exception.NsiFileException;
import org.cooletp.server.nsi.console.ftp.NsiFtpClient;
import org.cooletp.server.nsi.console.util.IOHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Getter
public class NsiLoader {
    private static final String DEFAULT_FTP_EXTENSION = ".xml.zip";

    private final NsiFtpClient client;
    private final String nsiPrefix;
    private final String nsiRootPath;
    private final boolean loadAll;

    private final String nsiDailyPath = "daily";

    public NsiLoader(NsiFtpClient client, String nsiPrefix, String nsiRootPath, boolean loadAll) {
        this.client = client;
        this.nsiPrefix = nsiPrefix;
        this.nsiRootPath = nsiRootPath;
        this.loadAll = loadAll;
    }

    public void downloadFromFtp() throws FtpClientException, NsiFileException {
        // Откроем коннект
        getClient().open();
        // Создаем директорию временную для нужного справочника
        IOHelper.createTmpDir(getNsiPrefix());
        // Строим путь до папки нужного нам файла на ФТП сервере
        String remoteFileDir = isLoadAll() ? getNsiStringDirPath() : getNsiStringDailyDirPath();
        // Получаем имя последнего файла на ФТП сервере
        String remoteFileName = getClient().getLatestFile(remoteFileDir);
        // Строим полный путь на сервере ФТП до нужного файла
        String remoteFileFullPath = Paths.get(remoteFileDir, remoteFileName).toString();
        // Скачиваем файл с ФТП во временную директорию
        checkRemoteFileName(remoteFileName);
        getClient().downloadFile(remoteFileFullPath, getLocalSourceTmpFilePath());
        // Закрываем коннект до ФТП сервера
        getClient().close();

        log.debug("File from FTP downloaded to ".concat(getLocalSourceTmpFilePath()));
    }

    public void unzipSourceFile() {
        
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

    /*
     * Проверяем файл на ФТП сервере на соотвестввие нашим договоренностям (на данный момент расширение чтобы соответствовало)
     */
    protected void checkRemoteFileName(String fileName) throws NsiFileException {
        if(!fileName.endsWith(DEFAULT_FTP_EXTENSION)) {
            throw new NsiFileException("Неверное расширение у скаченного файла! (".concat(fileName.substring(fileName.indexOf(".") + 1)).concat(")"));
        }
    }

    /*
     * Полный путь к скачанному файлу на локальной машине во временной директории
     */
    protected String getLocalSourceTmpFilePath() throws NsiFileException {
        String tmpDirName = IOHelper.getTmpDir(getNsiPrefix()).toString();
        String fileName = nsiPrefix.concat("_source").concat(DEFAULT_FTP_EXTENSION);

        return Paths.get(tmpDirName, fileName).toString();
    }
}
