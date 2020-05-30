package org.cooletp.server.nsi.console.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cooletp.nsi.ftp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FtpProperties {
    private String server = "localhost";
    private int port = 21;
    private String username = "user";
    private String password = "pass";

    private String nsiRootPath = "/out/nsi";
    private String nsiList = "{}";

}
