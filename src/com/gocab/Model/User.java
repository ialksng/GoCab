package com.gocab.Model;

public class User {
    public int userId;
    public String name;
    public String email;
    public long contactNumber;
    public int age;
    public String createdAt;

    public User(String name, String email, long contactNumber, int age, String createdAt) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.age = age;
        this.createdAt = createdAt;
    }

    public User(int userId, String name, String email, long contactNumber, int age, String createdAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.age = age;
        this.createdAt = createdAt;
    }
}
