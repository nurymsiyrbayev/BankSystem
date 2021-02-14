package kz.edu.nurym.services;

import kz.edu.nurym.filters.Logger;
import kz.edu.nurym.models.Authority;
import kz.edu.nurym.repository.interfaces.IAuthorityRepo;
import kz.edu.nurym.repository.interfaces.IBankCardRepo;
import kz.edu.nurym.services.interfaces.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AuthorityService implements IAuthorityService {
    private IAuthorityRepo authorityRepo;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public AuthorityService(IAuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
    }

    @Override
    public boolean insert(Authority entity) {
        authorityRepo.save(entity);
        executorService.submit(new Logger(IAuthorityRepo.class.getName(), "Insert was successfully. BankCard -> " + entity));
        return true;
    }

    @Override
    public Optional<Authority> select(long id) {

        return authorityRepo.findById(id);
    }

    @Override
    public Set<Authority> findAll() {
        return findAll();
    }
}
