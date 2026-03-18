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
                input message: 'Deploy Docker container to EC2?', ok: 'Deploy'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/CT5169.pem ubuntu@13.49.44.175 "mkdir -p /home/ubuntu/stephenspetitions-docker"

                    scp -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/CT5169.pem Dockerfile ubuntu@13.49.44.175:/home/ubuntu/stephenspetitions-docker/Dockerfile

                    scp -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/CT5169.pem target/stephenspetitions.war ubuntu@13.49.44.175:/home/ubuntu/stephenspetitions-docker/stephenspetitions.war

                    ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/CT5169.pem ubuntu@13.49.44.175 "
                        sudo systemctl stop tomcat10 || true
                        sudo systemctl disable tomcat10 || true
                        cd /home/ubuntu/stephenspetitions-docker
                        sudo docker rm -f stephenspetitions || true
                        sudo docker build -t stephenspetitions:latest .
                        sudo docker run -d --name stephenspetitions -p 9090:8080 stephenspetitions:latest
                    "
                '''
            }
        }
    }
}