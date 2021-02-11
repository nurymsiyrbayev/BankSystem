package kz.edu.nurym.services;

import kz.edu.nurym.models.Authority;
import kz.edu.nurym.repository.interfaces.IAuthorityRepo;
import kz.edu.nurym.services.interfaces.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthorityService implements IAuthorityService {
    private IAuthorityRepo authorityRepo;

    public AuthorityService(IAuthorityRepo authorityRepo) {
        this.authorityRepo = authorityRepo;
    }

    @Override
    public boolean insert(Authority entity) {
        authorityRepo.save(entity);
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
