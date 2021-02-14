package kz.edu.nurym.controllers;

import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.User;
import kz.edu.nurym.services.BankCardService;
import kz.edu.nurym.services.BankService;
import kz.edu.nurym.services.BankServicesService;
import kz.edu.nurym.services.UserService;
import kz.edu.nurym.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class BankController {
    private final IUserService userService;
    private final IBankServicesService bankServicesService;
    private final IBankCardService bankCardService;
    private final IExchangeRateService exchangeRateService;

    @Autowired
    public BankController(IUserService userService, IBankServicesService bankServicesService, IBankCardService bankCardService, IExchangeRateService exchangeRateService) {
        this.userService = userService;
        this.bankServicesService = bankServicesService;
        this.bankCardService = bankCardService;
        this.exchangeRateService = exchangeRateService;
    }

    @RequestMapping("/home")
    public String home( Model model) {
        String loginedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginedUser = userService.findByUsername(loginedUserUsername);
//        model.addAttribute("bankCards", bankCardService.findAllByUser(loginedUser));
        model.addAttribute("bankServices", bankServicesService.findAll());
        return "home";
    }



    @RequestMapping("/convert")
    public String convert(Model model){
        String loginedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginedUser = userService.findByUsername(loginedUserUsername);
        model.addAttribute("userBankCards", bankCardService.findAllByUser(loginedUser));
        model.addAttribute("exchangeRates", exchangeRateService.findAll());
        return "convert";
    }

    @RequestMapping("/transfer")
    public String transfer(Model model){
        String loginedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginedUser = userService.findByUsername(loginedUserUsername);
        model.addAttribute("userBankCards", bankCardService.findAllByUser(loginedUser));
        model.addAttribute("exchangeRates", exchangeRateService.findAll());
        return "transfer";
    }

}
