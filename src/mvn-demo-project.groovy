pipeline {
    agent any
    stages {
    	stage('Checkout'){
    		steps{
    			echo 'checkout here'
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
    // 			sh 'ls -l'

    			git url: 'https://github.com/mtalhajamil/mvn-demo-project'
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
