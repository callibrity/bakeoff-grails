package com.callibrity.bakeoff.grails.controllers

import com.callibrity.bakeoff.grails.ArtistDto
import com.callibrity.bakeoff.grails.Genre
import com.callibrity.bakeoff.grails.services.ArtistsService
import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest

class ArtistsControllerSpec extends HibernateSpec implements ControllerUnitTest<ArtistsController> {
    def setup() {
        controller.artistsService = GroovyMock(ArtistsService)
    }

    def "get /api/artists/ renders all artists as JSON ordered by id"() {
        given: "mocked ArtistsService"
        controller.artistsService.getAllArtists() >> [
                new ArtistDto(1, "pag", Genre.Pop),
                new ArtistDto(2, "man", Genre.Rock)
        ]
        request.method = 'GET'
        when: "hitting the index page for a list of all artists"
        controller.all()

        then: "responds with 200 and proper json"
        response.status == 200
        response.text == '[{"genre":"Pop","id":1,"name":"pag"},{"genre":"Rock","id":2,"name":"man"}]'
    }

    def "put /api/artists/{id} updates an artist"() {
        given: "mocked ArtistsService"
        controller.artistsService.updateArtist(_ as Integer, _ as String, _ as Genre) >> new ArtistDto(1, "pag2", Genre.Western)

        request.method = 'PUT'
        params["id"] = "1"
        request.json = "{\"name\": \"pag2\",\"genre\": \"Western\"}"

        when: "hitting the index page for a list of all artists"
        controller.update()

        then: "responds with 200 and proper json"
        response.status == 200
        response.text == '{"genre":"Western","id":1,"name":"pag2"}'
    }

    def "renders artist as JSON found by id"() {
        given: "mocked ArtistService"
        controller.artistsService.findArtistById(1) >> new ArtistDto(1, "pag", Genre.Pop)

        when: "hitting index with a non-int id"
        controller.params["id"] = "poggers"
        controller.show()
        then: "responds with 400"
        response.status == 400

        when: "hitting index with a valid id present"
        response.reset()
        controller.params["id"] = "1"
        controller.show()
        then: "responds with 200 and proper json"
        response.status == 200
        response.text == '{"genre":"Pop","id":1,"name":"pag"}'
    }

    def "can delete an existing artist by id"() {
        given: "mocked ArtistService"
        boolean deleted = false
        controller.artistsService.deleteArtistById(1) >> { deleted = true; return new ArtistDto(1, "pog", Genre.Western) }

        request.method = 'DELETE'

        params["id"] = 1

        when:
        controller.delete()
        then:
        response.status == 200
        deleted
    }

    def "can create new artists"() {
        given: "create artist post"
        controller.artistsService.createArtist("Raiden Shogun", Genre.Pop) >> { return new ArtistDto(1, "Raiden Shogun", Genre.Pop) }

        request.method = 'POST'
        request.json = "{\"name\": \"Raiden Shogun\",\"genre\": \"Pop\"}"

        when: "hitting create endpoint"
        controller.create()
        then:
        response.status == 200
        response.text == '{"genre":"Pop","id":1,"name":"Raiden Shogun"}'
    }
}
