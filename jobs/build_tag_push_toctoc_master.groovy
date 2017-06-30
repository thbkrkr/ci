freeStyleJob('build_toctoc') {
    displayName('build_toctoc')
    description('')

    properties {
        githubProjectUrl('https://github.com/thbkrkr/toctoc')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/toctoc')
                credentials('public')
            }
            branches('*/master')
        }
    }

    triggers {
        githubPush()
    }

    steps {
        shell('doo b')
        shell('doo t')
        shell('doo p')
        shell('docker rmi $(docker images --filter dangling=true -q 2>/dev/null) 2> /dev/null || true')
    }
}