freeStyleJob('sh-bot') {
    displayName('sh-bot')
    description('')

    properties {
        githubProjectUrl('https://github.com/thbkrkr/qli/sh-bot')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/qli')
                credentials('public')
            }
            branches('*/master')
        }
    }

    triggers {
        cron('H H * * *')
        githubPush()
    }

    steps {
        shell('make -C sh-bot build')
    }
}