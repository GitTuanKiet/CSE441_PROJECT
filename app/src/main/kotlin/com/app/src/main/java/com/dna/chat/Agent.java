package com.dna.chat;

public class Agent {
    private String title;
    private String description;
    private String category;
    private String author;

    public Agent(String title, String description, String category, String author) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.author = author;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
