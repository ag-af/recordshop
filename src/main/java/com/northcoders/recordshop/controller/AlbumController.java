package com.northcoders.recordshop.controller;


import com.northcoders.recordshop.exception.EntityNotFoundException;
import com.northcoders.recordshop.exception.InvalidInputException;
import com.northcoders.recordshop.exception.ResourceNotFoundException;
import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.model.Genre;
import com.northcoders.recordshop.repository.AlbumRepository;
import com.northcoders.recordshop.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    AlbumRepository albumRepository;

    //Get: list all albums in stock (200, 204)
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.findAllAlbums();
        if (albums.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    //Get: details of a specific record, get album by id (200, 404)
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        Album album = albumService.findAlbumById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found with id: " + id));
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    //Get: get album information by album name (200, 404)
    @GetMapping("/search")
    public ResponseEntity<Album> getAlbumByName(@RequestParam("name") String albumName) {
        Optional<Album> album = albumService.findAlbumByName(albumName);
        return album.map(alb -> new ResponseEntity<>(alb, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Get: list all albums by a given artist (200, 404)
    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<Album>> getAlbumByArtist(@PathVariable String artist) {
        List<Album> albums = albumService.findAlbumsByArtist(artist);
        if (albums.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(albums, HttpStatus.OK);
        }
    }

    //Get: list all albums by a given genre (200)
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Album>> getAlbumByGenre(@PathVariable("genre") String genre) {
        Genre genreEnum;
        try {
            genreEnum = Genre.valueOf(genre.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Album> albums = albumService.findAlbumsByGenre(String.valueOf(genreEnum));
        if (albums.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(albums, HttpStatus.OK);
        }
    }

    //Get: list all albums by a given release year (200)
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Album>> getAlbumByYear(@PathVariable int year) {
        List<Album> albums = albumService.findAlbumsByYear(year);
        if (albums.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    //Get: check the health of the API (200)
    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        boolean isHealthy = albumService.isHealthy();
        if (isHealthy) {
            return new ResponseEntity<>("The Record Shop API is up and running", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The Record Shop API is down", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    //Post: add new albums into the database (201, 400)
    @PostMapping
    public ResponseEntity<Album> addAlbum(@Valid @RequestBody Album album) {
//       if (album.getTitle() == null || album.getTitle().isEmpty()) {
//           throw new InvalidInputException("Album title is required");
//       }
        Album addedAlbum = albumService.saveAlbum(album);
        return new ResponseEntity<>(addedAlbum, HttpStatus.CREATED);
    }

    //Put: update album details(change price, stock) (200, 404)
    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable(value = "id") Long id, @Valid @RequestBody Album album) {
        try {
            Album updatedAlbum = albumService.updateAlbum(id, album);
            return ResponseEntity.ok(updatedAlbum);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete: delete albums from the database (204, 404)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        try {
            albumService.deleteAlbum(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if album not found
        }
    }
}



