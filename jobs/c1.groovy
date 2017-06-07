job('build_c1_master') {
    scm {
        git {
            remote {
                url('git@bitbucket.org:thb/c1.git')
                credentials('ci-bitbucket.id_rsa')
            }
            branch('master')
        }
    }
    triggers {
        scm('H * * * *')
    }
    steps {
        shell('make build tag push deploy')
    }
}
