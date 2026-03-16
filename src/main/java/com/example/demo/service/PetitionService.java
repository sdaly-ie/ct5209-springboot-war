package com.example.demo.service;

import com.example.demo.model.Petition;
import com.example.demo.model.Signature;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetitionService {

    private final List<Petition> petitions = new ArrayList<>();

    public PetitionService() {
        Petition p1 = new Petition(1, "Improve Public Transport", "Increase bus frequency and reliability.");
        Petition p2 = new Petition(2, "Protect Green Spaces", "Stop unnecessary removal of local trees and green areas.");
        Petition p3 = new Petition(3, "Support Community Beekeeping", "Create more community support for pollinator-friendly spaces.");

        p1.addSignature(new Signature("Alice", "alice@example.com"));
        p2.addSignature(new Signature("Bob", "bob@example.com"));

        petitions.add(p1);
        petitions.add(p2);
        petitions.add(p3);
    }

    public List<Petition> getAllPetitions() {
        return petitions;
    }

    public Petition getPetitionById(int id) {
        for (Petition petition : petitions) {
            if (petition.getId() == id) {
                return petition;
            }
        }
        return null;
    }

    public void addPetition(String title, String description) {
        int newId = petitions.size() + 1;
        petitions.add(new Petition(newId, title, description));
    }

    public List<Petition> searchPetitions(String keyword) {
        List<Petition> results = new ArrayList<>();

        for (Petition petition : petitions) {
            if (petition.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || petition.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(petition);
            }
        }

        return results;
    }

    public void addSignatureToPetition(int petitionId, String name, String email) {
        Petition petition = getPetitionById(petitionId);
        if (petition != null) {
            petition.addSignature(new Signature(name, email));
        }
    }
}