package com.callibrity.bakeoff.grails.services

import com.callibrity.bakeoff.grails.Artist
import org.hibernate.Session

import javax.annotation.PostConstruct

class DbInitService {
    static lazyInit = false

    @PostConstruct
    void initialize() {
        try {
            println "Creating DB schema"
            Artist.withNewSession { Session session ->
                Artist.withNewTransaction {
                    session.createSQLQuery("""
                        DROP TABLE IF EXISTS artist;
                        CREATE TABLE artist (
                            id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                            name VARCHAR(500),
                            genre VARCHAR(7)
                        );                    
                    """).executeUpdate()
                }
            }
        } catch (Exception e) {
            println "Unable to create DB schema."
            println e.message
            e.printStackTrace()
            throw e
        }
    }
}