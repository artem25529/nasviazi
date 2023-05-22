package com.es.nasviazi.controller;

import com.es.nasviazi.model.User;
import com.es.nasviazi.repository.AuthorityRepository;
import com.es.nasviazi.repository.UserRepository;
import com.es.nasviazi.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reg")
public class UserRegController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserValidator validator;

    @Autowired
    private PasswordEncoder encoder;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping
    public String regForm(Model model) {
        model.addAttribute("user", new User());
        return "reg";
    }

    @PostMapping
    public String process(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "reg";
        }

        user.setEnabled(true);
        user.setAuthorities(List.of(authorityRepository.findByAuthority("USER").get()));
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);
        return "redirect:/";
    }
}
