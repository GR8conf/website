package us.gr8conf.website

import grails.core.GrailsApplication

class DataController {
    ApiService apiService
    GrailsApplication grailsApplication

    def speakers(Integer id) {
        List speakers = apiService.getSpeakers(id)
        respond speakers, [model: [speakers: speakers]]
    }

    def talks(Integer id) {
        List talks = apiService.getTalks(id)
        respond talks, [model: [talks: talks]]
    }

    def agenda(String day) {
        List agenda = apiService.getAgenda(day)
        String startDate = grailsApplication.config.gr8conf.dates.start
        respond agenda, [model: [agenda: agenda, startDate: startDate]]
    }

    def fullCalendar() {
        List calendar = apiService.getCalendar()
        respond calendar, [model: [calendar: calendar]]
    }

    def tags(String tag) {
        List tags = apiService.getTags(tag)
        respond tags, [model: [tags: tags]]
    }
}
