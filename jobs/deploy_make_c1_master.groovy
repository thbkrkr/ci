freeStyleJob('deploy_c1') {
    displayName('deploy_c1')
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
        upstream('build_c1,build_toctoc,build_functions,build_sh-bot,build_docker-toolbox,build_dops,build_jks,build_logz')
    }

    steps {
        shell('make deploy')
    }
}