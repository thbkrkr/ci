job('build_sysd_master') {
    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/sysd.git')
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
