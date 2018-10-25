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
                dir(CHECKOUT_DIR){
                    sh 'mvn package -e -X -DskipTests'
                }
            }
        }
        stage('Run war on tomcat') {
//            agent {
//                docker {
//                    image 'tomcat:8'
//                    args '-v $HOME/.m2:/root/.m2'
//                }
//            }
            steps {
                echo CHECKOUT_DIR + '/target/mvnproject-0.0.1-SNAPSHOT'
                sh 'docker run -i --rm -e 8080 -p 8080:8080 -v ' + CHECKOUT_DIR + '/target/:/usr/local/tomcat/webapps/ tomcat:8'
            }
        }
    }
}
