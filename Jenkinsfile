pipeline {
    agent any
    environment {
        IMAGE_NAME = 'ziyatbek-dinmukhamed-student-api'
        IMAGE_TAG = 'v1.0-ziyatbek-dinmukhamed'
    }
    options { timestamps(); disableConcurrentBuilds() }
    stages {
        stage('Checkout') {
            steps {
                echo 'Student: Ziyatbek Dinmukhamed'
                checkout scm
            }
        }
        stage('Build') {
            steps { sh 'gradle --no-daemon clean bootJar' }
        }
        stage('Test') {
            steps { sh 'gradle --no-daemon test' }
            post { always { junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml' } }
        }
        stage('Docker Build') {
            steps { sh 'docker build -t "$IMAGE_NAME:$IMAGE_TAG" .' }
        }
        stage('Verify') {
            steps {
                sh 'docker image inspect "$IMAGE_NAME:$IMAGE_TAG" >/dev/null'
                sh 'docker image ls "$IMAGE_NAME:$IMAGE_TAG"'
                echo 'Verified Docker image for Ziyatbek Dinmukhamed'
            }
        }
    }
    post {
        success { echo 'Pipeline completed successfully for Ziyatbek Dinmukhamed.' }
        failure { echo 'Pipeline failed. Review the stage logs above; later stages are skipped.' }
    }
}
