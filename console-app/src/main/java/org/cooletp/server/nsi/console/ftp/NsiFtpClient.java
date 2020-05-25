package org.cooletp.server.nsi.console.ftp;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.cooletp.server.nsi.console.NsiConfig;
import org.cooletp.server.nsi.console.exception.FtpClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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

            ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

            ftpClient.connect(config.getFtpServer(), config.getFtpPort());

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("Ошибка подключения к FTP серверу (код " + reply + ")");
            }

            ftpClient.enterLocalPassiveMode();

            if (!ftpClient.login(config.getFtpUsername(), config.getFtpPassword())) {
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

    public List<String> listFiles(String path) throws FtpClientException {
        try {
            FTPFile[] files = ftpClient.listFiles(path);

            return Arrays.stream(files)
                    .map(FTPFile::getName)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new FtpClientException(ex.getMessage());
        }
    }

    /**
     * Получаем "последний" файл по указанному пути ФТП
     * Сортировку проводим в соответствие с именем файла, так как оно включает дату создания
     *
     * @param path in users home directory
     * @return String name of file in path
     * @throws FtpClientException if directory in path empty
     */
    public String getLatestFile(String path) throws FtpClientException {
        try {
            // Список файлов БЕЗ директорий
            FTPFile[] files = ftpClient.listFiles(path, new FTPFileFilter() {
                @Override
                public boolean accept(FTPFile ftpFile) {
                    return ftpFile.isFile();
                }
            });

            if (files.length == 0) {
                throw new IOException("Directory " + path + " is empty!");
            }

            // Сортируем по имени в обратном порядке
            Arrays.sort(files, (f1, f2) -> f2.getName().compareTo(f1.getName()));

            return files[0].getName();
        } catch (IOException ex) {
            throw new FtpClientException(ex.getMessage());
        }
    }

    /**
     * Получаем список файлов у которых в названии указана нужная нам дата
     * Шаблон имени файла такой
     * <nsiName>_<type>_<date>_<id>_<orderNum>.xml.zip
     *
     * @param path in users home directory
     * @param date date for which search a file(s)
     * @return Collection of files (if exists) ordered by orderNum ACC
     * @throws FtpClientException ftpClient communication can throw an exception
     */
    public List<String> getFilesForDate(String path, LocalDate date) throws FtpClientException {
        try {
            FTPFile[] files = ftpClient.listFiles(path, new FTPFileFilter() {
                @Override
                public boolean accept(FTPFile ftpFile) {
                    return ftpFile.getName().split("_")[2].equals(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                }
            });

            // Сортируем по orderNum
            Arrays.sort(files, (f1, f2) -> {
                String fName1 = f1.getName();
                String fName2 = f2.getName();

                return fName1.split("_")[4].compareTo(fName2.split("_")[4]);
            });

            return Arrays.stream(files)
                    .map(FTPFile::getName)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new FtpClientException(ex.getMessage());
        }
    }

    public void downloadFile(String srcPath, String destPath) throws FtpClientException {
        try {
            FileOutputStream out = new FileOutputStream(destPath);
            ftpClient.retrieveFile(srcPath, out);

            int reply = ftpClient.getReplyCode();

            if (reply == FTPReply.FILE_UNAVAILABLE) {
                throw new FtpClientException("File " + srcPath + " not found on server");
            }
        } catch (IOException ex) {
            throw new FtpClientException(ex.getMessage());
        }
    }
}
