package com.northcoders.recordshop.service;

import com.northcoders.recordshop.exception.InvalidInputException;
import com.northcoders.recordshop.exception.ResourceNotFoundException;
import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.model.Genre;
import com.northcoders.recordshop.repository.AlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Optional<Album> findAlbumById(Long id) {
        return Optional.ofNullable(albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found with id: " + id)));
    }

    @Override
    public Album saveAlbum(Album album) {
        if (album.getTitle() == null || album.getTitle().isEmpty()) {
            throw new InvalidInputException("Album title cannot be empty");
        }
        if (album.getArtist() == null || album.getArtist().isEmpty()){
            throw new InvalidInputException("Artist cannot be empty");
        }
        if (album.getGenre() == null) {
            throw new InvalidInputException("Genre cannot be empty");
        }
        if (album.getReleaseYear() == null) {
            throw new InvalidInputException("Release year cannot be empty");
        }
        if (album.getPrice() == null) {
            throw  new InvalidInputException("Price cannot be empty");
        }
        if (album.getStock() == null) {
            throw new InvalidInputException("Stock cannot be empty");
        }
        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbum(Long id, Album updatedAlbum) {
        Optional<Album> existingAlbum = albumRepository.findById(id);
        if (existingAlbum.isPresent()) {
            Album album = existingAlbum.get();
            album.setTitle(updatedAlbum.getTitle());
            album.setArtist(updatedAlbum.getArtist());
            album.setGenre(updatedAlbum.getGenre());
            album.setReleaseYear(updatedAlbum.getReleaseYear());
            album.setPrice(updatedAlbum.getPrice());
            album.setStock(updatedAlbum.getStock());
            return albumRepository.save(album);
        } else {
            throw new ResourceNotFoundException("Album not found with id: " + id);
        }
    }

    @Override
    public void deleteAlbum(Long id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            albumRepository.delete(album.get());
        } else {
               throw new ResourceNotFoundException("Album not found with id: " + id);
    }
    }

    @Override
    public Optional<Album> findAlbumByName(String name) {
        return albumRepository.findByTitle(name);
    }

    @Override
    public List<Album> findAlbumsByArtist(String artist) {
        return albumRepository.findByArtist(artist);
    }

    @Override
    public List<Album> findAlbumsByGenre(String genre) {
        return albumRepository.findByGenre(genre);
    }

    @Override
    public List<Album> findAlbumsByYear(Integer year) {
        return albumRepository.findByReleaseYear(year);
    }

    @Override
    public boolean isHealthy() {
        return true;
    }

//    @Override
//    public List<Album> findAlbumsByGenre(Genre genre) {
//        return albumRepository.findByGenre(String.valueOf(genre));
//    }
}