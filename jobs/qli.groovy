job('build_qli_master') {
    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/qli')
            }
            branch('master')
        }
    }
    triggers {
        scm('H * * * *')
    }
    steps {
        shell('make -C qws build')
    }
}
