package kz.edu.nurym.services;

import kz.edu.nurym.models.ExchangeRate;
import kz.edu.nurym.repository.interfaces.IExchangeRateRepo;
import kz.edu.nurym.services.interfaces.IExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService implements IExchangeRateService {
    private final IExchangeRateRepo exchangeRateRepo;

    @Autowired
    public ExchangeRateService(IExchangeRateRepo exchangeRateRepo) {
        this.exchangeRateRepo = exchangeRateRepo;
    }

    @Override
    public Optional<ExchangeRate> select(long id) {
        return exchangeRateRepo.findById(id);
    }

    @Override
    public List<ExchangeRate> findAll() {
        return exchangeRateRepo.findAll();
    }
}
