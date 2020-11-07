def giturl = 'https://https://github.com/RomanOvsyannikov/d323dsl'
job ("MNTLAB-rovsyannikov-main-build-job") {
 parameters {
        activeChoiceParam('BRANCH_NAME') {
            description('Choose branch')
            choiceType('SINGLE_SELECT')
            groovyScript {
              script("""("git ls-remote -h ${giturl}").execute().text.readLines().collect {
                      it.split()[1].replaceAll(\'refs/heads/\', \'\')}.sort()""")
            }
        }
        activeChoiceParam('Next_job') {
            description('Choose job')
            choiceType('CHECKBOX')
            groovyScript {
                script('''return ['MNTLAB-rovsyannikov-child1-build-job',
                            'MNTLAB-rovsyannikov-child2-build-job',
                            'MNTLAB-rovsyannikov-child3-build-job',
                            'MNTLAB-rovsyannikov-child4-build-job']''')
            }
        }
    }

 steps {
        downstreamParameterized {
            trigger('$Next_job') {
                block {
                    buildStepFailure('FAILURE')
                    failure('FAILURE')
                    unstable('UNSTABLE')
                }
                parameters {
                    predefinedProp('BRANCH_NAME', '$BRANCH_NAME')
                }
            }
        }
    }
}

for (i in (1..4)) {
    job("MNTLAB-rovsyannikov-child${i}-build-job") {
         parameters {
            stringParam('BRANCH_NAME')
        }
    }
}
