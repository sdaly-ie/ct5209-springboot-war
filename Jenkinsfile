pipeline {
    agent any

    stages {
        stage('GetProject') {
            steps {
                git branch: 'main', url: 'https://github.com/sdaly-ie/ct5209-springboot-war.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -DskipTests package'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts allowEmptyArchive: true, artifacts: '**/stephenspetitions*.war'
            }
        }

        stage('ApproveDeploy') {
            steps {
                input message: 'Deploy stephenspetitions.war?', ok: 'Deploy'
            }
        }
    }
}