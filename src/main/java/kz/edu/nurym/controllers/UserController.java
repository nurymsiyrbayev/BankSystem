package kz.edu.nurym.controllers;


import jakarta.validation.Valid;
import kz.edu.nurym.models.BankCard;
import kz.edu.nurym.models.User;
import kz.edu.nurym.services.UserService;
import kz.edu.nurym.services.interfaces.IBankCardService;
import kz.edu.nurym.services.interfaces.IBankService;
import kz.edu.nurym.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final IBankCardService bankCardService;

    @Autowired
    public UserController(IUserService userService, IBankCardService bankCardService) {
        this.userService = userService;
        this.bankCardService = bankCardService;
    }

    @PostMapping("change/password")
    public String changePassword(@ModelAttribute("userForm") @Valid User userForm){
        userService.update(userService.changePassword(userForm.getPassword()));
        return "redirect:/user/profile";
    }


    @GetMapping("/profile")
    public String getUser(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("bankCards", bankCardService.findAllByUser(user));
        model.addAttribute("user", user);
        model.addAttribute("userForm", new User());
        return "profile";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "registration";
        if (userService.insert(userForm)) return "redirect:/user/login";
        else return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
