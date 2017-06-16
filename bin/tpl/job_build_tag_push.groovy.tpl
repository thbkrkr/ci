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
        shell('doo b')
        shell('doo t')
        shell('doo p')
        shell('docker rmi $(docker images --filter dangling=true -q 2>/dev/null) 2> /dev/null || true')
    }
}