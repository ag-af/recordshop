package com.northcoders.recordshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.northcoders.recordshop.model.Album;
import com.northcoders.recordshop.model.Genre;
import com.northcoders.recordshop.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;

@AutoConfigureMockMvc
@SpringBootTest
class AlbumControllerTest {

    @InjectMocks
    AlbumController albumController;

    @Mock
    AlbumService albumService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    //Mock objects in test class, inserting annotations
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //200 with the list of albums
    @Test
    void getAllAlbums() {
        //Arrange
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Album 1", "Artist 1", Genre.BLUES, 1978, new BigDecimal("12.99"), 5));
        albums.add(new Album("Album 2", "Artist 2", Genre.ROCK, 1989, new BigDecimal("9.90"), 10));
        when(albumService.findAllAlbums()).thenReturn(albums);
        //Act
        ResponseEntity<List<Album>> result = albumController.getAllAlbums();
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size()); //response body contains 2 albums, matching the size of the list
    }

    //204
    @Test
    void getNoAlbums() {
        when(albumService.findAllAlbums()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Album>> result = albumController.getAllAlbums();
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    //200 and the album
    @Test
    void getAlbumById() {
        //Arrange
        Long albumId = 1L;
        Album album = new Album("Album 1", "Artist 1", Genre.MPB, 1999, new BigDecimal("14.50"), 7);
        when(albumService.findAlbumById(albumId)).thenReturn(Optional.of(album));
        //Act
        ResponseEntity<Album> result = albumController.getAlbumById(albumId);
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(album, result.getBody());
    }

    //404
    @Test
    void getAlbumIdNotFound() {
        Long albumId = 1L;
        when(albumService.findAlbumById(albumId)).thenReturn(Optional.empty());
        ResponseEntity<Album> result = albumController.getAlbumById(albumId);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //200 and album
    @Test
    void getAlbumByName() {
        //Arrange
        String albumName = "Album name";
        Album album = new Album("Album name", "Album Artist", Genre.CHRISTMAS, 2003, new BigDecimal("10.00"), 5);
        when(albumService.findAlbumByName(albumName)).thenReturn(Optional.of(album));
        //Act
        ResponseEntity<Album> result = albumController.getAlbumByName(albumName);
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(album, result.getBody());
    }

    //404
    @Test
    void getAlbumByNameNotFound() {
        String albumName = "Does not exist";
        when(albumService.findAlbumByName(albumName)).thenReturn(Optional.empty());
        ResponseEntity<Album> result = albumController.getAlbumByName(albumName);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //200
    @Test
    void getAlbumByArtist() {
        //Arrange
        String artistName = "Artist";
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Album 1", "Artist 1", Genre.BLUES, 1990, new BigDecimal("15.99"), 17));
        albums.add(new Album("Album 2", "Artist 2", Genre.POP, 1992, new BigDecimal("13.99"), 3));
        when(albumService.findAlbumsByArtist(artistName)).thenReturn(albums);
        //Act
        ResponseEntity<List<Album>> result = albumController.getAlbumByArtist(artistName);
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(albums, result.getBody());
    }

    //404
    @Test
    void getAlbumByArtistNotFound() {
        String artistName = "Does not exist";
        when(albumService.findAlbumsByArtist(artistName)).thenReturn(new ArrayList<>());
        ResponseEntity<List<Album>> result = albumController.getAlbumByArtist(artistName);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //200
    @Test
    void getAlbumByGenre() {
        //Arrange
        Genre genre = Genre.SAMBA;
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Album 1", "Artist 1", Genre.SAMBA, 1990, new BigDecimal("15.99"), 17));
        albums.add(new Album("Album 2", "Artist 2", Genre.SAMBA, 1992, new BigDecimal("13.99"), 3));
        when(albumService.findAlbumsByGenre(String.valueOf(genre))).thenReturn(albums);
        //Act
        ResponseEntity<List<Album>> result = albumController.getAlbumByGenre(String.valueOf(genre));
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(albums, result.getBody());
    }

    //404
    @Test
    void getAlbumByGenreNotFound() {
        String genre = "ROCK";
        when(albumService.findAlbumsByGenre(String.valueOf(Genre.ROCK))).thenReturn(new ArrayList<>());
        ResponseEntity<List<Album>> result = albumController.getAlbumByGenre(genre);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //200
    @Test
    void getAlbumByYear() {
        //Arrange
        int releaseYear = 1999;
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Album 1", "Artist 1", Genre.SAMBA, 1999, new BigDecimal("15.99"), 17));
        albums.add(new Album("Album 2", "Artist 2", Genre.SAMBA, 1999, new BigDecimal("13.99"), 3));
        when(albumService.findAlbumsByYear(releaseYear)).thenReturn(albums);
        //Act
        ResponseEntity<List<Album>> result = albumController.getAlbumByYear(releaseYear);
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals(albums, result.getBody());
    }

    //404
    @Test
    void getAlbumByYearNotFound() {
        int releaseYear = 1999;
        when(albumService.findAlbumsByYear(releaseYear)).thenReturn(new ArrayList<>());
        ResponseEntity<List<Album>> result = albumController.getAlbumByYear(releaseYear);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    //200
    @Test
    void checkHealth() {
        ResponseEntity<String> result = albumController.checkHealth();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("The Record Shop API is up and running", result.getBody());
    }

    //201 with the new album
    @Test
    void addAlbum() throws Exception {
        // Arrange
        Album validAlbum = new Album("Album Title", "Artist Name", Genre.ROCK, 2021, new BigDecimal("19.99"), 10);

        String album = objectMapper.writeValueAsString(validAlbum);
        // Act & Assert
        mockMvc.perform(post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(album))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Album Title")))
                .andExpect(jsonPath("$.artist", is("Artist Name")))
                .andExpect(jsonPath("$.genre", is("ROCK")))
                .andExpect(jsonPath("$.releaseYear", is(2021)))
                .andExpect(jsonPath("$.price", is(19.99)))
                .andExpect(jsonPath("$.stock", is(10)));
    }

    //400
    @Test
    void addAlbumValidationError() throws Exception {
        // Invalid album object
        Album invalidAlbum = new Album(" ", null, Genre.BLUES, 1800, new BigDecimal("-5"), -1);
        //POST request and check for errors
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(invalidAlbum)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateAlbum() {
    }

    @Test
    void deleteAlbum() {
    }
}