package com.es.nasviazi.controller;

import com.es.nasviazi.model.*;
import com.es.nasviazi.repository.ProjectResultRepository;
import com.es.nasviazi.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.System;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectResultRepository projectResultRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");

    @Autowired
    private GroupService groupService;

    @PostMapping("/process/yandex")
    public String processYandex(@ModelAttribute YandexData yandexData, Principal principal, RedirectAttributes redirectAttributes) {
        byte[] excel = groupService.createExcel(yandexData);
        ProjectResult res = projectResultRepository.save(
                new ProjectResult(yandexData.getProject(), principal.getName(), dateFormat.format(new Date()), excel));
        redirectAttributes.addFlashAttribute("project_id", res.getId());

        return "redirect:/project?success";
    }

    @PostMapping("/process/google")
    public String processGoogle(@ModelAttribute GoogleData googleData, Principal principal, RedirectAttributes redirectAttributes) {
        byte[] excel = groupService.createGoogleExcel(googleData);
        ProjectResult res = projectResultRepository.save(
                new ProjectResult(googleData.getProject(), principal.getName(), dateFormat.format(new Date()), excel));
        redirectAttributes.addFlashAttribute("project_id", res.getId());

        System.out.println("");
        groupService.createGoogleExcel(googleData);

        return "redirect:/project?success";
    }

    @GetMapping(value = "/{id}", produces = "application/vnd.ms-excel")
    @ResponseBody
    public byte[] getProject(@PathVariable Long id) {
        return projectResultRepository.findById(id).get().getFile();
    }
}
