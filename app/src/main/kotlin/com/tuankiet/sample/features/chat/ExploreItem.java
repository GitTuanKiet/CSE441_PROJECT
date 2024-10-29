package com.tuankiet.sample.features.chat;

public class ExploreItem {
    private final String title;
    private final String description;
    private final String category;
    private final String author;

    public ExploreItem(String title, String description, String category, String author) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }
}
