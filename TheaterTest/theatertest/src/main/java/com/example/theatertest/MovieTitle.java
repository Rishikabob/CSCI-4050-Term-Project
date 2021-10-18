package com.example.theatertest;

public class MovieTitle {
    
    public enum Category{Horror, Comedy, Action, Romance, Drama}
    public enum Rating{G, PG, PG13, R}

    private String title;
    private Category category;
    private String[] cast;
    private String director;
    private String producer;
    private String synopsis;
    private String[] reviews;
    private String trailerPicture; // maybe not a string
    private String trailerVideo; // maybe not a string
    private Rating ratingCode;
    
}