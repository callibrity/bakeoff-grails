FROM openjdk:11

COPY build/libs/bakeoff-grails-0.1.jar .
CMD java -jar bakeoff-grails-0.1.jar