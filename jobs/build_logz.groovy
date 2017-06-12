job('build_logz_master') {
    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/logz')
            }
            branch('master')
        }
    }
    triggers {
        scm('H * * * *')
    }
    steps {
        shell('doo b')
    }
}
