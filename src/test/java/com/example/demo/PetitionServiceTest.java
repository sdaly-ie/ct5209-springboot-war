package com.example.demo;

import com.example.demo.model.Petition;
import com.example.demo.service.PetitionService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PetitionServiceTest {

    @Test
    void shouldReturnSeededPetitions() {
        PetitionService petitionService = new PetitionService();

        List<Petition> petitions = petitionService.getAllPetitions();

        assertNotNull(petitions);
        assertFalse(petitions.isEmpty(), "Expected seeded petitions to exist");
    }

    @Test
    void shouldFindPetitionsByKeyword() {
        PetitionService petitionService = new PetitionService();

        List<Petition> results = petitionService.searchPetitions("Green");

        assertNotNull(results);
        assertFalse(results.isEmpty(), "Expected at least one petition to match keyword");
    }
}