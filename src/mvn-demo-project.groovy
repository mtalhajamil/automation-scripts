node{
	stage('Checkout'){
		checkout scm 'https://github.com/mtalhajamil/mvn-demo-project'
	}
    def proc = 'ls /badDir'.execute()
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