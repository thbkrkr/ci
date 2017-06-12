job('build_jks_master') {
    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/jks')
            }
            branch('master')
        }
    }
    triggers {
        scm('H * * * *')
    }
    steps {
        shell('doo b; doo t; doo p')
    }
}
