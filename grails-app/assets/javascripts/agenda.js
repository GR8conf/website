$(function () {
    $('#calendar').fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        allDaySlot: false,
        defaultView: 'agendaDay',
        defaultDate: moment.utc(gr8ConfStart),
        height: 'auto',
        minTime: "08:00:00",
        maxTime: "18:00:00",
        allDay: false,
        editable: false,
        selectable: false,
        eventLimit: false,
        header: {
            left: 'title',
            center: '',
            right: 'prev,next'
        },
        views: {
            agendaDay: {
                type: 'agenda',
                duration: {days: 1},
                groupByResource: true // Group by rooms
            }
        },
        // Disable button when beyond conf dates.
        viewRender: function (view, element) {
            var startDate = moment.utc(gr8ConfStart);
            var endDate = moment.utc(gr8ConfEnd);
            $("#calendar .fc-next-button").prop("disabled", endDate < view.end);
            $("#calendar .fc-prev-button").prop("disabled", view.start <= startDate);
        },
        resources: [
            {id: 'Schulze Hall Auditorium', title: 'Schulze Hall Auditorium'},
            {id: 'Schultze Hall 127', title: 'Schultze Hall 127'},
            {id: 'Law School 235', title: 'Law School 235'},
            {id: 'Law School 238', title: 'Law School 238'}
        ],
        events: {
            url: "/data/fullCalendar.json",
            cache: true
        },
        eventAfterAllRender: function () {
            // Extend all track events across all resources. CSS hack.
            $(".allColumns")
                .css('width', $("#calendar").width())
                .css('background-color', "#7e7e7e")
                .css('border-color', "#7e7e7e")
                .css('color', "white");
            $(".keynote")
                .css('width', $("#calendar").width())
                .css('background-color', "#D3D3D3")
                .css('border-color', "#D3D3D3;");
            if($('#calendar').fullCalendar('clientEvents').length == 0) {
                $('#calendarHeader').text("Schedules for " + gr8ConfYear + " are not posted yet, check back soon!")
            }
        },
        // If a event has a url property, allow navigation.
        eventClick: function (calEvent, jsEvent, view) {
            if (calEvent.url) {
                location.href = calEvent.url;
                return false;
            }
        }
    });
});
