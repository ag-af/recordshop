package com.northcoders.recordshop.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
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

}