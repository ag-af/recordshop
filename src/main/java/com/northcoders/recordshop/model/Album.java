package com.northcoders.recordshop.model;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;


@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Title is required")
//    @NotBlank(message = "Title is required")
    String title;

    @Column(nullable = false)
    @NotEmpty(message = "Artist is required")
//    @NotBlank(message = "Artist is required")
    String artist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotEmpty(message = "Genre is required")
// @NotBlank(message = "Genre is required")
    Genre genre;

    @Column(name = "release_year", nullable = false)
    @NotEmpty(message = "Release year is required")
//            @NotBlank(message = "Release year is required")
    Integer releaseYear;


    @Column(nullable = false)
    @NotEmpty(message = "Price is required")
//    @NotBlank(message = "Price is required")
    @DecimalMin (value = "0.0", message = "Price must be positive")
    BigDecimal price;

    @Column(nullable = false)
    @NotEmpty(message = "Stock is required")
//    @NotBlank(message = "Stock is required")
    @Min(value = 0, message = "Stock must be at least 0")
    Integer stock;

    public Album(String title, String artist, Genre genre, Integer releaseYear, BigDecimal price, Integer stock) {
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}