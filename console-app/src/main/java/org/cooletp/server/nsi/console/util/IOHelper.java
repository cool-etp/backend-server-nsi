package org.cooletp.server.nsi.console.util;

import lombok.extern.slf4j.Slf4j;
import org.cooletp.server.nsi.console.exception.NsiFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
public class IOHelper {
    private final static String rootTmpDir = IOHelper.class.getPackageName();

    public static void createTmpDir(String name) throws NsiFileException {
        try {
            Path tmpDirPath = getTmpDir(name);
            log.info(tmpDirPath.toString());
            // Удалим сначала временную папку со всем содержимым
            deleteDirectoryRecursively(tmpDirPath);
            Files.createDirectory(tmpDirPath);
        } catch(NsiFileException ex) {
            throw ex;
        } catch (IOException ex) {
            throw new NsiFileException(ex);
        }
    }

    public static Path getTmpDir(String name) throws NsiFileException {
        String rootTmpPath = createRootTmpDir().toString();
        return Paths.get(rootTmpPath, name);
    }

    private static String getRootTmpPath() {
        return System.getProperty("java.io.tmpdir");
    }

    private static void deleteDirectoryRecursively(Path dirPath) throws NsiFileException {
        try {
            // если указанной папки нет - ничего не делаем
            if(!Files.exists(dirPath)) {
                return;
            }

            if (Files.isDirectory(dirPath) && Files.list(dirPath).count() != 0) {
                // Сначала удалим все обычные файлы в директории
                Files.list(dirPath).filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);

                // Теперь рекурсивно удалим все папки
                for (Path path : Files.list(dirPath).filter(Files::isDirectory).collect(Collectors.toList())) {
                    deleteDirectoryRecursively(path);
                }
            }
            // И удалим саму (Теперь пустую) папку
            Files.delete(dirPath);
        } catch (IOException ex) {
            throw new NsiFileException(ex);
        }

    }

    /**
     * Проверим существует ли корневая временная директория
     * Если нет - создаем
     */
    private static Path createRootTmpDir() throws NsiFileException {
        Path fullTmpRootPath = Paths.get(getRootTmpPath(), rootTmpDir);

        // На случай если есть файл с таким же именем как директория - на всякий не будем удалять его
        if(Files.exists(fullTmpRootPath) && !Files.isDirectory(fullTmpRootPath)) {
            throw new NsiFileException("Tmp Root Dir cannot be created. File with same name exists. (" + fullTmpRootPath.toString() + ")");
        }

        try {
            if(Files.notExists(fullTmpRootPath)) {
                Files.createDirectory(fullTmpRootPath);
            }

            return fullTmpRootPath;
        } catch (IOException ex) {
            throw new NsiFileException(ex);
        }
    }
}
