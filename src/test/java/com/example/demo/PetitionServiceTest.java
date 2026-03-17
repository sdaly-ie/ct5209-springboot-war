package com.example.demo;

import com.example.demo.model.Petition;
import com.example.demo.service.PetitionService;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void shouldAddNewPetition() {
        PetitionService petitionService = new PetitionService();

        int before = petitionService.getAllPetitions().size();

        petitionService.addPetition("Test Petition", "Test Description");

        List<Petition> petitions = petitionService.getAllPetitions();
        int after = petitions.size();
        Petition lastPetition = petitions.get(after - 1);

        assertEquals(before + 1, after);
        assertEquals("Test Petition", lastPetition.getTitle());
        assertEquals("Test Description", lastPetition.getDescription());
    }
}