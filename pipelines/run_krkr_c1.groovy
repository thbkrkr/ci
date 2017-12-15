pipelineJob('run_krkr_c1') {

    description('krkr/c1')

    triggers {
        bitbucketPush()
    }

    definition {
        cps {
            properties {
                githubProjectUrl('git@bitbucket.org:thb/c1.git')
            }

            logRotator {
                numToKeep(20)
                daysToKeep(30)
            }

            scm {
                git {
                    remote {
                        url('git@bitbucket.org:thb/c1.git')
                        credentials('ci-bitbucket.id_rsa')
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