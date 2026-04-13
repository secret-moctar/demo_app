pipeline {
    agent any

    environment {
        APP_NAME = "demo_app"
        JAR_FILE = "target/demo_app.jar"
        ANSIBLE_INVENTORY = "ansible/inventory.ini"
        ANSIBLE_PLAYBOOK = "ansible/deploy.yml"
    }

    tools {
        maven 'Maven3'   // configure this in Jenkins
        jdk 'JDK17'      // configure this too
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/secret-moctar/demo_app'
            }
        }

        stage('Build Spring Boot') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Prepare Deployment') {
            steps {
                sh '''
                cp ${JAR_FILE} ansible/
                '''
            }
        }

        stage('Run Ansible Deploy') {
            steps {
                sh '''
                ansible-playbook -i ${ANSIBLE_INVENTORY} ${ANSIBLE_PLAYBOOK}
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful!"
        }
        failure {
            echo "❌ Deployment failed!"
        }
    }
}