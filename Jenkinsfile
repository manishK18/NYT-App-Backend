pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo "Build stage starting..."
                sh "mvn clean package -DskipTests"
                echo "Build stage completed..."
            }
        }
        stage('Test') {
            steps {
                echo "Test stage starting..."
                sh "mvn -Dspring.profiles.active=test -DnytApiKey=${env.NYT_API_KEY} -DguardianApiKey=${env.GUARDIAN_API_KEY} test"
                echo "Test stage completed..."
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploy stage starting..."
                sh 'docker-compose --env-file=../.env up --build -d'
                echo "Deployment completed..."
            }
        }
    }
}
