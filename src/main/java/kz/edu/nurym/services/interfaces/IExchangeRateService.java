package kz.edu.nurym.services.interfaces;

import kz.edu.nurym.models.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface IExchangeRateService {
    Optional<ExchangeRate> select(long id);
    List<ExchangeRate> findAll();
}
