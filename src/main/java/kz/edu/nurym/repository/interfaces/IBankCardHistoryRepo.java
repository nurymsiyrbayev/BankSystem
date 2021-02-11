package kz.edu.nurym.repository.interfaces;

import kz.edu.nurym.models.BankCardHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBankCardHistoryRepo extends JpaRepository<BankCardHistory, Long> {
}
