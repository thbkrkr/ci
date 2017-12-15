pipelineJob('build_krkr_logz') {

    description('krkr/logz')

    triggers {
        githubPush()
    }

    definition {
        cps {
            properties {
                githubProjectUrl('https://github.com/thbkrkr/logz.git')
            }

            logRotator {
                numToKeep(20)
                daysToKeep(30)
            }

            scm {
                git {
                    remote {
                        url('https://github.com/thbkrkr/logz.git')
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