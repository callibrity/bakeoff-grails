package com.callibrity.bakeoff.grails.services

import com.callibrity.bakeoff.grails.Artist
import com.callibrity.bakeoff.grails.Genre
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class ArtistsServiceSpec extends HibernateSpec implements ServiceUnitTest<ArtistsService> {
    @Override
    List<Class> getDomainClasses() {
        [Artist]
    }

    def "returns all artists order by id"() {
        given: "Artists in the DB"
        new Artist().tap {
            name = "i'm gonna"
            genre = Genre.Western
        }.save(failOnError: true)
        new Artist().tap {
            name = "PAAAG"
            genre = Genre.Country
        }.save(failOnError: true)

        when:
        def artists = service.getAllArtists()
        then:
        artists?.size() == 2
        artists[0].name == "i'm gonna"
        artists[0].genre == Genre.Western.toString()
        artists[1].name == "PAAAG"
        artists[1].genre == Genre.Country.toString()
    }
}
