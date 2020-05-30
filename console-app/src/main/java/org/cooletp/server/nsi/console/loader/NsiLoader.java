package org.cooletp.server.nsi.console.loader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.cooletp.common.exception.EtpFileException;
import org.cooletp.common.exception.EtpFtpException;
import org.cooletp.common.ftp.EtpFtpClient;
import org.cooletp.common.helper.IOHelper;

import java.nio.file.Paths;

@Slf4j
@Getter
public class NsiLoader {
    private static final String DEFAULT_FTP_EXTENSION = ".xml.zip";

    private final EtpFtpClient client;
    private final String nsiPrefix;
    private final String nsiRootPath;
    private final boolean loadAll;

    private final String nsiDailyPath = "daily";

    public NsiLoader(EtpFtpClient client, String nsiPrefix, String nsiRootPath, boolean loadAll) {
        this.client = client;
        this.nsiPrefix = nsiPrefix;
        this.nsiRootPath = nsiRootPath;
        this.loadAll = loadAll;
    }

    public void downloadFromFtp() throws EtpFileException, EtpFtpException {
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
    protected void checkRemoteFileName(String fileName) throws EtpFileException {
        if(!fileName.endsWith(DEFAULT_FTP_EXTENSION)) {
            throw new EtpFileException("Неверное расширение у скаченного файла! (".concat(fileName.substring(fileName.indexOf(".") + 1)).concat(")"));
        }
    }

    /*
     * Полный путь к скачанному файлу на локальной машине во временной директории
     */
    protected String getLocalSourceTmpFilePath() throws EtpFileException {
        String tmpDirName = IOHelper.getTmpDir(getNsiPrefix()).toString();
        String fileName = nsiPrefix.concat("_source").concat(DEFAULT_FTP_EXTENSION);

        return Paths.get(tmpDirName, fileName).toString();
    }
}
