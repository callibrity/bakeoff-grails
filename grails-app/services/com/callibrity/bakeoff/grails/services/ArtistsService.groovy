package com.callibrity.bakeoff.grails.services

import com.callibrity.bakeoff.grails.Artist
import com.callibrity.bakeoff.grails.ArtistDto
import com.callibrity.bakeoff.grails.Genre
import grails.gorm.transactions.Transactional

@Transactional
class ArtistsService {

    @Transactional(readOnly = true)
    List<ArtistDto> getAllArtists() {
        Artist.executeQuery("""
            SELECT new com.callibrity.bakeoff.grails.ArtistDto(id AS id, name AS name, genre AS genre)
            FROM Artist
            ORDER BY id ASC
        """) as List<ArtistDto>
    }

    @Transactional(readOnly = true)
    ArtistDto findArtistById(Integer id) {
        Artist artist = Artist.executeQuery("FROM Artist WHERE id = :id", [id: id])[0]
        ArtistDto.of artist
    }

    ArtistDto updateArtist(Integer id, String newName, Genre newGenre) {
        Artist artist = Artist.executeQuery("FROM Artist WHERE id = :id", [id: id])[0]
        if (artist) {
            artist.name = newName
            artist.genre = newGenre
            artist.save()
        }
        ArtistDto.of artist
    }

    ArtistDto deleteArtistById(int id) {
        Artist artist = Artist.executeQuery("FROM Artist WHERE id = :id", [id: id])[0]
        if (artist) {
            Artist.executeUpdate("DELETE FROM Artist WHERE id = :id", [id: id])
        }
        ArtistDto.of artist
    }

    ArtistDto createArtist(String name, Genre genre) {
        Artist artist = new Artist(name: name, genre: genre).save(failOnError: true)
        ArtistDto.of(artist)
    }
}


