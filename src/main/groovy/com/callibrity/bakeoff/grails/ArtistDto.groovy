package com.callibrity.bakeoff.grails

class ArtistDto {
    ArtistDto(Integer id, String name, Genre genre) {
        this.id = id
        this.name = name
        this.genre = genre.toString()
    }

    static ArtistDto of(Artist artist) {
        if (artist) {
            new ArtistDto(artist.id, artist.name, artist.genre)
        } else {
            null
        }
    }

    Integer id
    String name
    String genre
}