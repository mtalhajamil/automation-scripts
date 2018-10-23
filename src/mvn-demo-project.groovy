import jenkins.model.*
jenkins = Jenkins.instance

pipeline {
	node{
		stage('Checkout'){
			dir(builddir) {
		    	checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: false, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/mtalhajamil/mvn-demo-project']]])
		    }
		}
	} 
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
