node{
	checkout scm https://github.com/mtalhajamil/mvn-demo-project
}
pipeline { 
    agent any  
    stages { 
        stage('Build') { 
            steps { 
               echo 'This is a minimal pipeline.' 
            }
        }
    }
}