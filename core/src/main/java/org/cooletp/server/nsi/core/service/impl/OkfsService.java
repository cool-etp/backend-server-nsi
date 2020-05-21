package org.cooletp.server.nsi.core.service.impl;

import org.cooletp.common.jpa.repo.IRepository;
import org.cooletp.common.jpa.service.IJpaCrud;
import org.cooletp.server.nsi.core.entity.Okfs;
import org.cooletp.server.nsi.core.repo.IOkfsRepository;
import org.cooletp.server.nsi.core.service.IOkfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkfsService implements IOkfsService, IJpaCrud<Okfs> {
    private final IOkfsRepository repository;

    @Autowired
    public OkfsService(IOkfsRepository repository) {
        this.repository = repository;
    }

    @Override
    public IRepository<Okfs> getRepository() {
        return repository;
    }
}
