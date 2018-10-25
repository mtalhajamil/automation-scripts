pipeline {
    agent any
    options {
        skipDefaultCheckout()
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        CHECKOUT_DIR = ""
    }
    stages {
        stage('Checkout') {
            steps {
                sh 'pwd'
                // checkout([$class: 'GitSCM',
                //    branches: [[name: '*/master']],
                //    doGenerateSubmoduleConfigurations: false,
                //    extensions: [
                //        [$class: 'SparseCheckoutPaths',  sparseCheckoutPaths:[[$class:'SparseCheckoutPath', path:'mvn-demo-project/*']]]
                //                ],
                //    submoduleCfg: [],
                //    userRemoteConfigs: [[credentialsId: 'mtalhajamil',
                //    url: 'https://github.com/mtalhajamil/mvn-demo-project']]])

                //git url: 'https://github.com/mtalhajamil/mvn-demo-project'


                dir("code") {
                    checkout([$class           : 'GitSCM',
                              userRemoteConfigs: [[name: 'mtalhajamil',
                                                   url : 'https://github.com/mtalhajamil/mvn-demo-project']],
                              branches         : [[name: "master"]],
                              browser          : [$class: 'GithubWeb', repoUrl: 'https://github.com/mtalhajamil/mvn-demo-project'],
                              extensions       : [
                                      [$class: 'CloneOption', honorRefspec: true, noTags: true, reference: '../.git', shallow: true],
                                      [$class: 'LocalBranch', localBranch: "master"],
                              ],
                    ])
                }

                script{
                    CHECKOUT_DIR = WORKSPACE + "/code"
                }
                sh 'pwd'
            }

        }
        stage('Build') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'pwd'
                sh 'ls -l'
                echo 'maven build after this.'
                echo CHECKOUT_DIR
                dir(CHECKOUT_DIR){
                    sh 'mvn package -e -X'
                }
                //sh 'docker run -i --rm --name ./ -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven mvn package -f -e -X'
            }
        }
    }
}
