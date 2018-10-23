import jenkins.model.*
jenkins = Jenkins.instance

node{
	stage('Checkout'){
		dir(builddir) {
	    	def checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: false, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/mtalhajamil/mvn-demo-project']]])
	    }
	}
} 
pipeline {
    agent any
    stages { 
        stage('Build') {
        	agent any
            steps { 
               echo 'maven build after this.'
               sh 'docker run -i --rm --name ./ -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven mvn package -f -e -X' 
            }
        }
    }
}
