package com.example.demo.controller;

import com.example.demo.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PetitionController {

    @Autowired
    private PetitionService petitionService;

    @GetMapping("/petitions")
    public String viewAllPetitions(Model model) {
        model.addAttribute("petitions", petitionService.getAllPetitions());
        return "petitions";
    }

    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable int id, Model model) {
        model.addAttribute("petition", petitionService.getPetitionById(id));
        return "petition";
    }
}