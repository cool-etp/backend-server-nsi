package org.cooletp.server.nsi.core.service.impl;


import org.cooletp.common.jpa.repo.IRepository;
import org.cooletp.common.jpa.service.IJpaCrud;
import org.cooletp.server.nsi.core.entity.Okei;
import org.cooletp.server.nsi.core.repo.IOkeiRepository;
import org.cooletp.server.nsi.core.service.IOkeiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkeiService implements IOkeiService, IJpaCrud<Okei> {
    private final IOkeiRepository repository;

    @Autowired
    public OkeiService(IOkeiRepository repository) {
        this.repository = repository;
    }

    @Override
    public IRepository<Okei> getRepository() {
        return repository;
    }
}
