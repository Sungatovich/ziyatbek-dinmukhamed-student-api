pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 20, unit: 'MINUTES')
    }

    environment {
        IMAGE_NAME = 'ziyatbek-dinmukhamed-student-api'
        IMAGE_TAG = 'v1.0-ziyatbek-dinmukhamed'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Student: Ziyatbek Dinmukhamed'
                checkout scm
                sh 'git log -1 --oneline'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew --no-daemon clean bootJar'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew --no-daemon test'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
            }
        }

        stage('Verify') {
            steps {
                sh 'docker image inspect ${IMAGE_NAME}:${IMAGE_TAG} > /dev/null'
                sh 'echo "Docker image verified successfully."'
            }
        }
    }

    post {
        success {
            echo 'CI pipeline completed successfully for Ziyatbek Dinmukhamed.'
        }
        failure {
            echo 'Pipeline failed. Check the stage logs.'
        }
    }
}
