package com.northcoders.recordshop.service;

import com.northcoders.recordshop.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    List<Album> findAllAlbums();
    Optional<Album> findAlbumById(Long id);

    Album saveAlbum(Album album);
    Album updateAlbum(Album album);

    void deleteAlbum(Long id);

    Optional<Album> findAlbumByName(String name);

    List<Album> findAlbumsByArtist(String artist);

    List<Album> findAlbumsByGenre(String genre);

    List<Album> findAlbumsByYear(int year);
}