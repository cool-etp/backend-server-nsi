package org.cooletp.server.nsi.core;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.cooletp.server.nsi.core.repo")
@EntityScan(basePackages = "org.cooletp.server.nsi.core.entity")
@ComponentScan(basePackages = "org.cooletp.server.nsi.core.service")
public class NsiJpaConfiguration {
    public NsiJpaConfiguration() {
        super();
    }
}
