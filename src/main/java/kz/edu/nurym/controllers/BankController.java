package kz.edu.nurym.controllers;

import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.User;
import kz.edu.nurym.services.BankCardService;
import kz.edu.nurym.services.BankService;
import kz.edu.nurym.services.BankServicesService;
import kz.edu.nurym.services.UserService;
import kz.edu.nurym.services.interfaces.IBankCardService;
import kz.edu.nurym.services.interfaces.IBankService;
import kz.edu.nurym.services.interfaces.IBankServicesService;
import kz.edu.nurym.services.interfaces.IUserService;
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
@RequestMapping("/bank")
public class BankController {
    private final IUserService userService;
    private final IBankServicesService bankServicesService;
    private final IBankCardService bankCardService;

    @Autowired
    public BankController(UserService userService, BankServicesService bankServicesService, BankCardService bankCardService) {
        this.userService = userService;
        this.bankServicesService = bankServicesService;
        this.bankCardService = bankCardService;
    }


    @GetMapping("/home")
    public String home( Model model) {
        String loginedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginedUser = userService.findByUsername(loginedUserUsername);
//        model.addAttribute("bankCards", bankCardService.findAllByUser(loginedUser));
        model.addAttribute("bankServices", bankServicesService.findAll());
        return "home";
    }

    @GetMapping("/services/{id}")
    public String getSingleProductPage(@PathVariable Long id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("service", bankServicesService.select(id));
        model.addAttribute("userBankCards", bankCardService.findAllByUser(user));
        model.addAttribute("user", user);
        return "service";
    }

    public String service(Model model){
        return "services";
    }

    @PostMapping(value = "/service/buy/{id}/{price}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String payService(@PathVariable("id") long id, @PathVariable("price") float price,Model model) {
        Optional<BankCard> bankCard = bankCardService.select(id);
        if (bankCard.isPresent()) {
            bankCard.get().setKZTCurrency(bankCard.get().getKZTCurrency() - price);
            if (bankCardService.update(bankCard.get())){
                return "{\"response\": \"Pay success\"}";
            }else {
                return "{\"response\": \"Pay eror\"}";
            }
        }else
            return "{\"bankCardEror\": \"BankCard Eror\"}";
    }

    @GetMapping("/convert")
    public String convert(Model model){
        return "convert";
    }

    @GetMapping("/transfer")
    public String transfer(Model model){
        return "transfer";
    }

    @GetMapping("/history")
    public String history(Model model){
        return "history";
    }

}
