node{
	stage('Checkout'){
		checkout scm 'https://github.com/mtalhajamil/mvn-demo-project'
	}
} 
pipeline {
    agent any
    stages { 
        stage('Build') {
        	agent any
            steps { 
               echo 'maven build after this.'
               sh 'docker run -it --rm --name ./ -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean install' 
            }
        }
    }
}
