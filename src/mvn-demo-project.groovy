pipeline {
    agent any
    stages {
    	stage('Checkout'){
    		steps{
    			sh 'pwd'
    			sh 'ls -l'
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
    			sh 'pwd'
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
