pipelineJob('build_krkr_ci') {

    description('krkr/ci')

    triggers {
        githubPush()
    }

    definition {
        cps {
            properties {
                githubProjectUrl('https://github.com/thbkrkr/ci.git')
            }

            logRotator {
                numToKeep(20)
                daysToKeep(30)
            }

            scm {
                git {
                    remote {
                        url('https://github.com/thbkrkr/ci.git')
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