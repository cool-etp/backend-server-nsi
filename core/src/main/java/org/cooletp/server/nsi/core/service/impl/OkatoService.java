package org.cooletp.server.nsi.core.service.impl;

import org.cooletp.common.jpa.repo.IRepository;
import org.cooletp.common.jpa.service.IJpaCrud;
import org.cooletp.server.nsi.core.entity.Okato;
import org.cooletp.server.nsi.core.repo.IOkatoRepository;
import org.cooletp.server.nsi.core.service.IOkatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkatoService implements IOkatoService, IJpaCrud<Okato> {
    private final IOkatoRepository repository;

    @Autowired
    public OkatoService(IOkatoRepository repository) {
        this.repository = repository;
    }

    @Override
    public IRepository<Okato> getRepository() {
        return repository;
    }

    @Override
    public void reloadAllFromSource() {

    }
}
