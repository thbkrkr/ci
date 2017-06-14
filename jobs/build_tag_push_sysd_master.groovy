freeStyleJob('build_sysd') {
    displayName('build_sysd')
    description('')

    properties {
        githubProjectUrl('https://github.com/thbkrkr/sysd')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/sysd')
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