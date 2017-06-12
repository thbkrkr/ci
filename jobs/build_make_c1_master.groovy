freeStyleJob('c1') {
    displayName('c1')
    description('')

    properties {
        githubProjectUrl('https://bitbucket.org/thb/c1')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('git@bitbucket.org:thb/c1.git')
                credentials('ci-bitbucket.id_rsa')
            }
            branches('*/master')
        }
    }

    triggers {
        cron('H H * * *')
        githubPush()
    }

    steps {
        shell('DOCKER_REPO=r.blurb.space/krkr doo bp')
    }
}