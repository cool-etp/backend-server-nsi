package org.cooletp.server.nsi.core.service.impl;

import org.cooletp.common.jpa.repo.IRepository;
import org.cooletp.common.jpa.service.IJpaCrud;
import org.cooletp.server.nsi.core.entity.Okopf;
import org.cooletp.server.nsi.core.repo.IOkopfRepository;
import org.cooletp.server.nsi.core.service.IOkopfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkopfService implements IOkopfService, IJpaCrud<Okopf> {
    private final IOkopfRepository repository;

    @Autowired
    public OkopfService(IOkopfRepository repository) {
        this.repository = repository;
    }

    @Override
    public IRepository<Okopf> getRepository() {
        return repository;
    }
}
