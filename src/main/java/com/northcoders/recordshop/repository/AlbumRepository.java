package com.northcoders.recordshop.repository;

import com.northcoders.recordshop.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByTitle(String title);
    List<Album> findByArtist(String artist);
    List<Album> findByReleaseYear(int year);
    List<Album> findByGenre(String genre);
    /*
    findAll()
    findById()
    save()
    deleteById()
     */
}