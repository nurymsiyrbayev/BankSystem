package kz.edu.nurym.controllers;

import kz.edu.nurym.models.User;
import kz.edu.nurym.services.BankCardService;
import kz.edu.nurym.services.BankServicesService;
import kz.edu.nurym.services.UserService;
import kz.edu.nurym.services.interfaces.IBankCardService;
import kz.edu.nurym.services.interfaces.IBankServicesService;
import kz.edu.nurym.services.interfaces.IExchangeRateService;
import kz.edu.nurym.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service")
public class BankServiceController {

    private final IUserService userService;
    private final IBankServicesService bankServicesService;
    private final IBankCardService bankCardService;
    private final IExchangeRateService exchangeRateService;

    @Autowired
    public BankServiceController(IUserService userService, IBankServicesService bankServicesService, IBankCardService bankCardService, IExchangeRateService exchangeRateService) {
        this.userService = userService;
        this.bankServicesService = bankServicesService;
        this.bankCardService = bankCardService;
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/{id}")
    public String getSingleProductPage(@PathVariable Long id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("service", bankServicesService.select(id));
        model.addAttribute("userBankCards", bankCardService.findAllByUser(user));
        model.addAttribute("user", user);
        model.addAttribute("exchangeRates", exchangeRateService.findAll());
        return "service";
    }
}
