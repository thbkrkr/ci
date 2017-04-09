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
        //scm('H/30 * * * *')
    }
    steps {
        shell('doo b')
    }
    publishers {
        //downstream('deploy_staging-funk_master')
    }

    configure { nodeProject ->
        nodeProject / 'publishers' << 'jenkins.plugins.hipchat.HipChatNotifier'(plugin: 'hipchat@0.1.8') {
            token()
            room("dev")
            notifySuccess(false)
            notifyAborted(true)
            notifyNotBuilt(true)
            notifyUnstable(true)
            notifyFailure(true)
            notifyBackToNormal(true)
        }
    }
}
