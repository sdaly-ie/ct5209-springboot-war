package com.example.demo.controller;

import com.example.demo.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable int id,
                               @RequestParam String name,
                               @RequestParam String email) {
        petitionService.addSignatureToPetition(id, name, email);
        return "redirect:/petition/" + id;
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "search";
    }

    @GetMapping("/search/results")
    public String searchPetitions(@RequestParam String keyword, Model model) {
        model.addAttribute("results", petitionService.searchPetitions(keyword));
        model.addAttribute("keyword", keyword);
        return "results";
    }

    @GetMapping("/create")
    public String showCreatePetitionPage() {
        return "create";
    }

    @PostMapping("/create")
    public String createPetition(@RequestParam String title,
                                 @RequestParam String description) {
        petitionService.addPetition(title, description);
        return "redirect:/petitions";
    }
}