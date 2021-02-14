package kz.edu.nurym.services.interfaces;

import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IBankCardService {
    boolean insert(BankCard entity);

    boolean update(BankCard entity);

    boolean remove(BankCard entity);

    Optional<BankCard> select(long id);

    BankCard findByNumber(long number);

    Set<BankCard> findAllByUser(User user);

    String convertMoney(BankCard bankCard, long fromCurrencyId, long toCurrencyId, double amount);
}
