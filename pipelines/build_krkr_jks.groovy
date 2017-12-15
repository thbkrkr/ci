pipelineJob('build_krkr_jks') {

    description('krkr/jks')

    triggers {
        githubPush()
    }

    definition {
        cps {
            properties {
                githubProjectUrl('https://github.com/thbkrkr/jks.git')
            }

            logRotator {
                numToKeep(20)
                daysToKeep(30)
            }

            scm {
                git {
                    remote {
                        url('https://github.com/thbkrkr/jks.git')
                        credentials('public')
                    }
                    branches('*/master')
                }
            }

            sandbox()
            script('''
            buildPipeline {}
            ''')
        }
    }
}