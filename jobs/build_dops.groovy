job('build_dops_master') {
    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/dops')
            }
            branch('master')
        }
    }
    triggers {
        scm('H * * * *')
    }
    steps {
        shell('make build')
    }
}
