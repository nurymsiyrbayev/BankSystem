package kz.edu.nurym.controllers;

import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.ExchangeRate;
import kz.edu.nurym.models.User;
import kz.edu.nurym.services.BankCardService;
import kz.edu.nurym.services.BankServicesService;
import kz.edu.nurym.services.UserService;
import kz.edu.nurym.services.interfaces.IBankCardService;
import kz.edu.nurym.services.interfaces.IBankServicesService;
import kz.edu.nurym.services.interfaces.IExchangeRateService;
import kz.edu.nurym.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Optional;

@RestController
@RequestMapping("/service")
public class RestBankServiceController {

    private final IUserService userService;
    private final IBankServicesService bankServicesService;
    private final IBankCardService bankCardService;
    private final IExchangeRateService exchangeRateService;

    @Autowired
    public RestBankServiceController(IUserService userService, IBankServicesService bankServicesService, IBankCardService bankCardService, IExchangeRateService exchangeRateService) {
        this.userService = userService;
        this.bankServicesService = bankServicesService;
        this.bankCardService = bankCardService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping(value = "/buy/{id}/{price}/{currencyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String payService(@PathVariable("id") long id, @PathVariable("price") double price, @PathVariable("currencyId") long currencyId) {
        Optional<BankCard> bankCard = bankCardService.select(id);
        Optional<ExchangeRate> exchangeRate = exchangeRateService.select(currencyId);
        DecimalFormat df = new DecimalFormat("#.00");
        if (bankCard.isEmpty() && exchangeRate.isEmpty()) {
            return "{\"response\": \"There are no such Bank Card or Exchange Rate\"}";
        }
        if (exchangeRate.get().getId() == 1) {
            double balance = bankCard.get().getKZTCurrency() - price;
            if (balance>=0) bankCard.get().setKZTCurrency(balance);
            else return "{\"response\": \"Not enough balance\"}";
        } else if (exchangeRate.get().getId() == 2) {
            double balance = bankCard.get().getUSDCurrency() - price / exchangeRate.get().getValue();
            if (balance>=0) bankCard.get().setUSDCurrency(Double.parseDouble(df.format(balance)));
            else return "{\"response\": \"Not enough balance\"}";
        } else if (exchangeRate.get().getId() == 3) {
            double balance = bankCard.get().getEURCurrency() - price / exchangeRate.get().getValue();
            if (balance>=0) bankCard.get().setEURCurrency(Double.parseDouble(df.format(balance)));
            else return "{\"response\": \"Not enough balance\"}";
        } else {
            return "{\"response\": \"Exchange Rate Eror\"}";
        }

        if (bankCardService.update(bankCard.get())) {
            return "{\"response\": \"Pay success\"}";
        } else {
            return "{\"response\": \"Pay eror\"}";
        }
    }
}
