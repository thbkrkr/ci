freeStyleJob('build_c1') {
    displayName('build_c1')
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
        bitbucketPush()
    }

    steps {
        shell('make build push')
    }
}