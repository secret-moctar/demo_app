pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/secret-moctar/demo_app'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test' 
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package' 
            }
        }
        
    }
    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}