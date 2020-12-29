pipeline {
    agent {
        label 'Slave_Mac'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        disableConcurrentBuilds()
    }

    tools {
        jdk 'JDK8_Mac'
    }

    stages{
        stage('Checkout'){
            steps{
                echo "------------>Checkout<------------"
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHub_JohnMendozaC', url:'https://github.com/JohnMendozaC/ParkingApp.git']]])
                sh 'chmod +x ./gradlew'
            }
        }


	    stage('Build') {
            steps{
				echo "------------>Build<------------"
                    sh './gradlew clean build'
            }
        }

		stage('Unit Tests') {
			steps{
				echo "------------>Unit Tests<------------"
					sh './gradlew --b build.gradle test --scan'
					sh './gradlew --b build.gradle jacocoTestReport'
			}
		}

        stage('Static Code Analysis') {
            steps{
				echo "------------>Static Code Analysis<------------"
                withSonarQubeEnv('Sonar'){
                    sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
                }
            }
        }
    }

    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
            junit '**/test-results/testDebugUnitTest/*.xml'
        }
        failure {
            echo 'This will run only if failed'
            mail(to: 'john.mendoza@ceiba.com.co',
                subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                body: "Something is wrong with ${env.BUILD_URL}")
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}