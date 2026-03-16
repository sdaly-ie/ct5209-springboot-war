package com.example.demo.model;

public class Signature {
    private String name;
    private String email;

    public Signature(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}