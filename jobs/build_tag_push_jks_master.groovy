freeStyleJob('build_jks') {
    displayName('build_jks')
    description('')

    properties {
        githubProjectUrl('https://github.com/thbkrkr/jks')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/jks')
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
        shell('doo b')
        shell('doo t')
        shell('doo p')
        shell('docker rmi $(docker images --filter dangling=true -q 2>/dev/null) 2> /dev/null || true')
    }
}