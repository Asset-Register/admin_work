pipeline {
    agent any

tools {
        maven 'Maven' // This should match the name you gave in Global Tool Configuration
        jdk   'JAVA'
    }

    environment {
        REPO_URL = 'https://github.com/Asset-Register/admin_work.git'
        IMAGE_NAME = 'adminservertest'
        DOCKER_REGISTRY = 'localhost:5000' // Use 'localhost' if local
        CONTAINER_PORT = '8082'
        CONTAINER_NAME = "adminserver-${env.BUILD_ID}"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git credentialsId: '3185006e-b5c6-4e1e-8bff-0e457e03c3aa', branch: 'security', url: "${REPO_URL}"
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    bat 'mvn clean package' // For Java projects
                }
                echo 'Application build successful!'
            }
        }

       stage('Set Image Tag') {
                           steps {
                               script {
                                   // Use Jenkins build number as the version
                                   env.IMAGE_TAG = "v${BUILD_NUMBER}"
                                   echo "Image tag set to: ${env.IMAGE_TAG}"
                               }
                           }
       }


        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t ${IMAGE_NAME}:${env.IMAGE_TAG} ."
                }
                echo 'Docker image built successfully.'
            }
        }

        /* stage('Push Docker Image') {
            steps {
                script {
                    bat "docker tag ${IMAGE_NAME}:${env.IMAGE_TAG} ${IMAGE_NAME}:${env.IMAGE_TAG}"
                    bat "docker run -d -p ${CONTAINER_PORT}:${CONTAINER_PORT} --name ${CONTAINER_NAME} ${IMAGE_NAME}"
                }
                echo 'Docker image pushed successfully.'
            }
        } */
    }

    post {
        always {
            echo 'Pipeline execution completed.'
        }
    }
}