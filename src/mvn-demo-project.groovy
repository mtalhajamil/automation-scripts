pipeline {
    agent any
    stages {
    	stage('Checkout'){
    		steps{
    			echo 'checkout here'
    			sh 'mkdir -p mvn-demo-project'
				checkout([$class: 'GitSCM', 
			    branches: [[name: '*/master']],
			    doGenerateSubmoduleConfigurations: false,
			    extensions: [
			        [$class: 'SparseCheckoutPaths',  sparseCheckoutPaths:[[$class:'SparseCheckoutPath', path:'/mvn-demo-project']]]
			                ],
			    submoduleCfg: [],
			    userRemoteConfigs: [[credentialsId: 'mtalhajamil',
			    url: 'https://github.com/mtalhajamil/mvn-demo-project']]])
    			sh 'ls -l'
		    }
			
		}
        stage('Build') {
            steps { 
               echo 'maven build after this.'
               //sh 'docker run -i --rm --name ./ -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven mvn package -f -e -X' 
            }
        }
    }
}
