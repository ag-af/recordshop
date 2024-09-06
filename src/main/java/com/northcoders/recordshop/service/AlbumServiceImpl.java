package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.repository.AlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
        return albumRepository.findById(id);
    }

    @Override
    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbum(Long id, Album updatedAlbum) {
        return albumRepository.findById(id).map(album -> {
            album.setTitle(updatedAlbum.getTitle());
            album.setArtist(updatedAlbum.getArtist());
            album.setGenre(updatedAlbum.getGenre());
            album.setReleaseYear(updatedAlbum.getReleaseYear());
            album.setPrice(updatedAlbum.getPrice());
            album.setStock(updatedAlbum.getStock());
            return albumRepository.save(album);
        }).orElseThrow(() -> new EntityNotFoundException("Album not found"));
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
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
    public List<Album> findAlbumsByYear(int year) {
        return albumRepository.findByReleaseYear(year);
    }
}