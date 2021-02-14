package kz.edu.nurym.controllers;

import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.ExchangeRate;
import kz.edu.nurym.services.interfaces.IBankCardService;
import kz.edu.nurym.services.interfaces.IBankServicesService;
import kz.edu.nurym.services.interfaces.IExchangeRateService;
import kz.edu.nurym.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Optional;

@RestController
public class RestConvertController {

    private final IUserService userService;
    private final IBankServicesService bankServicesService;
    private final IBankCardService bankCardService;
    private final IExchangeRateService exchangeRateService;

    @Autowired
    public RestConvertController(IUserService userService, IBankServicesService bankServicesService, IBankCardService bankCardService, IExchangeRateService exchangeRateService) {
        this.userService = userService;
        this.bankServicesService = bankServicesService;
        this.bankCardService = bankCardService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping(value = "convert/{id}/{fromCurrencyId}/{toCurrencyId}/{amount}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String payService(@PathVariable("id") long id, @PathVariable("amount") double amount, @PathVariable("fromCurrencyId") long fromCurrencyId, @PathVariable("toCurrencyId") long toCurrencyId) {
        if (fromCurrencyId == toCurrencyId) return "{\"response\": \"Currencies are same\"}";
        Optional<BankCard> bankCard = bankCardService.select(id);
        if (bankCard.isEmpty()) return "{\"response\": \"There is no such Bank Card\"}";
        return bankCardService.convertMoney(bankCard.get(),fromCurrencyId,toCurrencyId,amount);
    }
}
