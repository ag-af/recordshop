package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Album updateAlbum(Album album) {
        return albumRepository.save(album);
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