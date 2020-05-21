package org.cooletp.server.nsi.core.service.impl;

import org.cooletp.common.jpa.repo.IRepository;
import org.cooletp.common.jpa.service.IJpaCrud;
import org.cooletp.server.nsi.core.entity.Okved2;
import org.cooletp.server.nsi.core.repo.IOkved2Repository;
import org.cooletp.server.nsi.core.service.IOkved2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Okved2Service implements IOkved2Service, IJpaCrud<Okved2> {
    private final IOkved2Repository repository;

    @Autowired
    public Okved2Service(IOkved2Repository repository) {
        this.repository = repository;
    }

    @Override
    public IRepository<Okved2> getRepository() {
        return repository;
    }
}
