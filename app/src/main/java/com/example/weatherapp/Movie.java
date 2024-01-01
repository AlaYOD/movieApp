package com.example.weatherapp;

import org.json.JSONArray;

public class Movie {
    private String id;
    private String title;
    private JSONArray genres;
    private String poster;
    private double rating;
    private String overview;
    private long releaseDate;

    public Movie(String id, String title, JSONArray genres, String poster, double rating, String overview, long releaseDate) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.poster = poster;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public JSONArray getGenres() {
        return genres;
    }

    public String getPoster() {
        return poster;
    }

    public double getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public long getReleaseDate() {
        return releaseDate;
    }
}
