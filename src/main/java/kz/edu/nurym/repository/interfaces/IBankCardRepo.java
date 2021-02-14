package kz.edu.nurym.repository.interfaces;


import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IBankCardRepo extends JpaRepository<BankCard, Long> {
    BankCard findByNumber(long number);
    Set<BankCard> findAllByUser(User user);
}
