package com.example.webflix.model;

public class SuggestionForm {
    private String title;
    private String type;
    private String genre;
    private Integer releaseYear;
    private String senderName;
    private String note;

    public SuggestionForm() {
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}