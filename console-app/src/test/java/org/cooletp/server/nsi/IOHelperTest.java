package org.cooletp.server.nsi;

import lombok.extern.slf4j.Slf4j;
import org.cooletp.server.nsi.console.exception.NsiFileException;
import org.cooletp.server.nsi.console.util.IOHelper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class IOHelperTest {

    @Test
    public void givenNothing_whenRequestTmpDirPath_thenCheckItIsExists() throws NsiFileException {
        // Сначала создадим папку
        IOHelper.createTmpDir("testDir");
        // Проверим что создалась
        Path tmpDirPath = IOHelper.getTmpDir("testDir");
        log.info(tmpDirPath.toString());
        assertThat(Files.exists(tmpDirPath)).isTrue();
        assertThat(Files.isDirectory(tmpDirPath)).isTrue();
    }
}
