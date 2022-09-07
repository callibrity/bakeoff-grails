package com.callibrity.bakeoff.grails.controllers

import com.callibrity.bakeoff.grails.ArtistDto
import com.callibrity.bakeoff.grails.Genre
import com.callibrity.bakeoff.grails.services.ArtistsService
import grails.converters.JSON

class ArtistsController {
    ArtistsService artistsService

    def update() {
        try {
            def json = request.JSON
            if (params["id"].toString().isInteger()) {
                def artist = artistsService.updateArtist(params["id"] as Integer, json["name"] as String, json["genre"] as Genre)
                if (artist) {
                    render artist as JSON
                } else {
                    log.info "Can't update non-existant artist with id ${params['id']}"
                    render status: 404
                }
            } else {
                log.info "Can't update artist with non-int id ${params['id']}"
                render status: 400
            }
        } catch (Exception e) {
            log.error "Caught exception updating artist.", e
            render(status: 500)
        }
    }

    def delete() {
        try {
            if (params["id"].toString().isInteger()) {
                ArtistDto artist = artistsService.deleteArtistById(params["id"] as Integer)
                if (artist) {
                    render status: 200
                } else {
                    log.info "Cannot delete artist with id ${params['id']}."
                    render status: 404
                }
            } else {
                log.info "Cannot delete artist with non-int id ${params['id']}."
                render status: 400
            }
        } catch (Exception e) {
            log.error "Caught exception deleting artist.", e
            render(status: 500)
        }
    }


    def show() {
        try {
            if (params["id"].toString().isInteger()) {
                render(artistsService.findArtistById(params["id"] as Integer) as JSON)
            } else {
                log.info "Cannot show artist with non-int id ${params['id']}."
                render status: 400
            }
        } catch (Exception e) {
            log.error "Caught exception showing artist.", e
            render(status: 500)
        }
    }

    def all() {
        try {
            render(artistsService.getAllArtists() as JSON)
        } catch (Exception e) {
            log.error "Caught exception showing all artists.", e
            render(status: 500)
        }
    }

    def create() {
        try {
            def json = request.JSON
            def artist = artistsService.createArtist(json["name"] as String, json["genre"] as Genre)
            render(artist as JSON)
        } catch (Exception e) {
            log.error "Caught exception creating artist.", e
            render(status: 500)
        }
    }
}
