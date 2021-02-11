package kz.edu.nurym.services.interfaces;

import kz.edu.nurym.models.Bank;
import kz.edu.nurym.models.BankCard;

import java.util.Optional;

public interface IBankService {
    boolean insert(Bank entity);

    boolean update(Bank entity);

    boolean remove(Bank entity);

    Optional<Bank> select(long id);

    Bank findByName(String name);
}
