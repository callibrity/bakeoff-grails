package com.callibrity.bakeoff.grails

class Artist {
    Integer id
    String name
    Genre genre
    static mapping = {
        id generator: 'identity'
        version false
    }
}
