package com.es.nasviazi.controller;

import com.es.nasviazi.repository.ProjectResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProjectResultRepository projectResultRepository;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("projects", projectResultRepository.findAll());
        return "admin";
    }
}
