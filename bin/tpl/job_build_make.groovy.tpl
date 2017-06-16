freeStyleJob('build_${name}') {
    displayName('build_${name}')
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
        ${gitProvider}Push()
    }

    steps {
        shell('${make_cmd}')
    }
}