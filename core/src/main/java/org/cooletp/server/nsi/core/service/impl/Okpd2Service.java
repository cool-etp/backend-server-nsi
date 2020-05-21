package org.cooletp.server.nsi.core.service.impl;

import org.cooletp.common.jpa.repo.IRepository;
import org.cooletp.common.jpa.service.IJpaCrud;
import org.cooletp.server.nsi.core.entity.Okpd2;
import org.cooletp.server.nsi.core.repo.IOkpd2Repository;
import org.cooletp.server.nsi.core.service.IOkpd2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Okpd2Service implements IOkpd2Service, IJpaCrud<Okpd2> {
    private final IOkpd2Repository repository;

    @Autowired
    public Okpd2Service(IOkpd2Repository repository) {
        this.repository = repository;
    }

    @Override
    public IRepository<Okpd2> getRepository() {
        return repository;
    }
}
