freeStyleJob('deploy_${name}') {
    displayName('deploy_${name}')
    description('${description}')

    properties {
        githubProjectUrl('${repo}')
    }

    logRotator {
        numToKeep(20)
        daysToKeep(30)
    }

    scm {
        git {
            remote {
                url('${origin}')
                credentials('${credentials}')
            }
            branches('*/${branch}')
        }
    }

    triggers {
        upstream('${upstream}')
    }

    steps {
        shell('${make_cmd}')
    }
}