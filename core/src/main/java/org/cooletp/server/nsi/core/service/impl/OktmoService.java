package org.cooletp.server.nsi.core.service.impl;


import org.cooletp.common.jpa.repo.IRepository;
import org.cooletp.common.jpa.service.IJpaCrud;
import org.cooletp.server.nsi.core.entity.Oktmo;
import org.cooletp.server.nsi.core.repo.IOktmoRepository;
import org.cooletp.server.nsi.core.service.IOktmoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OktmoService implements IOktmoService, IJpaCrud<Oktmo> {
    private final IOktmoRepository repository;

    @Autowired
    public OktmoService(IOktmoRepository repository) {
        this.repository = repository;
    }

    @Override
    public IRepository<Oktmo> getRepository() {
        return repository;
    }
}
