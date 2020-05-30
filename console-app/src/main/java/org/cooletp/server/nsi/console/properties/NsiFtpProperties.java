package org.cooletp.server.nsi.console.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cooletp.common.ftp.FtpProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cooletp.nsi.ftp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NsiFtpProperties extends FtpProperties {
    private String nsiRootPath = "/out/nsi";
    private String nsiList = "{}";
}
