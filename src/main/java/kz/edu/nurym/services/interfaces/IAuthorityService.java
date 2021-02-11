package kz.edu.nurym.services.interfaces;

import kz.edu.nurym.models.Authority;
import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.User;

import java.util.Optional;
import java.util.Set;

public interface IAuthorityService {

    boolean insert(Authority entity);

    Optional<Authority> select(long id);

    Set<Authority> findAll();
}
