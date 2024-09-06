package com.northcoders.recordshop.model;
import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotNull(message = "Title is required")
    String title;

    @Column(nullable = false)
    @NotNull(message = "Artist is required")
    String artist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Genre is required")
    Genre genre;

    @Column(name = "release_year", nullable = false)
    @NotNull(message = "Release year is required")
    int releaseYear;

    @Column(nullable = false)
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    BigDecimal price;

    @Column(nullable = false)
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be at least 0")
    int stock;

    public Album(String title, String artist, Genre genre, int releaseYear, BigDecimal price, int stock) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.price = price;
        this.stock = stock;
    }

    public Album(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}