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

        stage('Deploy') {
            steps {
                sh '''
                scp -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/CT5169.pem target/stephenspetitions.war ubuntu@13.49.44.175:/home/ubuntu/stephenspetitions.war

                ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/CT5169.pem ubuntu@13.49.44.175 "
                sudo systemctl stop tomcat10 &&
                sudo rm -rf /var/lib/tomcat10/webapps/stephenspetitions &&
                sudo rm -f /var/lib/tomcat10/webapps/stephenspetitions.war &&
                sudo cp /home/ubuntu/stephenspetitions.war /var/lib/tomcat10/webapps/ &&
                sudo systemctl start tomcat10
                "
                '''
            }
        }
    }
}