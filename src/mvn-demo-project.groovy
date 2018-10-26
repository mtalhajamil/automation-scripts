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
            steps {
                CHECKOUT_DIR = sh 'echo ' + CHECKOUT_DIR + ' | sed \'s|/var/jenkins_home|/jenkins-data|g\''
                //sh 'str=$(echo ' + CHECKOUT_DIR + ' | sed \'s|/var/jenkins_home|/jenkins-data|g\')'
                sh 'docker rm jenkins_tomcat'
                sh 'docker run -d -e 8080 -p 8080:8080 -v ' + CHECKOUT_DIR + '/target/:/usr/local/tomcat/webapps/ --name jenkins_tomcat tomcat:8'
                sh 'sleep 1'
                sh 'echo $! > .pidfile'
            }
        }
    }
}
