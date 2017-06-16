freeStyleJob('build_dops') {
    displayName('build_dops')
    description('')

    properties {
        githubProjectUrl('https://github.com/thbkrkr/dops')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/dops')
                credentials('public')
            }
            branches('*/master')
        }
    }

    triggers {
        githubPush()
    }

    steps {
        shell('make build')
    }
}