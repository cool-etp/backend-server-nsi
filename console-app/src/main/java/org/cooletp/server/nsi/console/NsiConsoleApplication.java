package org.cooletp.server.nsi.console;

import lombok.extern.slf4j.Slf4j;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.cooletp.server.nsi.console.exception.FtpClientException;
import org.cooletp.server.nsi.console.loader.NsiLoader;
import org.cooletp.server.nsi.console.loader.NsiFabric;
import org.cooletp.server.nsi.console.properties.NsiProperties;
import org.cooletp.server.nsi.console.util.CliParser;
import org.cooletp.server.nsi.core.NsiJpaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import(
        NsiJpaConfiguration.class
)
@EnableConfigurationProperties(NsiProperties.class)
public class NsiConsoleApplication implements CommandLineRunner {
    private final NsiFabric nsiFabric;
    private final CliParser parser;
    private final NsiConfig config;

    public static void main(String[] args) {
        SpringApplication.run(NsiConsoleApplication.class, args);
    }

    @Autowired
    public NsiConsoleApplication(NsiFabric nsiFabric, CliParser parser, NsiConfig config) {
        this.nsiFabric = nsiFabric;
        this.parser = parser;
        this.config = config;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            parser.parse(args);

            if (parser.isHelpCommand()) {
                parser.showHelpMessage();
            } else if (parser.isTypeCommand()) {
                String nsiType = parser.getTypeValue();
                boolean loadAll = parser.isLoadAll();

                NsiLoader loader = nsiFabric.getLoader(nsiType, loadAll);
                loader.downloadFromFtp();
            } else {
                throw new CliParseException("Необходимо указать что делать. Пустой запуск не разрешен!");
            }
        } catch (CliParseException ex) {
            // oops, something went wrong
            System.err.println("Запуск не возможен.  Причина: " + ex.getMessage() + "\n\n");
            parser.showHelpMessage();
        } catch (FtpClientException ex) {
            System.err.println("Ошибка взаимодействия с FTP: " + ex.getMessage() + "\n\n");
        }
    }
}
