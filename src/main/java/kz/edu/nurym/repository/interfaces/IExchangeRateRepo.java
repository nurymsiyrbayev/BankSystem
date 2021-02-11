package kz.edu.nurym.repository.interfaces;

import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExchangeRateRepo extends JpaRepository<ExchangeRate, Long> {
}
