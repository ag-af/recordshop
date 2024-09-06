package com.northcoders.recordshop.controller;


import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.model.Genre;
import com.northcoders.recordshop.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

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
        Optional<Album> album = albumService.findAlbumById(id);
        return album.map(alb -> new ResponseEntity<>(alb, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
            return new ResponseEntity<>(albums,HttpStatus.OK);
        }

        //Get: check the health of the API (200)
        @GetMapping("/health")
        public ResponseEntity<String> checkHealth() {
            return new ResponseEntity<>("The Record Shop API is up and running", HttpStatus.OK);
        }

        //Post: add new albums into the database (201, 400)
        @PostMapping
        public ResponseEntity<Album> addAlbum(@Valid @RequestBody Album album) {
            Album addedAlbum = albumService.saveAlbum(album);
            return new ResponseEntity<>(addedAlbum, HttpStatus.CREATED);
        }

        //Put: update album details(change price, stock) (200, 404)
        @PutMapping("/{id}")
        public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album albumDetails) {
            //service layer to retrieve album by id, check if the album exists, then get the album, otherwise return a 404
            //Update all fields with new data
            //updatedAlbum save the updated album(save handle inserting and updating)
            // If album was updated return the album and 200
            // If album does not exist 400
            Optional<Album> updatingAlbum = albumService.findAlbumById(id);
            if (updatingAlbum.isPresent()) {
                Album albumUpdate = updatingAlbum.get();
                albumUpdate.setTitle(albumDetails.getTitle());
                albumUpdate.setArtist(albumDetails.getArtist());
                albumUpdate.setGenre(albumDetails.getGenre());
                albumUpdate.setReleaseYear(albumDetails.getReleaseYear());
                albumUpdate.setPrice(albumDetails.getPrice());
                albumUpdate.setStock(albumUpdate.getStock());
                Album updatedAlbum = albumService.updateAlbum(albumUpdate);
                return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        //Delete: delete albums from the database (204, 404)
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
            Optional<Album> album = albumService.findAlbumById(id);
            if (album.isPresent()) {
                albumService.deleteAlbum(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }