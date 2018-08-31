package us.gr8conf.website

import grails.config.Config
import grails.core.GrailsApplication
import grails.plugin.cache.Cacheable
import grails.plugins.rest.client.RestBuilder

class ApiService {
    GrailsApplication grailsApplication
    def grailsLinkGenerator
    def EMPTY_LIST = [:]

    private Config getConfig() {
        grailsApplication.config
    }

    private String getUrl() {
        config.gr8conf.cfp.url
    }

    private String getConfId() {
        config.gr8conf.cfp.confId
    }

    @Cacheable(value = "speakers")
    List getSpeakers(Integer id) {
        List data = getData('speakers')
        if (id) {
            data = [data.find { it.id == id } ?: EMPTY_LIST]
        }
        return data
    }

    @Cacheable(value = "talks")
    List getTalks(Integer id) {
        List data = getData('talks')
        if (id) {
            data = [data.find { it.id == id } ?: EMPTY_LIST]
        }
        return data
    }

    @Cacheable(value = "agenda")
    List getAgenda(String day = null) {
        List data = getData('agenda')
        if (day) {
            data = [data.find { it.day == day } ?: EMPTY_LIST]
        }
        return data
    }

    /**
     * FullCalendar.js requires the data to be listed in events rather than by tracks as the GR8Conf api provides.
     * This maps the data to fullCalendar's format.
     * @return jsonMap compatible with fullCalendar.js
     */
    @Cacheable(value = "calendar")
    List getCalendar() {
        List json = []

        getData('agenda')?.each { date ->
            def agendaDay = date.day
            date.tracks.each { track ->
                def room = track.name
                track.slots.each { slot ->
                    List speaker = slot.speakers
                    Map jsonMap = [:]
                    jsonMap.start = "${agendaDay}T${slot.start}"
                    jsonMap.end = "${agendaDay}T${slot.end}"

                    if (!room || room == "Keynote") {
                        jsonMap.resourceId = "Schulze Hall Auditorium"
                        jsonMap.className = "keynote"
                    } else {
                        jsonMap.resourceId = room
                    }

                    if (room == "Object Partners HQ") {
                        jsonMap.resourceId = "Schulze Hall Auditorium"
                        jsonMap.className = "allColumns"
                    }

                    if (!track.breaks) {
                        jsonMap.id = slot.talk.id
                        jsonMap.title = "${slot.talk.title} - ${slot?.speakers[0]?.name}"
                        jsonMap.url = "${grailsLinkGenerator.link(controller: "talks")}/${slot.talk.id}"
                    } else {
                        jsonMap.id = "${slot.name}"
                        jsonMap.resourceId = "Schulze Hall Auditorium"
                        jsonMap.title = slot.name
                        jsonMap.className = track.allColumns ? "allColumns" : ""
                    }
                    json.add(jsonMap)
                }
            }
        }
        return json
    }

    @Cacheable(value = "tags")
    List getTags(String tag) {
        List data = getData('tags')
        if (tag) {
            data = [data.find { it.tag == tag } ?: EMPTY_LIST]
        }
        return data
    }

    private List getData(String type) {
        RestBuilder rest = new RestBuilder()
        def response = rest.get("${url}/${type}/${confId}") {
            accept 'application/json'
        }

        if (response.status == 200) {
            return response.json
        } else {
            log.error "Error communicating with API for ${type}"
            return null
        }
    }
}
