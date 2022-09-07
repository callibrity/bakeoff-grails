package com.callibrity.bakeoff.grails

class UrlMappings {

    static mappings = {
        "/api/artists"(controller: 'artists') {
            action = [GET: 'all', POST: 'create']
        }
        "/api/artists/"(controller: 'artists') {
            action = [GET: 'all', POST: 'create']
        }
        "/api/artists/$id"(controller: 'artists') {
            action = [GET: 'show', PUT: 'update', DELETE: 'delete']
        }
        "/api/artists/$id/"(controller: 'artists') {
            action = [GET: 'show', PUT: 'update', DELETE: 'delete']
        }
        "/"(view: "/index")
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
