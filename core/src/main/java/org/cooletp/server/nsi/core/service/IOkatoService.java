package org.cooletp.server.nsi.core.service;

import org.cooletp.common.service.ICrud;
import org.cooletp.server.nsi.core.entity.Okato;

public interface IOkatoService extends ICrud<Okato> {
    void reloadAllFromSource();
}
