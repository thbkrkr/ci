pipelineJob('build_krkr_toctoc') {

    description('krkr/toctoc')

    triggers {
        githubPush()
    }

    definition {
        cps {
            properties {
                githubProjectUrl('https://github.com/thbkrkr/toctoc.git')
            }

            logRotator {
                numToKeep(20)
                daysToKeep(30)
            }

            scm {
                git {
                    remote {
                        url('https://github.com/thbkrkr/toctoc.git')
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