package org.cooletp.server.nsi.console;

import lombok.extern.slf4j.Slf4j;
import org.cooletp.server.nsi.core.NsiJpaConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import(
        NsiJpaConfiguration.class
)
public class NsiConsoleApplication implements CommandLineRunner {
    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION!");
        SpringApplication.run(NsiConsoleApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("EXECUTING : command line runner");

        for (int i = 0; i < args.length; ++i) {
            log.info("args[{}]: {}", i, args[i]);
        }
    }
}
