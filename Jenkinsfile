pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-access-token-for-jenkins')
    }

    stages {
        stage("Clone Real Time Messaging API from Github") {
            steps {
                echo "Cloning Real Time Messaging API from Github. Please Wait..."
                git branch: 'main',
                    url: 'https://github.com/Elleined/rt-messaging-api'
                echo "Cloning Real Time Messaging API from Github. Success!"
            }
        }

        stage("Create Docker Image") {
            steps {
                echo "Creating docker image. Please Wait..."
                sh 'docker build -t elleined/rt-messaging-api:latest .'
                echo "Creating docker image. Success!"
            }
        }

        stage("Log in to DockerHub") {
            steps {
                echo "Logging in to DockerHub. Please Wait..."
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                echo "Logging in to DockerHub. Success!"
            }
        }

        stage("Push docker image to DockerHub") {
            steps {
                echo "Pushing docker image to DockerHub. Please Wait..."
                sh 'docker push elleined/rt-messaging-api:latest'
                echo "Pushing docker image to DockerHub. Success!"
            }
        }

    } // End of Stages

    post {
        always {
            sh 'docker logout'
        }
    }

} // End of Pipeline