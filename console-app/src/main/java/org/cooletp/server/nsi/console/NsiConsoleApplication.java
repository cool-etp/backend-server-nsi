package org.cooletp.server.nsi.console;

import lombok.extern.slf4j.Slf4j;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.cooletp.server.nsi.console.exception.FtpClientException;
import org.cooletp.server.nsi.console.ftp.NsiFtpLoader;
import org.cooletp.server.nsi.console.ftp.NsiLoaderFabric;
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
    private final NsiLoaderFabric nsiLoaderFabric;
    private final CliParser parser;
    private final NsiConfig config;

    public static void main(String[] args) {
        SpringApplication.run(NsiConsoleApplication.class, args);
    }

    @Autowired
    public NsiConsoleApplication(NsiLoaderFabric nsiLoaderFabric, CliParser parser, NsiConfig config) {
        this.nsiLoaderFabric = nsiLoaderFabric;
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

                NsiFtpLoader loader = nsiLoaderFabric.getLoader(nsiType, loadAll);
                loader.open();
                loader.startLoading();
                loader.close();
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
