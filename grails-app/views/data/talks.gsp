<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Talks - GR8Conf</title>
</head>
<body>
<g:set value="${talks.size()}" var="talkCount"/>
<section class="page-block">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <g:if test="${talkCount == 1}">
                    <h1>Talk</h1>
                </g:if>
                <g:else>
                    <h1>Talks</h1>
                    <p class="lead">Learn from the best and brightest in our community.</p>
                </g:else>
            </div>
        </div>

        <div class="row">
        <g:each in="${talks}" var="talk">
            <g:if test="${talkCount == 1}">
                <div class="col-md-12">
            </g:if>
            <g:else>
                <div class="col-md-6">
            </g:else>
                    <p>
                        <g:if test="${talkCount == 1}">
                            <a class="fa fa-2x" href="${createLink(controller: "data", action: "talks", params: [id: talk.id])}">${talk.title}</a>
                        </g:if>
                        <g:else>
                            <a href="${createLink(controller: "data", action: "talks", params: [id: talk.id])}">${talk.title}</a>
                        </g:else>
                        <div class="tags">
                        <g:each in="${talk.tags}" var="tag">
                            <span class="label label-info">${tag}</span>
                        </g:each>
                        </div>
                    </p>
                    <g:if test="${talkCount == 1}">
                        <p>${talk.level}</p>
                        <p>${raw(talk.summary)}</p>
                        <p>Speakers</p>
                        <ul>
                        <g:each in="${talk?.speakers}" var="speaker">
                            <li><a href="${createLink(controller: "data", action: "speakers", params: [id: speaker.id])}">${speaker.name}</a></li>
                        </g:each>
                        </ul>
                        <g:if test="${talk?.slot?.duration}">
                            <p>${talk.slot.duration} min</p>
                        </g:if>
                        <div class="slotTime" data-start="${talk?.slot?.start}" data-end="${talk?.slot?.end}"></div>
                    </g:if>
                </div>
        </g:each>
        <g:if test="${talkCount == 0}">
            <div class="col-md-12">
                <p>No Talks scheduled for GR8Conf ${grailsApplication.config.gr8conf.dates.year} yet!</p>
                <a target="_blank" href="http://cfp.gr8conf.org" class="btn btn-lg btn-info btn-raised">Submit your talk today!</a>
            </div>
        </g:if>
        </div>
    </div>
</section>
</body>
</html>
