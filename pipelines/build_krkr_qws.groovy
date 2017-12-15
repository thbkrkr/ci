pipelineJob('build_krkr_qws') {

    description('krkr/qws')

    triggers {
        githubPush()
    }

    definition {
        cps {
            properties {
                githubProjectUrl('https://github.com/thbkrkr/qli.git')
            }

            logRotator {
                numToKeep(20)
                daysToKeep(30)
            }

            scm {
                git {
                    remote {
                        url('https://github.com/thbkrkr/qli.git')
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