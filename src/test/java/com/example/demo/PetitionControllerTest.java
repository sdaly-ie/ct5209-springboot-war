package com.example.demo;

import com.example.demo.controller.PetitionController;
import com.example.demo.service.PetitionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import com.example.demo.model.Petition;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetitionController.class)
class PetitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetitionService petitionService;

    @Test
    void shouldReturnPetitionsPage() throws Exception {
        List<Petition> petitions = List.of(
                new Petition(1, "Improve Public Transport", "Increase bus frequency and reliability."),
                new Petition(2, "Protect Green Spaces", "Stop unnecessary removal of local trees and green areas.")
        );

        when(petitionService.getAllPetitions()).thenReturn(petitions);

        mockMvc.perform(get("/petitions"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("All Petitions")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Improve Public Transport")));
    }
}
