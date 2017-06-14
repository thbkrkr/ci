freeStyleJob('build_functions') {
    displayName('build_functions')
    description('')

    properties {
        githubProjectUrl('https://github.com/thbkrkr/functions')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/functions')
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
        shell('make deploy')
    }
}