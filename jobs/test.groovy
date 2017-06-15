//freeStyleJob('build_jks') {
pipelineJob('build_jks_p') {
    displayName('build_jks_p')
    description('')

    properties {
        githubProjectUrl('https://github.com/thbkrkr/jks')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('https://github.com/thbkrkr/jks')
                credentials('public')
            }
            branches('*/master')
        }
    }

    triggers {
        cron('H H * * *')
        githubPush()
    }

    definition {
        cps {
            sandbox()
            script("""
                node {
                    stage 'Hello world'
                    echo 'Hello World 1'
                    stage "invoke another pipeline"
                    build 'pipeline-being-called'
                    stage 'Goodbye world'
                    echo "Goodbye world"
                }
            """.stripIndent())
        }
    }
}
