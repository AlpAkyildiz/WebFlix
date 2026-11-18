package com.example.webflix.model;

import java.util.List;

public class MediaContent {
    private Long id;
    private String title;
    private String type;        // "FILM" veya "DIZI"
    private String genre;       // "Bilim Kurgu", "Gerilim", "Dram", "Aksiyon", "Suç"
    private int releaseYear;
    private double rating;
    private String duration;
    private String director;
    private String posterUrl;
    private String trailerUrl;
    private String summary;
    private List<String> cast;

    public MediaContent() {
    }

    public MediaContent(Long id, String title, String type, String genre, int releaseYear,
                        double rating, String duration, String director, String posterUrl,
                        String trailerUrl, String summary, List<String> cast) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.director = director;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.summary = summary;
        this.cast = cast;
    }

    public String getCastAsString() {
        return (cast == null || cast.isEmpty()) ? "" : String.join(", ", cast);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public List<String> getCast() { return cast; }
    public void setCast(List<String> cast) { this.cast = cast; }
}