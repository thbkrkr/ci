job('build_docker-toolbox_master') {
    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/docker-toolbox')
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
