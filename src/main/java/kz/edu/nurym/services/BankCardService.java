package kz.edu.nurym.services;

import kz.edu.nurym.filters.Logger;
import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.ExchangeRate;
import kz.edu.nurym.models.User;
import kz.edu.nurym.repository.interfaces.IBankCardRepo;
import kz.edu.nurym.services.interfaces.IBankCardService;
import kz.edu.nurym.services.interfaces.IExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BankCardService implements IBankCardService {
    private final IBankCardRepo bankCardRepo;
    private final IExchangeRateService exchangeRateService;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public BankCardService(IBankCardRepo bankCardRepo,ExchangeRateService exchangeRateService) {
        this.bankCardRepo = bankCardRepo;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public boolean insert(BankCard entity) {
        BankCard bankCardFromDB = bankCardRepo.findByNumber(entity.getNumber());
        if (bankCardFromDB != null) {
            return false;
        }
        bankCardRepo.save(entity);
        executorService.submit(new Logger(IBankCardRepo.class.getName(), "Insert was successfully. BankCard -> " + entity));
        return true;
    }

    @Override
    public boolean update(BankCard entity) {
        bankCardRepo.saveAndFlush(entity);
        executorService.submit(new Logger(IBankCardRepo.class.getName(), "Update was successfully. BankCard -> " + entity));
        return true;
    }

    @Override
    public boolean remove(BankCard entity) {
        if (bankCardRepo.findById(entity.getBank_card_id()).isEmpty()) {
            return false;
        }
        bankCardRepo.delete(entity);
        executorService.submit(new Logger(IBankCardRepo.class.getName(), "Remove was successfully. BankCard -> " + entity));
        return true;
    }

    @Override
    public Optional<BankCard> select(long id) {
        return bankCardRepo.findById(id);
    }

    @Override
    public BankCard findByNumber(long number) {
        return bankCardRepo.findByNumber(number);
    }

    @Override
    public Set<BankCard> findAllByUser(User user) {
        return bankCardRepo.findAllByUser(user);
    }

    @Transactional
    @Override
    public String convertMoney(BankCard bankCard, long fromCurrencyId, long toCurrencyId, double amount) {
        String convertIdentity = fromCurrencyId + "->" + toCurrencyId;
        ExecutorService executorService = Executors.newCachedThreadPool();
        switch ((int) fromCurrencyId) {
            case 1 -> {
                if (bankCard.getKZTCurrency() - amount < 0) return "{\"response\": \" Not enough Tg balance\"}";
                bankCard.setKZTCurrency(bankCard.getKZTCurrency() - amount);
            }
            case 2 -> {
                if (bankCard.getUSDCurrency() - amount < 0) return "{\"response\": \" Not enough $ balance\"}";
                bankCard.setUSDCurrency(bankCard.getUSDCurrency() - amount);
            }
            case 3 -> {
                if (bankCard.getEURCurrency() - amount < 0) return "{\"response\": \" Not enough € balance\"}";
                bankCard.setEURCurrency(bankCard.getEURCurrency() - amount);
            }
        }
        executorService.submit(() -> convertProcess(amount, bankCard, convertIdentity));
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "{\"response\": \"Convert success\"}";
    }

    private void convertProcess(Double amount, BankCard bankCard, String convertIdentity) {
        DecimalFormat df = new DecimalFormat("#.00");
        switch (convertIdentity) {
            case "1->2" -> bankCard.setUSDCurrency(bankCard.getUSDCurrency() + Double.parseDouble(df.format(amount / exchangeRateService.select(2).get().getValue())));
            case "1->3" -> bankCard.setEURCurrency(bankCard.getEURCurrency() + Double.parseDouble(df.format(amount / exchangeRateService.select(3).get().getValue())));
            case "2->1" -> bankCard.setKZTCurrency(bankCard.getKZTCurrency() + Double.parseDouble(df.format(amount * exchangeRateService.select(2).get().getValue())));
            case "2->3" -> bankCard.setEURCurrency(bankCard.getEURCurrency() + Double.parseDouble(df.format(amount * exchangeRateService.select(2).get().getValue() / exchangeRateService.select(3).get().getValue())));
            case "3->2" -> bankCard.setUSDCurrency(bankCard.getUSDCurrency() + Double.parseDouble(df.format(amount * exchangeRateService.select(3).get().getValue() / exchangeRateService.select(2).get().getValue())));
            case "3->1" -> bankCard.setKZTCurrency(bankCard.getKZTCurrency() + Double.parseDouble(df.format(amount * exchangeRateService.select(3).get().getValue())));
        }
    }
}
