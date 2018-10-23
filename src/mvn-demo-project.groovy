pipeline { 
	node{
		stage('Checkout'){
			checkout scm 'https://github.com/mtalhajamil/mvn-demo-project'
		}
	}  
    stages { 
        stage('Build') { 
            steps { 
               echo 'This is a minimal pipeline.' 
            }
        }
    }
}
