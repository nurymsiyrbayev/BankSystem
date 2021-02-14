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
public class RestTransferController {

    private final IUserService userService;
    private final IBankServicesService bankServicesService;
    private final IBankCardService bankCardService;
    private final IExchangeRateService exchangeRateService;

    @Autowired
    public RestTransferController(IUserService userService, IBankServicesService bankServicesService, IBankCardService bankCardService, IExchangeRateService exchangeRateService) {
        this.userService = userService;
        this.bankServicesService = bankServicesService;
        this.bankCardService = bankCardService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping(value = "transfer/{id}/{amount}/{currencyId}/{cardNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String payService( @PathVariable("id") long id, @PathVariable("amount") double amount, @PathVariable("currencyId") long currencyId, @PathVariable("cardNumber") long cardNumber) {
        Optional<BankCard> fromBankCard = bankCardService.select(id);
        BankCard toBankCard = bankCardService.findByNumber(cardNumber);
        Optional<ExchangeRate> exchangeRate = exchangeRateService.select(currencyId);
        DecimalFormat df = new DecimalFormat("#.00");
        if (fromBankCard.isEmpty() || exchangeRate.isEmpty() ||  toBankCard == null) {
            return "{\"response\": \"There are no such Bank Card or Exchange Rate\"}";
        }
        double transferAmount = amount * exchangeRate.get().getValue();
        if (toBankCard.getBank().getId() != 1 || transferAmount >= 100000){
            transferAmount = transferAmount * 0.99;
        }
        transferAmount = Double.parseDouble(df.format(transferAmount / exchangeRate.get().getValue()));
        if (exchangeRate.get().getId() == 1) {
            double balance = fromBankCard.get().getKZTCurrency() - amount;
            if (balance>=0) {
                fromBankCard.get().setKZTCurrency(balance);
                toBankCard.setKZTCurrency(toBankCard.getKZTCurrency() + transferAmount);
            }
            else return "{\"response\": \"Not enough Tg balance\"}";
        } else if (exchangeRate.get().getId() == 2) {
            double balance = fromBankCard.get().getUSDCurrency() - amount;
            if (balance>=0) {
                fromBankCard.get().setUSDCurrency(Double.parseDouble(df.format(balance)));
                toBankCard.setUSDCurrency(toBankCard.getUSDCurrency() + Double.parseDouble(df.format(transferAmount)));
            }
            else return "{\"response\": \"Not enough $ balance\"}";
        } else if (exchangeRate.get().getId() == 3) {
            double balance = fromBankCard.get().getEURCurrency() - amount;
            if (balance>=0) {
                fromBankCard.get().setEURCurrency(Double.parseDouble(df.format(balance)));
                toBankCard.setEURCurrency(toBankCard.getEURCurrency() + Double.parseDouble(df.format(transferAmount)));
            }
            else return "{\"response\": \"Not enough € balance\"}";
        } else {
            return "{\"response\": \"Exchange Rate Eror\"}";
        }

        if (bankCardService.update(fromBankCard.get()) && bankCardService.update(toBankCard)) {
            return "{\"response\": \"Transfer success\"}";
        } else {
            return "{\"response\": \"Transfer update eror\"}";
        }
    }
}
